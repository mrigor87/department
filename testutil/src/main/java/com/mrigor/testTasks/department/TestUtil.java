package com.mrigor.testTasks.department;

import org.springframework.test.web.servlet.ResultActions;
import java.io.UnsupportedEncodingException;

/**
 * util class for testing (MocMvc)
 */
public class TestUtil {

    public static ResultActions printContent(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return  action.andReturn().getResponse().getContentAsString();
    }
}
