$(document).ready(function () {
    $('#formStepConfiguration').on("submit", function (e) {
        e.preventDefault();
        var url = "/company/configuration/step/vk/add";
        var accessToken = $('#inputAccessToken').val();
        var applicationId = $('#inputApplicationId').val();
        var chatId = $('#inputChatId').val();
        var messageName = $('#inputMessageName').val();
        var apiVersion = $('#inputApiVersion').val();
        var data = {accessToken: accessToken, applicationId: applicationId, chatId: chatId,
            messageName: messageName, apiVersion: apiVersion};
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            success: function (data) {
                location.reload();
            },
            error: function (error) {
                e.preventDefault();
                var errorMessage = '<h4 style="color:red;" align="center">' + error.responseText + '</h4>';
                $('.errorMessage').html(errorMessage).show();
            }

        });
    });
    $('input[type="text"]').get(0).focus();
});

