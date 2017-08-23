/*function showModal(id) {
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
}*/

$(document).on('click', '#saveNewProductData', function () {
    var id = $(this).data('id');
    $('.messageAdd' + id).html(errorMessage).hide();

    var setName = $("#addName" + id).val();
    var setDes = $("#addDes" + id).val();
    var setCost = $("#addCost" + id).val();
	var setSelfCost = $("#addSelfCost" + id).val();

    if (!productDataValidating(setName, setCost, setSelfCost)) {
        var errorMessage = '<h4 style="color:red;" align="center">Неверный формат данных!</h4>';
        $('.messageAdd' + id).html(errorMessage).show();
        return false;
    }

    var ingredient = [];
    var amount = [];
    var myMap = new Object();
    var i;

    var table_parent = $(document).find('#recipeTable' + id);

    table_parent.find('.mySelect option:selected').each(function () {
        ingredient.push($(this).val());
    });
    table_parent.find('.inputClassTest').each(function () {

        amount.push($(this).val());
    });

    for (i = 0; i < ingredient.length; i++) {
        myMap[ingredient[i]] = amount[i];
    }

    var wrapper = {
        idCat: id,
        name: setName,
        description: setDes,
        cost: setCost,
        names: ingredient,
        amount: amount,
		selfCost: setSelfCost
    };
    $.ajax({
        type: "POST",
        url: "/boss/menu/addProduct",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(wrapper),
        success: function (result) {
            if ($("#addCost" + id)[0].hasAttribute('disabled')) {
                setCost = 'Плавающая';
            }
            var trCount = $("#qwe" + id + " > tbody > tr").length;
            var recipeButton = '<button form="getRecipePage" name="id" class="btn btn-primary btn-info" value="' + result.productId + '" th:type="submit"   >Изменить рецепт </button>';
            var editButton = '<a id="ins"  class="btn btn-primary btn-info" onclick="showModal(' + result.productId + ')"  data-toggle="modal" >Редактировать </a>';
            var delButton = '<a id="del"  class="btn btn-primary btn-danger" onclick="del2(' + result.productId + ')"   >Удалить </a>';
            var tr = '<tr id="tr' + result.productId + '"><td>' + trCount + '</td><td id="b' + result.productId + '"><p id="E' + result.productId + '">' + result.name + '</p></td><td id="c' + result.productId + '">' + result.description + '</td><td id="d' + result.productId + '">' + result.cost + '</td>><td id="e' + result.productId + '">' + result.selfCost + '</td><td>' + editButton + '</td><td>' + recipeButton + '</td><td>' + delButton + '</td></tr>';
            var allTR = '<tr id="allTR' + result.productId + '"><td>#</td><td id="allB' + result.productId + '">' + result.name + '</td><td id="allC' + result.productId + '">' + result.description + '</td><td id="allD' + result.productId + '">' + result.cost + '</td><td id="allE' + result.productId + '">' + result.selfCost + '</td><td>' + editButton + '</td><td>' + recipeButton + '</td><td>' + delButton + '</td></tr>';
            $('#qwe' + id + ' tr:last').after(tr);
            $('#allTable' + ' tr:last').after(allTR);

            $("#add" + id).modal('hide');
            $("#addName" + id).val("");
            $("#addDes" + id).val("");
            $("#addCost" + id).val("0.0");
        },
        error: function (e) {
            var errorMessage = '<h4 style="color:red;" align="center">Неудалось добавить продукт!</h4>';
            $('.messageAdd' + id).html(errorMessage).show();
        }
    });
});

function closeModal() {
    var modal = $('.dima_modal');
    modal.fadeOut();
}

/*function editProdModal() {
    var id = $("#d_id").val();
    var name = $("#d_name").val();
    var description = $("#d_des").val();
    var cost = $("#d_cost").val();
    var selfCost = $("#d_selfCost").val();


    var formData = {
        id: id,
        name: name,
        description: description,
        cost: cost,
        selfCost: selfCost
    }



    if (productDataValidating(name, cost)) {
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
                    $("#e" + id).html(result.selfCost);
                    $("#allB" + id).html(result.name);
                    $("#allC" + id).html(result.description);
                    $("#allD" + id).html(result.cost);
                    $("#allE" + id).html(result.selfCost);
                    closeModal()
                },
                error: function (e) {
                    alert("Неверный формат данных!")
                }
            });
        }
    else {
        alert("Неверный формат данных!")
    }
}*/

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

function productDataValidating(name, cost, selfCost) {
    if (name == "" || !$.isNumeric(cost) || cost < 0 || selfCost < 0) {
        return false;
    }
    return true;
};

