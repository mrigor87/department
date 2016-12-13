package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
@Repository
public class EmployeeRepoImpl implements EmployeeRepo {
    private static final BeanPropertyRowMapper<Employee> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Employee.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertEmployee;


    @Autowired
    public EmployeeRepoImpl(DataSource dataSource) {
        this.insertEmployee = new SimpleJdbcInsert(dataSource)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Employee save(Employee employee) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", employee.getId())
                .addValue("fullName", employee.getFullName())
                .addValue(("birthday"), Date.valueOf(employee.getBirthDay()))
/*                .addValue(("birthday"),employee.getBirthDay())
                .addValue(new Date.valueOf(employee.getBirthDay()))*/
                .addValue("departmentId", employee.getDepartmentId())
                .addValue(("salary"), employee.getSalary());

        if (employee.isNew()) {

            Number newKey = insertEmployee.executeAndReturnKey(map);
            employee.setId(newKey.intValue());
        } else {
            if (
                    namedParameterJdbcTemplate.update(
                            "UPDATE EMPLOYEES " +
                                    "SET FULLNAME=:fullName, BIRTHDAY=:birthday, SALARY=:salary " +
                                    "WHERE id=:id AND DEPARTMENT_ID=:departmentId", map)
                            == 0) return null;
        }
        return employee;

    }

    @Override
    public boolean delete(int id) {

        return jdbcTemplate.update("DELETE FROM EMPLOYEES WHERE id=?", id) != 0;
    }

    @Override
    public Employee get(int id) {
        List<Employee> employees = jdbcTemplate.query("SELECT * FROM EMPLOYEES WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(employees);
    }

    @Override
    public List<Employee> getAll() {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEES ORDER BY FULLNAME", ROW_MAPPER);
    }

    @Override
    public List<Employee> getByDep(int id) {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID=? ORDER BY FULLNAME ", ROW_MAPPER, id);
    }

    @Override
    public List<Employee> getBetweenDates(LocalDate from, LocalDate to) {
/*        LocalDate fromDate = from == null ? LocalDate.MIN : from;
        LocalDate toDate = to == null ? LocalDate.MAX : to;*/
        Date fromDate = Date.valueOf(from == null ? LocalDate.of(1800, 1, 1) : from);
        Date toDate = Date.valueOf(to == null ? LocalDate.of(3000, 1, 1) : to);

        return jdbcTemplate.query("SELECT * FROM EMPLOYEES  " +
                "WHERE (BIRTHDAY BETWEEN  ? AND ?)  ORDER BY FULLNAME ", ROW_MAPPER, fromDate, toDate);

    }
}
