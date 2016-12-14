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
@RequestMapping(value = REST_URL)
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    EmployeeService service;
    public static final String REST_URL = "/rest/employees";


/*    Employee create(Employee employee, int departamentId);
    void update(Employee employee, int departamentId) throws NotFoundException;
    void delete(int id) throws NotFoundException;
    Employee get(int id) throws NotFoundException;
    List<Employee> getAll();
    List<Employee> getByDep(int id) throws NotFoundException;
    List<Employee> getBetweenDates(LocalDate from, LocalDate to);
    List<Employee> getByDate(LocalDate date);*/

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAll() {
        LOG.info("get all employees");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee get(@PathVariable("id") int id) {
        LOG.info("get all employees");
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        LOG.info("delete employee id={}",id);
        service.delete(id);
    }


    @GetMapping(value = "/department/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllByDepartmentId(@PathVariable("id") int id) {
        LOG.info("get employees by department id={}", id);
        return service.getByDep(id);
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getBetweenDates(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LOG.info("get employees from dates from {} to {}", from, to);
        return service.getBetweenDates(from, to);
    }

    @GetMapping(value = "/byDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getBetweenDates(
            @RequestParam(value = "date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get employees from by date {}", date);
        return service.getByDate(date);
    }

    @PutMapping( value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id")int departmentId, @RequestBody Employee employee) {
        employee.setDepartmentId(departmentId);
        LOG.info("update employee {} from department id={}",employee,departmentId);
        service.update(employee);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createWithLocation(@PathVariable("id")int id, @RequestBody Employee employee) {
        LOG.info("created department {}",employee);
        Employee created = service.create(employee,id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }



}
