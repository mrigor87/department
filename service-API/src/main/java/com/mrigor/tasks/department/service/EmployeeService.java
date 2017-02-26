package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.model.adapters.LocalDateAdapter;
import com.mrigor.tasks.department.util.exception.NotFoundException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 11.12.2016.
 */
@WebService
/*@SOAPBinding(style = SOAPBinding.Style.RPC)*/
public interface EmployeeService {

    Employee create(Employee employee);

    void update(Employee employee) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Employee get(int id) throws NotFoundException;

    List<Employee> getAll();


    @WebMethod
    List<Employee> getFiltered(@XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate from, @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate to, Integer departmentId);

    List<Employee> getByDep(int departmentId);

}
