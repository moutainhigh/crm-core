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
            {"targets": [4], "orderable": false}],
        "oLanguage": {
            "sEmptyTable": "Нет доступных данных для таблицы!"
        },
    });
});

$(document).ready(function () {
    $('#searchCategory, #addCategory, #editCategory').autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                type: 'GET',
                url: '/manager/costs/search/category?name=' + request.term,
                success: function (data) {
                    response(data.length === 0 ? [] : data);
                }
            })
        }
    });
});


$(document).ready(function () {
    $('#searchGoods, #editName, #addName').autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                type: 'GET',
                url: '/manager/costs/search/goods?name=' + request.term,
                success: function (data) {
                    response(data.length === 0 ? [] : data);
                }
            })
        }
    });
});

$(document).ready(function () {
    $('#datePickerFrom').datepicker({
        format: 'dd-mm-yyyy',
        language: 'ru'
    });
    $('#datePickerTo').datepicker({
        format: 'dd-mm-yyyy',
        language: 'ru'
    });
});

$(document).ready(function () {
    $('#formAddGoods').submit(function (e) {
        e.preventDefault();
        var url = '/manager/tableDebt/addDebt';
        var formData = $('#formAddDebts').serialize();

        $.ajax({
            type: 'POST',
            url: url,
            data: formData,
            success: function (data) {
                var successMessage = '<h4 style="color:green;" align="center">' + data + '</h4>';
                $('.messageAd').html(successMessage).show();
                window.setTimeout(function () {
                    location.reload()
                }, 1000);
            },
            error: function (error) {
                var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                $('.messageAd').html(errorMessage).show();
            }

        });
    });
});

$(document).ready(function () {
    $('#searchDebtor, #editName, #addName').autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                type: 'GET',
                url: '/manager/costs/search/goods?name=' + request.term,
                success: function (data) {
                    response(data.length === 0 ? [] : data);
                }
            })
        }
    });
});

function removeDebt(id) {
    var url = '/manager/tableDebt/delete';

    var request = $.post(url, {debtId: id}, function () {
        location.reload();
    });
}
