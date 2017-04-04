package com.mrigor.tasks.department.rest;

/**
 * Created by Igor on 22.03.2017.
 */


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;



public class DepartmentRouteConfig extends RouteBuilder {

    //*********************************************************************************************************************
    private static final String GET_ALL_DEPARTMENTS_SQL = "SELECT * FROM DEPARTMENTS ORDER BY name";
    private static final String CREATE_DEPARTMENT_SQL = "INSERT INTO DEPARTMENTS (name) VALUES (:#name)";
    private static final String GET_LAST_DEPARTMENT_SQL = "SELECT * FROM DEPARTMENTS WHERE id IN (SELECT MAX(id) FROM DEPARTMENTS)";
    private static final String GET_DEPARTMENT_BY_ID_SQL = "SELECT * FROM DEPARTMENTS WHERE id=:#id";
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE DEPARTMENTS SET name=:#name WHERE id=:#id";
    private static final String DELETE_DEPARTMENT_BY_ID_SQL = "DELETE FROM DEPARTMENTS WHERE id=:#id";
    private static final String GET_EMPLOYEES_BY_DEPARTMENT_SQL = "SELECT * FROM EMPLOYEES WHERE EMPLOYEES.DEPARTMENT_ID=:#id ORDER BY FULLNAME ";
    private static final String GET_DEPARTMENTS_WITH_AVG_SALARY_SQL =
            "SELECT d.ID, d.NAME, AVG(e.SALARY) AS averagesalary " +
                    "FROM EMPLOYEES e " +
                    "RIGHT JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.ID " +
                    "GROUP BY d.ID " +
                    "ORDER  BY d.NAME";
    //*********************************************************************************************************************


    @Override
    public void configure() {
//rest
//*******************************************************************************
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");
        rest("/rest/departments").description("Department rest service")
                .consumes("application/json;charset=UTF-8").produces("application/json;charset=UTF-8")

                .get().to("direct:getAllDepartments")
                .post().to("direct:createDepartment")
                .get("/{id}").to("direct:getDepartment")
                .get("/{id}/employees").to("direct:getEmployeesByDepartment")
                .get("/withAvgSalary").to("direct:getWithAvgSalary")
                .put().to("direct:updateDepartment")
                .delete("/{id}").to("direct:deleteDepartment");
//************************************************************************

        from("direct:getEmployeesByDepartment")
                .log(LoggingLevel.INFO, "camel rest get all employees from department")
                .to("sql:" + GET_EMPLOYEES_BY_DEPARTMENT_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.model.Employee")
                .log(LoggingLevel.INFO, "get employees from department by camel-sql");


        from("direct:getAllDepartments")
                .log(LoggingLevel.INFO, "camel rest get all departments")
                .to("sql:" + GET_ALL_DEPARTMENTS_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.model.Department")
                .log(LoggingLevel.INFO, "get all departments by camel-sql");

        from("direct:createDepartment")
                .log(LoggingLevel.INFO, "camel rest create department ")
                .to("sql:" + CREATE_DEPARTMENT_SQL)
                .to("sql:" + GET_LAST_DEPARTMENT_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Department")
                .log(LoggingLevel.INFO, "new department: ${body}");

        from("direct:getWithAvgSalary")
                .log(LoggingLevel.INFO, "camel rest get all departments with salary")
                .to("sql:" + GET_DEPARTMENTS_WITH_AVG_SALARY_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.to.DepartmentWithAverageSalary")
                .log(LoggingLevel.INFO, "get all departments with salary by camel-sql");


        from("direct:getDepartment")
                .log(LoggingLevel.INFO, "  ${body}")
                .log(LoggingLevel.INFO, "camel rest get department by id     ${in.body}")
                .to("sql:" + GET_DEPARTMENT_BY_ID_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Department")
                .choice()
                .when().simple("${body} != null")
                .log(LoggingLevel.INFO, "get department: ${body}")
                .otherwise()
                .to("direct:notFound");

        from("direct:updateDepartment")
                .log(LoggingLevel.INFO, "camel rest uodate department '${body}'")
                .log(LoggingLevel.INFO, "in.header.name '${in.header.name}'")
                .log(LoggingLevel.INFO, "in.body.name '${in.body.name}'")
                .log(LoggingLevel.INFO, "{in.body.id} '${in.body.id}'")
                .to("sql:" + UPDATE_DEPARTMENT_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Department")
                .log(LoggingLevel.INFO, "updated department: ${body}");

        from("direct:deleteDepartment")
                .log(LoggingLevel.INFO, "camel rest delete department by id")
                .to("sql:" + DELETE_DEPARTMENT_BY_ID_SQL);


        //not found
        from("direct:notFound")
                .log("Not found entity id=${in.header.id}")
                .process(exchange -> {
                    exchange.getIn().setBody(("Not found entity id=" + exchange.getIn().getHeader("id")));
                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_TEXT, "Not found entity with id=${in.header.id}");
                });
    }
}
