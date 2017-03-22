package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.service.DepartmentService;
import com.mrigor.tasks.department.service.EmployeeService;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@Api(value = "departments", description = "Endpoint for Department specific operations")

public class DepartmentController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService service;

    @Autowired
    private EmployeeService employeeService;

    public static final String REST_URL = "/rest/departments";

    /**
     * get employees from department with @param id
     *
     * @param id identifier of department
     * @return employee or exception if not found
     */

    //@GetMapping(value = "/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE) //swagger-maven-plugin doesn't understand that
    @RequestMapping(value = "/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "get employee's list from department",
            notes = "get employee from department",
            response = Employee.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 404, message = "department with such identifier doesn't exists")
    })
    public List<Employee> getEmployeesByDepartment(
            @ApiParam(value = "identifier of department", required = true) @PathVariable("id") int id) {
        LOG.info("getAll employees from departments id=", id);
        return employeeService.getByDep(id);
    }

    /**
     * get all departments
     *
     * @return department's list or empty if not found
     */
   // @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)   //swagger-maven-plugin doesn't understand that
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "get all departments",
            response = Department.class, notes = "get all departments")
    public List<Department> getAll() {
        LOG.info("getAll departments");
        return service.getAll();
    }

    /**
     * get department by id
     *
     * @param id identifier by department
     * @return department or exception if not found
     */

   // @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)  //swagger-maven-plugin doesn't understand that
    @RequestMapping (value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "get department by id",
            response = Employee.class, notes = "get department by id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "department with such identifier doesn't exists")
    })
    public Department get(@ApiParam(value = "identifier of department", required = true) @PathVariable("id") int id) {
        LOG.info("get department id={}", id);
        return service.get(id);
    }

    /**
     * get all department with information about average salary
     *
     * @return list or empty if not found
     */
    //@GetMapping(value = "/withAvgSalary", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/withAvgSalary", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "get all employees with information about average salary",
            response = DepartmentWithAverageSalary.class, responseContainer = "List",
            notes = "get department's list with information about average salary by each department. " +
                    "If department don't have any employees, then AVG salary will by = 0 ")
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.info("get departments with avg salary");
        return service.getAllWithAvgSalary();
    }

    /**
     * delete department by id
     *
     * @param id identifier of department
     */
   // @DeleteMapping(value = "/{id}")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "delete department by id",
            notes = "delete department by id"
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "department with such identifier doesn't exists")
    })
    public void delete(
            @ApiParam(value = "identifier of department") @PathVariable("id") int id) {
        LOG.info("delete department id={}", id);
        service.delete(id);
    }

    /**
     * update department
     *
     * @param department
     */
    //@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.PUT)
    @ApiOperation(value = "update department",
            notes = "update department"
    )
    public void update(@ApiParam(value = "new department") @RequestBody Department department) {
        LOG.info("update department {}", department);
        service.update(department);
    }

    /**
     * create new department
     *
     * @param department
     * @return entity with response body
     */
    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    @ApiOperation(value = "create new department",
            notes = "create new department")
    public ResponseEntity<Department> createWithLocation(@ApiParam(value = "new department") @RequestBody Department department) {
        LOG.info("created department {}", department);
        Department created = service.create(department);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}