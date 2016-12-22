var ajaxUrl = 'ajax/employees/';
var datatableApi;

function updateTable8() {
    $.ajax({
        type: "get",
        url: ajaxUrl+"filtered?departmentid="+departId,
        success: updateTableByData
    });
}

function updateTable() {
    $.ajax({
        type: "post",
        url: ajaxUrl+"filter",
        data: $('#filter').serialize(),

        success: updateTableByData
    });
}
function updateTable2() {
/*    data.add("departmentid",departId);*/
    $.ajax({
        type: "post",
        url: ajaxUrl+"filter",
        data: $('#filter').serialize(),

        success: updateTableByData
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {

            "url": ajaxUrl+"filtered?departmentid="+departId,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "fullName"
            },

            {
                "data": "birthDay"
/*                "render": function (date, type, row) {
                    if (type == 'display') {
                        return date.replace('T', ' ').substr(0, 16);
                    }
                    return date;
                }*/
            },
            {
                "data": "salary"
            },


            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn

            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass('normal');
        },

        "initComplete": makeEditable
    });




});