package com.mrigor.testTasks.department.service;


import com.mrigor.testTasks.department.rest.DepartmentController;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.easymock.EasyMock.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.mrigor.testTasks.department.service.DepTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Игорь on 17.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml"
})


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentServiceClientTest {

    private static final String REST_URL = DepartmentController.REST_URL + '/';

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DepartmentServiceClient departmentService;

    private MockMvc mockMvc;

    @Resource
    DepartmentController departmentRest;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws Exception {

/*        InternalResourceViewResolver viewResolver =
                new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");*/

        mockMvc = standaloneSetup(departmentRest)
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter())
                /*.setViewResolvers(viewResolver)*/
                .build();
        mockServer = MockRestServiceServer.createServer(restTemplate);


    }


    @Test
    public void create() throws Exception {

        //mockServer.
        mockServer.expect(
                requestTo("/rest/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "{\"id\":100000,\"name\":\"Marketing\"},{\"id\":100001,\"name\":\"Production\"}",
                        MediaType.APPLICATION_JSON))
        ;

        mockMvc.perform(
                get("/rest/departments")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MATCHER.contentListMatcher(DEP1, DEP2));
/*               .andExpect(forwardedUrl("/WEB-INF/view/users.jsp"))*/
        ;


/*               .andRespond(withSuccess(
                       "{\"users\":null,\"totalUsers\":null,\"user\":null}",
                       MediaType.APPLICATION_JSON))*/
        ;
    }



/*    @Test
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
*//*        service.delete(100001);
        System.out.println(service.getAll());*//*

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
       // System.out.println(service.getAllWithAvgSalary());
    }*/

}