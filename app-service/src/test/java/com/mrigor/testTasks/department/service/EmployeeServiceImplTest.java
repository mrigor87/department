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
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Игорь on 11.12.2016.
 */


/*@ContextConfiguration({
        "classpath:spring/mock.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")*/

public class EmployeeServiceImplTest {
  //  @Autowired
  //  EmployeeService service;

    @Test
    public void save() throws Exception {

    }

 //   @Test(expected = NotFoundException.class)
    public void update() throws Exception {
       // service.update(new Employee(1,"fsd",LocalDate.of(2000,1,1),100),10000);
    }

   // @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
    //    service.delete(42);
    }

    //@Test(expected = NotFoundException.class)
    public void get() throws Exception {
      //  service.get(42);
    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getByDep() throws Exception {

    }

    @Test
    public void getBetweenDates() throws Exception {

    }

    @Test
    public void getByDate() throws Exception {

    }

}