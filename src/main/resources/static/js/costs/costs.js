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
    $('#searchCost, #editName, #addName').autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                type: 'GET',
                url: '/manager/cost/search/cost?name=' + request.term,
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
    $('#formAddCost').submit(function (e) {
        e.preventDefault();
        $('.messageAd').html('').hide();
        if ($('#addCategory').val() == "") {
            var errorMessage = '<h4 style="color:red;" align="center">Не указана категория расхода</h4>';
            $('.messageAd').html(errorMessage).show();
            return false;
        }
        var url = '/manager/cost/add';
        var formData = $('#formAddCost').serialize();

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
    $('#formEditCost').submit(function (e) {
        e.preventDefault();
        $('.messageAd').html('').hide();
        if ($('#editCategory').val() == "") {
            var errorMessage = '<h4 style="color:red;" align="center">Не указана категория расхода</h4>';
            $('.messageAd').html(errorMessage).show();
            return false;
        }
        var url = '/manager/cost/edit';
        var formData = $('#formEditCost').serialize();

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
    $('#deleteAllCosts').click(function () {
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

        var url = '/manager/cost/delete/all';
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

function removeCost(id) {
    var url = '/manager/cost/delete';

    var request = $.post(url, {costId: id}, function () {
        location.reload();
    });
}

function populateCostEditModal(rowIdx) {
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

$(document).ready(function () {
    $('#addCostDropListCategory').find('li').on('click', function () {
        $('#addCostDropdownMenuCategory').html($(this).text() + ' <span class="caret"></span>');
        $('#addCategory').val($(this).text());
    })
});

$(document).ready(function () {
    $('#editCostDropListCategory').find('li').on('click', function () {
        $('#editCostDropdownMenuCategory').html($(this).text() + ' <span class="caret"></span>');
        $('#editCategory').val($(this).text());
    })
});

$(document).ready(function () {
    $('#searchCategoryDropList').find('li').on('click', function () {
        $('#searchCategoryDropdownMenu').html($(this).text() + ' <span class="caret"></span>');
        $('#searchCategory').val($(this).text());
    })
});