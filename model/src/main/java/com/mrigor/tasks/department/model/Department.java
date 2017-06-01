package com.mrigor.tasks.department.model;



import javax.persistence.*;
import java.util.List;

/**
 * Created by Igor on 10.12.2016.
 */


@NamedQueries({
        @NamedQuery(name = Department.GET_ALL_JPQL, query = "select d from Department d order by d.name"),
        @NamedQuery(name = Department.GET_ALL_WITH_AVG_SALARY_JPQL,
                query = "SELECT NEW com.mrigor.tasks.department.to.DepartmentWithAverageSalary (d.id, d.name, CAST (AVG(e.salary) as integer)) " +
                        "FROM Department d LEFT JOIN FETCH Employee e ON d.id=e.department.id " +
                        "GROUP BY d.id ORDER BY d.name")
})
@NamedEntityGraphs({
        @NamedEntityGraph(name = Department.GRAPH_WITH_EMMPLOYEES, attributeNodes = @NamedAttributeNode("employeeList"))
})

@Entity
@Table(name = "DEPARTMENTS")
public class Department extends BaseEntity {
    public final static String GET_ALL_JPQL = "Department.getAll";
    public final static String GRAPH_WITH_EMMPLOYEES = "Department.graphWithEmployees";
    public final static String GET_ALL_WITH_AVG_SALARY_JPQL = "Department.getWithAvgSalary";

    @Column
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
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
    }

    public Department(Integer id, String name, List<Employee> employeeList) {
        this.id = id;
        this.name = name;
    }

    public Department(String name) {
        this(null, name, null);
    }

    public Department() {
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
                '}';
    }

}
