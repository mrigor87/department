package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 *interface for working with database (employee)
 */
public interface EmployeeRepo {
    /**
     *  update or create new record of employee in database
     * @param employee
     * @return updated or created entity
     */
    Employee save(Employee employee);

    /**
     * delete record of employee by id from database
     * @param id entity identifier
     * @return  false if not found
     */
    boolean delete(int id);

    /**
     * get record of employee by id from database
     * @param id entity identifier
     * @return entity or null if not found
     */
    Employee get(int id);

    /**
     * get all employees from database
     * @return List all entities
     */
    List<Employee> getAll();

    /**
     * get all employees by department from database
     * @param departmentId identifier of department
     * @return employee's list, or empty list if missing
     */
    List<Employee> getByDep(int departmentId);

    /**
     * get all employees by filter from database
     * parameters of filter
     * @param from day of birth
     * @param to day of birth
     * @param departmentId identifier of department
     * @return employee's list or empty list if missing
     */
    List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId);

   default Employee getWithDepartment(int id){
       throw  new UnsupportedOperationException();
   };




}
