$(document).ready(function () {

    var url = "/advertising/url";

    var hideIfVisible = function (elem) {
        if (elem.is(':visible')) {
            elem.collapse('hide');
        }
    };

    var resetValues = function (unnecessary1, unnecessary2) {
        unnecessary1.val(null);
        unnecessary2.val(null);
    };

    $('#collapseUrl').click(function () {
        url = "/advertising/url";
        resetValues($('#mailFile'), $('#mailText'));
        hideIfVisible($('#fromFile'));
        hideIfVisible($('#fromText'));
        $('#mailLink').show();
    });
    $('#collapseFile').click(function () {
        url = "/advertising/file";
        resetValues($('#mailUrl'), $('#mailText'));
        hideIfVisible($('#fromUrl'));
        hideIfVisible($('#fromText'));
        $('#mailLink').show();
    });
    $('#collapseText').click(function () {
        url = "/advertising/text";
        resetValues($('#mailFile'), $('#mailUrl'));
        hideIfVisible($('#fromUrl'));
        hideIfVisible($('#fromFile'));
        $('#mailLink').hide();
    });

    $('#mailForm').submit(function (e) {
        // var input = $("#mailLink").val();
        // if ((input.trim().length == 0) && (($('#mailFile').val() != "") || ($('#mailUrl').val() != ""))) {
        //     $('#mailLink').val("https://vk.com/hookahpacman");
        // };
        e.preventDefault();

        var hasInputData = function (elem) {
            return (elem.val() != "");
        };

        var inputDataIsEmpty = function (elem1, elem2, elem3) {
            return !hasInputData(elem1) && !hasInputData(elem2) && !hasInputData(elem3);
        };


        var formData = new FormData($(this)[0]);

        var clean = function () {
            $('#mailUrl').val(null);
            $('#mailFile').val(null);
            $('#mailText').val(null);
            $('#mailLink, #mailSubject').val("");
            $('#fromFile, #fromUrl, #fromText').collapse('hide');
        };

        if (!inputDataIsEmpty($('#mailUrl'), $('#mailFile'), $('#mailText'))) {
            $.ajax({
                type: "POST",
                url: url,
                data: formData,
                success: function (data) {
                    var successMessage = '<h4 style="color:green;" align="center">' + data + '</h4>';
                    $('.messageMail').html(successMessage).show().delay(3000).hide(500);
                    clean();
                },
                error: function (error) {
                    var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                    $('.messageMail').html(errorMessage).show().delay(3000).hide(500);
                    clean();
                },
                cache: false,
                contentType: false,
                processData: false
            })
        } else {
            var warningMessage = '<h4 style="color:orangered;" align="center">Выберите одну из форм загрузки рекламы</h4>';
            $('.messageMail').html(warningMessage).show().delay(3000).hide(500);
        }
    });
});

