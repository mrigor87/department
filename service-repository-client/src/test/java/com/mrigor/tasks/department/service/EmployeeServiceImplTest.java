package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.mrigor.tasks.department.DepTestData.DEP1;
import static com.mrigor.tasks.department.DepTestData.DEP1_ID;
import static com.mrigor.tasks.department.EmployeeTestData.*;
import static java.time.LocalDate.of;

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
        updateEmpl.setDepartment(DEP1);
        service.update(updateEmpl);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3,updateEmpl), service.getByDep(DEP1_ID));
    }

    @Test
    public void create() throws Exception {
        Employee createEmpl=getCreated();
        createEmpl.setDepartment(DEP1);
        Employee employee = service.create(createEmpl);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(EMPL1,createEmpl,EMPL2,EMPL3), service.getByDep(DEP1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void createException() throws Exception {
        Employee createEmpl=getCreated();
        createEmpl.setDepartment(new Department(8,"",null));
        service.create(createEmpl);
    }

    @Test(expected = NotFoundException.class)
    public void updateException() throws Exception {
        Employee updateEmpl=getUpdated();
        updateEmpl.setDepartment(new Department(8,"",null));
        service.update(updateEmpl);
    }



    @Test
    public void delete() throws Exception {
        service.delete(EMPL1_ID);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3), service.getByDep(DEP1_ID));
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
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(EMPL5,EMPL4), service.getFiltered(of(1993,1,1),null,null));
    }

    @Test
    public void getFilter() throws Exception {
        MATCHER_LIGHT.assertCollectionEquals(EMPL_D1, service.getFiltered(null,null,DEP1_ID));
    }

}