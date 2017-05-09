package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.model.Employee;
import com.mrigor.tasks.department.model.adapters.LocalDateAdapter;
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

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * implementation interface of service layer with using. (department)
 * Data access by REST API using Spring RestTemplate
 */
@Service
public class EmployeeServiceClient implements EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceClient.class);



    @Autowired
    private RestTemplate restTemplate;

    private String prefixDepRestUrl;

    private String prefixEmplRestUrl;

   // @Autowired
/*    public EmployeeServiceClient(String prefix) {

        prefixDepRestUrl = prefix + "/rest/departments/";
        prefixEmplRestUrl = prefix + "/rest/employees/";
    }*/


    public EmployeeServiceClient(@Value("${myrest.port}") String restPort, @Value("${myrest.host}") String restHost) {
        prefixDepRestUrl="http://"+restHost+":"+restPort+ "/rest/departments/";
        prefixEmplRestUrl="http://"+restHost+":"+restPort+ "/rest/employees/";
    }

    @Override
    public Employee create(Employee employee) throws ResourceAccessException {
            LOG.debug("create employee ({}) by url-{}", employee, prefixEmplRestUrl);
            Employee created = restTemplate.postForObject(prefixEmplRestUrl, employee, Employee.class);
            return created;
    }

    @Override
    public void update(Employee employee) throws NotFoundException, ResourceAccessException {
            LOG.debug("update employee ({}) by url-{}", employee, prefixEmplRestUrl);
            restTemplate.put(prefixEmplRestUrl, employee);
    }

    @Override
    public void delete(int id) throws NotFoundException,ResourceAccessException {
        String currentRest = prefixEmplRestUrl + id;
        LOG.debug("delete employee, id={} by url-{}", id, currentRest);
        restTemplate.delete(currentRest);
    }
    @Override
    public Employee get(int id) throws NotFoundException,ResourceAccessException {

            String currentRest = prefixEmplRestUrl + id;
            LOG.debug("get employee, id={} by url-{}", id, currentRest);
            Employee employees = restTemplate.getForObject(currentRest, Employee.class);
            return employees;
    }

    @Override
    public List<Employee> getAll() throws ResourceAccessException {

            LOG.debug("get all employees by url-{}", prefixEmplRestUrl);
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(prefixEmplRestUrl,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
    }

    @Override
    public List<Employee> getByDep(int departmentId) throws NotFoundException,ResourceAccessException {

            String currentRest = prefixDepRestUrl + departmentId + "/employees";
            LOG.debug("get all employees from departmentId={} by url-{}", departmentId, currentRest);
            ResponseEntity<List<Employee>> emplResponse =
                    restTemplate.exchange(currentRest,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                            });
            List<Employee> employees = emplResponse.getBody();
            return employees;
    }

    @Override
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) throws ResourceAccessException {

            String currentRest = prefixEmplRestUrl + "filtered?" +
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
