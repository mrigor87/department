package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Игорь on 17.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml"
})



@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceClientTest {
/*    private static final String
            REST_URL="http://localhost:8080/department/rest/employees/";*/
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmployeeService service;

    @Test
    public void create() throws Exception {
        Employee created=EmployeeTestData.getCreated();
        created.setDepartmentId(100000);
        service.create(created,100000);
    }

    @Test
    public void update() throws Exception {
        Employee updated=EmployeeTestData.getUpdated();
        service.update(updated);
    }

    //@Test
    public void delete() throws Exception {
        service.delete(100003);
    }

    @Test
    public void get() throws Exception {
        service.get(100004);
    }

    @Test
    public void getAll() throws Exception {
        service.getAll();

    }

    @Test
    public void getByDep() throws Exception {
        List<Employee> byDep = service.getByDep(100000);
        System.out.println(byDep);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Employee> betweenDates = service.getBetweenDates(LocalDate.of(1993, 1, 1),LocalDate.of(1993, 1, 1));
        System.out.println(betweenDates);
    }

    @Test
    public void getByDate() throws Exception {
      //  REST_URL+"byDate?date=1993-01-01"
        List<Employee> byDate = service.getByDate(LocalDate.of(1993, 1, 1));
        System.out.println(byDate);

    }

}