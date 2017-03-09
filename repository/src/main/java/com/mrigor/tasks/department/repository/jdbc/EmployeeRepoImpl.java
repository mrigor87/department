package com.mrigor.tasks.department.repository.jdbc;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.repository.EmployeeRepo;
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

    //***********************************  SQL EXPRESSIONS  *************************************************************
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE EMPLOYEES SET FULLNAME=:fullName, BIRTHDAY=:birthday, SALARY=:salary WHERE id=:id"; //named parameter
    private static final String GET_ALL_EMPLOYEES_SQL = "SELECT * FROM EMPLOYEES ORDER BY FULLNAME";
    private static final String DELETE_EMPLOYEES_BY_ID_SQL = "DELETE FROM EMPLOYEES WHERE id=?";
    private static final String GET_EMPLOYEES_BY_ID_SQL = "SELECT * FROM EMPLOYEES WHERE id=?";
    private static final String GET_EMPLOYEES_BY_DEPARTMENT_SQL = "SELECT * FROM EMPLOYEES WHERE EMPLOYEES.DEPARTMENT_ID=? ORDER BY FULLNAME ";
    private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITH_DEP_SQL = "SELECT * FROM EMPLOYEES  WHERE ((BIRTHDAY BETWEEN  ? AND ?) AND department_id=?)";
    private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITHOUT_DEP_SQL = "SELECT * FROM EMPLOYEES WHERE (BIRTHDAY BETWEEN  ? AND ?)  ORDER BY FULLNAME ";
    //*******************************************************************************************************************

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
                .addValue("departmentId", employee.getDepartmentId())
                .addValue(("salary"), employee.getSalary());

        if (employee.isNew()) {
            LOG.debug("create new employee {}", employee);
            Number newKey = insertEmployee.executeAndReturnKey(map);
            employee.setId(newKey.intValue());
        } else {
            LOG.debug("uodate employee {}", employee);
            if (namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE_SQL, map) == 0) return null;
        }
        return employee;

    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete  employee, id={}", id);
        return jdbcTemplate.update(DELETE_EMPLOYEES_BY_ID_SQL, id) != 0;
    }

    @Override
    public Employee get(int id) {
        LOG.debug("get  employee, id={}", id);
        List<Employee> employees = jdbcTemplate.query(GET_EMPLOYEES_BY_ID_SQL, ROW_MAPPER, id);
        return DataAccessUtils.singleResult(employees);
    }

    @Override
    public List<Employee> getAll() {
        LOG.debug("get all  employee");
        return jdbcTemplate.query(GET_ALL_EMPLOYEES_SQL, ROW_MAPPER);
    }

    @Override
    public List<Employee> getByDep(int departmentIid) {
        LOG.debug("get all  employee from departmentId={}", departmentIid);
        return jdbcTemplate.query(GET_EMPLOYEES_BY_DEPARTMENT_SQL, ROW_MAPPER, departmentIid);
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) {
        LOG.debug("get filteted employee, departmentId={}, from={}, to={}", departmentId, from, to);
        Date fromDate = Date.valueOf(from == null ? LocalDate.of(1800, 1, 1) : from);
        Date toDate = Date.valueOf(to == null ? LocalDate.of(3000, 1, 1) : to);
        if (departmentId != null) {
            return jdbcTemplate.query(GET_ORDERED_FILTERED_EMPLOYEES_WITH_DEP_SQL, ROW_MAPPER, fromDate, toDate, departmentId);
        } else {
            return jdbcTemplate.query(GET_ORDERED_FILTERED_EMPLOYEES_WITHOUT_DEP_SQL, ROW_MAPPER, fromDate, toDate);
        }
    }
}
