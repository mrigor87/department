package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Игорь on 17.12.2016.
 */
@Service
public class DepartmentServiceClient implements DepartmentService {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceClient.class);


    @Autowired
    private String prefix;


    @Autowired
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }


    @Override
    public Department create(Department department) throws ResourceAccessException{
        try {
            String currentRest=prefix + "/rest/departments/";
            LOG.debug("create department ({}) by url-{}", department, currentRest);
            Department created = restTemplate.postForObject(currentRest, department, Department.class);
            return created;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: "+prefix);
            throw new ResourceAccessException("REST API is not available: "+prefix);
        }
    }


    @Override
    public void update(Department department) throws NotFoundException, ResourceAccessException {
        try {
            String currentRest=prefix + "/rest/departments/";
            LOG.debug("update department ({}) by url-{}", department, currentRest);
            restTemplate.put(currentRest, department);
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: "+prefix);
            throw new ResourceAccessException("REST API is not available: "+prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found department {}", department);
            throw new NotFoundException("not found department " + department);
        }
    }


    @Override
    public void delete(int id) throws NotFoundException, ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/departments/" + id;
            LOG.debug("delete department, id={} by url-{}", id, currentRest);

            restTemplate.delete(currentRest);
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: "+prefix);
            throw new ResourceAccessException("REST API is not available: "+prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found department with id={}", id);
            throw new NotFoundException("not found department with id=" + id);
        }
    }

    @Override
    public Department get(int id) throws NotFoundException, ResourceAccessException {
        //Department department;
        try {
            String currentRest = prefix + "/rest/departments/" + id;
            LOG.debug("get department, id={} by url-{}", id, currentRest);
            Department department = restTemplate.getForObject(currentRest, Department.class);
            return department;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: "+prefix);
            throw new ResourceAccessException("REST API is not available: "+prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found department with id={}", id);
            throw new NotFoundException("not found department with id=" + id);
        }

    }

    @Override
    public List<Department> getAll() throws ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/departments/";
            LOG.debug("get all department by url-{}", currentRest);
            ResponseEntity<List<Department>> depResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>() {
                            });
            List<Department> departments = depResponse.getBody();
            return departments;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: "+prefix);
            throw new ResourceAccessException("REST API is not available: "+prefix);
        }
    }


    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() throws ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/departments/" + "withAvgSalary";
            LOG.debug("get all department with avg salary by url-{}", currentRest);
            ResponseEntity<List<DepartmentWithAverageSalary>> depResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<DepartmentWithAverageSalary>>() {
                            });
            List<DepartmentWithAverageSalary> departments = depResponse.getBody();
            return departments;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: "+prefix);
            throw new ResourceAccessException("REST API is not available: "+prefix);
        }
    }
}