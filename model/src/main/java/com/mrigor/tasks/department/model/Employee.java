package com.mrigor.tasks.department.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mrigor.tasks.department.model.adapters.LocalDateAdapter;


import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Igor on 10.12.2016.
 */
@NamedQueries({
        @NamedQuery(name = Employee.GET_ALL_JPQL, query = "SELECT e FROM Employee e order by e.fullName"),
        @NamedQuery(name = Employee.GET_BY_DEPARTMENT_ID_JPQL, query = "SELECT  e FROM Employee e WHERE  e.department.id=:departmentId ORDER BY e.fullName"),
        @NamedQuery(name = Employee.GET_BY_DEPARTMENT_ID_WITH_EMPLOYYES_JPQL,
                query = "SELECT  e FROM Employee e  JOIN FETCH e.department WHERE  e.department.id=:departmentId ORDER BY e.fullName"),
        @NamedQuery(name = Employee.GET_FILTERED,
                query = "SELECT e FROM Employee e " +
                        "WHERE ((:departmentId is null) or (:departmentId=e.department.id)) AND (e.birthDay BETWEEN :from AND :to) " +
                        "ORDER BY e.fullName"),

}
)
@Entity
@Table(name = "EMPLOYEES")
public class Employee extends BaseEntity implements Serializable {
    public static final String GET_ALL_JPQL = "Employee.getAll";
    public static final String GET_BY_DEPARTMENT_ID_JPQL = "Employee.getByDepartmentId";
    public static final String GET_BY_DEPARTMENT_ID_WITH_EMPLOYYES_JPQL = "Employee.getByDepartmentIdWithEmployees";
    public static final String GET_FILTERED = "Employee.getFiltered";

    @Column
    private String fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)

    @Column
    private LocalDate birthDay;

    @Column
    private int salary;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public Employee(Integer id, String fullName, LocalDate birthDay, int salary, Department department) {
        this.id = id;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.salary = salary;
        this.department = department;
    }

    public Employee(Employee employee) {
        this.id = employee.id == null ? null : new Integer(employee.id);
        this.fullName = employee.fullName;
        this.birthDay = employee.birthDay == null ? null : LocalDate.from(birthDay);
        this.salary = employee.salary;
        this.department = employee.department == null ? null : new Department(employee.department);
    }

    public Employee(String fullName, LocalDate birthDay, int salary, Department department) {
        this(null, fullName, birthDay, salary, department);
    }

    public Employee(Integer id, String fullName, LocalDate birthDay, int salary) {
        this(id, fullName, birthDay, salary, null);
    }

    public Employee(String fullName, LocalDate birthDay, int salary) {
        this(null, fullName, birthDay, salary, null);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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
