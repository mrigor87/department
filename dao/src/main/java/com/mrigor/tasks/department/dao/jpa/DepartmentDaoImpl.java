package com.mrigor.tasks.department.dao.jpa;

import com.mrigor.tasks.department.dao.DepartmentDao;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by igor on 029 29.05.17.
 */
//@Repository
public class DepartmentDaoImpl implements DepartmentDao {
 //   @PersistenceContext
   // EntityManager em;

    @Override
    public int insert(Department department) {
        return 0;
    }

    @Override
    public int update(Department department) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Department getWithEmployees(int id) {
        return null;
    }

    @Override
    public Department get(int id) {
        return null;
    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        return null;
    }
}
