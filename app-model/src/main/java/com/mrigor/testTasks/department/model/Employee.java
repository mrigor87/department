package com.mrigor.testTasks.department.model;

import java.time.LocalDate;

/**
 * Created by Игорь on 10.12.2016.
 */
public class Employee {
    private Integer id;
    private String fullName;
    private LocalDate birthDay;
    private int salary;
    private Department department;

    public Employee(Integer id, String fullName, LocalDate birthDay, int salary, Department department) {
        this.id = id;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.salary = salary;
        this.department = department;
    }

    public Employee(String fullName, LocalDate birthDay, int salary, Department department) {
        this(null, fullName, birthDay, salary, department);
    }
    public Employee(Integer id,String fullName, LocalDate birthDay, int salary) {
        this(id, fullName, birthDay, salary, null);
    }

    public Employee(String fullName, LocalDate birthDay, int salary) {
        this(null, fullName, birthDay, salary, null);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee() {
    }
    public boolean isNew(){
        return id==null;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDay=" + birthDay +
                ", salary=" + salary +
                '}';
    }
}
