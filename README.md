#Staff accounting.

##Description
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


##Instruction (Spring boot version)

1.	Go to the root directory and run the command `mvn clean install`
2.	Run `java -jar server-rest/target/server-rest-1.0-SNAPSHOT.jar`  and then  `java -jar server-web/target/server-web-1.0-SNAPSHOT.jar`

3.	you can see result at the following url:

  - `http://localhost:8080/server-rest-1.0-SNAPSHOT/rest/departments` (RESTful service)
  - `http://localhost:8081/server-web-1.0-SNAPSHOT` (web-application)





