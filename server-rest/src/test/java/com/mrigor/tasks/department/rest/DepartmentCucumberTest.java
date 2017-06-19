package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.DepTestData;
import com.mrigor.tasks.department.matcher.JsonUtil;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.service.DepartmentService;
import com.mrigor.tasks.department.service.EmployeeService;
import com.mrigor.tasks.department.util.DbPopulator;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

import static com.mrigor.tasks.department.DepTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
//@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentCucumberTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    DbPopulator dbPopulator;

    @Autowired
    private DepartmentService service;

    private static final String REST_URL = DepartmentController.REST_URL + '/';

    private MockMvc mockMvc;

    private ResultActions ra = null;

    @Before

    public void before() {

    }

    @Given("^REST server has just started$")
    public void init() {
        dbPopulator.execute();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }
    @When("^the client updates department$")
    public void update_department() throws Throwable   {
       ra= mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DepTestData.getUpdated())));
    }
    @Then("^department is updated$")
    public void one_department_id_updated() throws Throwable {
        MATCHER.assertEquals(DepTestData.getUpdated(), service.get(DEP1_ID));
    }

    @When("^the client creates new department$")
    public void create_department() throws Throwable   {
        ra = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getCreated())));
    }
    @Then("^department is created$")
    public void department_is_created() throws Throwable{
        Department returned = MATCHER.fromJsonAction(ra);
        Department expected=getCreated();
        expected.setId(returned.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1, expected, DEP2), service.getAll());
    }

    @Then("^the response contains the created department$")
    public void the_response_contains_the_created_department() throws Throwable{
        Department returned = MATCHER.fromJsonAction(ra);
        Department expected=getCreated();
        expected.setId(returned.getId());
        MATCHER.assertEquals(expected, returned);
    }

    @When("^the client delete existing department$")
    public void delete_department() throws Throwable {
        ra = mockMvc.perform(delete(REST_URL + DEP1_ID));
    }

    @Then("^department is deleted$")
    public void one_department_id_deleted() throws Throwable {
        MATCHER.assertCollectionEquals(Collections.singletonList(DEP2), service.getAll());
    }

    @When("^I try to test$")
    public void test() throws Throwable {
        System.out.println("*************************** I'm testing the application");
    }

    @When("^the user requests list of departments$")
    public void the_client_requests_GET_all_departments() throws Throwable {
        System.out.println("*************************** I'm testing the application");
        ra = mockMvc.perform(get(REST_URL));
    }

    @When("^the user requests department with id=(\\d+)$")
    public void the_client_requests_GET__department(int departmentId) throws Throwable {

        ra = mockMvc.perform(get(REST_URL + DEP1_ID));
    }

    @Then("^the response is list of departments$")
    public void the_response_is_list_of_departments() throws Throwable {
        ra.andExpect(MATCHER.contentListMatcher(DEP1, DEP2));
    }

    @Then("^the response has the status OK$")
    public void the_response_has_status_OK() throws Throwable {
        ra.andExpect(status().isOk());
    }

    @Then("^the response body is JSON$")
    public void the_response_is_JSON() throws Throwable {
        ra.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
