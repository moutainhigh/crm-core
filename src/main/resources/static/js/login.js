function validateEmail(email) { //Validates the email address
    var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return emailRegex.test(email);
}

function validatePhone(phone) { //Validates the phone number
    var phoneRegex = /^(8){1}[\d]+$/;
    return phoneRegex.test(phone);
}


$(document).ready(function () {
    $("#signin").submit(function (event) {
        var username = $("#username").val();
        if (!(validateEmail(username.trim()) || validatePhone(username.trim()))) {
            event.preventDefault();
            $("#incorrect").text("Некоректный формат номера телефона или e-mail").addClass("alert-danger");
        }
    });
});

$(document).ready(function () {
    $('[data-toggle="popover"]')
        .popover();
});

$(document).ready(function () {
    $('#username, #password').on('click', function () {
        $("#incorrect, #wrong, #successful-logout").text("").removeClass("alert-danger");

    });
});

