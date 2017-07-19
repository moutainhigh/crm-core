function sendLogLevel() {
    var check = $('#chooseLogLevel').val();
    if (check == 'INFO' || check == 'ERROR' || check == 'DEBUG' || check == 'WARN') {
        $('#formLog').submit();
    }
}