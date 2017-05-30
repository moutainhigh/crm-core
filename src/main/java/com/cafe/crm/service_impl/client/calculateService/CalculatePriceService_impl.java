package com.cafe.crm.service_impl.client.calculateService;

import com.cafe.crm.service_abstract.client.CalculatePriceService;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Client;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class CalculatePriceService_impl implements CalculatePriceService {

	public Double calculatePriceTime(Calculate calculate) {
		Double allPriceTime = 0.0;
		Long totalNumber = 0L;
		calculate.setPassedTime(LocalTime.of(0,0,0));
		for (Client client : calculate.getClient()) {
			LocalTime timeStart = client.getTimeStart();
			LocalTime timeNow = LocalTime.now().withSecond(0);
			LocalTime timePassed = timeNow.minusHours(timeStart.getHour()).minusMinutes(timeStart.getMinute());
			calculate.setPassedTime(
					calculate.getPassedTime().plusHours(timePassed.getHour()).plusMinutes(timePassed.getMinute())
			);
			Long hour = (long) timePassed.getHour();
			Long min = (long) timePassed.getMinute();
			Double priceTime = 0.0;
			if (hour == 0) {
				priceTime += (min * 10.0 / 6.0) * 3.0;
			} else {
				priceTime += ((hour - 1.0) * 200.0) + (min * 10.0 / 6.0) * 2.0 + 300.0;
			}
			priceTime *= client.getTotalNumber();
			allPriceTime += priceTime;
			totalNumber += client.getTotalNumber();
		}
		calculate.setCalculateNumber(totalNumber);
		return Math.round(allPriceTime * 100) / 100.00;
	}

	public Double calculatePriceTime(Client client, Calculate calculate) {
		LocalTime timeStart = client.getTimeStart();
		LocalTime timeNow = LocalTime.now().withSecond(0);
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour()).minusMinutes(timeStart.getMinute());
		calculate.setPassedTime(timePassed);
		Long hour = (long) timePassed.getHour();
		Long min = (long) timePassed.getMinute();
		Double priceTime;

		if (hour == 0) {
			priceTime = (min * 10.0 / 6.0) * 3.0;
		} else {
			priceTime = ((hour - 1.0) * 200.0) + (min * 10.0 / 6.0) * 2.0 + 300.0;
		}
		priceTime *= calculate.getCalculateNumber();
		return Math.round(priceTime * 100) / 100.00;
	}

	public Double addDiscountToAllPrice(Calculate calculate) {

		Card card = calculate.getCard();

		Double allPrice = calculate.getPriceMenu() + calculate.getPriceTime();

		Long discCard = 0L;
		Long discInp;
		if (card != null) {
			discCard = card.getDiscount();
		}
		discInp = calculate.getDiscount();
		if (discInp <= 0 || discInp > 100) {
			discInp = 0L;
		}
		allPrice -= allPrice * (discCard + discInp) / 100;
		return (Math.round(allPrice * 100) / 100.00);
	}

	public Double round(Double allPrice) {

		Long allLong = allPrice.longValue();
		Long two = allPrice.longValue() % 100;

		if (two > 50) {
			if (two >= 75) {
				return (double) (allLong - two) + 100;
			} else {
				return (double) (allLong - two) + 50;
			}
		} else if (two < 50) {
			if (two >= 25) {
				return (double) (allLong - two) + 50;
			} else {
				return (double) (allLong - two);
			}
		} else {
			return (double) allLong;
		}

	}
}
