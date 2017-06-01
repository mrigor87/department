package com.mrigor.tasks.department.dao.jpa;

import com.mrigor.tasks.department.dao.EmployeeDao;
import com.mrigor.tasks.department.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Igor on 31.05.2017.
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Employee insert(Employee employee) {
        em.persist(employee);
        return employee;
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        return em.merge(employee);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Employee reference = em.getReference(Employee.class, id);
        em.remove(reference);
        return true;
    }

    @Override
    public Employee get(int id) {
        Employee employee = em.find(Employee.class, id);
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> resultList = em.createNamedQuery(Employee.GET_ALL_JPQL, Employee.class).getResultList();
        return resultList;
    }

    @Override
    public List<Employee> getByDep(int departmentId) {
        List<Employee> resultList = em.createNamedQuery(Employee.GET_BY_DEPARTMENT_ID_JPQL, Employee.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
        return resultList;
    }

    @Override
    public List<Employee> getByDepWithDepartment(int departmentId) {
        List<Employee> resultList = em.createNamedQuery(Employee.GET_BY_DEPARTMENT_ID_WITH_EMPLOYYES_JPQL, Employee.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
        return resultList;
    }

    @Override
    public List<Employee> getFiltered(LocalDate from, LocalDate to, Integer departmentid) {
        LocalDate fromDate=from==null?LocalDate.of(1800,1,1):from;
        LocalDate toDate=to==null?LocalDate.of(3000,1,1):to;

        List<Employee> resultList = em.createNamedQuery(Employee.GET_FILTERED, Employee.class)
                .setParameter("from", fromDate)
                .setParameter("to", toDate)
                .setParameter("departmentId", departmentid)
                .getResultList();

        return resultList;
    }
}
