package com.mrigor.tasks.department.web;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * tests
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml"

})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("rest")
public abstract class RootControllerTest {

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
    public void departments() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("departmentPage"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/departmentPage.jsp"));

    }

    @Test
    public void employees() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/"+100000))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        mockMvc.perform(get("/department/"+100000+"/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("employeePage"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/employeePage.jsp"));


    }

    @Test
    public void testResources() throws Exception {
        mockMvc.perform(get("/resources/js/departmentDatatables.js"))
                //.andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("application/octet-stream")))
                .andExpect(status().isOk());
    }
    }

