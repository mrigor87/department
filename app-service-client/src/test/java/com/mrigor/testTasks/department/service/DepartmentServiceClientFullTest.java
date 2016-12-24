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
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentServiceClientFullTest {

    private MockRestServiceServer mockServer;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestTemplate restTemplate;



    private MockMvc mockMvc;



    @Before
    public void setUp() throws Exception {
        mockServer = MockRestServiceServer.createServer(service.getRestTemplate());
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
        ;


    }

    @Autowired
    DepartmentServiceClient service;

    @Test
    public void create() throws Exception {

/*        RestTemplate rt=new RestTemplate()*/
/*        Department createDep=getCreated();
        service.create(createDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1,createDep,DEP2), service.getAll());*/
    }




    @Test
    public void get1() throws Exception {
        //mockServer.expect(requestTo(service.REST_URL()))
          //      .andExpect(method(HttpMethod.PUT))
               // .andRespond(withSuccess(responseXml, MediaType.APPLICATION_XML)); // (2)
/*
        mockServer.expect(
                requestTo(service.REST_URL +
                        "100000"
                        ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        mockMvc.perform(
                get(service.REST_URL+ "100000")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(view().name("user"));
*/

/*        Department dep=service.get(DepTestData.DEP1_ID);
        MATCHER.assertEquals(dep,DEP1);*/
    }
}


