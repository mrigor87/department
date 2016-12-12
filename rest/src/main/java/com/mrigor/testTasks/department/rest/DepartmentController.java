package com.mrigor.testTasks.department.rest;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mrigor.testTasks.department.rest.DepartmentController.REST_URL;



/**
 * Created by Игорь on 11.12.2016.
 */


@RestController
@RequestMapping(REST_URL)
public class DepartmentController {
    @Autowired
    DepartmentService serviceD;

    protected static final String REST_URL = "/rest/employees";

/*    produces = "application/json"*/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getAll() {
        List<Department> all = serviceD.getAll();
        return all;
    }


}