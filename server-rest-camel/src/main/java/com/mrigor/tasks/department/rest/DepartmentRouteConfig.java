package com.mrigor.tasks.department.rest;

/**
 * Created by Igor on 22.03.2017.
 */

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class DepartmentRouteConfig extends RouteBuilder {
    //***********************************  SQL EXPRESSIONS  *************************************************************
/*    private static final String GET_DEPARTMENTS_WITH_AVG_SALARY_SQL =
            "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
                    "FROM EMPLOYEES e " +
                    "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                    "GROUP BY d.ID " +
                    "ORDER  BY d.NAME";*/

    // private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name=:name WHERE id=:id"; //named parameter
//   private static final String DELETE_DEPARTMENT_BY_ID_SQL = "DELETE FROM DEPARTMENTS WHERE id=?";
    //*******************************************************************************************************************
/*
    private static final String GET_ALL_DEPARTMENTS_SQL = "SELECT * FROM DEPARTMENTS ORDER BY name";
    private static String CREATE_DEPARTMENT_SQL = "INSERT INTO DEPARTMENTS (name) VALUES ('${header.name}')";
    private static String GET_LAST_DEPARTMENT_SQL = "SELECT * FROM DEPARTMENTS WHERE id IN (SELECT MAX(id) FROM DEPARTMENTS)";
    private static final String GET_DEPARTMENT_BY_ID_SQL = "SELECT * FROM DEPARTMENTS WHERE id='${header.id}'";
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name='${header.name}' WHERE id=${header.id}";
    private static final String DELETE_DEPARTMENT_BY_ID_SQL = "DELETE FROM DEPARTMENTS WHERE id='${header.id}'";
    private static final String GET_DEPARTMENTS_WITH_AVG_SALARY_SQL =
            "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
                    "FROM EMPLOYEES e " +
                    "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                    "GROUP BY d.ID " +
                    "ORDER  BY d.NAME";

*/
    //*********************************************************************************************************************
    private static final String GET_ALL_DEPARTMENTS_SQL = "SELECT * FROM DEPARTMENTS ORDER BY name";
    private static String CREATE_DEPARTMENT_SQL = "INSERT INTO DEPARTMENTS (name) VALUES (:#name')";
    private static String GET_LAST_DEPARTMENT_SQL = "SELECT * FROM DEPARTMENTS WHERE id IN (SELECT MAX(id) FROM DEPARTMENTS)";
    private static final String GET_DEPARTMENT_BY_ID_SQL = "SELECT * FROM DEPARTMENTS WHERE id=:#id";
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name='${header.name}' WHERE id=:#id";
    private static final String DELETE_DEPARTMENT_BY_ID_SQL = "DELETE FROM DEPARTMENTS WHERE id=:#id";
    private static final String GET_DEPARTMENTS_WITH_AVG_SALARY_SQL =
            "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
                    "FROM EMPLOYEES e " +
                    "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                    "GROUP BY d.ID " +
                    "ORDER  BY d.NAME";
    //*********************************************************************************************************************




    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
        ;
        rest("/rest/departments").description("Department rest service")
                .consumes("application/json").produces("application/json")

                .get().to("direct:getAll")
                .post().to("direct:create")
                .get("/{id}").to("direct:get")
                .get("/withAvgSalary").to("direct:getWithAvgSalary")
                .put("/{id}").to("direct:update")
                .delete("/{id}").to("direct:delete")
        ;

        from("direct:getAll")
                .to("sql:"+GET_ALL_DEPARTMENTS_SQL)
/*                .setBody(simple(GET_ALL_DEPARTMENTS_SQL))
                .to("jdbc:dataSource")*/
        ;

        from("direct:create")
                .to("sql:"+CREATE_DEPARTMENT_SQL)
                .to("sql:"+GET_LAST_DEPARTMENT_SQL);

/*
                .setBody(simple(CREATE_DEPARTMENT_SQL))
                .to("jdbc:dataSource")
                .setBody(simple(GET_LAST_DEPARTMENT_SQL))
                .to("jdbc:dataSource");
*/

        from("direct:getWithAvgSalary")
                .to("sql:"+GET_DEPARTMENTS_WITH_AVG_SALARY_SQL);
/*                .setBody(simple(GET_DEPARTMENTS_WITH_AVG_SALARY_SQL))
                .to("jdbc:dataSource");*/


        from("direct:get")
                .to("sql:" + GET_DEPARTMENT_BY_ID_SQL)
/*                .setBody(simple(GET_DEPARTMENT_BY_ID_SQL))
                .to("jdbc:dataSource")*/
        ;

        from("direct:update")
                .to("sql:" + UPDATE_DEPARTMENT_SQL);
/*
                .setBody(simple(UPDATE_DEPARTMENT_SQL))
                .to("jdbc:dataSource");*/

        from("direct:delete")
                .to("sql:" + DELETE_DEPARTMENT_BY_ID_SQL);
               /* .setBody(simple(DELETE_DEPARTMENT_BY_ID_SQL))
                .to("jdbc:dataSource")*/
               ;

    }
}
