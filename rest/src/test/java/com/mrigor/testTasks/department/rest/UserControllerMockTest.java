package com.mrigor.testTasks.department.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrigor.testTasks.department.service.DepartmentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by juga on 23.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
      //  "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
public class UserControllerMockTest {

    @Resource
    private DepartmentController controller;

    private MockMvc mockMvc;

    @Autowired
    private DepartmentService service;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void name() throws Exception {
        System.out.printf("ff");

    }

    @Test
    public void addUserTest() throws Exception {


        mockMvc.perform(
                post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
      //          .content(user)
        ).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("3"));
    }

}
