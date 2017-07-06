function test(id) {
    if ($('#1' + id).attr('type') == 'hidden') {
        $('#1' + id).attr('type', 'text');
    } else {
        $('#1' + id).attr('type', 'hidden');
    }
}


function test2() {
    var salaryShift = parseFloat($("#salaryShift").val());
    var cache = parseFloat($("#cache").val());
    var payWithCard = parseFloat($("#payWithCard").val());
    var test2 = cache + payWithCard;

    if (test2 != salaryShift) {
        $("#cache").css('border', 'red 3px solid');
        $("#payWithCard").css('border', 'red 3px solid');
        $('#errorBlock').html('Сверка бюджета не прошла!');
        $("#closeShiftButton").css('visibility', 'visible');
    } else {
        $('#errorBlock').css('visibility', 'hidden');
        $("#cache").css('border', 'green 3px solid');
        $("#payWithCard").css('border', 'green 3px solid');
        $("#closeShiftButton").css('visibility', 'visible');
        $("#budget").css('visibility', 'hidden');
        $('#goodBlock').html('Сверка бюджета прошла успешно!');
    }
}