$(document).on('click', '#saveEditProductData', function () {
    var id = $(this).data("id");
    $('.messageEdit' + id).html(errorMessage).hide();

    var prodId = $("#updId" + id).val();
    var name = $("#updName" + id).val();
    var des = $("#updDes" + id).val();
    var cost = $("#updCost" + id).val();
    var selfCost = $("#updSelfCost" + id).val();

    if (!productDataValidating(name, cost, selfCost)) {
        var errorMessage = '<h4 style="color:red;" align="center">Неверный формат данных!</h4>';
        $('.messageEdit' + id).html(errorMessage).show();
        return false;
    }

    var formData = {
        id: prodId,
        name: name,
        description: des,
        cost: cost,
        selfCost: selfCost
    };

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
            $("#e" + id).html(result.selfCost);
            $("#allB" + id).html(result.name);
            $("#allC" + id).html(result.description);
            $("#allD" + id).html(result.cost);
            $("#allE" + id).html(result.selfCost);
            $('#allName' + id).val(result.name);
            $('#allDes' + id).val(result.description);
            $('#allCost' + id).val(result.cost);
            $('#selfCost' + id).val(result.cost);

            $("#p" + id).modal('hide');
        },
        error: function (e) {
            var errorMessage = '<h4 style="color:red;" align="center">Неудалось изменить данные!</h4>';
            $('.messageEdit' + id).html(errorMessage).show();
        }
    });
});

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
            modal.find("#d_selfCost").val(product.selfCost);

        },
        error: function (e) {
            alert("Ошибка ;( ");
            console.log("ERROR: ", e);
        }
    });

    modal.fadeIn();
}
$(document).on('click', '#saveEditProductDataAll', function () {
    var id = $(this).data("id");
    $('.messageEditAll' + id).html(errorMessage).hide();

    var prodId = $("#allId" + id).val();
    var name = $("#allName" + id).val();
    var des = $("#allDes" + id).val();
    var cost = $("#allCost" + id).val();
    var selfCost = $("#selfCost" + id).val();

    if (!productDataValidating(name, cost, selfCost)) {
        var errorMessage = '<h4 style="color:red;" align="center">Неверный формат данных!</h4>';
        $('.messageEditAll' + id).html(errorMessage).show();
        return false;
    }

    var formData = {
        id: prodId,
        name: name,
        description: des,
        cost: cost,
        selfCost: selfCost
    };

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
            $("#e" + id).html(result.selfCost);
            $('#allB' + id).html(result.name);
            $("#allC" + id).html(result.description);
            $("#allD" + id).html(result.cost);
            $("#allE" + id).html(result.selfCost);
            $('#updName' + id).val(result.name);
            $('#updDes' + id).val(result.description);
            $('#updCost' + id).val(result.cost);

            $("#allP" + id).modal('hide');
        },
        error: function (e) {
            var errorMessage = '<h4 style="color:red;" align="center">Неудалось изменить данные!</h4>';
            $('.messageEditAll' + id).html(errorMessage).show();
        }
    });
});

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

function sendLogLevel() {
    var levelMap = {level: $('#chooseLogLevel').val()};
    if (isLogLevel($('#chooseLogLevel').val())) {
        $.ajax({
            type: "POST",
            url: "/boss/property/logLevel",
            data: levelMap,
            success: function (result) {
                $('.form-group').html('Уровень логирования задан.');
            },
            error: function (e) {
                var json = '<h4 style="color:aqua">Действующий пароль введён неверно</h4>';
            }
        });

    } else {
        var json = '<h4 style="color:red">Неизвестный уровень логгирования</h4>';
        $('.modal-title').html(json);
    }
}

function isLogLevel(log) {
    return log == 'INFO' || log == 'ERROR'
        || log == 'DEBUG' || log == 'WARN'
}

function newSMTPSettings() {
    var formData = {
        settingsName: $("#settingsName").val(),
        password: $("#settingsPassword").val(),
        email: $("#settingsEmail").val()
    }
    $.ajax({
        type: "POST",
        url: "/boss/settings/advert-setting/new",
        data: formData,
        success: function (result) {
            $("#successModal").modal('show')
        },
        error: function (e) {
            $("#errorModal").modal('show')
        }
    });
}

function applySMTPSettings(id) {
    var formData = {
        settingsId: id,
    }
    $.ajax({
        type: "POST",
        url: "/boss/settings/advert-setting/existing-settings",
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
    var url = '/boss/settings/advert-setting/del-settings';

    var request = $.post(url, {settingsId: id},
        window.setTimeout(function () {
            location.reload()
        }, 100)
    )
}

function addIng(id) {
    var first_row = $('#recipeTable' + id).find('#Row2');
    first_row.clone().appendTo('#recipeTable' + id);
    calculateCostPrice(id);
};

function deleteIng(id) {
    var table_parent = $(document).find('#recipeTable' + id);
    var inputs = [];
    table_parent.find('.inputClassTest').each(function () {
        inputs.push($(this).val());
    });

    if (inputs.length >= 2) {
        $('#recipeTable' + id + ' tr:last').remove();
    }
    calculateCostPrice(id);
};

function test(id) {
    var ingredient = [];
    var amount = [];
    var map = new Object();
    var i = 0;
    var table_parent = $(document).find('#recipeTable' + id);
    table_parent.find('.mySelect option:selected').each(function () {
        ingredient.push($(this).val());
    });
    table_parent.find('.inputClassTest').each(function () {
        amount.push($(this).val());
    });
    for (i = 0; i < ingredient.length; i++) {
        map[ingredient[i]] = amount[i];
    }
};

function calculateCostPrice(categoryId) {
    var totalCostPrice = 0;
    var ingredients = $('#recipeTable'+categoryId).find('.ingredients');
    for (var i = 0; i < ingredients.length; i++) {
        var countIngredient = 0;
        var priceIngredient = 0.0;
        $(ingredients[i]).find('input,select').each(function (index, element) {
            elType = element.nodeName.toLowerCase();
            if (elType === 'input') {
                countIngredient = parseInt(element.value);
            } else if (elType === 'select') {
                priceIngredient = parseFloat($(element).find(':selected').attr('data-price'));
            }
        });
        totalCostPrice += countIngredient * priceIngredient;
    }
    $('#addSelfCost'+categoryId).val(totalCostPrice.toFixed(3));
}
