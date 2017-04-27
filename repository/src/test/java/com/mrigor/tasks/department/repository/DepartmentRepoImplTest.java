package com.mrigor.tasks.department.repository;

import com.mrigor.tasks.department.DepartmentApplication;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.repository.DepartmentRepo;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mrigor.tasks.department.DepTestData.*;


/**
 * Created by Igor on 10.12.2016.
 */

@SpringBootTest(classes = DepartmentApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:data.sql")
public class DepartmentRepoImplTest {

    @Autowired
    private DepartmentRepo repository;

    @Test
    public void update() throws Exception {
        Department updateDep = getUpdated();
        repository.save(updateDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP2, updateDep), repository.getAll());
    }

    @Test
    public void create() throws Exception {
        Department createDep = getCreated();
        repository.save(createDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1, createDep, DEP2), repository.getAll());
    }

    @Test
    public void delete() throws Exception {
        repository.delete(DEP1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP2), repository.getAll());
    }

    @Test
    public void get() throws Exception {
        Department dep = repository.get(DEP1_ID);
        MATCHER.assertEquals(dep, DEP1);

    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1, DEP2), repository.getAll());
    }

    @Test
    public void getAllWithAvgSalary() throws Exception {
        MATCHER_WITH_SALARY.assertCollectionEquals(repository.getAllWithAvgSalary(), DEP_WITH_AVG_SALARY);

    }

    @Test
    public void getAllWithAvgSalaryWithEmptyDep() throws Exception {
        List<DepartmentWithAverageSalary> newTestDepWithSal = new ArrayList<>(DEP_WITH_AVG_SALARY);
        DepartmentWithAverageSalary newRecord = new DepartmentWithAverageSalary(100007, "test", 0);
        newTestDepWithSal.add(newRecord);
        Department newEmptyDep = new Department("test");
        repository.save(newEmptyDep);
        MATCHER_WITH_SALARY.assertCollectionEquals(repository.getAllWithAvgSalary(), newTestDepWithSal);

    }

}