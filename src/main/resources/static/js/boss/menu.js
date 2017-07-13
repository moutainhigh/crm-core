function showModal(id) {
    var modal = $('.dima_modal');
    modal.find("#d_id").val(id);
    $.ajax({
        type: "POST",
        url: "/boss/menu/getProduct",
        data: ({
            id: id
        }),
        success: function (product) {

            modal.find("#d_name").val(product.name);
            modal.find("#d_des").val(product.description);
            modal.find("#d_cost").val(product.cost);

        },
        error: function (e) {

            alert("Error show modal ");
        }
    });

    modal.fadeIn();
}

function createProd(id) {
    var setName = $("#addName" + id).val();
    var setDes = $("#addDes" + id).val();
    var setCost = $("#addCost" + id).val();
    ajaxPost();
    function ajaxPost() {
        $.ajax({
            type: "POST",
            url: "/boss/menu/addAjax",
            data: ({
                idCat: id,
                name: $("#addName" + id).val(),
                description: $("#addDes" + id).val(),
                cost: $("#addCost" + id).val()
            }),
            success: function (result) {
                var button = '<a id="ins"  class="btn btn-primary btn-info" onclick="showModal(' + result + ')"  data-toggle="modal" >Редактировать </a>';
                var button2 = '<a id="del"  class="btn btn-primary btn-danger" onclick="del2(' + result + ')"   >Удалить </a>';
                var tr = '<tr id="tr' + result + '"><td>#</td><td id="b' + result + '"><p id="E' + result + '">' + setName + '</p></td><td id="c' + result + '">' + setDes + '</td><td id="d' + result + '">' + setCost + '</td><td>' + button + '</td><td>' + button2 + '</td></tr>';
                var allTR = '<tr id="allTR' + result + '"><td>#</td><td id="allB' + result + '">' + setName + '</td><td id="allC' + result + '">' + setDes + '</td><td id="allD' + result + '">' + setCost + '</td><td>' + button + '</td><td>' + button2 + '</td></tr>';
                $('#qwe' + id + ' tr:last').after(tr);
                $('#allTable' + ' tr:last').after(allTR);
            },
            error: function (e) {
                alert("Неверный формат данных");
            }
        });
    }
}

function closeModal() {
    var modal = $('.dima_modal');
    modal.fadeOut();
}

function editProdModal() {
    var formData = {
        id: $("#d_id").val(),
        name: $("#d_name").val(),
        description: $("#d_des").val(),
        cost: $("#d_cost").val(),
    }

    var cst = $("#d_cost").val();
    var nme = $("#d_name").val();
    var id = $("#d_id").val();

    if (!isNaN(+cst)) {
        if ("" != nme) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/boss/menu/updProd",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    $("#b" + id).html(result.name);
                    $("#c" + id).html(result.description);
                    $("#d" + id).html(result.cost);
                    $("#allB" + id).html(result.name);
                    $("#allC" + id).html(result.description);
                    $("#allD" + id).html(result.cost);
                    closeModal()
                },
                error: function (e) {
                    alert("Неверный формат данных!")
                }
            });
        }
        else {
            alert("Неверный формат названия!")
        }
    }
    else {
        alert("Неверный формат цены!")
    }
}

function del(id) {
    var formData = {
        del: id,
    }
    $.ajax({
        type: "POST",
        url: "/boss/menu/deleteProduct",
        data: formData,
        success: function (result) {
            $("#tr" + id).remove();
            $("#allTR" + id).remove();
        },
        error: function (e) {
        }
    });
}

function del2(id) {
    var formData = {
        del: id,
    }
    $.ajax({
        type: "POST",
        url: "/boss/menu/deleteProduct",
        data: formData,
        success: function (result) {
            $(document).find("#tr" + id).remove();
            $(document).find("#allTR" + id).remove();
        },
        error: function (e) {
        }
    });
}

