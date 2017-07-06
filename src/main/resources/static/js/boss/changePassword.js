function testB() {
    var new_pass_1 = $('#new').val();
    var new_pass_2 = $('#secondNew').val();
    if (new_pass_1 === new_pass_2) {
        var formData = {
            new: $('#new').val(),
            old: $('#old').val(),
            secondNew: $('#secondNew').val(),
        }
        $.ajax({
            type: "POST",
            url: "/boss/changePassword",
            data: formData,
            success: function (result) {
                $('.form-group').html('Смена пароля прошла успешно.');
                $('#major').hide();
                $("#save").hide()
            },
            error: function (e) {
                var json = '<h4 style="color:red">Действующий пароль введён неверно</h4>';
                $('#major').html(json);
                $('#old').val('').attr("placeholder", "");
            }
        });
    } else {
        $('#new, #secondNew').val('').attr("placeholder", "");
        var json = '<h4 style="color:red">Новые пароли не совпадают </h4>';
        $('.modal-title').html(json);
        return false;
    }
};