package com.mrigor.tasks.department.web;

import com.mrigor.tasks.department.matcher.JsonUtil;
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
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static com.mrigor.tasks.department.DepTestData.*;
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
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-mvc-test.xml"

})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

public class DepartmentControllerTest {

    @Autowired
    private String prefix;

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void get1() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/departments/" + 100000))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(JsonUtil.writeValue(DEP1), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/ajax/departments/" + 100000))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(DEP1));
    }

    @Test
    public void getAllWithAvgSalary() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/departments/withAvgSalary"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(JsonUtil.writeValue(DEP_WITH_AVG_SALARY), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/ajax/departments/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_SALARY.contentListMatcher(DEP_WITH_AVG_SALARY));
    }

    @Test
    public void delete1() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/departments/" + 100000))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());

        mockMvc.perform(delete("/ajax/departments/" + 100000))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void update() throws Exception {

        mockServer.expect(
                requestTo(prefix +
                        "/rest/departments/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(JsonUtil.writeValue(getUpdated()), MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/ajax/departments/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated()))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }


}