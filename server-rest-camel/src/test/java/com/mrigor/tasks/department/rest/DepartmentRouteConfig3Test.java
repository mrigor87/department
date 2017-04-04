package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.apache.camel.*;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static com.mrigor.tasks.department.DepTestData.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.mrigor.tasks.department.rest.util.TestHelper.checkRestUrl;

/**
 * Created by Igor on 24.03.2017.
 */
@ContextConfiguration({

                "classpath:spring/camel-config.xml",
                "classpath:spring/spring-db.xml"
                })
@RunWith(CamelSpringJUnit4ClassRunner.class)

@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentRouteConfig3Test  {//extends CamelSpringTestSupport

    @Autowired
    protected CamelContext context;

    @Produce()
    protected ProducerTemplate template;


    @Test
    public void testGetAll() throws Exception {
        assertTrue(checkRestUrl("/rest/departments", "GET",context));
        List<Department> list = (List<Department>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), DEPS.toString());
    }

    @Test
    public void testGetAllWithSalary() throws Exception {
        assertTrue(checkRestUrl("/rest/departments/withAvgSalary", "GET",context));
        List<DepartmentWithAverageSalary> list = (List<DepartmentWithAverageSalary>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), DEPS.toString());
    }


    @Test
    public void testById() throws Exception {
        assertTrue(checkRestUrl("/rest/departments/{id}", "GET",context));
        Department dep = template.requestBodyAndHeader("direct:getDepartment", "", "id", 100000, Department.class);
        assertEquals(dep.toString(), DEP1.toString());

    }

    @Test()
    public void testByIdNotFound() throws Exception {
        String error = template.requestBodyAndHeader("direct:getDepartment", "", "id", 10000, String.class);
        assertEquals(error, "Not found entity id=10000");
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(checkRestUrl("/rest/departments/{id}", "DELETE",context));
        template.requestBodyAndHeader("direct:deleteDepartment", "", "id", 100000, Department.class);
        List<Department> list = (List<Department>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), Collections.singletonList(DEP2).toString());
    }

    @Test

    public void testUpdate() throws Exception {
        assertTrue(checkRestUrl("/rest/departments", "PUT",context));

        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Map<String,Object> m=new HashMap<>();
                m.put("id", getUpdated().getId());
                m.put("name", getUpdated().getName());
                exchange.getIn().setHeaders(m);
            }
        };
        template.send("direct:updateDepartment", p);

        Department dep = template.requestBodyAndHeader("direct:getDepartment", "", "id", 100000, Department.class);
        assertEquals(dep.toString(), getUpdated().toString());
    }


    @Test
    //@DirtiesContext
    public void testCreate() throws Exception {

        assertTrue(checkRestUrl("/rest/departments", "POST",context));
        Department expected = getCreated();

        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeader("id", expected.getId());
                exchange.getIn().setHeader("name", expected.getName());
            }
        };
        Exchange request = template.request("direct:createDepartment", p);
        Department dep = request.getIn().getBody(Department.class);

        List<Department> list = (List<Department>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), Arrays.asList(DEP1, dep, DEP2).toString());

    }




}




