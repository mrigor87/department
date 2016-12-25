package com.mrigor.testTasks.department.rest;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.mrigor.testTasks.department.rest.EmployeeController.REST_URL;

/**
 * Created by Igor on 13.12.2016.
 */
@RestController
@RequestMapping(REST_URL)
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    EmployeeService service;
    public static final String REST_URL = "/rest/employees";



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAll() {
        LOG.info("get all employees");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee get(@PathVariable("id") int id) {
        LOG.info("get employee by id={}",id);
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        LOG.info("delete employee id={}", id);
        service.delete(id);
    }



@GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
public List<Employee> filter(
        @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
    @RequestParam(value = "departmentid", required = false)  Integer departmentId)
{
    LOG.info("get filtered employees  departmentId={}  from={} to={}", departmentId, from, to);
    return service.getFiltered(from, to, departmentId);
}


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Employee employee) {

        LOG.info("update employee {} from department id={}", employee, employee.getDepartmentId());
        service.update(employee);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createWithLocation( @RequestBody Employee employee) {
        LOG.info("created department {}", employee);
        Employee created = service.create(employee);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}
