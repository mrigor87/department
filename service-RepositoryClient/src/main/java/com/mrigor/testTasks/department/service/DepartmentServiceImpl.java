package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.repository.DepartmentRepo;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.testTasks.department.util.exception.ExceptionUtil;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * implementation interface of service layer with using. (department)
 * Data access by repository interface
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    private DepartmentRepo repository;

    @Override
    public Department create(Department department) {
        LOG.debug("create new department {}", department);
        Assert.notNull(department, "department must not be null");
        return repository.save(department);
    }

    @Override
    public void update(Department department) throws NotFoundException {
        LOG.debug("update department {}", department);
        Assert.notNull(department, "department must not be null");
        ExceptionUtil.checkNotFoundWithId(repository.save(department), department.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        LOG.debug("delete department, id={}", id);
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Department get(int id) throws NotFoundException {
        LOG.debug("get department, id={}", id);
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Department> getAll() {
        LOG.debug("get all departments");
        return repository.getAll();
    }


    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.debug("get departments with avg salary");
        return repository.getAllWithAvgSalary();
    }
}
