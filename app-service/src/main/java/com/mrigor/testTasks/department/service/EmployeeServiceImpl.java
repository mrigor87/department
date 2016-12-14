package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.repository.EmployeeRepo;
import com.mrigor.testTasks.department.util.exception.ExceptionUtil;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 11.12.2016.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private EmployeeRepo repository;

    public void setRepository(EmployeeRepo repository) {
        this.repository = repository;
    }

    @Override
    public Employee create(Employee employee, int depId) {
        Assert.notNull(employee, "employee must not be null");
        employee.setDepartmentId(depId);
        Employee savedEmployee;
        try {
            savedEmployee = repository.save(employee);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("can't create new employee because department with id=" + depId + " isn't exist");
        }
        return savedEmployee;
    }

    @Override
    public void update(Employee employee) throws NotFoundException {
        Assert.notNull(employee, "employee must not be null");
        ExceptionUtil.checkNotFoundWithId(repository.save(employee), employee.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Employee get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Employee> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Employee> getByDep(int depId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.getByDep(depId), depId);
    }

    @Override
    public List<Employee> getBetweenDates(LocalDate from, LocalDate to) {
        return repository.getBetweenDates(from, to);
    }

    @Override
    public List<Employee> getByDate(LocalDate date) {
        return repository.getBetweenDates(date, date);
    }
}
