package com.mrigor.tasks.department.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Created by Igor on 10.12.2016.
 */

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class Department {
    // @JsonProperty("id")
    private Integer id;

    //  @JsonProperty("name")
    private String name;

    private List<Employee> employeeList;


    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }


/*    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }*/
    public Department (Department department){
        this.id=department.id==null?null: new Integer(department.id);
        this.name=department.name;
        this.employeeList=department.employeeList==null?null: new ArrayList<>(employeeList);
    }

    public Department(Integer id, String name, List<Employee> employeeList) {
        this.id = id;
        this.name = name;
        this.employeeList=employeeList;
    }

    public Department(String name) {
        this(null, name,null);
    }

    public Department() {
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

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees='" + employeeList +
                '}';
    }

    public boolean isNew() {
        return id == null;
    }
}
