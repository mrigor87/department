package com.mrigor.tasks.department.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.mrigor.tasks.department.model.adapters.LocalDateAdapter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Created by Igor on 10.12.2016.
 */
/*

String INSERT_SQL="INSERT INTO employees (fullName,birthday,salary,department_id) VALUES (#{fullName},#{birthDay},#{salary},#{department.id})";
        String UPDATE_SQL="UPDATE EMPLOYEES SET FULLNAME=#{fullName}, BIRTHDAY=#{birthDay}, SALARY=#{salary} WHERE id=#{id}";
        String DELETE_SQL="DELETE FROM employees WHERE id=#{id}";
        String SELECT_BY_ID="SELECT * FROM employees WHERE  id=#{id}";
        String SELECT_ALL="SELECT * FROM employees  ORDER BY FULLNAME";
        String SELECT_BY_DEPARTMENT="SELECT * FROM EMPLOYEES WHERE EMPLOYEES.DEPARTMENT_ID=#{departmentId} ORDER BY FULLNAME";
*/

@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Employee.GET_ALL, query = "SELECT e FROM Employee e  ORDER BY e.fullName"),
        @NamedQuery(name = Employee.DELETE, query = "DELETE FROM Employee e WHERE e.id=:id")
})


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)

@Table(name = "employees")
@Entity
public class Employee implements Serializable {
    public static final String GET_ALL = "Employee.getAll";
    public static final String DELETE = "Employee.delete";

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "fullname")
    private String fullName;

/*
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
*/

    @Column(name = "birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @Column(name = "salary")
    private int salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
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

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDay=" + birthDay +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }
}
