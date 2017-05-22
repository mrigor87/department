package com.mrigor.tasks.department.dao;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static com.mrigor.tasks.department.DepTestData.*;
import static com.mrigor.tasks.department.EmployeeTestData.EMPL_D1;
import static com.mrigor.tasks.department.EmployeeTestData.EMPL_D1_WITHOUT_DEP;


/**
 * Created by Igor on 10.12.2016.
 */

@ContextConfiguration({
        //  "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db-test-mb.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentDaoImplTest {

    @Autowired
    private DepartmentDao dao;

      @Test
    public void update() throws Exception {
          String n="";
          String s1[]=n.split(" ");
          Map<String, Double> map = new TreeMap<>();
          String s="1.35";
          map.put(s, Double.parseDouble(s));
          map.put(s, map.get(s)+Double.parseDouble(s));


        Department updateDep = new Department(getUpdated());
        int update = dao.update(updateDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP2, updateDep), dao.getAll());
    }



      @Test
    public void create() throws Exception {
        Department createDep = getCreated();
        int insert = dao.insert(createDep);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP1, createDep, DEP2), dao.getAll());
    }

    //   @Test
    public void delete() throws Exception {
        dao.delete(DEP1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(DEP2), dao.getAll());
    }

    @Test
    public void get() throws Exception {
        Department dep = dao.get(DEP1_ID);
        MATCHER.assertEquals(dep, DEP1);

    }

    @Test
    public void getWithEmployees() throws Exception {
        Department dep = dao.getWithEmployees(DEP1_ID);
        Department testDep=new Department(DEP1);
        testDep.setEmployeeList(EMPL_D1_WITHOUT_DEP);
        MATCHER.assertEquals(testDep, dep);

    }

    @Test
    public void getAll() throws Exception {
        dao.getAll();
        Arrays.asList(DEP1, DEP2);
         MATCHER.assertCollectionEquals(Arrays.asList(DEP1, DEP2), dao.getAll());
    }

       @Test
    public void getAllWithAvgSalary() throws Exception {
        MATCHER_WITH_SALARY.assertCollectionEquals(dao.getAllWithAvgSalary(), DEP_WITH_AVG_SALARY);

    }

    // @Test
    public void getAllWithAvgSalaryWithEmptyDep() throws Exception {
        List<DepartmentWithAverageSalary> newTestDepWithSal = new ArrayList<>(DEP_WITH_AVG_SALARY);
        DepartmentWithAverageSalary newRecord = new DepartmentWithAverageSalary(100007, "test", 0);
        newTestDepWithSal.add(newRecord);
        Department newEmptyDep = new Department("test");
        dao.insert(newEmptyDep);
        MATCHER_WITH_SALARY.assertCollectionEquals(dao.getAllWithAvgSalary(), newTestDepWithSal);

    }

    @Test
    public void tt() {
        dao.getAll();
    }

}