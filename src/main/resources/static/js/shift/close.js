function test(id) {
    if ($('#1' + id).attr('type') == 'hidden') {
        $('#1' + id).attr('type', 'text');
    } else {
        $('#1' + id).attr('type', 'hidden');
    }
}


function test2() {
    var totalCashBox = parseFloat($("#totalCashBox").val());
    var cashBox = parseFloat($("#cashBox").val());
    var payWithCard = parseFloat($("#payWithCard").val());
    var bankCashBox = parseFloat($("#bankCashBox").val());
    var budget = cashBox + bankCashBox;
    if (budget < totalCashBox) {
        $("#cashBox").css('border', 'red 3px solid');
        $("#bankCashBox").css('border', 'red 3px solid');
        $('#errorBlock').html('Сверка бюджета не прошла!');
        $("#closeShiftButtonShortage").css('visibility', 'visible');
        // $("#budget").css('visibility', 'hidden');
    } else if (budget >= totalCashBox) {
        $('#budget').attr('type', 'submit');
    }
}

function recalculation() {
    $.ajax({
        type: "POST",
        url: "/manager/recalculation",
        data: $('#form').serialize(),
        success: function (data) {
            $('#usersTotalShiftSalary').val(data[0]);
            $('#totalCashBox').val(data[1]);
        },
        error: function () {
            console.log('ajaxModal сломался? ');
        }
    });
}

$(document).ready(function () {
    $('#checklistInputForm').submit(function (e) {
        e.preventDefault();
        var form = document.getElementById('checklistInputForm');
        var inputs = form.getElementsByTagName('input');
        var isChecked = false;
        for(var x = 0; x < inputs.length; x++) {
            if(inputs[x].type == 'checkbox') {
               isChecked = inputs[x].checked;
                if(!isChecked) {
                    $('#checklistModal').find('.showMessage').text('Вы выбрали не все пункты');
                    break;
                }
            }
        }
        if(isChecked) {
            var checklistModal = $('#checklistModal');
            var addShiftModal = $('#add');
            addShiftModal.modal('show');
            checklistModal.modal('hide');
        }
    });
});

$(document).ready(function () {
    $('#checklistInputForm').submit(function (e) {
        e.preventDefault();
        var form = document.getElementById('checklistInputForm');
        var inputs = form.getElementsByTagName('input');
        var isChecked = false;
        for(var x = 0; x < inputs.length; x++) {
            if(inputs[x].type == 'checkbox') {
                isChecked = inputs[x].checked;
                if(!isChecked) {
                    $('#checklistOnCloseModal').find('.showMessage').text('Вы выбрали не все пункты');
                    break;
                }
            }
        }
        if(isChecked) {
            var checklistModal = $('#checklistOnCloseModal');
            var closeShiftModal = $('#closeShiftModalId');
            closeShiftModal.modal('show');
            checklistModal.modal('hide');
        }
    });
});