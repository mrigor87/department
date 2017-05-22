package com.mrigor.tasks.department.repository.jdbc;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.repository.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

    @Value("${employee.select}")
    String getAllSql;

    @Value("${employee.selectById}")
    String getByIdSql;

    @Value("${employee.selectByDepartmentId}")
    String getByDepartmentIdSql;

    @Value("${employee.selectBetween2DatesAndByDepartmentId}")
    String getBetween2DatesAndByDepartmentIdSql;

    @Value("${employee.selectBetween2Dates}")
    String getBetween2Dates;

    @Value("${employee.update}")
    String updateSql;

    @Value("${employee.deleteById}")
    String deleteSql;

    static final String ID = "id";
    static final String FULLANME = "fullName";
    static final String BIRTHDAY="birthDay";
    static final String SALARY="salary";
    static final String DEPARTMENT_ID="departmentId";
    static final String FROM_DATE="from";
    static final String TO_DATE="to";


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
                .usingGeneratedKeyColumns(ID);
    }

    @Override
    public Employee save(Employee employee) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue(ID, employee.getId())
                .addValue(FULLANME, employee.getFullName())
                .addValue((BIRTHDAY), Date.valueOf(employee.getBirthDay()))
                .addValue(DEPARTMENT_ID, employee.getDepartmentId())
                .addValue((SALARY), employee.getSalary());

        if (employee.isNew()) {
            LOG.debug("create new employee {}", employee);
            Number newKey = insertEmployee.executeAndReturnKey(map);
            employee.setId(newKey.intValue());
        } else {
            LOG.debug("update employee {}", employee);
            if (namedParameterJdbcTemplate.update(updateSql, map) == 0) return null;
        }
        return employee;

    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete  employee, id={}", id);
        SqlParameterSource param = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.update(deleteSql, param) != 0;
    }

    @Override
    public Employee get(int id) {
        LOG.debug("get  employee, id={}", id);
        SqlParameterSource param = new MapSqlParameterSource(ID, id);
        List<Employee> employees = namedParameterJdbcTemplate.query(getByIdSql, param,ROW_MAPPER);
        return DataAccessUtils.singleResult(employees);
    }

    @Override
    public List<Employee> getAll() {
        LOG.debug("get all  employee");
        return jdbcTemplate.query(getAllSql, ROW_MAPPER);
    }

    @Override
    public List<Employee> getByDep(int departmentIid) {
        LOG.debug("get all  employee from departmentId={}", departmentIid);
        SqlParameterSource param = new MapSqlParameterSource(DEPARTMENT_ID, departmentIid);
        return namedParameterJdbcTemplate.query(getByDepartmentIdSql, param, ROW_MAPPER);
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) {
      //  LOG.debug("get filteted employee, departmentId={}, from={}, to={}", departmentId, from, to);
        Date fromDate = Date.valueOf(from == null ? LocalDate.of(1800, 1, 1) : from);
        Date toDate = Date.valueOf(to == null ? LocalDate.of(3000, 1, 1) : to);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(FROM_DATE,fromDate)
                .addValue(TO_DATE,toDate);
        if (departmentId != null) {
            params.addValue(DEPARTMENT_ID,departmentId);
            return namedParameterJdbcTemplate.query(getBetween2DatesAndByDepartmentIdSql, params, ROW_MAPPER);
        } else {
            return namedParameterJdbcTemplate.query(getBetween2Dates,params, ROW_MAPPER);
        }
    }
}
