function editRecipe(id) {

    var ingredient = [];
    var amount = [];
    var myMap = new Object();
    var i;

    var table_parent = $(document).find('#editRecipe');

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
        name: "name",
        description: "name",
        cost: 0,
        names: ingredient,
        amount: amount
    };
    $.ajax({
        type: "POST",
        url: "/boss/menu/edit/recipe",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(wrapper),
        success: function () {
            location.reload();

        },
        error: function (e) {
            alert("Неверный формат данных -/");
        }
    });
}
function addIng() {
    first_row = $('#firstRow');

    $('#editRecipe tr:last').after(first_row.clone());
};


function deleteIng() {
    var table_parent = $(document).find('#editRecipe');

    var inputs = [];
    table_parent.find('.inputClassTest').each(function () {
        inputs.push($(this).val());
    });

    console.log(inputs.length);
    if (inputs.length >= 2) {

        $('#editRecipe   tr:last').remove();
    }
};
