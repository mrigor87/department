<%--
  Created by IntelliJ IDEA.
  User: Игорь
  Date: 17.12.2016
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Department</title>
</head>
<body>
!!!!
<section>
<%--    <jsp:useBean id="department1" scope="page" type="com.mrigor.testTasks.department.model.Employee">
        </jsp:useBean>--%>
    <table>
        <thead>
        <tr>
            <th>Name</th>
        </tr>
        </thead>
<%--<c:forEach items="${meals}" var="meal">
    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>--%>

        <c:forEach items="${departmentList}" var="department">
            <jsp:useBean id="department" type="com.mrigor.testTasks.department.model.Department"/>

            <tr>
                <td>${department.name}</td>
            </tr>
        </c:forEach>


    </table>
</section>
</body>
</html>
