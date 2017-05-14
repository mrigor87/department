package com.mrigor.tasks.department.dao;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 011 11.05.17.
 */
public interface DepartmentDao {


    /**
     * update or create new record of department in database
     *
     * @param department
     * @return updated or created entity
     */
    @Insert("INSERT INTO departments (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true)
    void insert(Department department);


    /**
     * update or create new record of department in database
     *
     * @param department
     * @return updated or created entity
     */
    @Update("UPDATE departments SET name=#{name} WHERE id=#{id}")
    void update(Department department);

    @Delete("DELETE FROM DEPARTMENTS WHERE id=#{id}")
    boolean delete(int id);

/*               @Results({
            @Result(id=true, column="id", property="id"),
            @Result(column="fullname", property="fullName"),
            @Result(column="birthday", property="birthDay"),
            @Result(column="salary", property="salary"),
            @Result(property="department", column="department_id",
                    one=@One(select="com.mrigor.tasks.department.dao.DepartmentDao.get"))
    })*/

    @Select("SELECT * FROM departments WHERE  id = #{id}")
    @ResultMap("com.mrigor.tasks.department.dao.DepartmentDao.DepartmentResult")
    Department getWithEmployees(int id);


    /**
     * get record of department by id from database
     *
     * @param id entity identifier
     * @return entity or null if not found
     */
    @Select("SELECT * FROM departments WHERE  id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
/*            ,
            @Result(property = "employeeList", column = "id",
                    many = @Many(select = "com.mrigor.tasks.department.dao.EmployeeDao.getByDep"))*/
    })
    Department get(int id);

    /**
     * get all departments from database
     *
     * @return List all entities
     */
    @Select("SELECT * FROM departments  ORDER BY name")
/*    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "employeeList", column = "id",
                    many = @Many(select = "com.mrigor.tasks.department.dao.EmployeeDao.getByDep"))
    })*/
    List<Department> getAll();

    /**
     * get all entity with information about average salary
     *
     * @return List all entities
     */
    @Select(
            "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
                    "FROM EMPLOYEES e " +
                    "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                    "GROUP BY d.ID " +
                    "ORDER  BY d.NAME"
    )
    //@ResultMap("com.mrigor.tasks.department.dao.DepartmentDao.DepartmentWithAverageSalaryResult")
    List<DepartmentWithAverageSalary> getAllWithAvgSalary();
}
