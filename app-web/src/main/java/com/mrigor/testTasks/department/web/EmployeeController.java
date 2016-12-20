package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Игорь on 18.12.2016.
 */
@RequestMapping(value ="ajax/employees" )
@RestController
public class EmployeeController {

   @Autowired
    EmployeeService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAll(){
        return service.getAll();
    }


/*    @GetMapping(value = "/department/{departId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getByDepartmentId(@PathVariable("departId") int departId){*/
    @GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getByDepartmentId(@RequestParam(value = "departmentid")  Integer departmentId){
        return service.getByDep(departmentId);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee get(@PathVariable("id") int id){
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}" )
    public void delete (@PathVariable("id") int id){
         service.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(Employee employee, BindingResult result) {
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
