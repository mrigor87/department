package com.mrigor.testTasks.department.rest;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.service.DepartmentService;
import com.mrigor.testTasks.department.service.EmployeeService;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.mrigor.testTasks.department.rest.DepartmentController.REST_URL;


/**
 * Created by Игорь on 11.12.2016.
 */


@RestController
@RequestMapping(REST_URL)
public class DepartmentController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    DepartmentService service;

    @Autowired
    EmployeeService employeeService;

    public static final String REST_URL = "/rest/departments";

    @GetMapping(value = "/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesByDepartment(@PathVariable("id") int id){
        LOG.info("getAll employees from departments id=",id);
        return employeeService.getByDep(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getAll() {
        LOG.info("getAll employees");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department get (@PathVariable("id") int id){
        LOG.info("get department id={}",id);
        return service.get(id);
    }
    @GetMapping(value = "/withAvgSalary", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary(){
        LOG.info("get departments with avg salary");
        return service.getAllWithAvgSalary();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        LOG.info("delete department id={}",id);
        service.delete(id);
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Department department){
        LOG.info("update department {}",department);
        service.update(department);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> createWithLocation(@RequestBody Department department) {
        LOG.info("created department {}",department);
        Department created = service.create(department);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }







}