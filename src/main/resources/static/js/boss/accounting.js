
// скрыть-показать инпуты в newWorkerPage
$(".answer").hide();
$(".cast").click(function () {
    if ($(this).is(":checked")) {
        $(".answer").show();
    } else {
        $(".answer").hide();
    }
});

// скрыть-показать инпуты в editBossModal
$(".answer").hide();
$(".castBossToAdmin").click(function () {
    if ($(this).is(":checked")) {
        $(".answer").show();
    } else {
        $(".answer").hide();
    }
});


// валидность паролей в newWorkerPage
$(document).ready(function () {
    $('#repeatPasswordWorker').change(function () {
        var pass = $("#passwordWorker").val();
        var pass_rep = $("#repeatPasswordWorker").val();
        if (pass != pass_rep) {
            $("#passwordWorker").css('border', 'red 1px solid');
            $("#repeatPasswordWorker").css('border', 'red 1px solid');
            $('#errorBlockWorker').html('Пароли не совпадают').css('font-size','20px').css('color','#a94442');
            event.preventDefault();
        }
    });
});



// валидность паролей в WorkerEdit
$(document).ready(function () {
    $('#repeatPasswordWorkerEdit').change(function () {
        var pass = $("#passwordWorkerEdit").val();
        var pass_rep = $("#repeatPasswordWorkerEdit").val();
        if (pass != pass_rep) {
            $("#passwordWorkerEdit").css('border', 'red 1px solid');
            $("#repeatPasswordWorkerEdit").css('border', 'red 1px solid');
            $('#errorBlockWorkerEdit').html('Пароли не совпадают').css('font-size','20px').css('color','#a94442');
            event.preventDefault();
        }
    });
});



// валидность паролей в newManagerPage
$(document).ready(function () {
    $('#repeatPasswordManager').change(function () {
        var pass = $("#passwordManager").val();
        var pass_rep = $("#repeatPasswordManager").val();
        if (pass != pass_rep) {
            $("#passwordManager").css('border', 'red 1px solid');
            $("#repeatPasswordManager").css('border', 'red 1px solid');
            $('#errorBlockManager').html('Пароли не совпадают').css('font-size','20px').css('color','#a94442');
            event.preventDefault();
        }
    });
});


// валидность паролей в newBossPage
$(document).ready(function () {
    $('#repeatPasswordBoss').change(function () {
        var pass = $("#passwordBoss").val();
        var pass_rep = $("#repeatPasswordBoss").val();
        if (pass != pass_rep) {
            $("#passwordBoss").css('border', 'red 1px solid');
            $("#repeatPasswordBoss").css('border', 'red 1px solid');
            $('#errorBlockBoss').html('Пароли не совпадают').css('font-size','20px').css('color','#a94442');
            event.preventDefault();
        }
    });
});

// скрыть-показать инпуты в workerList
$(".answerAddPos").hide();
$(".addPos").click(function () {
    if ($(this).is(":checked")) {
        $(".answerAddPos").show();
    } else {
        $(".answerAddPos").hide();
    }
});




//Валидация модалок в worker/manager/boss
$(document).ready(function() {
    $('#editEmailWorker').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidEmailWorker').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});


$(document).ready(function() {
    $('#editPhoneWorker').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidPhoneWorker').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','0');
            }
        }
    });
});

$(document).ready(function() {
    $('#editShiftSalaryWorker').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidShiftSalaryWorker').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});

$(document).ready(function() {
    $('#editEmailManager').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidEmailManager').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});

$(document).ready(function() {
    $('#editPhoneManager').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidPhoneManager').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','0%');
            }
        }
    });
});



$(document).ready(function() {
    $('#editEmailManager2').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidEmailManager2').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});

$(document).ready(function() {
    $('#editPhoneManager2').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidPhoneManager2').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','0%');
            }
        }
    });
});





$(document).ready(function() {
    $('#editShiftSalaryManager').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidShiftSalaryManager').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});






$(document).ready(function() {
    $('#editEmailBoss').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidEmailBoss').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});



$(document).ready(function() {
    $('#editPhoneBoss').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidPhoneBoss').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','0%');
            }
        }
    });
});



$(document).ready(function() {
    $('#editEmailBoss2').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidEmailBoss2').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});



$(document).ready(function() {
    $('#editPhoneBoss2').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#editValidPhoneBoss2').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','0%');
            }
        }
    });
});

$(document).ready(function() {
    $('#EditSalaryShiftBoss').blur(function() {
        if($(this).val() != '') {
            var pattern = /^\d+$/;
            if(!pattern.test($(this).val())){
                $(this).css({'border' : '1px solid #ff0000'});
                $('#EditValidSalaryShiftBoss').text('Неверный формат').css('font-size','14px').css('color','#a94442')
                    .css('margin-left','3%');
            }
        }
    });
});



//  валидация форм создания worker/manage/boss плагином (bootstrapValidator) подключение плагина:
//  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"/>
//  <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"> </script>
//  В ACCOUNTING PAGE!!!!
$(document).ready(function() {
    $('#addWorker,#addManager,#addBoss').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Имя" не должно быть пустым'
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Фамилия" не должно быть пустым'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Email" не должно быть пустым'
                    },
                    regexp: {
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                        message: 'Неверный формат Email'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Телефон" не должно быть пустым'
                    },
                    regexp: {
                        regexp: /^(8|\+7)?[\d]{10}$/,
                        message: 'Неверный формат номера телефона'
                    }
                }
            },
            shiftSalary: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Оклад" не должно быть пустым'
                    },
                    regexp: {
                        regexp: /^\d+$/,
                        message: 'Неверный формат'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Пароль для входа в систему" не должно быть пустым'
                    },
                    identical: {
                        field: 'submitPassword',
                        message: 'Пароли не совпадают'
                    }
                }
            },
            submitPassword: {
                validators: {
                    notEmpty: {
                        message: 'Поле "Подтвердите пароль" не должно быть пустым'
                    },
                    identical: {
                        field: 'password',
                        message: 'Пароли не совпадают'
                    }
                }
            }
        }
    });
});











