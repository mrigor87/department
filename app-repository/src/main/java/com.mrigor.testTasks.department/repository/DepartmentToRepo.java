package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;

import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
public interface DepartmentToRepo {
    List<DepartmentWithAverageSalary> getAll();

    DepartmentWithAverageSalary get(int id);
}
