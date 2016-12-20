package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 17.12.2016.
 */
@Service
public class EmployeeServiceClient implements EmployeeService {
    private static final String
            REST_URL = "http://localhost:8080/department/rest/employees/";
    @Autowired
    RestTemplate restTemplate;
//    Department created=restTemplate.postForObject(REST_URL,department,Department.class);

    @Override
    public Employee create(Employee employee) {
        String currentREST = REST_URL;
        //employee.setDepartmentId(i);
        Employee created = restTemplate.postForObject(currentREST, employee, Employee.class);
        return created;
    }

    @Override
    public void update(Employee employee) throws NotFoundException {

        restTemplate.put(REST_URL, employee);
    }

    @Override
    public void delete(int i) throws NotFoundException {
        String currentRest = REST_URL + i;
        restTemplate.delete(currentRest);
    }

    @Override
    public Employee get(int i) throws NotFoundException {
        String currentRest = REST_URL + i;
        Employee employees = restTemplate.getForObject(currentRest, Employee.class);
        return employees;
    }

    @Override
    public List<Employee> getAll() {
        ResponseEntity<List<Employee>> emplResponse =
                restTemplate.exchange(REST_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = emplResponse.getBody();
        return employees;
    }

    @Override
    public List<Employee> getByDep(int departmentId) throws NotFoundException {
        String currentRest = REST_URL + "filtered?departmentid=" + departmentId;
        ResponseEntity<List<Employee>> emplResponse =
                restTemplate.exchange(currentRest,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = emplResponse.getBody();
        return employees;
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) {
/*        String currentRest = REST_URL + "filtered?" +
                "from=" +  "" +
                "&to=" +  "" +
                "&departmentid=" +  departmentId ;*/
        String currentRest = REST_URL + "filtered?" +
                (from != null ? ("from=" + from) : "") +
                (to != null ? ("&to=" + to) : "") +
                (departmentId != null ? "&departmentid=" + departmentId : "");
        ResponseEntity<List<Employee>> emplResponse =
                restTemplate.exchange(currentRest,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = emplResponse.getBody();
        return employees;
    }


/*    @Override
    public List<Employee> getBetweenDates(LocalDate from, LocalDate to) {

        String currentRest=REST_URL+"between?from={from}&to={to}";
        ResponseEntity<List<Employee>> emplResponse =
                restTemplate.exchange(currentRest,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        }, from,to);
        List<Employee> employees = emplResponse.getBody();
        return employees;
    }



    @Override
    public List<Employee> getByDate(LocalDate localDate) {

        String currentRest=REST_URL+"byDate?date={date}";
        ResponseEntity<List<Employee>> emplResponse =
                restTemplate.exchange(currentRest,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                        }, localDate);
        List<Employee> employees = emplResponse.getBody();
        return employees;
    }*/


}
