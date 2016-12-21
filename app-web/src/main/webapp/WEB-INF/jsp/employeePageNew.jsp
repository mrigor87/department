<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3> ${department.name}</h3>

            <div class="view-box">
                <%--                <form method="post" class="form-horizontal" role="form" id="filter">
                                    <div class="form-group">
                                        <input type="number" name="departmentid" value="${department.id}">

                                        <label class="control-label col-sm-2" for="from">from:</label>

                                        <div class="col-sm-2">
                                            <input class="form-control" type="date" name="from" id="from">
                                        </div>

                                        <label class="control-label col-sm-2" for="to">to:</label>

                                        <div class="col-sm-2">
                                            <input class="form-control" type="date" name="to" id="to">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-8">
                                            <button class="btn btn-primary pull-right" type="button" onclick="updateTable2()">filter
                                            </button>
                                        </div>
                                    </div>
                                </form>--%>


                <div class="form-group">
                    <input type="number" name="departmentid" value="${department.id}">

                    <label class="control-label col-sm-2" for="from">from:</label>

                    <div class="col-sm-2">
                        <input class="form-control" type="date" name="from" id="from">
                    </div>

                    <label class="control-label col-sm-2" for="to">to:</label>

                    <div class="col-sm-2">
                        <input class="form-control" type="date" name="to" id="to">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-8">
<%--                        <button class="btn btn-primary pull-right" type="button" onclick="updateTable2()">filter
                        </button>--%>
                        <a class="btn btn-primary pull-right" onclick="filterByPeriod()">filter2</a>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label col-sm-2" for="to">Day:</label>
                    <div class="col-sm-2">
                        <input class="form-control" type="date" name="date" id="date">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-8">
                        <%--            <button class="btn btn-primary pull-right" type="button" onclick="updateTable2()">filter
                                    </button>--%>
                        <a class="btn btn-primary pull-right" onclick="filterByDate()">filter2</a>
                    </div>
                </div>


                <div>
                    <a class="btn btn-sm btn-info" onclick="add('add')">add</a>
                </div>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Bithday</th>
                        <th>salary</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="hidden" id="id" name="id">
                    <input type="number" id="departmentId" name="departmentId">

                    <div class="form-group">
                        <label for="fullName" class="control-label col-xs-3">FullName></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="fullName" name="fullName"
                                   placeholder="fullName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="birthDay" class="control-label col-xs-3">Birthday</label>

                        <div class="col-xs-9">
                            <input type="date" class="form-control" id="birthDay"
                                   name="birthDay" placeholder="Enter day of birth">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="salary" class="control-label col-xs-3">salary</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="salary" name="salary"
                                   placeholder="100">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="saveWrapper()">save</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var departId = ${department.id};
    function saveWrapper() {
        form.find("input[name=departmentId]").val(departId);
        save();
    }
    function filterByDate() {
        var date= document.getElementById("date").value;
        $.ajax({
            type: "get",
            url: ajaxUrl + "filtered?departmentid=" + departId + "&from=" + date + "&to=" + date,
            success: updateTableByData
        });
    }

    function filterByPeriod() {
        var from= document.getElementById("date").value;
        var to= document.getElementById("date").value;
        $.ajax({
            type: "get",
            url: ajaxUrl + "filtered?departmentid=" + departId + "&from=" + from + "&to=" + to,
            success: updateTableByData
        });
    }


</script>
<script type="text/javascript">
    var edit_title = 'edit';
</script>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/employeeDatatablesNew.js"></script>
</html>