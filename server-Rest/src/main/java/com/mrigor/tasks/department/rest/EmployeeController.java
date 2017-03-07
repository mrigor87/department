package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.service.EmployeeService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.mrigor.tasks.department.rest.EmployeeController.REST_URL;

/**
 * REST controllers for employees
 * Data returns in json format.
 * Exceptions is handled with ExceptionInfoHandler
 */
@RestController
@RequestMapping(REST_URL)
@Api(value = "employees", description = "Endpoint for Employee specific operations")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private EmployeeService service;

    public static final String REST_URL = "/rest/employees";

    /**
     * get all employees
     * @return list of employees or empty if not found
     */
    //@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)   //swagger-maven-plugin doesn't understand that
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ApiOperation(value = "get all employees",
            response = Department.class,responseContainer = "List",notes = "get all employees")
    public List<Employee> getAll() {
        LOG.info("get all employees");
        return service.getAll();
    }

    /**
     * get employee by id
     * @param id identifier of employee
     * @return employee or exception id not found
     */
    //@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ApiOperation(value = "get employee by id",
            response = Employee.class,notes = "get employee by id")
    @ApiResponses( {
            @ApiResponse( code = 404, message = "employee with such identifier doesn't exists" )
    } )
    public Employee get(
            @ApiParam( value = "identifier of employee", required = true )@PathVariable("id") int id) {
        LOG.info("get employee by id={}", id);
        return service.get(id);
    }

    /**
     * delete employee by id
     * @param id identifier of employee
     */
    //@DeleteMapping(value = "/{id}")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "delete employee by id",
            response = Employee.class,notes = "delete employee by id")
    @ApiResponses( {
            @ApiResponse( code = 404, message = "employee with such identifier doesn't exists" )
    } )
    public void delete(@ApiParam( value = "identifier of employee", required = true ) @PathVariable("id") int id) {
        LOG.info("delete employee id={}", id);
        service.delete(id);
    }

    /**
     * get filtered list of employees
     * params for filtering:
     * @param from day of birth
     * @param to day of birth
     * @param departmentId identifier of department
     * @return list of employees or empty id not found
     */
    //@GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "get filtered list of employees",
            response = Department.class,responseContainer = "List",notes = "get filtered list of employees by params")
    public List<Employee> filter(
            @ApiParam( value = "from", required = false )   @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(value = "departmentid", required = false) Integer departmentId) {
        LOG.info("get filtered employees  departmentId={}  from={} to={}", departmentId, from, to);
        return service.getFiltered(from, to, departmentId);
    }

    /**
     * update employee
     * @param employee
     */
    //@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.PUT)
    @ApiOperation(value = "update employee",
            notes = "update employee"
    )
    public void update(@ApiParam( value = "new employee" )  @RequestBody Employee employee) {

        LOG.info("update employee {} from department id={}", employee, employee.getDepartmentId());
        service.update(employee);
    }

    /**
     * create new employee
     * @param employee
     * @return entity with response body
     */
    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    @ApiOperation(value = "create employee",
            notes = "create employee"
    )
    public ResponseEntity<Employee> createWithLocation(@RequestBody Employee employee) {
        LOG.info("created department {}", employee);
        Employee created = service.create(employee);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}
