package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.apache.camel.*;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.servlet.ServletEndpoint;
import org.apache.camel.spi.BrowsableEndpoint;

import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.test.spring.CamelTestContextBootstrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.util.parsing.combinator.testing.Str;

import java.util.*;

import static com.mrigor.tasks.department.DepTestData.*;

/**
 * Created by Igor on 24.03.2017.
 */
/*@RunWith(CamelSpringRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)*/
@ContextConfiguration({

                "classpath:spring/camel-config.xml",
                "classpath:spring/spring-db.xml"
                })
//@BootstrapWith(CamelTestContextBootstrapper.class)
@RunWith(CamelSpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentRouteConfig2Test extends CamelSpringTestSupport {





    @Test
   // @DirtiesContext
    public void testGetAll() throws Exception {

        assertTrue(checkRestUrl("/rest/departments", "GET"));
        List<Department> list = (List<Department>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), DEPS.toString());
    }

    @Test
   // @DirtiesContext
    public void testGetAllWithSalary() throws Exception {

        assertTrue(checkRestUrl("/rest/departments/withAvgSalary", "GET"));
        List<DepartmentWithAverageSalary> list = (List<DepartmentWithAverageSalary>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), DEPS.toString());
    }


    @Test
  //  @DirtiesContext
    public void testById() throws Exception {
        assertTrue(checkRestUrl("/rest/departments/{id}", "GET"));
        Department dep = template.requestBodyAndHeader("direct:getDepartment", "", "id", 100000, Department.class);
        assertEquals(dep.toString(), DEP1.toString());

    }

    @Test()
  //  @DirtiesContext
    public void testByIdNotFound() throws Exception {
        String error = template.requestBodyAndHeader("direct:getDepartment", "", "id", 10000, String.class);
        assertEquals(error, "Not found entity id=10000");

    }

    @Test
   // @DirtiesContext
    public void testDelete() throws Exception {
        assertTrue(checkRestUrl("/rest/departments/{id}", "DELETE"));
        template.requestBodyAndHeader("direct:deleteDepartment", "", "id", 100000, Department.class);
        List<Department> list = (List<Department>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), Collections.singletonList(DEP2).toString());
    }

    @Test
  //  @DirtiesContext
    public void testUpdate() throws Exception {
        assertTrue(checkRestUrl("/rest/departments", "PUT"));

        Processor p = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Map<String,Object> m=new HashMap<>();
                m.put("id", getUpdated().getId());
                m.put("name", getUpdated().getName());
                exchange.getIn().setHeaders(m);
             //   exchange.getIn().setHeader("id", getUpdated().getId());
             //   exchange.getIn().setHeader("name", getUpdated().getName());
            }
        };
        template.send("direct:updateDepartment", p);

        Department dep = template.requestBodyAndHeader("direct:getDepartment", "", "id", 100000, Department.class);
        assertEquals(dep.toString(), getUpdated().toString());
    }


    @Test
   // @DirtiesContext
    public void testCreate() throws Exception {
        assertTrue(checkRestUrl("/rest/departments", "POST"));
        Department expected = getCreated();
        //template.requestBody("direct:createDepartment",expected);

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




