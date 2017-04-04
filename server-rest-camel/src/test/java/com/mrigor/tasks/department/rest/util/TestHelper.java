package com.mrigor.tasks.department.rest.util;

import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.ServletEndpoint;

/**
 * Created by Igor on 04.04.2017.
 */
public class TestHelper {
    public static boolean checkRestUrl(String url, String method, CamelContext context) {
        return context.getEndpoints().stream()
                .filter(e -> e instanceof ServletEndpoint)
                .map(e -> (ServletEndpoint) e)
                .filter(e -> e.getContextPath().equals(url) && e.getHttpMethodRestrict().equals(method))
                .count()
                > 0;
    }
}
