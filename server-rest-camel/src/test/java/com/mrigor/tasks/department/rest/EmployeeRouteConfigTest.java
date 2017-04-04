package com.mrigor.tasks.department.rest;


import com.mrigor.tasks.department.model.Employee;
import org.apache.camel.*;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static com.mrigor.tasks.department.DepTestData.DEP1_ID;
import static com.mrigor.tasks.department.DepTestData.DEP2_ID;
import static com.mrigor.tasks.department.EmployeeTestData.*;
import static com.mrigor.tasks.department.rest.util.TestHelper.checkRestUrl;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by igor on 002 02.04.17.
 */
@ContextConfiguration({

        "classpath:spring/camel-config.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(CamelSpringJUnit4ClassRunner.class)

@Sql(scripts = "classpath:db/populateDB.sql")
public class EmployeeRouteConfigTest  {
    @Autowired
    protected CamelContext context;

    @Produce()
    protected ProducerTemplate template;


    @Test
    public void testGetAll() throws Exception {
        assertTrue(checkRestUrl("/rest/employees", "GET",context));
        List<Employee> list = (List<Employee>) template.requestBody("direct:getAllEmployees", "");
        assertEquals(list.toString(), EMPL_ALL.toString());
    }




    @Test
    public void testById() throws Exception {
        assertTrue(checkRestUrl("/rest/employees/{id}", "GET",context));
        Employee empl = template.requestBodyAndHeader("direct:getEmployee", "", "id", EMPL1_ID, Employee.class);
        assertEquals(empl.toString(), EMPL1.toString());
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(checkRestUrl("/rest/employees/{id}", "DELETE",context));
        template.requestBodyAndHeader("direct:deleteEmployee", "", "id", EMPL1_ID, Employee.class);
        List<Employee> list = (List<Employee>) template.requestBody("direct:getAllEmployees", "");

        assertEquals(list.toString(), Arrays.asList (EMPL5, EMPL4, EMPL2, EMPL3).toString());
    }



    @Test
    public void testUpdate() throws Exception {
        assertTrue(checkRestUrl("/rest/employees", "PUT",context));

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
    public void testCreate() throws Exception {
        assertTrue(checkRestUrl("/rest/employees", "POST",context));
        Employee expected = getCreated();
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

    @Test
    public void testGetBetween() throws Exception {
        assertTrue(checkRestUrl("/rest/employees/filtered", "GET",context));
        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader("from", "1993-01-01");
                exchange.getIn().setHeader("departmentid", DEP2_ID);

            }
        };
        Exchange request = template.request("direct:getFilteredEmployee", p);
        List<Employee> list = (List<Employee>) request.getIn().getBody();
        assertEquals(list.toString(), EMPL_D2.toString());
    }

    @Test()
    public void testByIdNotFound() throws Exception {
        String error = template.requestBodyAndHeader("direct:getEmployee", "", "id", 10000, String.class);
        assertEquals(error, "Not found entity id=10000");
    }

}
