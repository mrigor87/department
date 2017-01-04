package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

/**
 * implementation interface of service layer with using. (department)
 * Data access by REST API using Spring RestTemplate
 * Exceptions are handled with ExceptionInfoHandler
 */
@Service
public class EmployeeServiceClient implements EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceClient.class);
    @Autowired
    private String prefix;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Employee create(Employee employee) throws ResourceAccessException {
            String currentREST = prefix + "/rest/employees/";
            LOG.debug("create employee ({}) by url-{}", employee, currentREST);
            Employee created = restTemplate.postForObject(currentREST, employee, Employee.class);
            return created;
    }

    @Override
    public void update(Employee employee) throws NotFoundException, ResourceAccessException {
            LOG.debug("update employee ({}) by url-{}", employee, prefix + "/rest/employees/");
            restTemplate.put(prefix + "/rest/employees/", employee);
    }

    @Override
    public void delete(int id) throws NotFoundException,ResourceAccessException {
        String currentRest = prefix + "/rest/employees/" + id;
        LOG.debug("delete employee, id={} by url-{}", id, currentRest);
        restTemplate.delete(currentRest);
    }
    @Override
    public Employee get(int id) throws NotFoundException,ResourceAccessException {

            String currentRest = prefix + "/rest/employees/" + id;
            LOG.debug("get employee, id={} by url-{}", id, currentRest);
            Employee employees = restTemplate.getForObject(currentRest, Employee.class);
            return employees;
    }

    @Override
    public List<Employee> getAll() throws ResourceAccessException {

            LOG.debug("get all employees by url-{}", prefix + "/rest/employees/");
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(prefix + "/rest/employees/",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
    }

    @Override
    public List<Employee> getByDep(int departmentId) throws NotFoundException,ResourceAccessException {

            String currentRest = prefix + "/rest/departments/" + departmentId + "/employees";
            LOG.debug("get all employees from departmentId={} by url-{}", departmentId, currentRest);
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) throws ResourceAccessException {

            String currentRest = prefix + "/rest/employees/" + "filtered?" +
                    (from != null ? ("from=" + from) : "") +
                    (to != null ? ("&to=" + to) : "") +
                    (departmentId != null ? "&departmentid=" + departmentId : "");
            LOG.debug("get filtered employees with departmentId={}, from={}, to={} by url-{}", departmentId, from, to, currentRest);
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
    }

}
