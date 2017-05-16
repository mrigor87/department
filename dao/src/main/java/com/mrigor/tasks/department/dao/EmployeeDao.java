package com.mrigor.tasks.department.dao;

import com.mrigor.tasks.department.dao.sqlproviders.DynamicSQL;
import com.mrigor.tasks.department.model.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by igor on 011 11.05.17.
 */
@Repository
public interface EmployeeDao {
String INSERT_SQL="INSERT INTO employees (fullName,birthday,salary,department_id) VALUES (#{fullName},#{birthDay},#{salary},#{department.id})";
String UPDATE_SQL="UPDATE EMPLOYEES SET FULLNAME=#{fullName}, BIRTHDAY=#{birthDay}, SALARY=#{salary} WHERE id=#{id}";
String DELETE_SQL="DELETE FROM employees WHERE id=#{id}";
String SELECT_BY_ID="SELECT * FROM employees WHERE  id=#{id}";
String SELECT_ALL="SELECT * FROM employees  ORDER BY FULLNAME";
String SELECT_BY_DEPARTMENT="SELECT * FROM EMPLOYEES WHERE EMPLOYEES.DEPARTMENT_ID=#{departmentId} ORDER BY FULLNAME";


    @Insert(INSERT_SQL )
    @Options(useGeneratedKeys = true)
    int insert(Employee employee);

    @Update(UPDATE_SQL)
    int update(Employee employee);

    @Delete(DELETE_SQL)
    boolean delete(int id);


    @Select(SELECT_BY_ID)
    @ResultMap("com.mrigor.tasks.department.dao.EmployeeDao.EmployeeResult")
    Employee get(int id);

    @Select(SELECT_ALL)
    @ResultMap("com.mrigor.tasks.department.dao.EmployeeDao.EmployeeResult")
    List<Employee> getAll();


    @Select(SELECT_BY_DEPARTMENT)
/*    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "fullname", property = "fullName"),
            @Result(column = "birthday", property = "birthDay"),
            @Result(column = "salary", property = "salary")})*/
    List<Employee> getByDep(int departmentId);


    @Select(SELECT_BY_DEPARTMENT)
    @ResultMap("com.mrigor.tasks.department.dao.EmployeeDao.EmployeeResult")
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
    @SelectProvider(type = DynamicSQL.class, method = "selectFilteredEmployees")
    @ResultMap("com.mrigor.tasks.department.dao.EmployeeDao.EmployeeResult")
    List<Employee> getFiltered(@Param ("from") LocalDate from,@Param ("to") LocalDate to, @Param ("departmentid") Integer departmentid);

}