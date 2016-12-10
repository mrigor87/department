package com.mrigor.testTasks.department.repository;

import com.mrigor.testTasks.department.model.Department;

import java.util.List;

/**
 * Created by Игорь on 10.12.2016.
 */

public class DepartmentRepoImpl implements DepartmentRepo {


    @Override
    public Department save(Department user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
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
    public Department getWithEmployees(int id) {
        return null;
    }
}
