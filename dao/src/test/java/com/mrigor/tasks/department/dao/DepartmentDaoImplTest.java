package com.mrigor.tasks.department.dao;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

@ContextConfiguration({
      //  "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db-test-mb.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
//@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentDaoImplTest {

    @Autowired
    private DepartmentDao dao;

  //  @Test
    public void update() throws Exception {
        Department updateDep =new Department(getUpdated());
        int update = dao.update(updateDep);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP2, updateDep), dao.getAll());
    }

   // @Test
/*    public void updateError() throws Exception {
        Department updateDep = getCreated();
        int update = dao.update(updateDep);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP2, updateDep), dao.getAll());
    }*/

  //  @Test
    public void create() throws Exception {
        Department createDep =new Department( getCreated());
        int insert = dao.insert(createDep);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP1, createDep, DEP2), dao.getAll());
    }

 //   @Test
    public void delete() throws Exception {
        dao.delete(DEP1_ID);
        MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP2), dao.getAll());
    }

 //   @Test
    public void get() throws Exception {
        Department dep = dao.get(DEP1_ID);
        MATCHER_LIGHT.assertEquals(dep, DEP1);

    }

 //   @Test
    public void getWithEmployees() throws Exception {
        Department dep = dao.getWithEmployees(DEP1_ID);

        MATCHER.assertEquals( DEP1,dep);

    }

    @Test
    public void getAll() throws Exception {
        dao.getAll();
        Arrays.asList(DEP1, DEP2);
       // MATCHER_LIGHT.assertCollectionEquals(Arrays.asList(DEP1, DEP2), dao.getAll());
    }

 //   @Test
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
    public void tt(){
        dao.getAll();
    }

}