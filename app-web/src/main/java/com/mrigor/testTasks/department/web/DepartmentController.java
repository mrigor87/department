package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;




/**
 * Created by Игорь on 17.12.2016.
 */

@RequestMapping(value ="ajax/departments" )
@RestController
public class DepartmentController {

    @Autowired
    DepartmentService service;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department get(@PathVariable("id") int id)
    {
        return service.get(id);
    }


    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {

        return service.getAllWithAvgSalary();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }


/*    @PostMapping
    public ResponseEntity<String> createOrUpdate( Department department, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (department.isNew()) {
            service.create(department);
        } else {
            service.update(department);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }*/



}
