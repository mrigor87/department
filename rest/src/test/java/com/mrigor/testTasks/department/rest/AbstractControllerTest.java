package com.mrigor.testTasks.department.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;



/*
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)*/


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
//@Sql(scripts = "classpath:db/populateDB.sql")


abstract public class AbstractControllerTest {


    protected MockMvc mockMvc;
   @Autowired
    private DepartmentController conroller;
    @Before
    public void setUp() {
        mockMvc = standaloneSetup(conroller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

}
