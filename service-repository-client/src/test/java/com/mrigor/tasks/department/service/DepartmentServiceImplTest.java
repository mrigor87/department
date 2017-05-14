package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.mrigor.tasks.department.DepTestData.*;


/**
 * tests
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db-test.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentServiceImplTest {

    @Autowired
    DepartmentService service;

    @Test
    public void create() throws Exception {
        Department createDep=getCreated();
        Department department = service.create(createDep);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP1,createDep,DEP2), service.getAll());
    }


    @Test
    public void update() throws Exception {
        Department updateDep=getUpdated();
        service.update(updateDep);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP2,updateDep), service.getAll());
    }


    @Test
    public void delete() throws Exception {
        service.delete(DEP1_ID);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP2), service.getAll());
    }
    @Test(expected = NotFoundException.class)
    public void deleteNotExist() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Department dep=service.get(DEP1_ID);
        MATCHER.assertEquals(dep,DEP1);
    }
    @Test(expected = NotFoundException.class)
    public void getNotExist() throws Exception {
        Department dep=service.get(1);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP1,DEP2), service.getAll());
    }

    @Test
    public void getAllWithAvgSalary() throws Exception {
        MATCHER_WITH_SALARY.assertCollectionEquals( service.getAllWithAvgSalary(),DEP_WITH_AVG_SALARY);
    }

}