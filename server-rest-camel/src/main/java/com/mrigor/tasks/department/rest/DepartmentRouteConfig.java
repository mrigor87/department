package com.mrigor.tasks.department.rest;

/**
 * Created by Igor on 22.03.2017.
 */

import com.mrigor.tasks.department.model.Department;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DepartmentRouteConfig extends RouteBuilder {
    @Autowired
    org.springframework.jdbc.datasource.DriverManagerDataSource db;

    @Autowired
    org.apache.camel.component.sql.SqlComponent sq;

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
    private static String CREATE_DEPARTMENT_SQL = "INSERT INTO DEPARTMENTS (name) VALUES (:#name)";
    private static String GET_LAST_DEPARTMENT_SQL = "SELECT * FROM DEPARTMENTS WHERE id IN (SELECT MAX(id) FROM DEPARTMENTS)";
    private static final String GET_DEPARTMENT_BY_ID_SQL = "SELECT * FROM DEPARTMENTS WHERE id=:#id";
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name=:#name WHERE id=:#id";
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
        System.out.println(db);
        System.out.println(sq);

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)

        // .dataFormatProperty("prettyPrint", "true")
        ;
        rest("/rest/departments").description("Department rest service")
                .consumes("application/json;charset=UTF-8").produces("application/json;charset=UTF-8")

                .get()                .to("direct:getAll")
                .post()               .to("direct:create")
                .get("/{id}")         .to("direct:get")
                .get("/withAvgSalary").to("direct:getWithAvgSalary")
                .put()                .to("direct:update")
                .delete("/{id}")      .to("direct:delete")
        ;

        from("direct:getAll")


                // .to("file:/qwertyqwe2/zzzzz.zzz")
                // .to("log:qqqqqqq")
                .log("qqqqq")
                .log("${body}")
                .to("sql:" + GET_ALL_DEPARTMENTS_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.model.Department")
                .log("body first: ${body}")



/*                .transform(new Expression() {
                    public <T> T evaluate(Exchange exchange, Class<T> type) {
                        // the result is a List
                        //exchange.
                       // Object body = (ArrayList<Department>)exchange.getIn().getBody();
                        Object body = exchange.getIn().getBody();
// and each row in the list is a Map
                    //    Map<?, ?> row = (Map<?, ?>) body.get(0);
                        // String body = exchange.getIn().getBody(String.class);
                      //  body=body.toLowerCase();
                        //body="[{'id'=100000,'name'='Marketing'},{'id'=100001,'name'='Production'}]";
                        return (T) body;
                    }

                })*/
                .log("body: ${body}")
        // .log("body4: ${header}")



/*                .setBody(simple(GET_ALL_DEPARTMENTS_SQL))
                .to("jdbc:dataSource?useJDBC4ColumnNameAndLabelSemantics=true")
                .log("body jdbc: ${body}")*/
        ;

        from("direct:create")
                .to("sql:" + CREATE_DEPARTMENT_SQL)
                .to("sql:" + GET_LAST_DEPARTMENT_SQL+ "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Department")
                .log("body create: ${body}")
        ;

/*
                .setBody(simple(CREATE_DEPARTMENT_SQL))
                .to("jdbc:dataSource")
                .setBody(simple(GET_LAST_DEPARTMENT_SQL))
                .to("jdbc:dataSource");
*/

        from("direct:getWithAvgSalary")
                .to("sql:" + GET_DEPARTMENTS_WITH_AVG_SALARY_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.to.DepartmentWithAverageSalary")
                .log("qqqqq get with salary")
                .log("${body}")
        ;
/*                .setBody(simple(GET_DEPARTMENTS_WITH_AVG_SALARY_SQL))
                .to("jdbc:dataSource");*/


        from("direct:get")
                .to("sql:" + GET_DEPARTMENT_BY_ID_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Department")
                .log("qqqqq")
                .log("${body}")
/*                .setBody(simple(GET_DEPARTMENT_BY_ID_SQL))
                .to("jdbc:dataSource")*/
        ;

        from("direct:update")
                .log("body update: ${body}")
                .to("sql:" + UPDATE_DEPARTMENT_SQL+ "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Department")
                .log("body update: ${body}");
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
