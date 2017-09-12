$(document).ready(function () {
    $('.buttonNoteDelete').on('click', function () {
        var url = '/boss/settings/note/delete';
        var id = $(this).data('id');
        var data = $('#noteId' + id).serialize();
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

$(document).ready(function () {
    $('.checkboxNoteEnable').on('click', function () {
        var url = '/boss/settings/note/changeStatus';
        var id = $(this).data('id');
        var enable = $(this).val();
        var data = {id : id, enable : enable};
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

$(document).ready(function () {
    $('#formAddNote').on('submit', function (e) {
        e.preventDefault();
        var url = '/boss/settings/note/add';
        var data = $(this).serialize();
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