function editProd(id) {
    var formData = {
        id: $("#updId" + id).val(),
        name: $("#updName" + id).val(),
        description: $("#updDes" + id).val(),
        cost: $("#updCost" + id).val(),
    }
    var cst = $("#updCost" + id).val();
    var nme = $("#updName" + id).val();

    if (!isNaN(+cst)) {

        if ("" != nme) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/boss/menu/updProd",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {

                    $("#b" + id).html(result.name);
                    $("#c" + id).html(result.description);
                    $("#d" + id).html(result.cost);
                    $("#allB" + id).html(result.name);
                    $("#allC" + id).html(result.description);
                    $("#allD" + id).html(result.cost);
                },
                error: function (e) {
                    alert("Неверный формат данных!")
                }
            });
        }
        else {
            alert("Неверный формат названия!")
        }
    }
    else {
        alert("Неверный формат цены!")
    }
}

$(document).ready(function () {

    (function ($) {

        $('#filter').keyup(function () {

            var rex = new RegExp($(this).val(), 'i');
            $('.searchable tr').hide();
            $('.searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
        })
    }(jQuery));
});

$(document).ready(function () {
    $("#myTab a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
});

$('.super').click(function () {
    var pageName = $(this).data('page');
    // ajax-запрос
    $.ajax({
        url: '/boss/menu/upd',
        cache: false,
        success: function (html) {

            $("#bossview").html(html);
        }
    });
});

function showModal(id) {
    var modal = $('.dima_modal');
    modal.find("#d_id").val(id);
    $.ajax({
        type: "POST",
        url: "/boss/menu/getProduct",
        data: ({
            id: id
        }),
        success: function (product) {

            modal.find("#d_name").val(product.name);
            modal.find("#d_des").val(product.description);
            modal.find("#d_cost").val(product.cost);

        },
        error: function (e) {
            alert("Ошибка ;( ");
            console.log("ERROR: ", e);
        }
    });

    modal.fadeIn();
}
function editProdAll(id) {

    var formData = {
        id: $("#allId" + id).val(),
        name: $("#allName" + id).val(),
        description: $("#allDes" + id).val(),
        cost: $("#allCost" + id).val(),
    }
    var cst = $("#allCost" + id).val();
    var nme = $("#allName" + id).val();

    if (!isNaN(+cst)) {

        if ("" != nme) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/boss/menu/updProd",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {

                    $("#b" + id).html(result.name);
                    $("#c" + id).html(result.description);
                    $("#d" + id).html(result.cost);
                    $("#allB" + id).html(result.name);
                    $("#allC" + id).html(result.description);
                    $("#allD" + id).html(result.cost);
                },
                error: function (e) {
                    alert("Неверный формат данных!")
                    console.log("ERROR: ", e);
                }
            });
        }
        else {
            alert("Неверный формат названия!")
        }
    }
    else {
        alert("Неверный формат цены!")
    }
}
function closeModal() {
    var modal = $('.dima_modal');
    modal.fadeOut();
}
$(document).ready(function () {

    (function ($) {

        $('#filter').keyup(function () {

            var rex = new RegExp($(this).val(), 'i');
            $('.searchable tr').hide();
            $('.searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
        })
    }(jQuery));
});
$(document).ready(function () {
    $("#myTab a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
});
$('.super').click(function () {
    var pageName = $(this).data('page');
    // ajax-запрос
    $.ajax({
        url: '/boss/menu/upd',
        cache: false,
        success: function (html) {
            $("#bossview").html(html);
        }
    });
});



function applySMTPSettings(id) {
    var formData = {
        settingsId: id,
    }
    $.ajax({
        type: "POST",
        url: "/boss/property/advertising-existing-settings",
        data: formData,
        success: function (result) {
            var successMessage = '<h4 style="color:green;" align="center">' + result + '</h4>';
            $('.messageAd').html(successMessage).show();
            window.setTimeout(function () {
                location.reload()
            }, 1000);
        },
        error: function (e) {
            var errorMessage = '<h4 style="color:red;" align="center">' + e.responseText + '</h4>';
            $('.messageAd').html(errorMessage).show();
        }
    });
}

function removeSettings(id) {
    var url = '/boss/property/advertising-del-settings';

    var request = $.post(url, {settingsId: id},
        window.setTimeout(function () {
            location.reload()
        }, 1000)
    )
}