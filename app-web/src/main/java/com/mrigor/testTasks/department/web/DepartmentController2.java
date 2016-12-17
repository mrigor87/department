package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mrigor.testTasks.department.web.DepartmentController.REST_URL;

/**
 * Created by Игорь on 17.12.2016.
 */

@RequestMapping(REST_URL)
@Controller
public class DepartmentController2 {
    @Autowired
    DepartmentService service;

    public static final String REST_URL = "/departments";

    @GetMapping
    String getAll(Model model) {
        List<Department> all = service.getAll();
        model.addAttribute("departmentList", all);
        return "users";
    }

/*    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        LOG.info("delete department id={}",id);
        service.delete(id);
    }*/

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {

        service.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name) {

        Department department = new Department(id, name);
        if (department.isNew()) {
            service.create(department);
        } else {
            service.update(department);
        }
    }

}
