package com.mrigor.tasks.department.dao.jpa;

import com.mrigor.tasks.department.dao.DepartmentDao;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 029 29.05.17.
 */
@Repository
public class DepartmentDaoImpl implements DepartmentDao {
    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public int insert(Department department) {
        em.persist(department);
        return department.getId();
    }

    @Override
    @Transactional
    public int update(Department department) {
        em.merge(department);
        return department.getId();
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Department reference = em.getReference(Department.class, id);
        em.remove(reference);
        return true;
    }

    @Override
    public Department getWithEmployees(int id) {

        EntityGraph graph = this.em.getEntityGraph("withEmployees");
        Map hints = new HashMap();
        hints.put("javax.persistence.fetchgraph", graph);
        Department department = this.em.find(Department.class, id,hints);

        return department;

        //return null;
    }

    @Override
    public Department get(int id) {
        Department department = em.find(Department.class, id);
        return department;
    }

    @Override
    public List<Department> getAll() {
        return em.createNamedQuery("getAllSorted", Department.class).getResultList();


    }

    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        return null;
    }
}
