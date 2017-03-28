package com.mrigor.tasks.department.rest;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.test.spring.CamelTestContextBootstrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Igor on 24.03.2017.
 */
@RunWith(CamelSpringRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration({

                "classpath:spring/camel-config.xml",
                "classpath:spring/spring-db.xml"
                })
public class DepartmentRouteConfigTest extends CamelSpringTestSupport{



    @EndpointInject(uri = "mock:sql:*")
    MockEndpoint mockSql;

    @Autowired
    protected CamelContext camelContext;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @EndpointInject(uri = "mock:foo")
    protected MockEndpoint foo;

    @EndpointInject(uri = "mock:foo")
    protected MockEndpoint dep;

    @Produce(uri = "direct:getAllDepartments")
    protected ProducerTemplate testProducer;



    @Test
    @DirtiesContext
    public void testMocksAreValid() throws Exception {

        camelContext.getEndpoint("direct:getAllDepartments").createConsumer(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn();
            }
        });
camelContext.getRoute("direct://getAllDepartments").getProperties();

        //camelContext.getEndpointMap()

       // MockEndpoint.assertIsSatisfied(camelContext);
        // ...
       // from("direct:start").to("mock:Service?q=test");
       // String ret = testProducer.requestBody("<hello/>", String.class);

        foo.message(0).header("bar").isEqualTo("ABC");
       // MockEndpoint.assertIsSatisfied(camelContext);
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return null;
    }
}




