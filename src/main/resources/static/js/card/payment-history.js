$(document).ready(function () {
    $('#paymentTable').dataTable({
        "scrollCollapse": false,
        "autoWidth": true,
        "lengthChange": false,
        "paging": false,
        "info": false,
        "searching": false,
        "order": [[0, "asc"]],
        "oLanguage": {
            "sEmptyTable": "Нет доступных данных для таблицы!"
        },
    });
});