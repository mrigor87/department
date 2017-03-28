package com.mrigor.tasks.department.rest;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by Igor on 24.03.2017.
 */
public class TestTest extends CamelTestSupport {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new DepartmentRouteConfig();
    }

    @Test
    public  void test() throws Exception{
        //template = context.createProducerTemplate();
        //template.send("direct://getDepartment",);
    }
}
