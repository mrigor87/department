package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;

import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
public interface DepartmentRepo {


Department save(Department department);

    // false if not found
    boolean delete(int id);

    // null if not found
    Department get(int id);

    List<Department> getAll();

    Department getWithEmployees(int id);

     List<DepartmentWithAverageSalary> getAllWithAvgSalary();
}
