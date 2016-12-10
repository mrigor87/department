package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.mrigor.testTasks.department.repository.DepTestData.*;


/**
 * Created by Игорь on 10.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentRepoImplTest {

    @Autowired
    DepartmentRepo repository;

    @Test
    public void update() throws Exception {
        Department updateDep=getUpdated();
         repository.save(updateDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP2,updateDep), repository.getAll());
    }

    @Test
    public void create() throws Exception {
        Department createDep=getCreated();
        repository.save(createDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1,createDep,DEP2), repository.getAll());
    }

    @Test
    public void delete() throws Exception {
        repository.delete(DEP1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP2), repository.getAll());
    }

    @Test
    public void get() throws Exception {
        Department dep=repository.get(DepTestData.DEP1_ID);
        MATCHER.assertEquals(dep,DEP1);

    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1,DEP2), repository.getAll());
    }

    @Test
    public void getAllWithAvgSalary() throws Exception {
        MATCHER_WITH_SALARY.assertCollectionEquals( repository.getAllWithAvgSalary(),DEP_WITH_AVG_SALARY);
       ;
    }

}