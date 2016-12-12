package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.repository.DepartmentRepo;
import com.mrigor.testTasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.testTasks.department.util.exception.ExceptionUtil;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Игорь on 11.12.2016.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    //  @Autowired
    private DepartmentRepo repository;

    public void setRepository(DepartmentRepo repository) {
        this.repository = repository;
    }


    @Override
    public Department save(Department department) {
        return repository.save(department);
    }

    @Override
    public Department update(Department department) throws NotFoundException {
        Assert.notNull(department, "department must not be null");
        return ExceptionUtil.checkNotFoundWithId(repository.save(department), department.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Department get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Department> getAll() {
        return repository.getAll();
    }


    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        return repository.getAllWithAvgSalary();
    }
}
