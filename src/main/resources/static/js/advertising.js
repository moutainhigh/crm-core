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
        resetValues($('#adFile'), $('#adText'));
        hideIfVisible($('#fromFile'));
        hideIfVisible($('#fromText'));
    });
    $('#collapseFile').click(function () {
        url = "/advertising/file";
        resetValues($('#adUrl'), $('#adText'));
        hideIfVisible($('#fromUrl'));
        hideIfVisible($('#fromText'));
    });
    $('#collapseText').click(function () {
        url = "/advertising/text";
        resetValues($('#adFile'), $('#adUrl'));
        hideIfVisible($('#fromUrl'));
        hideIfVisible($('#fromFile'));
    });

    $('#adForm').submit(function (e) {
        var input = $("#adLink").val();
        if ((input.trim().length == 0) && (($('#adFile').val() != "") || ($('#adUrl').val() != ""))) {
            $('#adLink').val("https://vk.com/hookahpacman");
        };
        e.preventDefault();

        var hasInputData = function (elem) {
            return (elem.val() != "");
        };

        var inputDataIsEmpty = function (elem1, elem2, elem3) {
            return !hasInputData(elem1) && !hasInputData(elem2) && !hasInputData(elem3);
        };


        var formData = new FormData($(this)[0]);

        var clean = function () {
            $('#adUrl').val(null);
            $('#adFile').val(null);
            $('#adText').val(null);
            $('#adLink, #adSubject').val("");
            $('#fromFile, #fromUrl, #fromText').collapse('hide');
        };

        if (!inputDataIsEmpty($('#adUrl'), $('#adFile'), $('#adText'))) {
            $.ajax({
                type: "POST",
                url: url,
                data: formData,
                success: function (data) {
                    var successMessage = '<h4 style="color:green;" align="center">' + data + '</h4>';
                    $('.messageAd').html(successMessage).show().delay(3000).hide(500);
                    clean();
                },
                error: function (error) {
                    var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                    $('.messageAd').html(errorMessage).show().delay(3000).hide(500);
                    clean();
                },
                cache: false,
                contentType: false,
                processData: false
            })
        } else {
            var warningMessage = '<h4 style="color:orangered;" align="center">Выберите одну из форм загрузки рекламы</h4>';
            $('.messageAd').html(warningMessage).show().delay(3000).hide(500);
        }
    });
});

