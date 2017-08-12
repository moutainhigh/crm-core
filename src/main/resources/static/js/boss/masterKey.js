function setMasterKey() {
    var new_pass_1 = $('#newKey').val();
    var new_pass_2 = $('#repeatNewKey').val();
    if (new_pass_1 === new_pass_2) {
        var formData = {
            new: $('#newKey').val(),
            old: $('#bossPassword').val(),
            secondNew: $('#repeatNewKey').val(),
        }
        $.ajax({
            type: "POST",
            url: "/boss/settings/masterKey/addMasterKey",
            data: formData,
            success: function (result) {
                $("#successModal").modal('show')
            },
            error: function (e) {
                $("#errorModal").modal('show')
                $('#old').val('');
            }
        });
    } else {
        $('#newKey, #repeatNewKey').val('').attr("placeholder", "");
        $("#wrongModal").modal('show')
        return false;
    }
}