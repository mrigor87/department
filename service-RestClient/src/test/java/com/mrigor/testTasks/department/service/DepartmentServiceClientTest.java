package com.mrigor.testTasks.department.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static com.mrigor.testTasks.department.DepTestData.getCreated;
import static com.mrigor.testTasks.department.DepTestData.getUpdated;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;





/**
 * tests
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml"

})

@RunWith(SpringJUnit4ClassRunner.class)



public class DepartmentServiceClientTest {

    @Autowired
    private String prefix;

    private MockRestServiceServer mockServer;


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
        service.create(getCreated());
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
        service.update(getUpdated());
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


