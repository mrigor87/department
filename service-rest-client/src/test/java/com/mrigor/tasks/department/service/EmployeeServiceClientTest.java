package com.mrigor.tasks.department.service;


import com.mrigor.tasks.department.DepartmentApplication;
import com.mrigor.tasks.department.EmployeeTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by Igor on 17.12.2016.
 */
@SpringBootTest(classes = DepartmentApplication.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceClientTest {

    private MockRestServiceServer mockServer;

    @Value("${rest.port}") String restPort;
    @Value("${rest.host}") String restHost;


    private String prefix;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeService service;

    @Before
    public void setUp() throws Exception {
        prefix="http://"+restHost+":"+restPort;
        mockServer = MockRestServiceServer.createServer(restTemplate);

    }

    @Test
    public void create() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());
        service.create(EmployeeTestData.getCreated());
        mockServer.verify();
    }

    @Test
    public void update() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess());
        service.update(EmployeeTestData.getUpdated());
        mockServer.verify();
    }

    @Test
    public void delete() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/"+100002))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        service.delete(100002);
        mockServer.verify();
    }

    @Test
    public void get() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/"+100002))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.get(100002);
        mockServer.verify();
    }

    @Test
    public void getAll() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.getAll();
        mockServer.verify();

    }

    @Test
    public void getByDep() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/departments/"+100000+"/employees"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.getByDep(100000);
        mockServer.verify();
    }

    @Test
    public void getBetweenDates() throws Exception {
        mockServer.expect(requestTo(prefix + "/rest/employees/filtered?from=1993-01-01&to=1993-01-01&departmentid=100000"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        service.getFiltered(LocalDate.of(1993, 1, 1), LocalDate.of(1993, 1, 1), 100000);
        mockServer.verify();
    }



}