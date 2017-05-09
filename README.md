# Staff accounting.

## Description
 This is Maven multi-module project on Java 8, which contains two main parts. The first part is RESTful service (in-memory HSQLDB, Spring JDBC, Spring MVC). The second one is web-application which uses this REST API and provides UI (Spring RestTemplate, Spring MVC, Bootstrap, jQuery)

REST API:

URL	| Method	| Description
--- | ------- | ---------------
rest/departments/|GET|get department's list
rest/departments/{id}/|GET|get department by id
rest/departments/{id}/|DELETE|delete department by id
rest/departments/|PUT|update department
rest/departments/|POST|create new department
rest/departments/withAvgSalary/|GET|get department's list with information about average salary by each department. If department don't have any employees, then AVG salary will by = 0
rest/departments/{id}/employees/|GET|get all employees from particular  department
rest/employees|GET|get employee's list
rest/employees/{id}|GET|get employee by id
rest/employees/{id}|DELETE|delete employee
rest/employees/|PUT|update employee
rest/employees/|POST|create new employee
rest/employees/filtered/|GET|get all employees by filter with parameters:"from" ,"to" - period when employee was born."departmentid" - id of department. For example: `/rest/employees/filtered?departmentid=100000&from=1993-01-01&to=1993-12-31` 


## Instruction
1.	Go to the root directory and run the command `mvn clean install`
2.	Copy war files `/server-rest/target/server-rest-1.0-SNAPSHOT.war` and `/server-web/target/server-web-1.0-SNAPSHOT.war` to the `webapps` directory of Tomcat server.

   **Note:** Web-application uses default prefix url for REST `http://localhost:8080/server-rest-1.0-SNAPSHOT`, which can be changed with spring-app.xml
   
3.	If you will use default setting of Tomcat that 
you can see result at the following url:

  - `http://localhost:8080/server-rest-1.0-SNAPSHOT/rest/departments` (RESTful service)
  - `http://localhost:8080/server-web-1.0-SNAPSHOT` (web-application)

UPDATE: branch "newHandlingAvgSalary" for handling null-value in RESTful service

UPDATE: SOAP web-service. To use this service you should `mvn clean install –P soap` and deploy `server-soap-1.0-SNAPSHOT.war`
         Or `mvn clean install –P rest` for RESTful service

UPDATE: Add Swagger-UI.
By default settings online documentation is available on: 
 `http://localhost:8080/server-rest-1.0-SNAPSHOT//swagger-ui.html`
or `http://localhost:8080/server-rest-1.0-SNAPSHOT`
Some examples of offline documentation are generated in the following paths:
 - `//server-rest/docs/html/index.html`
 - `//server-rest/docs/pdf/index.pdf`
 -  [`//server-res/asciidoc/index.adoc`](https://github.com/mrigor87/department/tree/master/server-rest/asciidoc/index.adoc)



