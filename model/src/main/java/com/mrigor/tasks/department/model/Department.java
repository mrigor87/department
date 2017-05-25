package com.mrigor.tasks.department.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Created by Igor on 10.12.2016.
 */

/*
         String INSERT_SQL="INSERT INTO departments (name) VALUES (#{name})";
         String UPDATE_SQL="UPDATE departments SET name=#{name} WHERE id=#{id}";
         String DELETE_SQL="DELETE FROM DEPARTMENTS WHERE id=#{id}";
         String SELECT_BY_ID="SELECT * FROM departments WHERE  id = #{id}";
         String SELECT_ALL="SELECT * FROM departments  ORDER BY name";

         String SELECT_WITH_AVG_SALARY= "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
         "FROM EMPLOYEES e " +
         "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
         "GROUP BY d.ID " +
         "ORDER  BY d.NAME";
         */
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Department.GET_ALL, query = "SELECT d FROM Department d  ORDER BY d.name"),
        @NamedQuery(name = Department.DELETE, query = "DELETE FROM Department d WHERE d.id=:id"),
        @NamedQuery(name = Department.GET_WITH_AVG_SALARY, query =
                "SELECT d.id, d.name, AVG(e.salary) AS averagesalary " +
                        "FROM Employee e " +
                        " JOIN Department d ON e.department.id = d.id " +
                        "GROUP BY d.id " +
                        "ORDER  BY d.name")
})

@Table(name = "departments")
@Entity
public class Department implements Serializable{
    public static final String GET_ALL = "Department.getAll";
    public static final String DELETE = "Department.delete";
    public static final String GET_WITH_AVG_SALARY = "Department.getWithAvgSalary";
    // @JsonProperty("id")
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    //  @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "department")
    private List<Employee> employeeList;


    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }


    public Department(Department department) {
        this.id = department.id == null ? null : new Integer(department.id);
        this.name = department.name;
        this.employeeList = department.employeeList == null ? null : new ArrayList<>(employeeList);
    }

    public Department(Integer id, String name, List<Employee> employeeList) {
        this.id = id;
        this.name = name;
        this.employeeList = employeeList;
    }

    public Department(String name) {
        this(null, name, null);
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
