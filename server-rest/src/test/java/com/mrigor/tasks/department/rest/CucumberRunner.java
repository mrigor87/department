package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.service.EmployeeService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Игорь on 19.06.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class CucumberRunner {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    EmployeeService service;

    static final String REST_URL = EmployeeController.REST_URL + '/';

    MockMvc mockMvc;

    @Before
    public void setUp() {

    }
}
