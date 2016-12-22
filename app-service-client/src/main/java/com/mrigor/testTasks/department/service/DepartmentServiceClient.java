package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Игорь on 17.12.2016.
 */
@Service
public class DepartmentServiceClient implements DepartmentService {
    private static final String
            REST_URL="http://localhost:8080/department/rest/departments/";
    @Autowired
    RestTemplate restTemplate;





    @Override
    public Department create(Department department) {

        Department created=restTemplate.postForObject(REST_URL,department,Department.class);

        return created;
    }




    @Override
    public void update(Department department) throws NotFoundException {
        restTemplate.put(REST_URL,department);
    }



    @Override
    public void delete(int i) throws NotFoundException {
        String currentRest=REST_URL+i;
        restTemplate.delete(currentRest);

    }

    @Override
    public Department get(int i) throws NotFoundException {
        String currentRest=REST_URL+i;
        Department department = restTemplate.getForObject(currentRest, Department.class);
        return department;
    }

    @Override
    public List<Department> getAll() {
        ResponseEntity<List<Department>> depResponse =
                restTemplate.exchange(REST_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>() {
                        });
        List<Department> departments = depResponse.getBody();
        return departments;
    }


    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        String currentRest=REST_URL+"getAllWithAvgSalary";
        ResponseEntity<List<DepartmentWithAverageSalary>> depResponse =
                restTemplate.exchange(currentRest,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<DepartmentWithAverageSalary>>() {
                        });
        List<DepartmentWithAverageSalary> departments = depResponse.getBody();
        return departments;
    }
}
