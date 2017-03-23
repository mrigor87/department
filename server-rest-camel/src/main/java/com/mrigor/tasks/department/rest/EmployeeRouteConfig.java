package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.ArrayList;

/**
 * Created by Igor on 23.03.2017.
 */
public class EmployeeRouteConfig extends RouteBuilder {


    //***********************************  SQL EXPRESSIONS  *************************************************************
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE EMPLOYEES SET FULLNAME=:#fullName, birthDay=:#birthDay, salary=:#salary WHERE id=:#id"; //named parameter
    private static final String GET_ALL_EMPLOYEES_SQL = "SELECT * FROM EMPLOYEES ORDER BY FULLNAME";
    private static final String DELETE_EMPLOYEES_BY_ID_SQL = "DELETE FROM EMPLOYEES WHERE id=:#id";
    private static final String GET_EMPLOYEES_BY_ID_SQL = "SELECT * FROM EMPLOYEES WHERE id=:#id";
    private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITH_DEP_SQL = "SELECT * FROM EMPLOYEES  " +
            " WHERE (BIRTHDAY BETWEEN  :#from AND :#to) AND department_id=:#departmentid";
     private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITHOUT_DEP_SQL = "SELECT * FROM EMPLOYEES " +
             "WHERE (BIRTHDAY BETWEEN  :#from AND :#to)  ORDER BY FULLNAME ";

    //   private static final String GET_ORDERED_FILTERED_EMPLOYEES_WITHOUT_DEP_SQL = "SELECT * FROM EMPLOYEES WHERE department_id=:#departmentid";
    private static final String CREATE_EMPLOYEE_SQL = "INSERT INTO EMPLOYEES (fullName, birthDay, salary, department_id) " +
            "VALUES (:#fullName, :#birthDay, :#salary, :#departmentId)";
    private static final String GET_LAST_EMPLOYEE_SQL = "SELECT * FROM EMPLOYEES WHERE id IN (SELECT MAX(id) FROM EMPLOYEES)";

    //*******************************************************************************************************************



    @Override
    public void configure() throws Exception {


        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
        ;
        rest("/rest/employees").description("Employee rest service")
                .consumes("application/json;charset=UTF-8").produces("application/json;charset=UTF-8")

                .get().route()
                .to("direct:getAllEmployees")
                .endRest()

                .post().to("direct:createEmployee")
                .get("/{id}").to("direct:getEmployee")
                .get("/filtered").to("direct:getFilteredEmployee")
                .put().to("direct:updateEmployee")
                .delete("/{id}").to("direct:deleteEmployee")
        ;
        from("direct:getFilteredEmployee")

                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String from = exchange.getIn().getHeader("from", String.class);
                        String to = exchange.getIn().getHeader("to", String.class);
                        //String dep = exchange.getIn().getHeader("departmentid", String.class);
                        if (from == null)
                            exchange.getIn().setHeader("from", "1800-01-01");
                        if (to == null)
                            exchange.getIn().setHeader("to", "3000-01-01");
                    }
                })
                //.setHeader("from",)
                .log(LoggingLevel.INFO, "camel rest get getFiltere employees    from=${in.header.from}, to=${in.header.from}, departmentId=${in.header.departmentid}")
                .to("sql:" + GET_ORDERED_FILTERED_EMPLOYEES_WITH_DEP_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.model.Employee")
                .log(LoggingLevel.INFO, "get all employees by camel-sql   ${body}")
        ;
        from("direct:getAllEmployees")
                .log(LoggingLevel.INFO, "camel rest get all employees")
                .to("sql:" + GET_ALL_EMPLOYEES_SQL + "?outputType=SelectList&outputClass=com.mrigor.tasks.department.model.Employee")
                .log(LoggingLevel.INFO, "get all employees by camel-sql")
        ;
        from("direct:createEmployee")
                .log(LoggingLevel.INFO, "camel rest create employee")
                .to("sql:" + CREATE_EMPLOYEE_SQL)
                .to("sql:" + GET_LAST_EMPLOYEE_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Employee")
                .log(LoggingLevel.INFO, "new employee: ${body}")
        ;


        from("direct:getEmployee")
                .log(LoggingLevel.INFO, "camel rest get employee by id")
                .to("sql:" + GET_EMPLOYEES_BY_ID_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Employee")
                .log(LoggingLevel.INFO, "get employee: ${body}")
        ;

        from("direct:updateEmployee")
                .log(LoggingLevel.INFO, "camel rest uodate employees")
                .to("sql:" + UPDATE_EMPLOYEE_SQL + "?outputType=SelectOne&outputClass=com.mrigor.tasks.department.model.Employee")
                .log(LoggingLevel.INFO, "updated employee: ${body}")
        ;

        from("direct:deleteEmployee")
                .log(LoggingLevel.INFO, "camel rest delete employee by id")
                .to("sql:" + DELETE_EMPLOYEES_BY_ID_SQL);
    }

}
