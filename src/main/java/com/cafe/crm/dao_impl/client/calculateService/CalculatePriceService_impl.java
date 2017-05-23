package com.cafe.crm.dao_impl.client.calculateService;

import com.cafe.crm.dao.client.calculateService.CalculatePriceService;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Card;
import com.cafe.crm.models.client.Client;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class CalculatePriceService_impl implements CalculatePriceService {

	public Double calculatePrice(Client client) {
		LocalTime timeStart = client.getTimeStart();
		LocalTime timeNow = LocalTime.now().withSecond(0);
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour());
		timePassed = timePassed.minusMinutes(timeStart.getMinute());

		client.setPassedTime(timePassed);

		Long hour = (long) timePassed.getHour();
		Long min = (long) timePassed.getMinute();
		Double priceTime;

		if (hour == 0) {
			priceTime = (min * 10.0 / 6.0) * 3.0;
		} else {
			priceTime = ((hour - 1.0) * 200.0) + (min * 10.0 / 6.0) * 2.0 + 300.0;
		}

		priceTime *= client.getCalculateNumber();

		return Math.round(priceTime * 100) / 100.00;
	}

	public Double addDiscountToAllPrice(Client client) {
		Double allPrice = client.getPriceMenu() + client.getPriceTime();
		Calculate calculate = client.getCalculate();
		Card card = calculate.getCard();
		Long discCard = 0L;
		Long discInp;
		if (card != null) {
			discCard = card.getDiscount();
		}
		discInp = client.getDiscount();
		if ( discInp <= 0 || discInp > 100) {
			discInp = 0L;
		}
		allPrice -= allPrice * (discCard + discInp) / 100;

		return (Math.round(allPrice * 100) / 100.00);
	}

}
