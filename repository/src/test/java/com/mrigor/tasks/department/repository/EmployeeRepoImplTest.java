package com.mrigor.tasks.department.repository;

import com.mrigor.tasks.department.DepTestData;
import com.mrigor.tasks.department.EmployeeTestData;
import com.mrigor.tasks.department.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.mrigor.tasks.department.DepTestData.DEP2_ID;
import static java.time.LocalDate.of;

/**
 * Created by Igor on 10.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db-test.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class EmployeeRepoImplTest {

    @Autowired
    private EmployeeRepo repository;

    @Test
    public void update() throws Exception {
        Employee updateEmpl= EmployeeTestData.getUpdated();
        updateEmpl.setDepartmentId(DepTestData.DEP1_ID);
        repository.save(updateEmpl);
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL2, EmployeeTestData.EMPL3,updateEmpl), repository.getByDep(DepTestData.DEP1_ID));
    }

    @Test
    public void create() throws Exception {
        Employee createEmpl= EmployeeTestData.getCreated();
        createEmpl.setDepartmentId(DepTestData.DEP1_ID);
        repository.save(createEmpl);
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL1,createEmpl, EmployeeTestData.EMPL2, EmployeeTestData.EMPL3), repository.getByDep(DepTestData.DEP1_ID));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createException() throws Exception {
        Employee createEmpl= EmployeeTestData.getCreated();
        createEmpl.setDepartmentId(8);
        repository.save(createEmpl);
    }

/*    @Test()
    public void updateException() throws Exception {
        Employee updateEmpl=getUpdated();
        updateEmpl.setDepartmentId(8);
        repository.save(updateEmpl);
        MATCHER.assertCollectionEquals(Arrays.asList(EMPL2,EMPL3,updateEmpl), repository.getByDep(DEP1_ID));

    }*/

    @Test
    public void delete() throws Exception {
        repository.delete(EmployeeTestData.EMPL1_ID);
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL2, EmployeeTestData.EMPL3), repository.getByDep(DepTestData.DEP1_ID));
    }

    @Test
    public void get() throws Exception {
        Employee empl=repository.get(EmployeeTestData.EMPL1_ID);
        EmployeeTestData.MATCHER.assertEquals(empl, EmployeeTestData.EMPL1);
    }

    @Test
    public void getAll() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(EmployeeTestData.EMPL_ALL, repository.getAll());
    }

    @Test
    public void getByDep() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(EmployeeTestData.EMPL_D1, repository.getByDep(DepTestData.DEP1_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL5, EmployeeTestData.EMPL4), repository.getFiltered(of(1993,1,1),null,null));
    }

    @Test
    public void getBetweenDates2() throws Exception {
        EmployeeTestData.MATCHER.assertCollectionEquals(Arrays.asList(EmployeeTestData.EMPL5), repository.getFiltered(of(1993,6,1),null,DEP2_ID));
    }

}