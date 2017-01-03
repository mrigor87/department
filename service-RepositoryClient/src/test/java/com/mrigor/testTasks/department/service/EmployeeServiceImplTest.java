package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.repository.EmployeeRepo;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.mrigor.testTasks.department.DepTestData.DEP1_ID;
import static com.mrigor.testTasks.department.EmployeeTestData.*;
import static java.time.LocalDate.of;
import static org.junit.Assert.*;

/**
 * tests
 */


@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db-test.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class EmployeeServiceImplTest {
    @Autowired
    EmployeeService service;

    @Test
    public void update() throws Exception {
        Employee updateEmpl=getUpdated();
        updateEmpl.setDepartmentId(DEP1_ID);
        service.update(updateEmpl);
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3,updateEmpl), service.getByDep(DEP1_ID));
    }

    @Test
    public void create() throws Exception {
        Employee createEmpl=getCreated();
        createEmpl.setDepartmentId(DEP1_ID);
        service.create(createEmpl);
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL1,createEmpl,EMPL2,EMPL3), service.getByDep(DEP1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void createException() throws Exception {
        Employee createEmpl=getCreated();
        createEmpl.setDepartmentId(8);
        service.create(createEmpl);
    }

    @Test(expected = NotFoundException.class)
    public void updateException() throws Exception {
        Employee updateEmpl=getUpdated();
        updateEmpl.setDepartmentId(8);
        service.update(updateEmpl);
    }



    @Test
    public void delete() throws Exception {
        service.delete(EMPL1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3), service.getByDep(DEP1_ID));
    }

    @Test
    public void get() throws Exception {
        Employee empl=service.get(EMPL1_ID);
        MATCHER.assertEquals(empl,EMPL1);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(EMPL_ALL, service.getAll());
    }

    @Test
    public void getByDep() throws Exception {
        MATCHER.assertCollectionEquals(EMPL_D1, service.getByDep(DEP1_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL5,EMPL4), service.getFiltered(of(1993,1,1),null,null));
    }


}