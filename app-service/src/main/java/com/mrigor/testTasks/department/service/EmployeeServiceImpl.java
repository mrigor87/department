package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Employee;
import com.mrigor.testTasks.department.repository.EmployeeRepo;
import com.mrigor.testTasks.department.util.exception.ExceptionUtil;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Игорь on 11.12.2016.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired(required = false)
    EmployeeRepo repository;

    @Override
    public Employee save(Employee employee, int depId) {
        return repository.save(employee,depId);
    }

    @Override
    public Employee update(Employee employee, int depId) throws NotFoundException {
        Assert.notNull(employee, "employee must not be null");
        return ExceptionUtil.checkNotFoundWithId(repository.save(employee,depId),employee.getId());
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
        return repository.getBetweenDates(from,to);
    }
    @Override
    public List<Employee> getByDate(LocalDate date) {
        return repository.getBetweenDates(date,date);
    }
}
