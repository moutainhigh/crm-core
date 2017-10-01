jQuery(document).ready( function() {
    var totalGivenDebt = 0;
    var totalRepaidDebt = 0;

    Array.from(document.getElementById("givenPrice").rows).forEach(function (item) {
        totalGivenDebt += +item.querySelector(".specificGivenPrice").innerHTML;
    });

    Array.from(document.getElementById("repaidPrice").rows).forEach(function (item) {
        totalRepaidDebt += +item.querySelector(".specificRepaidPrice").innerHTML;
    });

    if (totalGivenDebt === 0 && totalRepaidDebt !== 0) {
        createAndInsertTotalDebtCell("repaidPrice", totalRepaidDebt);
    } else if (totalGivenDebt !== 0 && totalRepaidDebt === 0) {
        createAndInsertTotalDebtCell("givenPrice", totalGivenDebt);
    } else if (totalGivenDebt !== 0 && totalRepaidDebt !== 0) {
        createAndInsertTotalDebtCell("givenPrice", totalGivenDebt);
        createAndInsertTotalDebtCell("repaidPrice", totalRepaidDebt);
    }



    if($("#clubCardPaymentSum").length) {
        var totalSumWithCard = [0, 0, 0, 0, 0, 0];
        Array.from(document.getElementById("clients").rows).forEach(function(item) {
            if(item.querySelector('.commonCheck')) {
                totalSumWithCard[0] += +item.querySelector('.commonCheck').innerHTML;
            }
            if(item.querySelector('.cashPayment')) {
                totalSumWithCard[1] += +item.querySelector('.cashPayment').innerHTML;
            }
            if(item.querySelector('.clubCardPayment')) {
                totalSumWithCard[2] += +item.querySelector('.clubCardPayment').innerHTML;
            }
            if(item.querySelector('.menuOtherCost')) {
                totalSumWithCard[3] += +item.querySelector('.menuOtherCost').innerHTML;
            }
            if(item.querySelector('.menuDirtyCost')) {
                totalSumWithCard[4] += +item.querySelector('.menuDirtyCost').innerHTML;
            }
            if(item.querySelector('.timeCost')) {
                totalSumWithCard[5] += +item.querySelector('.timeCost').innerHTML;
            }
        });
        var totalDirtyPriceWithOutCard = totalSumWithCard[0] + totalRepaidDebt - totalGivenDebt;
        document.getElementById("commonCheckSum").innerHTML += " " + totalDirtyPriceWithOutCard;
        document.getElementById("cashPaymentSum").innerHTML += " " + totalSumWithCard[1];
        document.getElementById("clubCardPaymentSum").innerHTML += " " + totalSumWithCard[2];
        document.getElementById("menuOtherSum").innerHTML += " " + totalSumWithCard[3];
        document.getElementById("menuDirtySum").innerHTML += " " + totalSumWithCard[4];
        document.getElementById("timeCostSum").innerHTML += " " + totalSumWithCard[5];
    } else {
        var totalSum = [0, 0, 0, 0, 0];
        Array.from(document.getElementById("clients").rows).forEach(
            function(item) {
                if(item.querySelector('.commonCheck')) {
                    totalSum[0] += +item.querySelector('.commonCheck').innerHTML;
                }
                if(item.querySelector('.cashPayment')) {
                    totalSum[1] += +item.querySelector('.cashPayment').innerHTML;
                }
                if(item.querySelector('.menuOtherCost')) {
                    totalSum[2] += +item.querySelector('.menuOtherCost').innerHTML;
                }
                if(item.querySelector('.menuDirtyCost')) {
                    totalSum[3] += +item.querySelector('.menuDirtyCost').innerHTML;
                }
                if(item.querySelector('.timeCost')) {
                    totalSum[4] += +item.querySelector('.timeCost').innerHTML;
                }
            });
        var totalDirtyPrice = totalSum[0] + totalRepaidDebt - totalGivenDebt;
        document.getElementById("commonCheckSum").innerHTML += " " + totalDirtyPrice;
        document.getElementById("cashPaymentSum").innerHTML += " " + totalSum[1];
        document.getElementById("menuOtherSum").innerHTML += " " + totalSum[2];
        document.getElementById("menuDirtySum").innerHTML += " " + totalSum[3];
        document.getElementById("timeCostSum").innerHTML += " " + totalSum[4];
    }

});

function createAndInsertTotalDebtCell(tbodyId, totalDebt) {
    var tbody = document.getElementById(tbodyId);
    var row = tbody.getElementsByTagName("tr")[0];
    var rowsCount = tbody.getElementsByTagName("tr").length;
    var cell = row.insertCell(0);
    cell.innerHTML = totalDebt;
    cell.rowSpan = rowsCount;
    if (tbodyId === "repaidPrice") {
        cell.style.background = "#CEFFD0";
    } else {
        cell.style.background = "#FFD7D7";
    }

}