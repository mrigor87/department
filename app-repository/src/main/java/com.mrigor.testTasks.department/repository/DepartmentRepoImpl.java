package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
@Repository
public class DepartmentRepoImpl implements DepartmentRepo {

    private static final BeanPropertyRowMapper<Department> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Department.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertDep;


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
                .addValue("name", department.getName())
;

        if (department.isNew()) {
            Number newKey = insertDep.executeAndReturnKey(map);
            department.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE DEPARTMENTS SET name=:name WHERE id=:id", map);
        }
        return department;
    }

    @Override
    public boolean delete(int id)
    {
        return jdbcTemplate.update("DELETE FROM DEPARTMENTS WHERE id=?", id) != 0;
    }

    @Override
    public Department get(int id) {

        List<Department> departments = jdbcTemplate.query("SELECT * FROM DEPARTMENTS WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(departments);
    }

    @Override
    public List<Department> getAll() {
        return jdbcTemplate.query("SELECT * FROM DEPARTMENTS ORDER BY name", ROW_MAPPER);

    }

    @Override
    public Department getWithEmployees(int id) {
        return null;
    }
}
