package com.mrigor.testTasks.department.web;


import com.mrigor.testTasks.department.matcher.JsonUtil;
import com.mrigor.testTasks.department.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.mrigor.testTasks.department.EmployeeTestData.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


/**
 * tests
 */

@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-mvc-test.xml"

})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)


public class EmployeeControllerTest {


    @Autowired
    String prefix;

    @Autowired
    EmployeeService service;

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getFiltered() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/filtered?from=1992-12-30&departmentid=100000"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(JsonUtil.writeValue(Arrays.asList(EMPL2)), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/ajax/employees/filtered?departmentid=100000&from=1992-12-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(Arrays.asList(EMPL2)));
    }

    @Test
    public void get1() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/" + 100002))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(JsonUtil.writeValue(EMPL1), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/ajax/employees/" + 100002))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(EMPL1));
    }

    @Test
    public void delete1() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/" + 100002))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());

        mockMvc.perform(delete("/ajax/employees/" + 100002))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void update() throws Exception {

        mockServer.expect(
                requestTo(prefix +
                        "/rest/employees/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(JsonUtil.writeValue(getUpdated()), MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/ajax/employees/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(getUpdated()))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}