package com.mrigor.tasks.department.dao.jpa;

import com.mrigor.tasks.department.dao.DepartmentDao;
import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.to.DepartmentWithAverageSalary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

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
    public Department insert(Department department) {
        em.persist(department);
        return department;
    }

    @Override
    @Transactional
    public Department update(Department department) {

        return em.merge(department);
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

        EntityGraph graph = this.em.getEntityGraph(Department.GRAPH_WITH_EMMPLOYEES);
        Map hints = new HashMap();
        hints.put("javax.persistence.fetchgraph", graph);
        Department department = this.em.find(Department.class, id,hints);

        return department;
    }

    @Override
    public Department get(int id) {
        Department department = em.find(Department.class, id);
        return department;
    }

    @Override
    public List<Department> getAll() {
        return em.createNamedQuery(Department.GET_ALL_JPQL, Department.class).getResultList();


    }

    @Override
    public List<DepartmentWithAverageSalary> getAllWithAvgSalary() {
        List<DepartmentWithAverageSalary> resultList = em.createNamedQuery(Department.GET_ALL_WITH_AVG_SALARY_JPQL, DepartmentWithAverageSalary.class).getResultList();
        return resultList;
    }
}
