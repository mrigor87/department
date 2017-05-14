package com.mrigor.tasks.department.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mrigor.tasks.department.model.adapters.LocalDateAdapter;


import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Created by Igor on 10.12.2016.
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class Employee {
    private Integer id;
    private String fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
/*@ApiModelProperty(dataType ="string",example = "2016-01-01")*/
    private LocalDate birthDay;
    private int salary;
    //@JsonIgnore
    //private Integer departmentId;
    private Department department;

    public Employee(Integer id, String fullName, LocalDate birthDay, int salary, Department department) {
        this.id = id;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.salary = salary;
        this.department = department;
    }
public Employee (Employee employee){
        this.id=employee.id==null?null: new Integer(employee.id);
        this.fullName=employee.fullName;
        this.birthDay=employee.birthDay==null?null: LocalDate.from(birthDay);
        this.salary=employee.salary;
        this.department=employee.department==null?null: new Department(employee.department);
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
