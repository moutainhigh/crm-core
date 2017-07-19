getServerTimeDateAjax();
function getServerTimeDateAjax() {
    $.ajax({
        type: "POST",
        url: "/boss/settings/general-setting/get-server-time-date",
        success: function (data) {
            var date = (data[0].dayOfMonth < 10 ? '0' + data[0].dayOfMonth : data[0].dayOfMonth)// потому что при парсе в дату это ***** прибавляет месяц
                + '-' + (data[0].monthValue < 10 ? '0' + data[0].monthValue : data[0].monthValue) + '-' + data[0].year;
            var time = new Date(data[0].year, data[0].monthValue, data[0].dayOfMonth,
                data[0].hour, data[0].minute, data[0].second, 0);
            $("#state").html('Время: ' + (data[1] ? 'server' : 'local'));
            $("#date").html(date);
            $("#time").html(time.toLocaleTimeString());
        },
        error: function () {
            console.log('getServerTimeDate сломался?');
        }
    });
}

