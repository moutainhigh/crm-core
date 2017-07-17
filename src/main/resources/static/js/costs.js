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
            {"targets": [7, 8], "orderable": false}],
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
        var url = '/manager/costs/add';
        var formData = $('#formAddGoods').serialize();

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
    $('#formEditGoods').submit(function (e) {
        e.preventDefault();
        var url = '/manager/costs/edit';
        var formData = $('#formEditGoods').serialize();

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
    $('#deleteAllGoods').click(function () {
        var table = $('#sortedTable').DataTable();
        var numberOfRows = table.data().length;
        if (numberOfRows === 0) {
            var errorMessage = '<h4 style="color:red;" align="center">Нет товаров для удаления!</h4>';
            $('.messageDelAll').html(errorMessage).show();
            window.setTimeout(function () {
                location.reload()
            }, 1000);
            return false;
        }
        var idArray = [];

        for (var i = 0; i < numberOfRows; i++) {
            var data = table.row(i).data();
            idArray.push(data[0]);
        }

        var url = '/manager/costs/delete/all';
        data = JSON.stringify(idArray);
        var request = $.post(url, {ids: data}, function (data) {
            var successMessage = '<h4 style="color:green;" align="center">' + data + '</h4>';
            $('.messageDelAll').html(successMessage).show();
            window.setTimeout(function () {
                location.reload()
            }, 1000);
        });
    });
});

function removeGoods(id) {
    var url = '/manager/costs/delete';

    var request = $.post(url, {goodsId: id}, function () {
        location.reload();
    });
}

function populateGoodsEditModal(rowIdx) {
    var table = $('#sortedTable').DataTable();
    var numberOfRows = table.data().length;

    for (var i = 0; i < numberOfRows; i++) {
        var data = table.row(i).data();
        var id = data[0];
        if (id == rowIdx) {
            var name = data[1];
            var category = data[2];
            var price = parseFloat(data[3].replace(',', '.'));
            var quantity = parseFloat(data[4].replace(',', '.'));
            var date = moment(data[6], "DD-MM-YYYY").format("YYYY-MM-DD");

            $('#editId').val(id);
            $('#editName').val(name);
            $('#editCategory').val(category);
            $('#editPrice').val(price);
            $('#editQuantity').val(quantity);
            $('#editDate').val(date);

            return false;
        }
    }
}