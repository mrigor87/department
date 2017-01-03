package com.mrigor.testTasks.department;

import com.mrigor.testTasks.department.matcher.ModelMatcher;
import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;

import java.util.Arrays;
import java.util.List;

/**
 * test data
 */
public class DepTestData {
    public static final ModelMatcher<Department> MATCHER = new ModelMatcher<>(Department.class);
    public static final ModelMatcher<DepartmentWithAverageSalary> MATCHER_WITH_SALARY = new ModelMatcher<>(DepartmentWithAverageSalary.class);

    public static final int DEP1_ID = 100000;
    public static final int DEP2_ID = 100001;
    public static final Department DEP1= new Department(DEP1_ID,"Marketing");
    public static final Department DEP2= new Department(DEP2_ID,"Production");
    public static final List<Department> MEALS = Arrays.asList(DEP1, DEP2);
    public static final List<DepartmentWithAverageSalary> DEP_WITH_AVG_SALARY=Arrays.asList(
            new DepartmentWithAverageSalary(100000,"Marketing",286),
            new DepartmentWithAverageSalary(100001,"Production",275)
    );
    public static Department getCreated() {
        return new Department("New department");
    }

    public static Department getUpdated() {
        return new Department(DEP1_ID, "update department");
    }

}
