package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
public interface EmployeeRepo {

    Employee save(Employee employee);

    // false if not found
    boolean delete(int id);

    // null if not found
    Employee get(int id);

    List<Employee> getAll();

    List<Employee> getByDep(int id);

    List<Employee> getBetweenDates(LocalDate from, LocalDate to);

   default Employee getWithDepartment(int id){
       throw  new UnsupportedOperationException();
   };




}
