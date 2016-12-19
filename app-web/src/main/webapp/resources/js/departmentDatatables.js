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
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn

            },


            /*            function deleteRow(id) {
             $.ajax({
             url: ajaxUrl + id,
             type: 'DELETE',
             success: function () {
             updateTable();
             /!*            successNoty('common.deleted');*!/
             }
             });
             }*/
            /*            function renderDeleteBtn(data, type, row) {
             if (type == 'display') {
             return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');">'+'delete'+'</a>';
             }*/


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
        return '<a class="btn btn-xs btn-primary" onclick="goTo(' + row.id + ');">' + 'go' + '</a>';
    }
}
function goTo(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();

        }
    });
}