package com.mrigor.testTasks.department.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Created by Игорь on 10.12.2016.
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class Employee {
    private Integer id;
    private String fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" )
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)

    private LocalDate birthDay;
    private int salary;
    //@JsonIgnore
    private Integer departmentId;

    public Employee(Integer id, String fullName, LocalDate birthDay, int salary, Integer departmentId) {
        this.id = id;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public Employee(String fullName, LocalDate birthDay, int salary, Integer departmentId) {
        this(null, fullName, birthDay, salary, departmentId);
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
