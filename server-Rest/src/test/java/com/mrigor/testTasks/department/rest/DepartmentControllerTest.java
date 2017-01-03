package com.mrigor.testTasks.department.rest;



import com.mrigor.testTasks.department.matcher.JsonUtil;
import com.mrigor.testTasks.department.model.Department;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;
import java.util.Collections;


import static com.mrigor.testTasks.department.TestUtil.printContent;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.mrigor.testTasks.department.DepTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * tests
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    DepartmentService service;

    private static final String REST_URL = DepartmentController.REST_URL + '/';

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

    }

    @Test
    public void testGetAll() throws Exception {
        printContent(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(DEP1, DEP2)));

    }

    @Test
    public void testGetAllWithSalary() throws Exception {
        printContent(mockMvc.perform(get(REST_URL+"withAvgSalary"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_SALARY.contentListMatcher(DEP_WITH_AVG_SALARY)));

    }

    @Test
    public void testGet() throws Exception {
        printContent(mockMvc.perform(get(REST_URL + DEP1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(DEP1))
        );
    }




    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete(REST_URL + DEP1_ID))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Collections.singletonList(DEP2), service.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        Department updated = getUpdated();

        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, service.get(DEP1_ID));
    }

    @Test
    public void testCreate() throws Exception {
        Department expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());
        Department returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1, expected, DEP2), service.getAll());
    }


}