$(document).ready(function(){

//minimum 8 characters
    var bad = /(?=.{8,}).*/;
//Alpha Numeric plus minimum 8
    var good = /^(?=\S*?[a-z])(?=\S*?[0-9])\S{8,}$/;
//Must contain at least one upper case letter, one lower case letter and (one number OR one special char).
    var better = /^(?=\S*?[A-Z])(?=\S*?[a-z])((?=\S*?[0-9])|(?=\S*?[^\w\*]))\S{8,}$/;
//Must contain at least one upper case letter, one lower case letter and (one number AND one special char).
    var best = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])(?=\S*?[^\w\*])\S{8,}$/;

    $('#new').on('keyup', function () {
        var password = $(this);
        var pass = password.val();
        var passLabel = $('[for="new"]');
        var stength = 'Слабый';
        var pclass = 'danger';
        if (best.test(pass) == true) {
            stength = 'Очень сильный';
            pclass = 'success';
        } else if (better.test(pass) == true) {
            stength = 'Сильный';
            pclass = 'warning';
        } else if (good.test(pass) == true) {
            stength = 'Средний';
            pclass = 'warning';
        } else if (bad.test(pass) == true) {
            stength = 'Слабый';
        } else {
            stength = 'Очень слабый';
        }

        var popover = password.attr('data-content', stength).data('bs.popover');
        popover.setContent();
        popover.$tip.addClass(popover.options.placement).removeClass('danger success info warning primary').addClass(pclass);

    });

    $('input[data-toggle="popover"]').popover({
        placement: 'top',
        trigger: 'focus'
    });

})

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
                /*$('.form-group').html('Смена пароля прошла успешно.');*/
                $("#successModal").modal('show')
                $('#major').hide();
                $("#save").hide()
            },
            error: function (e) {
                /*var json = '<h4 style="color:red">Действующий пароль введён неверно</h4>';*/
                $("#errorModal").modal('show')
                /*$('.modal-title').html(json);*/
                $('#old').val('').attr("placeholder", "");
            }
        });
    } else {
        $('#new, #secondNew').val('').attr("placeholder", "");
        $("#wrongModal").modal('show')
        return false;
    }
};

function sendLogLevel() {
    var levelMap = { level: $('#chooseLogLevel').val()};

    if($('#chooseLogLevel').val() == 'INFO' || $('#chooseLogLevel').val() == 'ERROR'
        || $('#chooseLogLevel').val() == 'DEBUG' || $('#chooseLogLevel').val() == 'WARN') {
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