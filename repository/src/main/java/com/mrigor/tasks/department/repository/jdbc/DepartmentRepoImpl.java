package com.mrigor.tasks.department.repository.jdbc;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.repository.DepartmentRepo;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Implementation of interface by Spring JDBC Template
 */
@Repository
public class DepartmentRepoImpl implements DepartmentRepo {
    //***********************************  SQL EXPRESSIONS  *************************************************************
    private static final String GET_DEPARTMENTS_WITH_AVG_SALARY_SQL =
            "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
                    "FROM EMPLOYEES e " +
                    "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                    "GROUP BY d.ID " +
                    "ORDER  BY d.NAME";

//    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name=:name WHERE id=:id"; //named parameter
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name=:name WHERE id=100000"; //named parameter
    private static final String DELETE_DEPARTMENT_BY_ID_SQL = "DELETE FROM DEPARTMENTS WHERE id=?";
    private static final String GET_DEPARTMENT_BY_ID_SQL = "SELECT * FROM DEPARTMENTS WHERE id=?";
    private static final String GET_ALL_DEPARTMENTS_SQL = "SELECT * FROM DEPARTMENTS ORDER BY name";
    //*******************************************************************************************************************

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentRepoImpl.class);
    private static final BeanPropertyRowMapper<Department> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Department.class);

    //default mapper
    //private static final BeanPropertyRowMapper<DepartmentWithAverageSalary> ROW_MAPPER_TO = BeanPropertyRowMapper.newInstance(DepartmentWithAverageSalary.class);

    //for handling AvgSalaty==null
    private static final RowMapper<DepartmentWithAverageSalary> ROW_MAPPER_TO = (rs, rowNum) -> {
        DepartmentWithAverageSalary depWithSal = new DepartmentWithAverageSalary();
        depWithSal.setId(rs.getInt("ID"));
        depWithSal.setName(rs.getString("NAME"));

        Integer salary = rs.getInt("AVERAGESALARY");
        depWithSal.setAverageSalary(salary == null ? 0 : salary);
        return depWithSal;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertDep;

    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.debug("get all departments with avg salary");
        return jdbcTemplate.query(GET_DEPARTMENTS_WITH_AVG_SALARY_SQL, ROW_MAPPER_TO);

    }


    @Autowired
    public DepartmentRepoImpl(DataSource dataSource) {
        this.insertDep = new SimpleJdbcInsert(dataSource)
                .withTableName("departments")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Department save(Department department) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", department.getId())
                .addValue("name", department.getName());

        if (department.isNew()) {
            LOG.debug("create department {}", department);
            Number newKey = insertDep.executeAndReturnKey(map);
            department.setId(newKey.intValue());
        } else {
            LOG.debug("update department {}", department);
            if (
                    namedParameterJdbcTemplate.update(
                            UPDATE_DEPARTMENT_SQL, map)
                            == 0) return null;
        }
        return department;
    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete department, id={}", id);
        return jdbcTemplate.update(DELETE_DEPARTMENT_BY_ID_SQL, id) != 0;
    }

    @Override
    public Department get(int id) {
        LOG.debug("get department, id={}", id);
        List<Department> departments = jdbcTemplate.query(GET_DEPARTMENT_BY_ID_SQL, ROW_MAPPER, id);
        return DataAccessUtils.singleResult(departments);
    }

    @Override
    public List<Department> getAll() {
        LOG.debug("get all departments");
        return jdbcTemplate.query(GET_ALL_DEPARTMENTS_SQL, ROW_MAPPER);

    }

}
