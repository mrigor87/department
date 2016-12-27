package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 17.12.2016.
 */
@Service
public class EmployeeServiceClient implements EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceClient.class);
    @Autowired
    private String prefix;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public Employee create(Employee employee) throws ResourceAccessException {
        try {
            String currentREST = prefix + "/rest/employees/";
            LOG.debug("create employee ({}) by url-{}", employee, currentREST);
            Employee created = restTemplate.postForObject(currentREST, employee, Employee.class);
            return created;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        }

    }

    @Override
    public void update(Employee employee) throws NotFoundException, ResourceAccessException {
        try {
            LOG.debug("update employee ({}) by url-{}", employee, prefix + "/rest/employees/");
            restTemplate.put(prefix + "/rest/employees/", employee);
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found employee {}", employee);
            throw new NotFoundException("not found department " + employee);
        }
    }

    @Override
    public void delete(int id) throws NotFoundException,ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/employees/" + id;
            LOG.debug("delete employee, id={} by url-{}", id, currentRest);
            restTemplate.delete(currentRest);
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found employee with id={}", id);
            throw new NotFoundException("not found employee with id=" + id);
        }
    }

    @Override
    public Employee get(int id) throws NotFoundException,ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/employees/" + id;
            LOG.debug("get employee, id={} by url-{}", id, currentRest);
            Employee employees = restTemplate.getForObject(currentRest, Employee.class);
            return employees;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found employee with id={}", id);
            throw new NotFoundException("not found employee with id=" + id);
        }
    }

    @Override
    public List<Employee> getAll() throws ResourceAccessException {
        try {
            LOG.debug("get all employees by url-{}", prefix + "/rest/employees/");
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(prefix + "/rest/employees/",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        }
    }

    @Override
    public List<Employee> getByDep(int departmentId) throws NotFoundException,ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/departments/" + departmentId + "/employees";
            LOG.debug("get all employees from departmentId={} by url-{}", departmentId, currentRest);
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        } catch (HttpClientErrorException e) {
            LOG.error("not found department with id={}", departmentId);
            throw new NotFoundException("not found department with id=" + departmentId);
        }
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) throws ResourceAccessException {
        try {
            String currentRest = prefix + "/rest/employees/" + "filtered?" +
                    (from != null ? ("from=" + from) : "") +
                    (to != null ? ("&to=" + to) : "") +
                    (departmentId != null ? "&departmentid=" + departmentId : "");
            LOG.debug("get diltered employees with departmentId={}, from={}, to={} by url-{}", departmentId, from, to, currentRest);
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
        } catch (ResourceAccessException e) {
            LOG.error("REST API is not available: " + prefix);
            throw new ResourceAccessException("REST API is not available: " + prefix);
        }
    }

}
