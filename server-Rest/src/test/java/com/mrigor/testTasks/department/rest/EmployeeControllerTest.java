package com.mrigor.testTasks.department.rest;

import com.mrigor.testTasks.department.service.EmployeeService;
import com.mrigor.testTasks.department.matcher.JsonUtil;
import com.mrigor.testTasks.department.model.Employee;
import static com.mrigor.testTasks.department.DepTestData.DEP1_ID;


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

import static com.mrigor.testTasks.department.EmployeeTestData.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.mrigor.testTasks.department.TestUtil.printContent;

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
public class EmployeeControllerTest {
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
        printContent(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(EMPL5, EMPL1, EMPL4, EMPL2, EMPL3))
        );
    }

    @Test
    public void testGet() throws Exception {

        mockMvc.perform(get(REST_URL + EMPL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(EMPL1));
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete(REST_URL + EMPL1_ID))
              //  .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList (EMPL5, EMPL4, EMPL2, EMPL3), service.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        Employee updated = getUpdated();

        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
            //    .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3,updated), service.getByDep(DEP1_ID));
    }


    @Test
    public void testCreate() throws Exception {
        Employee expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());
        Employee returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL1,expected,EMPL2,EMPL3), service.getByDep(DEP1_ID));
    }

@Test
public void testGetAllbyDepartment() throws Exception {
    printContent(mockMvc.perform(get(REST_URL+"/filtered?departmentid="+DEP1_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(MATCHER.contentListMatcher(EMPL_D1))
    );
}

    @Test
    public void testGetBetweenDates() throws Exception {
        printContent(mockMvc.perform(get(REST_URL+"filtered?from=1993-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(EMPL_D2))
        );
    }

    @Test
    public void testGetfromDate() throws Exception {
        printContent(mockMvc.perform(get(REST_URL+"filtered?from=1993-01-01&to=1993-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(EMPL4))
        );
    }


}
