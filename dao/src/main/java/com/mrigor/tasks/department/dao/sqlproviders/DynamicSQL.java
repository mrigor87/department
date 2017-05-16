package com.mrigor.tasks.department.dao.sqlproviders;

import com.mrigor.tasks.department.model.Department;
import org.apache.ibatis.jdbc.SQL;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by igor on 014 14.05.17.
 */
public class DynamicSQL {

    public String selectFilteredEmployees(Map<String, Object> map) {
      //  Date fromDate = Date.valueOf(LocalDate.of(1800, 1, 1));
      //  Date toDate = Date.valueOf(LocalDate.of(3000, 1, 1) );
        LocalDate from=(LocalDate) map.get("from");
        LocalDate to=(LocalDate) map.get("to");
        Integer depId=(Integer)map.get("departmentid");
        Date fromDate = Date.valueOf(from == null ? LocalDate.of(1800, 1, 1) : from);
        Date toDate = Date.valueOf(to == null ? LocalDate.of(3000, 1, 1) : to);
        return new SQL() {{
            SELECT("*");
            FROM("EMPLOYEES");
            String whereFirstPart = "BIRTHDAY BETWEEN '" + fromDate + "' AND '" + toDate + "'";
            if (depId == null)
                WHERE(whereFirstPart);
            else
                WHERE(whereFirstPart, "department_id=#{departmentid}");
            ORDER_BY("FULLNAME");

        }}.toString();
    }
}
