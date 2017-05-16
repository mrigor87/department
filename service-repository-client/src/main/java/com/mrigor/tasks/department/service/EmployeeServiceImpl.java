package com.mrigor.tasks.department.service;


import com.mrigor.tasks.department.dao.DepartmentDao;
import com.mrigor.tasks.department.dao.EmployeeDao;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.model.Employee;

import com.mrigor.tasks.department.util.exception.ExceptionUtil;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * implementation interface of service layer with using. (department)
 * Data access by repository interface
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao repository;

    @Autowired
    private DepartmentDao departmentRepository;


    public void setRepository(EmployeeDao repository) {
        this.repository = repository;
    }

    @Override
    public Employee create(Employee employee) {
        LOG.debug("create new employee {}",employee);
        Assert.notNull(employee, "employee must not be null");
        try {
            //savedEmployee = repository.save(employee);
            int insert = repository.insert(employee);
         //   employee.setId(insert);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("can't create new employee because department with id=" + employee.getDepartment().getId() + " isn't exist");
        }

        return employee;
    }

    @Override
    public void update(Employee employee) throws NotFoundException {
        LOG.debug("update employee {}",employee);
        Assert.notNull(employee, "employee must not be null");
       // ExceptionUtil.checkNotFoundWithId(departmentRepository.get(employee.getDepartmentId()), employee.getDepartmentId());
        ExceptionUtil.checkNotFoundWithId(departmentRepository.get(employee.getDepartment().getId()), employee.getDepartment().getId());
        int update = repository.update(employee);
        ExceptionUtil.checkNotFoundWithId(update==1, employee.getId().intValue());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        LOG.debug("delete employee, id={}",id);
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Employee get(int id) throws NotFoundException {
        LOG.debug("get employee, id={}",id);
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Employee> getAll() {
        LOG.debug("get all employees");
        return repository.getAll();
    }

    @Override
    public List<Employee> getByDep(int departmentId) {
        LOG.debug("get all employees from departmentsId={}",departmentId);
        ExceptionUtil.checkNotFoundWithId(departmentRepository.get(departmentId), departmentId);
        return repository.getByDepWithDepartment(departmentId);
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentId) {
        LOG.debug("get filteted employee, departmentId={}, from={}, to={}",departmentId,from,to);
       // Department dep=departmentId==null?null: departmentRepository.get(departmentId);
        return repository.getFiltered(from, to, departmentId);
    }

}
