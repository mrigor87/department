package com.mrigor.tasks.department.dao;



import com.mrigor.tasks.department.model.Employee;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by igor on 011 11.05.17.
 */
@Repository
public interface EmployeeDao {



    Employee insert(Employee employee);

    Employee update(Employee employee);

    boolean delete(int id);

    Employee get(int id);

    List<Employee> getAll();

    List<Employee> getByDep(int departmentId);

    List<Employee> getByDepWithDepartment(int departmentId);

    /**
     * get all employees by filter from database
     * parameters of filter
     *
     * @param from         day of birth
     * @param to           day of birth
     * @param departmentid identifier of department
     * @return employee's list or empty list if missing
     */

    List<Employee> getFiltered(LocalDate from,LocalDate to,  Integer departmentid);

}