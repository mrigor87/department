package com.mrigor.tasks.department.dao;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;



import java.util.List;

/**
 * Created by igor on 011 11.05.17.
 */

public interface DepartmentDao {


    Department insert(Department department);

    Department update(Department department);

    boolean delete(int id);

    Department getWithEmployees(int id);

    Department get(int id);

    List<Department> getAll();

    List<DepartmentWithAverageSalary> getAllWithAvgSalary();
}
