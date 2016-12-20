package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.util.exception.NotFoundException;

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
/*    List<Employee> getByDep(int id) throws NotFoundException;
    List<Employee> getBetweenDates(LocalDate from, LocalDate to);
    List<Employee> getByDate(LocalDate date);*/

    List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId);
    List<Employee> getByDep(int departmentId);

}
