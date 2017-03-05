package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.service.DepartmentService;
import com.mrigor.tasks.department.service.EmployeeService;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.mrigor.tasks.department.rest.DepartmentController.REST_URL;


/**
 * REST controllers for department
 * data returns in json format
 * Exceptions is handled with ExceptionInfoHandler
 */


@RestController
@RequestMapping(REST_URL)
@Api(value = "contacts", description = "contacts") // Swagger annotation
public class DepartmentController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService service;

    @Autowired
    private EmployeeService employeeService;

    public static final String REST_URL = "/rest/departments";

    /**
     * get employee from department with @param id
     *
     * @param id identifier of department
     * @return employee or exception if not found
     */
    @ApiOperation(value = "Resets password")
    @GetMapping(value = "/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesByDepartment(@PathVariable("id") int id) {
        LOG.info("getAll employees from departments id=", id);
        return employeeService.getByDep(id);
    }

    /**
     * get all employees
     *
     * @return employee's list or empty if not found
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getAll() {
        LOG.info("getAll departments");
        return service.getAll();
    }

    /**
     * get employees by id
     *
     * @param id identifier by employee
     * @return employee or exception if not found
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department get(@PathVariable("id") int id) {
        LOG.info("get department id={}", id);
        return service.get(id);
    }

    /**
     * get all employees with information about average salary
     *
     * @return list or empty if not found
     */
    @GetMapping(value = "/withAvgSalary", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.info("get departments with avg salary");
        return service.getAllWithAvgSalary();
    }

    /**
     * delete department by id
     *
     * @param id identifier of department
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        LOG.info("delete department id={}", id);
        service.delete(id);
    }

    /**
     * update department
     *
     * @param department
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Department department) {
        LOG.info("update department {}", department);
        service.update(department);
    }

    /**
     * create new department
     *
     * @param department
     * @return entity with response body
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> createWithLocation(@RequestBody Department department) {
        LOG.info("created department {}", department);
        Department created = service.create(department);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}