package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.testTasks.department.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by Игорь on 11.12.2016.
 */
public interface DepartmentService {

    Department save(Department department);

    Department update(Department department) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Department get(int id) throws NotFoundException;

    List<Department> getAll();

/*    Department getWithEmployees(int id) throws NotFoundException*/;

    List<DepartmentWithAverageSalary> getAllWithAvgSalary();

}
