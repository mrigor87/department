package com.mrigor.tasks.department.web;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.service.EmployeeService;
import org.omg.CORBA.portable.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Ajax controllers (employees)
 */
@RequestMapping(value = "ajax/employees")
@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService service;

    /**
     * get filtered list of employees
     * @param departmentId identifier of department
     * @param from day of birth
     * @param to day of birth
     * @return list of employees
     */
    @GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getFiltered(@RequestParam(value = "departmentid", required = false) Integer departmentId,
                                      @RequestParam(value = "from", required = false) LocalDate from,
                                      @RequestParam(value = "to", required = false) LocalDate to) {
        LOG.debug("get filtered employees, departmentId={}, from={}, to={}",departmentId,from,to);
        List<Employee> filtered = service.getFiltered(from, to, departmentId);
        return filtered==null? Collections.emptyList():filtered;
    }


    /**
     * get employee
     * @param id identifier of employee
     * @return employee
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee get(@PathVariable("id") int id) {
        LOG.debug("get employee, id={}",id);
        return service.get(id);
    }

    /**
     * delete employee
     * @param id identifier of employee
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        LOG.debug("delete employee, id={}",id);
        service.delete(id);
    }

    /**
     * create or update employee
     * @param employee
     * @param result information about result of binding
     * @return  entity with response
     */
    @PostMapping
   // @RequestMapping
    public ResponseEntity<String>  createOrUpdate(HttpSession session, Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Department department=(Department) session.getAttribute("department");
        department.setEmployeeList(null);
        employee.setDepartment(department);
        if (employee.isNew()) {
            LOG.debug("create or update employee {}",employee);
            service.create(employee);
        } else {
            LOG.debug("create or update employee {}",employee);
            service.update(employee);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
