package com.mrigor.testTasks.department.repository.jdbc;

import com.mrigor.testTasks.department.repository.DepartmentToRepo;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
//@Repository
public class DepartmentToRepoImpl implements DepartmentToRepo {
    private static final BeanPropertyRowMapper<DepartmentWithAverageSalary> ROW_MAPPER = BeanPropertyRowMapper.newInstance(DepartmentWithAverageSalary.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<DepartmentWithAverageSalary> getAll() {
        return jdbcTemplate.query("SELECT d.ID, d.NAME, AVG(e.SALARY) as averagesalary " +
                "FROM EMPLOYEES e " +
                "INNER JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                "GROUP BY d.ID"
                ,ROW_MAPPER);

    }

    @Override
    public DepartmentWithAverageSalary get(int id) {
        return null;
    }
}
