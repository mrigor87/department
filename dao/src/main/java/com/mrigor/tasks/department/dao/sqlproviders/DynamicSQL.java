package com.mrigor.tasks.department.dao.sqlproviders;

import com.mrigor.tasks.department.model.Department;
import org.apache.ibatis.jdbc.SQL;


import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by igor on 014 14.05.17.
 */
public class DynamicSQL {
   // private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITH_DEP_SQL = "SELECT * FROM EMPLOYEES  WHERE ((BIRTHDAY BETWEEN  ? AND ?) AND department_id=?)";

    public String selectFilteredEmployees(LocalDate from, LocalDate to, Department department) {
        Date fromDate = Date.valueOf(from == null ? LocalDate.of(1800, 1, 1) : from);
        Date toDate = Date.valueOf(to == null ? LocalDate.of(3000, 1, 1) : to);
        return new SQL() {{
            SELECT("*");
            FROM("EMPLOYEES");
            String whereFirstPart="BIRTHDAY BETWEEN '" + fromDate + "' AND '"+toDate+"'";
            if (department==null)
                WHERE(whereFirstPart);
            else
                WHERE(whereFirstPart,"department_id=#{department.id}");
            ORDER_BY("FULLNAME");

        }}.toString();
    }
}
