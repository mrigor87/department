package com.mrigor.testTasks.department.rest;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
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

    public static final String REST_URL = "/rest/departments";
/*   Department save(Department department);
    Department update(Department department) throws NotFoundException;
    void delete(int id) throws NotFoundException;
    Department get(int id) throws NotFoundException;
    List<Department> getAll();
    List<DepartmentWithAverageSalary> getAllWithAvgSalary();*/


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getAll() {
        LOG.info("getAll for departments");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department get (@PathVariable("id") int id){
        LOG.info("get department id={}",id);
        return service.get(id);
    }
    @GetMapping(value = "/getAllWithAvgSalary", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary(){
        LOG.info("get delartments with avg salary");
        return service.getAllWithAvgSalary();
    }

    @DeleteMapping(value = "/{id}")
   // @ResponseStatus()
    public void delete(@PathVariable("id") int id){

        LOG.info("delete department id={}",id);
        service.delete(id);
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Department department){
        LOG.info("udate department {}",department);
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