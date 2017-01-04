package com.mrigor.testTasks.department.repository.jdbc;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.repository.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of interface by Spring JDBC Template
 */
@Repository
public class EmployeeRepoImpl implements EmployeeRepo {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeRepoImpl.class);
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
            LOG.debug("create new employee {}",employee);
            Number newKey = insertEmployee.executeAndReturnKey(map);
            employee.setId(newKey.intValue());
        } else {
            LOG.debug("uodate employee {}",employee);
            if (
                    namedParameterJdbcTemplate.update(
                            "UPDATE EMPLOYEES " +
                                    "SET FULLNAME=:fullName, BIRTHDAY=:birthday, SALARY=:salary " +
                                    "WHERE id=:id", map)
                            == 0) return null;
        }
        return employee;

    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete  employee, id={}",id);
        return jdbcTemplate.update("DELETE FROM EMPLOYEES WHERE id=?", id) != 0;
    }

    @Override
    public Employee get(int id) {
        LOG.debug("get  employee, id={}",id);
        List<Employee> employees = jdbcTemplate.query("SELECT * FROM EMPLOYEES WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(employees);
    }

    @Override
    public List<Employee> getAll() {
        LOG.debug("get all  employee");
        return jdbcTemplate.query("SELECT * FROM EMPLOYEES ORDER BY FULLNAME", ROW_MAPPER);
    }

    @Override
    public List<Employee> getByDep(int departmentIid) {
        LOG.debug("get all  employee from departmentId={}",departmentIid);
        return jdbcTemplate.query("SELECT * FROM EMPLOYEES WHERE EMPLOYEES.DEPARTMENT_ID=? ORDER BY FULLNAME ", ROW_MAPPER, departmentIid);
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) {
        LOG.debug("get filteted employee, departmentId={}, from={}, to={}",departmentId,from,to);
        Date fromDate = Date.valueOf(from == null ? LocalDate.of(1800, 1, 1) : from);
        Date toDate = Date.valueOf(to == null ? LocalDate.of(3000, 1, 1) : to);
        if (departmentId != null) {
            return jdbcTemplate.query("SELECT * FROM EMPLOYEES  " +
                    "WHERE ((BIRTHDAY BETWEEN  ? AND ?)" +
                    "AND department_id=?)  " +
                    "ORDER BY FULLNAME ", ROW_MAPPER, fromDate, toDate, departmentId);
        } else {
            return jdbcTemplate.query("SELECT * FROM EMPLOYEES  " +
                    "WHERE (BIRTHDAY BETWEEN  ? AND ?)  ORDER BY FULLNAME ", ROW_MAPPER, fromDate, toDate);
        }
    }
}
