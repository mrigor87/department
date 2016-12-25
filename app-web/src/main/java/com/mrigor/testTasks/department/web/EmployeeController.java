package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 18.12.2016.
 */
@RequestMapping(value = "ajax/employees")
@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService service;

/*    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAll() {
        return service.getAll();
    }*/



    @GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getFiltered(@RequestParam(value = "departmentid", required = false) Integer departmentId,
                                            @RequestParam(value = "from", required = false) LocalDate from,
                                            @RequestParam(value = "to", required = false) LocalDate to) {
        LOG.debug("get filtered employees, departmentId={}, from={}, to={}",departmentId,from,to);
        return service.getFiltered(from,to, departmentId);
    }



    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee get(@PathVariable("id") int id) {
        LOG.debug("get employee, id={}",id);
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        LOG.debug("delete employee, id={}",id);
        service.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(Employee employee, BindingResult result) {
        LOG.debug("create or update employee {}",employee);
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (employee.isNew()) {
            service.create(employee);
        } else {
            service.update(employee);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
