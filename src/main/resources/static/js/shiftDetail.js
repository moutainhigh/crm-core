
var totalPrice = 0;

function formatTime(element) {
    var elem = document.getElementById(element);
    var d = new Date();
    d.setTime(Date.parse(elem.textContent));
    var dd = d.getDate();
    var MM = d.getMonth() + 1;
    var HH = d.getHours();
    var mm = d.getMinutes();
    elem.textContent = dd + "." + MM + " " + HH + ":" + mm;
}

function insertTotalPrice(clients, element) {
    document.getElementById(element).innerHTML += totalPrice;
    totalPrice = 0;
}

function setPrice(price) {
    totalPrice += price;
}