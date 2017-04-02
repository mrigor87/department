package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.servlet.ServletEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mrigor.tasks.department.DepTestData.DEP1_ID;
import static com.mrigor.tasks.department.DepTestData.DEP2;
import static com.mrigor.tasks.department.DepTestData.DEP2_ID;
import static com.mrigor.tasks.department.EmployeeTestData.*;

/**
 * Created by igor on 002 02.04.17.
 */
public class EmployeeRouteConfigTest extends CamelSpringTestSupport {

    @Test
    @DirtiesContext
    public void testGetAll() throws Exception {
        assertTrue(checkRestUrl("/rest/employees", "GET"));
        List<Employee> list = (List<Employee>) template.requestBody("direct:getAllEmployees", "");
        assertEquals(list.toString(), EMPL_ALL.toString());
    }




    @Test
    @DirtiesContext
    public void testById() throws Exception {
        assertTrue(checkRestUrl("/rest/employees/{id}", "GET"));
        Employee empl = template.requestBodyAndHeader("direct:getEmployee", "", "id", EMPL1_ID, Employee.class);
        assertEquals(empl.toString(), EMPL1.toString());
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {
        assertTrue(checkRestUrl("/rest/employees/{id}", "DELETE"));
        template.requestBodyAndHeader("direct:deleteEmployee", "", "id", EMPL1_ID, Employee.class);
        List<Employee> list = (List<Employee>) template.requestBody("direct:getAllEmployees", "");

        assertEquals(list.toString(), Arrays.asList (EMPL5, EMPL4, EMPL2, EMPL3).toString());
    }



    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        assertTrue(checkRestUrl("/rest/employees", "PUT"));

        Employee updated = getUpdated();
        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader("id", updated.getId());
                exchange.getIn().setHeader("birthDay", updated.getBirthDay().toString());
                exchange.getIn().setHeader("departmentId", updated.getDepartmentId());
                exchange.getIn().setHeader("fullName", updated.getFullName());
                exchange.getIn().setHeader("salary", updated.getSalary());
            }
        };
        template.send("direct:updateEmployee", p);

        Employee dep = template.requestBodyAndHeader("direct:getEmployee", "", "id", updated.getId(), Employee.class);
        assertEquals(dep.toString(), getUpdated().toString());
    }




    @Test
    @DirtiesContext
    public void testCreate() throws Exception {
        assertTrue(checkRestUrl("/rest/employees", "POST"));
        Employee expected = getCreated();
        //template.requestBody("direct:createDepartment",expected);

        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader("id", expected.getId());
                exchange.getIn().setHeader("birthDay", expected.getBirthDay().toString());
                exchange.getIn().setHeader("departmentId", expected.getDepartmentId());
                exchange.getIn().setHeader("fullName", expected.getFullName());
                exchange.getIn().setHeader("salary", expected.getSalary());
            }
        };
        Exchange request = template.request("direct:createEmployee", p);
        Employee dep = request.getIn().getBody(Employee.class);
        List<Employee> list =(List<Employee>) template.requestBodyAndHeader("direct:getEmployeesByDepartment", "", "id", DEP1_ID);
        assertEquals(list.toString(), Arrays.asList(EMPL1,dep,EMPL2,EMPL3).toString());


    }
/*    private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITH_DEP_SQL = "SELECT * FROM EMPLOYEES  " +
            " WHERE (BIRTHDAY BETWEEN  :#from AND :#to) AND department_id=:#departmentid";*/

    @Test
    @DirtiesContext
    public void testGetBetween() throws Exception {
        assertTrue(checkRestUrl("/rest/employees/filtered", "GET"));
      //  Employee expected = getCreated();
        //template.requestBody("direct:createDepartment",expected);

        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader("from", "1993-01-01");
                exchange.getIn().setHeader("departmentid", DEP2_ID);

            }
        };
        Exchange request = template.request("direct:getFilteredEmployee", p);
        List<Employee> list = (List<Employee>) request.getIn().getBody();
       // List<Employee> list =(List<Employee>) template.requestBodyAndHeader("direct:getEmployeesByDepartment", "", "id", DEP1_ID);
        assertEquals(list.toString(), EMPL_D2.toString());


    }


    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return
                new ClassPathXmlApplicationContext(
                        "classpath:spring/camel-config.xml",
                        "classpath:spring/spring-db.xml"
                );
    }


    private boolean checkRestUrl(String url, String method) {
        return context().getEndpoints().stream()
                .filter(e -> e instanceof ServletEndpoint)
                .map(e -> (ServletEndpoint) e)
                .filter(e -> e.getContextPath().equals(url) && e.getHttpMethodRestrict().equals(method))
                .count()
                > 0;
    }
}
