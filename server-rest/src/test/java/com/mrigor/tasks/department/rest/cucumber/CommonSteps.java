package com.mrigor.tasks.department.rest.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Igor on 20.06.2017.
 */
@Ignore
public  class CommonSteps extends CucumberSpring {
    @Given("^REST server has just started$")
    public void init_department() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        dbPopulator.execute();
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
