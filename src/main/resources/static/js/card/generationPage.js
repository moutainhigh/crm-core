function toggleIcon(e) {
    $(e.target)
        .prev('.panel-heading')
        .find(".more-less")
        .toggleClass('glyphicon-plus glyphicon-minus');
}
$('.panel-group').on('hidden.bs.collapse', toggleIcon);
$('.panel-group').on('shown.bs.collapse', toggleIcon);

$(document).ready(function () {
    if ($('#ch:checked').length) {
        $('#sr').attr('readonly', true); // On Load, should it be read only?
    }

    $('#ch').change(function () {
        if ($('#ch:checked').length) {
            $('#sr').attr('readonly', true); //If checked - Read only
        } else {
            $('#sr').attr('readonly', false);//Not Checked - Normal
        }
    });
});

$(document).ready(function () {
    if ($('#ch2:checked').length) {
        $('#sr2').attr('readonly', true); // On Load, should it be read only?
    }

    $('#ch2').change(function () {
        if ($('#ch2:checked').length) {
            $('#sr2').attr('readonly', true); //If checked - Read only
        } else {
            $('#sr2').attr('readonly', false);//Not Checked - Normal
        }
    });
});
