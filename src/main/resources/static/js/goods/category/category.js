$(document).ready(function () {
    $('#sortedTable').dataTable({
        // rowReorder: true,
        // "scrollY": "46vh",
        "scrollCollapse": false,
        "autoWidth": true,
        "lengthChange": false,
        "paging": false,
        "info": false,
        "searching": false,
        // "ordering": false
        "order": [[0, "asc"]],
        "columnDefs": [{"targets": [], "visible": false},
            {"targets": [2,3], "orderable": false}],
        "oLanguage": {
            "sEmptyTable": "Нет доступных данных для таблицы!"
        },
    });
});

function removeCategory(id) {
    var url = '/boss/category/delete';

    var request = $.post(url, {id: id}, function () {
        location.reload();
    });
}

function categoryEditModal(rowIdx) {
    var table = $('#sortedTable').DataTable();
    var numberOfRows = table.data().length;

    for (var i = 0; i < numberOfRows; i++) {
        var data = table.row(i).data();
        var id = data[0];
        if (id == rowIdx) {
            var category = data[1];
            $('#editId').val(id);
            $('#editCategory').val(category);
            return false;
        }
    }
}