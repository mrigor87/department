package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 11.12.2016.
 */
public interface EmployeeService {

    Employee create(Employee employee);
    void update(Employee employee) throws NotFoundException;
    void delete(int id) throws NotFoundException;
    Employee get(int id) throws NotFoundException;
    List<Employee> getAll();

    List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId);
    List<Employee> getByDep(int departmentId);

}
