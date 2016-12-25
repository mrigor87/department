var ajaxUrl = 'ajax/departments/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "get",
        url: ajaxUrl,
        success: updateTableByData
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {

            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "averageSalary"
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

            },




            {
                "defaultContent": "",
                "orderable": false,
                "render": renderGoto

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

function renderGoto(data, type, row) {
    if (type == 'display') {
/*        return '<a class="btn btn-xs btn-primary" href="/employees/filtered?departmentid='+row.id + '">' +'go'+  '</a>';*/
        return '<a class="btn btn-xs btn-primary" href="/department/'+row.id+'/employees">' +'go'+  '</a>';
/*        /employees/department{id}*/
/*        return '<a class="btn btn-xs btn-primary" onclick="goTo(' + row.id + ');">' + 'go' + '</a>';*/
    }
}
