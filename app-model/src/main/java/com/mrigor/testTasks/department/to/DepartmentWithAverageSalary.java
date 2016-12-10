package com.mrigor.testTasks.department.to;

/**
 * Created by Игорь on 10.12.2016.
 */
public class DepartmentWithAverageSalary {
    private Integer id;
    private String name;
    private int averageSalary;

    public DepartmentWithAverageSalary(Integer id, String name, int averageSalary) {
        this.id = id;
        this.name = name;
        this.averageSalary = averageSalary;
    }
    public DepartmentWithAverageSalary(String name, int averageSalary) {
        this(null,name,averageSalary);
    }
}
