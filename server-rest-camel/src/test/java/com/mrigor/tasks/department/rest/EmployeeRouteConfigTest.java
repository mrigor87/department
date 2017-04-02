package com.mrigor.tasks.department.rest;

import org.apache.camel.component.servlet.ServletEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by igor on 002 02.04.17.
 */
public class EmployeeRouteConfigTest extends CamelSpringTestSupport {









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
