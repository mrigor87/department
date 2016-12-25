package com.mrigor.testTasks.department.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;




/**
 * Created by Игорь on 17.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml"
/*        ,
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"*/
})
/*@WebAppConfiguration*/
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional


public class DepartmentServiceClientTest {

    @Autowired
    String prefix;

    private MockRestServiceServer mockServer;

/*    @Autowired
    private WebApplicationContext webApplicationContext;*/

    @Autowired
    private RestTemplate restTemplate;




    @Before
    public void setUp() throws Exception {

        mockServer = MockRestServiceServer.createServer(restTemplate);

    }

    @Autowired
    DepartmentServiceClient service;

    @Test
    public void create() throws Exception {

        mockServer.expect(requestTo(prefix+"/rest/departments/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());
        service.create(DepTestData.getCreated());
        mockServer.verify();
    }




    @Test
    public void get() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/"+100000))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.get(100000);
        mockServer.verify();
    }



    @Test
    public void update() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess());
        service.update(DepTestData.getUpdated());
        mockServer.verify();
    }

    @Test
    public void delete() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/"+100000))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        service.delete(100000);
        mockServer.verify();
    }

    @Test
    public void getAll() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.getAll();
        mockServer.verify();
    }

    @Test
    public void getAllWithAvgSalary() throws Exception {
        mockServer.expect(requestTo(prefix+"/rest/departments/withAvgSalary"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.getAllWithAvgSalary();
        mockServer.verify();
    }

}


