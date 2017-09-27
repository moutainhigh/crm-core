function check(cardId, e) {
    e.preventDefault();
    var formData = {
        searchParam: $("#checkForm").val(),
        ourId: cardId
    };
    $.ajax({
        type: "POST",
        url: "/manager/card/checkUser",
        data: formData,
        success: function (card) {
            $("#checkLabel").attr('style', 'color:green ;');
            $("#checkLabel").html(card.name + " " + card.surname);
            $("#idInvited").val(card.id);
            var imageURL = "/manager/card/image/" + card.id;
            $("#invitedAvatar").attr("src", imageURL).fadeIn();
            $("#invitedAvatar").attr('style', ' border-radius: 5px ');
        },
        error: function (result) {
            if (result.responseText == "nope") {
                var notFound = '<h4 style="color:red">Пользователь не найден</h4>';
                $("#checkLabel").html(notFound);
                $("#invitedAvatar").attr('style', ' border-radius: 5px ; display: none;');
                $("#idInvited").val(null);
            }
            else {
                var notFound = '<h4 style="color:red">Найдено пользователей : ' + result.responseText + ', уточните запрос</h4>';
                $("#checkLabel").html(notFound);
                $("#invitedAvatar").attr('style', ' border-radius: 5px ; display: none;');
                $("#idInvited").val(null);
            }
        }
    });
}
function validateEmail(emailParam) {
    var emailF = {
        email: emailParam
    };
    $.ajax({
        type: "POST",
        url: "/manager/card/validateEmail",
        data: emailF,
        success: function (card) {
            var form = $('#editForm');
            $.ajax({
                type: "POST",
                url: form.attr('action'),
                data: form.serialize(),
                success: function (card) {
                    $('#myModal1').modal('hide');
                    location.reload()
                },
                error: function (result) {
                    $("#editPhone").html("Такой номер уже использован");
                    $("#editPhone").attr('style', 'color:red ;');
                }
            });
        },
        error: function (result) {
            $("#labelEditEmail").html("Неверный формат почты");
            $("#labelEditEmail").attr('style', 'color:red ;');
        }
    });
}
function validateEmailReg(emailParam) {
    var emailF = {
        email: emailParam
    };
    $.ajax({
        type: "POST",
        url: "/manager/card/validateEmail",
        data: emailF,
        success: function (card) {
            var form = $('#registrationForm');
            $.ajax({
                type: "POST",
                url: form.attr('action'),
                data: form.serialize(),
                success: function (card) {
                    $('#regModal').modal('hide');
                    location.reload()
                },
                error: function (result) {
                    $("#regPhone").html("Такой номер уже использован");
                    $("#regPhone").attr('style', 'color:red ;');
                }
            });
        },
        error: function (result) {
            $("#registLabelEmail").html("Неверный формат почты");
            $("#registLabelEmail").attr('style', 'color:red ;');
        }
    });
}
function submitEdit(e, form) {
    e.preventDefault();
    var elems = form.elements;
    var z, x, c, v = false;
    resetError(elems.name.parentNode);
    if (!elems.name.value) {
        showError(elems.name.parentNode, ' <h4 style="color:red">Заполните поле</h4>');
    }
    else {
        z = true;
    }
    resetError(elems.surname.parentNode);
    if (!elems.surname.value) {
        showError(elems.surname.parentNode, '  <h4 style="color:red">Заполните поле</h4>');
    } else {
        x = true;
    }
    resetError(elems.phone.parentNode);
    if (!elems.phone.value) {
        showError(elems.phone.parentNode, '  <h4 style="color:red">Заполните поле</h4>');
    }
    else {
        c = true;
    }
    resetError(elems.email.parentNode);
    if (!elems.email.value) {
        showError(elems.email.parentNode, '  <h4 style="color:red">Заполните поле</h4>');
    }
    else {
        v = true;
    }
    if (v && c && x && z) {
        validateEmail($('#editEmailInput').val())
    }
}
function submitRegistration(e, form) {
    e.preventDefault();
    var elems = form.elements;
    var z, x, c, v = false;
    resetError(elems.name.parentNode);
    if (!elems.name.value) {
        showError(elems.name.parentNode, '  <h4 style="color:red">Заполните поле</h4>');
    }
    else {
        z = true;
    }
    resetError(elems.surname.parentNode);
    if (!elems.surname.value) {
        showError(elems.surname.parentNode, ' <h4 style="color:red">Заполните поле</h4>');
    } else {
        x = true;
    }
    resetError(elems.phone.parentNode);
    if (!elems.phone.value) {
        showError(elems.phone.parentNode, '  <h4 style="color:red">Заполните поле</h4>');
    }
    else {
        c = true;
    }
    resetError(elems.email.parentNode);
    if (!elems.email.value) {
        showError(elems.email.parentNode, '  <h4 style="color:red">Заполните поле</h4>');
    }
    else {
        v = true;
    }
    if (v && c && x && z) {
        validateEmailReg($('#registrationEmail').val());
    }
}
function showError(container, errorMessage) {
    container.className = 'error';
    var msgElem = document.createElement('span');
    msgElem.className = "error-message";
    msgElem.innerHTML = errorMessage;
    container.appendChild(msgElem);
}
function resetError(container) {
    container.className = '';
    if (container.lastChild.className == "error-message") {
        container.removeChild(container.lastChild);
    }
}
document.getElementById('inputRegPhone').onkeypress = function (e) {
    e = e || event;
    if (e.ctrlKey || e.altKey || e.metaKey) return;
    var chr = getChar(e);
    if (chr == null) return;
    if (chr < '0' || chr > '9') {
        return false;
    }
}
function getChar(event) {
    if (event.which == null) {
        if (event.keyCode < 32) return null;
        return String.fromCharCode(event.keyCode) // IE
    }
    if (event.which != 0 && event.charCode != 0) {
        if (event.which < 32) return null;
        return String.fromCharCode(event.which) // остальные
    }
    return null;
}
document.getElementById('inputEditPhone').onkeypress = function (e) {
    e = e || event;
    if (e.ctrlKey || e.altKey || e.metaKey) return;
    var chr = getChar(e);
    if (chr == null) return;
    if (chr < '0' || chr > '9') {
        return false;
    }
}
function getChar(event) {
    if (event.which == null) {
        if (event.keyCode < 32) return null;
        return String.fromCharCode(event.keyCode) // IE
    }
    if (event.which != 0 && event.charCode != 0) {
        if (event.which < 32) return null;
        return String.fromCharCode(event.which) // остальные
    }
    return null;
}
