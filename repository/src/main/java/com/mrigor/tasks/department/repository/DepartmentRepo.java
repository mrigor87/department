package com.mrigor.tasks.department.repository;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;

import java.util.List;

/**
 *interface for working with database (department)
 */
public interface DepartmentRepo {

    /**
     * update or create new record of department in database
     * @param department
     * @return updated or created entity
     */
    Department save(Department department);

    /**
     * delete record of department by id from database
     * @param id entity identifier
     * @return  false if not found
     */
    boolean delete(int id);

    /**
     * get record of department by id from database
     * @param id entity identifier
     * @return entity or null if not found
     */
    Department get(int id);

    /**
     * get all departments from database
     * @return List all entities
     */
    List<Department> getAll();

    /**
     * get all entity with information about average salary
     * @return List all entities
     */
     List<DepartmentWithAverageSalary> getAllWithAvgSalary();
}
