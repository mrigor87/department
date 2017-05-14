package com.mrigor.tasks.department.service;

import com.mrigor.tasks.department.dao.DepartmentDao;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import com.mrigor.tasks.department.util.exception.ExceptionUtil;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * implementation interface of service layer with using. (department)
 * Data access by departmentDao interface
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Department create(Department department) {
        LOG.debug("create new department {}", department);
        Assert.notNull(department, "department must not be null");
        int insert = departmentDao.insert(department);
       // department.setId(insert);
        return department;
    }

    @Override
    public void update(Department department) throws NotFoundException {
        LOG.debug("update department {}", department);
        Assert.notNull(department, "department must not be null");
        int update = departmentDao.update(department);
        ExceptionUtil.checkNotFoundWithId(update == 1, department.getId().intValue());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        LOG.debug("delete department, id={}", id);
        ExceptionUtil.checkNotFoundWithId(departmentDao.delete(id), id);
    }

    @Override
    public Department get(int id) throws NotFoundException {
        LOG.debug("get department, id={}", id);
        return ExceptionUtil.checkNotFoundWithId(departmentDao.getWithEmployees(id), id);
    }

    @Override
    public List<Department> getAll() {
        LOG.debug("get all departments");
        return departmentDao.getAll();
    }


    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        LOG.debug("get departments with avg salary");
        return departmentDao.getAllWithAvgSalary();
    }
}
