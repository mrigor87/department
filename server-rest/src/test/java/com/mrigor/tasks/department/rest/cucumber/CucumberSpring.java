package com.mrigor.tasks.department.rest.cucumber;

import com.mrigor.tasks.department.service.EmployeeService;
import com.mrigor.tasks.department.util.DbPopulator;
import cucumber.api.java.en.Given;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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

public abstract class CucumberSpring {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    DbPopulator dbPopulator;

    static MockMvc mockMvc;

    static ResultActions ra;




}
