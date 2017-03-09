package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.tasks.department.util.exception.NotFoundException;

import javax.jws.WebService;
import java.util.List;

/**
 * interface for service-layer of employees
 */
@WebService
public interface DepartmentService {
    /**
     * create new department
     * @param department
     * @return new department
     */
    Department create(Department department);

    /**
     * update department
     * @param department
     * @throws NotFoundException if @param department doesn't exist
     */
    void update(Department department) throws NotFoundException;

    /**
     * delete department by id
     * @param id identifier of departments
     * @throws NotFoundException if department with @param id doesn't exist
     */
    void delete(int id) throws NotFoundException;

    /**
     * get department by @param id
     * @param id
     * @return department by @param id
     * @throws NotFoundException if department with @param id doesn't exist
     */
    Department get(int id) throws NotFoundException;

    /**
     * @return List of departments
     */
    List<Department> getAll();

    /**
     * @return List of departments with information about average salary
     */
    List<DepartmentWithAverageSalary> getAllWithAvgSalary();

}
