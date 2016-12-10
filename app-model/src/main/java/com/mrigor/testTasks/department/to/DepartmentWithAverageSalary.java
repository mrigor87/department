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

    public DepartmentWithAverageSalary() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(int averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return "DepartmentWithAverageSalary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", averageSalary=" + averageSalary +
                '}';
    }
}
