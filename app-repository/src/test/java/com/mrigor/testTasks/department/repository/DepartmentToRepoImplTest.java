package com.mrigor.testTasks.department.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Игорь on 10.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class DepartmentToRepoImplTest {
    @Autowired
    DepartmentToRepo repository;

    @Test
    public void getAll() throws Exception {
        System.out.println(repository.getAll());
    }

    @Test
    public void get() throws Exception {

    }

}