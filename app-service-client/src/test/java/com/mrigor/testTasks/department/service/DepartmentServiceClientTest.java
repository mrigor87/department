package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Department;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Игорь on 17.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml"
})



@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentServiceClientTest {
    @Autowired
    DepartmentService service;

    @Autowired
    RestTemplate restTemplate;


    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws Exception {
/*        InternalResourceViewResolver viewResolver =
                new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        mockServer = MockRestServiceServer.createServer(restTemplate);*/
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
/*        mockMvc = standaloneSetup(socialController)
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter())
                .setViewResolvers(viewResolver)
                .build();*/
    }




    @Test
    public void create() throws Exception {
        Department created=DepTestData.getCreated();
        System.out.println(service.create(created));

        System.out.println(service.getAll());
    }

    @Test
    public void update() throws Exception {
        Department updated=DepTestData.getUpdated();
        service.update(updated);
        System.out.println(service.getAll());

    }

    @Test
    public void delete() throws Exception {
        service.delete(100001);
        System.out.println(service.getAll());
    }

    @Test
    public void get() throws Exception {
        //restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        Department department = service.get(100000);
        System.out.println(department);
    }

    @Test
    public void getAll() throws Exception {
        List<Department> all = service.getAll();
        System.out.println(all);

    }

    @Test
    public void getAllWithAvgSalary() throws Exception {
        System.out.println(service.getAllWithAvgSalary());
    }

}