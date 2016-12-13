package com.mrigor.testTasks.department.rest;


import com.mrigor.testTasks.department.service.DepartmentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.mrigor.testTasks.department.DepTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Igor on 13.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    DepartmentService service;

    private static final String REST_URL = DepartmentController.REST_URL + '/';

    protected MockMvc mockMvc;

    // private DepartmentController conroller;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    public void testGet() throws Exception {

        mockMvc.perform(get(REST_URL + DEP1_ID))
                .andExpect(status().isOk())
                .andDo(print())
// https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(DEP1));
    }

    //@Test
/*    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DEP1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Collections.singletonList(DEP2), service.getAll());
    }*/


}