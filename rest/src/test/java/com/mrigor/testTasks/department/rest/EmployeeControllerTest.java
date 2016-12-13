package com.mrigor.testTasks.department.rest;

import com.mrigor.testTasks.department.service.EmployeeService;
import com.mrigor.testTasks.department.TestUtil;
import com.mrigor.testTasks.department.matcher.JsonUtil;
import com.mrigor.testTasks.department.model.Employee;



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
import org.springframework.web.context.WebApplicationContext;

import static com.mrigor.testTasks.department.EmployeeTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Игорь on 13.12.2016.
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
public class EmployeeControllerTest{
        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        EmployeeService service;

        private static final String REST_URL = EmployeeController.REST_URL + '/';

        protected MockMvc mockMvc;

        @Before
        public void setUp() {
            mockMvc = MockMvcBuilders
                    .webAppContextSetup(webApplicationContext)
                    .build();
        }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(EMPL5, EMPL1,EMPL4,EMPL2,EMPL3))
                )
        ;
    }


    @Test
    public void testGet() throws Exception {

        mockMvc.perform(get(REST_URL + EMPL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(EMPL1));
    }

}
