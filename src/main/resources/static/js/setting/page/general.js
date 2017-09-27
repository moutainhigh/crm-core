$(document).ready(function () {
    $('#buttonCardEnable').on('click', function () {
        var url = '/boss/settings/card/changeStatus';
        var data = $('#inputCardEnable').serialize();
        $.ajax({
            type: 'post',
            url: url,
            data: data,
            success: function () {
                location.reload();
            }
        })
    })
});
$(document).ready(function () {
    $('.buttonPropertyRefresh').on('click', function () {
        var url = '/boss/settings/general-setting/edit';
        var id = $(this).data('id');
        var data = $('#formProperty' + id).serialize();
        $.ajax({
            type: 'post',
            url: url,
            data: data,
            success: function () {
                location.reload();
            },
            error: function (error) {
                var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                $('.errorMessage').html(errorMessage).show();
            }
        })
    })
});