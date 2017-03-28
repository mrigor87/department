package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.apache.camel.*;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.servlet.ServletEndpoint;
import org.apache.camel.spi.BrowsableEndpoint;
import org.apache.camel.test.spring.CamelSpringRunner;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.mrigor.tasks.department.DepTestData.DEPS;

/**
 * Created by Igor on 24.03.2017.
 */
/*@RunWith(CamelSpringRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration({

                "classpath:spring/camel-config.xml",
                "classpath:spring/spring-db.xml"
                })*/
public class DepartmentRouteConfig2Test extends CamelSpringTestSupport {


    @Override
    public String isMockEndpoints() {
        return "rest*";
    }


    @Test
    @DirtiesContext
    public void testGetAll1() throws Exception {
        assertNotNull(context.getEndpoint("servlet:/rest/departments?httpMethodRestrict=GET"));
        List<Department> list = (List<Department>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), DEPS.toString());
    }

    @Test
    @DirtiesContext
    public void testGetAllWithSalary() throws Exception {
        assertNotNull(context.getEndpoint("servlet:/rest/departments?httpMethodRestrict=GET"));
        List<DepartmentWithAverageSalary> list = (List<DepartmentWithAverageSalary>) template.requestBody("direct:getAllDepartments", "");
        assertEquals(list.toString(), DEPS.toString());
    }


    @Test
    @DirtiesContext
    public void testMocks() throws Exception {

        ServletEndpoint endpoint = (ServletEndpoint) context.getEndpoint("servlet:/rest/departments?httpMethodRestrict=GET");
        BrowsableEndpoint be = context.getEndpoint("servlet:/rest/departments?httpMethodRestrict=GET", BrowsableEndpoint.class);
        template.requestBody(endpoint, "");
        //  String qqq=endpoint.getHttpUri().;
        //  template.requestBody(http://localhost:8080/myapp/myserver)
        template.requestBody("servlet:/rest/departments?httpMethodRestrict=GET", "");
        Object o = template.requestBody(endpoint, "");
        //endpoint.
        //  getMockEndpoint("mock:rest:get:/rest/departments").expectedBodiesReceived(DEPS);
        // getMockEndpoint("servlet:/rest/departments?httpMethodRestrict=GET").expectedBodiesReceived(DEPS);
        //assertMockEndpointsSatisfied();
/*        Endpoint endpoint = context.getEndpoint("mock:rest:get:/rest/departments");
        Object o = template.requestBody(endpoint,"");
        System.out.println("");*/
/*        MockEndpoint mockEndpoint = getMockEndpoint("mock:foo");
        mockEndpoint.expectedMessageCount(1);
        template.sendBody("foo","ff");
        mockEndpoint.assertIsSatisfied();*/

    }

    @Test
    @DirtiesContext
    public void testMocksAreValid() throws Exception {

        Endpoint endpoint = context.getEndpoint("direct:getAllDepartments");

        //template.setDefaultEndpoint(endpoint);
        Object oo = new Object();
        Object o = template.requestBody(endpoint, "");
        // template.sendBody(endpoint,);

        // consumer.receiveBody("direct:getAllDepartments");


        // template.sendBody(endpoint);
        //   BrowsableEndpoint be=context.getEndpoint("activemq:queue:direct:getAllDepartments",BrowsableEndpoint.class);
/*        MockEndpoint mock=getMockEndpoint("mock:direct:getAllDepartments");
        Endpoint endpoint = context.getEndpoint("direct:getAllDepartments");
        template.sendBody(endpoint,"");
        Object resultProduct = mock.getExchanges().get(0).getIn().getBody(Object.class);*/
        // NotifyBuilder nb=new NotifyBuilder(context).whenDone(10).create();

/*        getMockEndpoint("mock:direct:getAllDepartments2").expectedBodiesReceived("k");
        template.sendBody("direct:getAllDepartments","dfsf");*/


/*        NotifyBuilder nb=new NotifyBuilder(context).from("direct:getAllDepartments").whenAnyDoneMatches(
                body().isEqualTo("fdsfsfd"))
                .create();*/


//BrowsableEndpoint be=context.getEndpoint("direct:getAllDepartments",BrowsableEndpoint.class);
        //MockEndpoint mock=getMockEndpoint("mock:foo");
/*        List<Department>departments=new ArrayList<>(DEPS);
        Endpoint endpoint = context.getEndpoint("direct:getAllDepartments");
        template.sendBody(endpoint,"");*/
        // BrowsableEndpoint e=context.getEndpoint("direct:getAllDepartments",BrowsableEndpoint.class);

        // context.getEndpoint("").geE

        //  List<Department> resultProduct1 = mock.getExchanges().get(0).getIn().getBody(List.class);
        // endpoint.

        //   List<Department> resultProduct = resultEndpoint.getExchanges().get(0).getIn().getBody(Department.class);
        //      assertEquals(expectedEANCode, resultProduct.getEANCode());
        // context.getEndpoint("\"direct:getAllDepartments\"").start();

//context.start();
        //body().
        // String content =context.getTypeConverter().convertTo(List<Department>).

    }


    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return
                new ClassPathXmlApplicationContext(
                        "classpath:spring/camel-config.xml",
                        "classpath:spring/spring-db.xml"
                );
    }
}




