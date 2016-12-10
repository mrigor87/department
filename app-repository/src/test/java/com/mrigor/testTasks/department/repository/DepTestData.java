package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.repository.matcher.ModelMatcher;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */
public class DepTestData {
    public static final ModelMatcher<Department> MATCHER = new ModelMatcher<>();

    public static final int DEP1_ID = 100000;
    public static final int DEP2_ID = 100001;
    public static final Department DEP1= new Department(DEP1_ID,"Marketing");
    public static final Department DEP2= new Department(DEP2_ID,"Production");
    public static final List<Department> MEALS = Arrays.asList(DEP1, DEP2);
    public static Department getCreated() {
        return new Department("New department");
    }

    public static Department getUpdated() {
        return new Department(DEP1_ID, "update department");
    }

}
