package com.mrigor.tasks.department.rest.cucumber;

import com.mrigor.tasks.department.EmployeeTestData;
import com.mrigor.tasks.department.matcher.JsonUtil;
import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.rest.EmployeeController;
import com.mrigor.tasks.department.service.DepartmentService;
import com.mrigor.tasks.department.service.EmployeeService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.mrigor.tasks.department.DepTestData.DEP1_ID;
import static com.mrigor.tasks.department.EmployeeTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * Created by Igor on 20.06.2017.
 */
@Ignore //for junit
public  class EmployeeCucumberStepDefs extends CucumberSpring {
    @Autowired
    private EmployeeService service;
    @Autowired
    private DepartmentService departmentService;

    private static final String REST_URL = EmployeeController.REST_URL + '/';



    @Given("^REST server has just started1$")
    public void init_department() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        dbPopulator.execute();
    }


    @When("^the client updates employee$")
    public void update_employee() throws Throwable   {
        ra= mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(EmployeeTestData.getUpdated())));
    }

    @Then("^employee is updated$")
    public void employee_is_updated() throws Throwable {
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3,EmployeeTestData.getUpdated()), service.getByDep(DEP1_ID));
    }
    @When("^the client creates new employee")
    public void create_employee() throws Throwable   {
        ra = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(EmployeeTestData.getCreated())));
    }
    @Then("^employee is created$")
    public void employee_is_created() throws Throwable{
        Employee returned = MATCHER.fromJsonAction(ra);
        Employee expected=EmployeeTestData.getCreated();
        expected.setId(returned.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL1,expected,EMPL2,EMPL3), service.getByDep(DEP1_ID));
    }
}
