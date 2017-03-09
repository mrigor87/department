package com.mrigor.tasks.department.web;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.service.DepartmentService;
import com.mrigor.tasks.department.service.EmployeeService;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;




/**
 * Ajax controllers (department)
 */

@RequestMapping(value ="ajax/departments" )
@RestController
public class DepartmentController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService service;
    @Autowired
    private EmployeeService employeeService;

    /**
     * get department
     * @param id identifier of department
     * @return department
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department get(@PathVariable("id") int id)
    {
        LOG.debug("get department, id={}",id);
        return service.get(id);
    }

    /**
     * get all departments
     * @return list of departments
     */
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.debug("get all departments with avg salary}");
        List<DepartmentWithAverageSalary> allWithAvgSalary = service.getAllWithAvgSalary();
        return allWithAvgSalary==null?Collections.emptyList():allWithAvgSalary;
    }

    /**
     * get all employees from particular department
     * @param id identifier of department
     * @return list of employees
     */
    @GetMapping( value ="/{id}/employees",  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesByDepartment(@PathVariable("id") int id) {
        LOG.debug("get all employees by departmentId={}",id);
        List<Employee> byDep = employeeService.getByDep(id);
        return byDep==null?Collections.emptyList():byDep;
    }

    /**
     * delete department
     * @param id identifier of department
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        LOG.debug("delete departments, id={}",id);
        service.delete(id);
    }

    /**
     * create or update department
     * @param department
     * @param result information about result of binding
     * @return  entity with response
     */
    @PostMapping
    public ResponseEntity<String> createOrUpdate( Department department, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (department.isNew()) {
            LOG.debug("create department {}",department);
            service.create(department);
        } else {
            LOG.debug("update department {}",department);
            service.update(department);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
