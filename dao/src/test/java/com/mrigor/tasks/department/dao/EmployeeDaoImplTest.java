package com.mrigor.tasks.department.dao;

import com.mrigor.tasks.department.DepTestData;
import com.mrigor.tasks.department.EmployeeTestData;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static com.mrigor.tasks.department.DepTestData.*;
import static java.time.LocalDate.of;

/**
 * Created by Igor on 10.12.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class EmployeeDaoImplTest {

    @Autowired
    private EmployeeDao repository;

    @Test
    public void update() throws Exception {
        Employee updateEmpl= EmployeeTestData.getUpdated();
        updateEmpl.setDepartment(DepTestData.DEP1);
        repository.update(updateEmpl);
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL2, EmployeeTestData.EMPL3,updateEmpl), repository.getByDepWithDepartment(DepTestData.DEP1_ID));
    }

    @Test
    public void create() throws Exception {
        Employee createEmpl= ( EmployeeTestData.getCreated());
        createEmpl.setDepartment(DepTestData.DEP1);
        repository.insert(createEmpl);
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL1,createEmpl, EmployeeTestData.EMPL2, EmployeeTestData.EMPL3), repository.getByDep(DepTestData.DEP1_ID));
    }





    @Test
    public void delete() throws Exception {
        repository.delete(EmployeeTestData.EMPL1_ID);
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL2, EmployeeTestData.EMPL3), repository.getByDep(DepTestData.DEP1_ID));
    }

    @Test
    public void get() throws Exception {
        Employee empl=repository.get(EmployeeTestData.EMPL1_ID);
        EmployeeTestData.MATCHER.assertEquals( EmployeeTestData.EMPL1,empl);
    }

    @Test
    public void getAll() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(EmployeeTestData.EMPL_ALL, repository.getAll());
    }

    @Test
    public void getByDepWitDepartment() throws Exception {
        List<Employee> byDepWithDepartment = repository.getByDepWithDepartment(DepTestData.DEP1_ID);
        Department department = byDepWithDepartment.get(0).getDepartment();
        EmployeeTestData.MATCHER.assertCollectionEquals(EmployeeTestData.EMPL_D1, byDepWithDepartment);
        DepTestData.MATCHER.assertEquals(DEP1,department);
    }

    @Test
    public void getByDep() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(EmployeeTestData.EMPL_D1_WITHOUT_DEP, repository.getByDep(DepTestData.DEP1_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {

        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL5, EmployeeTestData.EMPL4), repository.getFiltered(of(1993,1,1),null,null));
    }

    @Test
    public void getBetweenDates2() throws Exception {

        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL5, EmployeeTestData.EMPL4), repository.getFiltered(of(1993,1,1),null,DEP2_ID));
    }

    @Test
    public void getFilteredByDep() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(EmployeeTestData.EMPL_D1, repository.getFiltered(null,null,DEP1_ID));

    }

}