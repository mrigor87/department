package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * implementation interface of service layer with using. (department)
 * Data access by REST API using Spring RestTemplate.
 */
@Service
public class DepartmentServiceClient implements DepartmentService {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceClient.class);


    @Autowired
    private RestTemplate restTemplate;



    private String prefixDepRestUrl;

    public DepartmentServiceClient(@Value("${myrest.port}") String restPort, @Value("${myrest.host}") String restHost) {
        prefixDepRestUrl="http://"+restHost+":"+restPort+ "/rest/departments/";
    }

    @Override
    public Department create(Department department) throws ResourceAccessException {
        LOG.debug("create department ({}) by url-{}", department, prefixDepRestUrl);
        Department created = restTemplate.postForObject(prefixDepRestUrl, department, Department.class);
        return created;

    }


    @Override
    public void update(Department department) throws NotFoundException, ResourceAccessException {
        LOG.debug("update department ({}) by url-{}", department, prefixDepRestUrl);
        restTemplate.put(prefixDepRestUrl, department);
    }


    @Override
    public void delete(int id) throws NotFoundException, ResourceAccessException {
        String currentRest = prefixDepRestUrl + id;
        LOG.debug("delete department, id={} by url-{}", id, currentRest);
        restTemplate.delete(currentRest);

    }

    @Override
    public Department get(int id) throws NotFoundException, ResourceAccessException {
        String currentRest = prefixDepRestUrl + id;
        LOG.debug("get department, id={} by url-{}", id, currentRest);
        Department department = restTemplate.getForObject(currentRest, Department.class);
        return department;
    }

    @Override
    public List<Department> getAll() {
        try {
            LOG.debug("get all department by url-{}", prefixDepRestUrl);
            ResponseEntity<List<Department>> depResponse =
                    restTemplate.exchange(prefixDepRestUrl,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>() {
                            });
            List<Department> departments = depResponse.getBody();
            return departments;
        } catch (Exception e) {
            throw new NotFoundException(prefixDepRestUrl);
        }
    }

    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() throws ResourceAccessException {

        String currentRest = prefixDepRestUrl + "withAvgSalary";
        LOG.debug("get all department with avg salary by url-{}", currentRest);
        ResponseEntity<List<DepartmentWithAverageSalary>> depResponse =
                restTemplate.exchange(currentRest,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<DepartmentWithAverageSalary>>() {
                        });
        List<DepartmentWithAverageSalary> departments = depResponse.getBody();
        return departments;

    }
}
