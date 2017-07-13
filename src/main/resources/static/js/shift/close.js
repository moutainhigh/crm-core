function test(id){
    if ($('#1'+ id).attr('type')=='hidden'){
        $('#1'+ id).attr('type','text');
    }else {
        $('#1'+ id).attr('type','hidden');
    }
}


//<![CDATA[
function test2(){
    var cashBox = parseFloat($("#totalCashBox").val());
    var cache = parseFloat($("#cache").val());
    var payWithCard = parseFloat($("#payWithCard").val());
    var bankKart = parseFloat($("#bankKart").val());
    var budget = cache + payWithCard + bankKart;
    if (budget < cashBox){
        $("#cache").css('border', 'red 3px solid');
        $("#bankKart").css('border', 'red 3px solid');
        $('#errorBlock').html('Сверка бюджета не прошла!');
        $("#closeShiftButtonShortage").css('visibility','visible');
        $("#budget").css('visibility','hidden');
    } else if (budget >= cashBox) {
        $('#budget').attr('type','submit');
    }
}
//]]>
