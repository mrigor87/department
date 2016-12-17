package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.mrigor.testTasks.department.web.DepartmentController.REST_URL;

/**
 * Created by Игорь on 17.12.2016.
 */

//@RequestMapping(REST_URL)
//@Controller
public class DepartmentController {
    @Autowired
    DepartmentService service;

    public static final String REST_URL = "/departments";

    @GetMapping
    String getAll(Model model){
        List<Department> all = service.getAll();
        model.addAttribute ("departmentList",all);
        return "departmentPage";

    }
}
