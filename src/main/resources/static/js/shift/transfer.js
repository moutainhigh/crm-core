$(document).ready(function () {
    $('#transferToCard').submit(function (e) {
        e.preventDefault();
        var url = '/manager/shift/edit/transferCashOnCard';
        var formData = {transferCard: $("#transferCard").val()};
        if ($("#transferCard").val() > 0) {
            $.ajax({
                type: 'POST',
                url: url,
                data: formData,
                success: function (data) {

                    var successMessage = '<h4 style="color:green;" align="center">' + "Вы успешно перевели сумму на карту: " + $("#transferCard").val() + '</h4>';
                    $('.messageAd').html(successMessage).show();
                    $('#cashBox').text(data[0]);
                    $('#bankCashBox').text(data[1]);
                },
                error: function (error) {
                    var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                    $('.messageAd').html(errorMessage).show();
                }

            });
        }
        else {
            var errorMessage = '<h4 style="color:red;" align="center">' + "Сумма перевода не может быть меньше нуля!" + '</h4>';
            $('.messageAd').html(errorMessage).show();
        }
    });
});

$(document).ready(function () {
    $('#transferToCasse').submit(function (e) {
        e.preventDefault();
        var url = '/manager/shift/edit/transferCashOnCasse';
        var formData = {transferCasse: $("#transferCasse").val()};
        if ($("#transferCasse").val() > 0) {
            $.ajax({
                type: 'POST',
                url: url,
                data: formData,
                success: function (data) {

                    var successMessage = '<h4 style="color:green;" align="center">' + "Вы успешно перевели сумму в кассу: " + $("#transferCasse").val() + '</h4>';
                    $('.messageAd2').html(successMessage).show();
                    $('#cashBox').text(data[0]);
                    $('#bankCashBox').text(data[1]);
                },
                error: function (error) {
                    var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                    $('.messageAd2').html(errorMessage).show();
                }

            });
        }
        else {
            var errorMessage = '<h4 style="color:red;" align="center">' + "Сумма перевода не может быть меньше нуля!" + '</h4>';
            $('.messageAd2').html(errorMessage).show();
        }
    });
});