package com.mrigor.tasks.department.web;


import com.mrigor.tasks.department.DepartmentApplication;
import com.mrigor.tasks.department.EmployeeTestData;
import com.mrigor.tasks.department.matcher.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * tests
 */

@SpringBootTest(classes = DepartmentApplication.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeControllerTest {

    @Value("${rest.port}")    String restPort;
    @Value("${rest.host}")    String restHost;


    private String prefix;


    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        prefix = "http://" + restHost + ":" + restPort;
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
                .andRespond(MockRestResponseCreators.withSuccess(JsonUtil.writeValue(Arrays.asList(EmployeeTestData.EMPL2)), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/ajax/employees/filtered?departmentid=100000&from=1992-12-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EmployeeTestData.MATCHER.contentListMatcher(Arrays.asList(EmployeeTestData.EMPL2)));
    }

    @Test
    public void get1() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/" + 100002))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(JsonUtil.writeValue(EmployeeTestData.EMPL1), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/ajax/employees/" + 100002))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EmployeeTestData.MATCHER.contentMatcher(EmployeeTestData.EMPL1));
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
                .andRespond(withSuccess(JsonUtil.writeValue(EmployeeTestData.getUpdated()), MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/ajax/employees/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(EmployeeTestData.getUpdated()))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}