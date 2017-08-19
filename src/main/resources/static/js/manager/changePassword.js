function testA() {
    var new_pass_1 = $('#new1').val();
    var new_pass_2 = $('#secondNew1').val();
    if (new_pass_1 === new_pass_2) {
        if (new_pass_1.length === 0 || new_pass_1.trim() === "") { //check for empty
            $('#new1, #secondNew1').val('').attr("placeholder", "");
            var json = '<h4 style="color:red">Новый пароль не может быть пустым!</h4>';
            $('.modal-title').html(json);
            return false;
        }
        var formData = {
            new: $('#new1').val(),
            old: $('#old1').val(),
            secondNew: $('#secondNew1').val(),
        }
        $.ajax({
            type: "POST",
            url: "/manager/changePassword",
            data: formData,
            success: function (result) {
                $('.form-group').html('Смена пароля прошла успешно.');
                $('#major').hide();
                $("#save").hide()
            },
            error: function (e) {
                var json = '<h4 style="color:red">Действующий пароль введён неверно</h4>';
                $('#major2').html(json);
                $('#old1').val('').attr("placeholder", "");
            }
        });
    } else {
        $('#new1, #secondNew1').val('').attr("placeholder", "");
        var json = '<h4 style="color:red">Новые пароли не совпадают </h4>';
        $('.modal-title').html(json);
        return false;
    }
};
