package com.mrigor.tasks.department.repository.jdbc;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.repository.DepartmentRepo;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Implementation of interface by Spring JDBC Template
 */
@Repository
public class DepartmentRepoImpl implements DepartmentRepo {

    @Value("${department.select}")
    String getAllSql;

    @Value("${department.selectById}")
    String getByIdSql;

    @Value("${department.update}")
    String updateSql;

    @Value("${department.deleteById}")
    String deleteSql;

    @Value("${department.selectWithAvgSalary}")
    String getWithAvgSalarySql;

    static final String ID = "id";
    static final String NAME = "name";

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertDep;

    //default mapper
    private static final BeanPropertyRowMapper<Department> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Department.class);

    //custom mapper for handling AvgSalaty==null
    private static final RowMapper<DepartmentWithAverageSalary> ROW_MAPPER_TO = (rs, rowNum) ->
    {
        DepartmentWithAverageSalary depWithSal = new DepartmentWithAverageSalary();
        depWithSal.setId(rs.getInt("ID"));
        depWithSal.setName(rs.getString("NAME"));
        Integer salary = rs.getInt("AVERAGESALARY");
        depWithSal.setAverageSalary(salary == null ? 0 : salary);
        return depWithSal;
    };


    @Autowired
    public DepartmentRepoImpl(DataSource dataSource) {
        this.insertDep = new SimpleJdbcInsert(dataSource)
                .withTableName("departments")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public Department save(Department department) {

        SqlParameterSource map = new MapSqlParameterSource()
                .addValue(ID, department.getId())
                .addValue(NAME, department.getName());

        if (department.isNew()) {
            LOG.debug("create department {}", department);
            Number newKey = insertDep.executeAndReturnKey(map);
            department.setId(newKey.intValue());
        } else {
            LOG.debug("update department {}", department);
            if (namedParameterJdbcTemplate.update(updateSql, map) == 0)
                return null;
        }
        return department;
    }

    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.debug("get all departments with avg salary");
        return jdbcTemplate.query(getWithAvgSalarySql, ROW_MAPPER_TO);
    }


    @Override
    public boolean delete(int id) {
        LOG.debug("delete department, id={}", id);
        SqlParameterSource param = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.update(deleteSql, param) != 0;
    }

    @Override
    public Department get(int id) {
        LOG.debug("get department, id={}", id);
        SqlParameterSource param = new MapSqlParameterSource(ID, id);
        List<Department> departments = namedParameterJdbcTemplate.query(getByIdSql, param, ROW_MAPPER);
        return DataAccessUtils.singleResult(departments);
    }

    @Override
    public List<Department> getAll() {
        LOG.debug("get all departments");
        return jdbcTemplate.query(getAllSql, ROW_MAPPER);
    }

}
