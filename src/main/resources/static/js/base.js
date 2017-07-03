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
