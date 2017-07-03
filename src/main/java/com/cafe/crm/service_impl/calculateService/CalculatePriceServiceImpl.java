package com.cafe.crm.service_impl.calculateService;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.service_abstract.calculateService.CalculatePriceService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class CalculatePriceServiceImpl implements CalculatePriceService {

	@Autowired
	private TimeManager timeManager;

	public void calculatePriceTime(Client client) {
		LocalTime timeStart = client.getTimeStart();
		LocalTime timeNow = timeManager.getDate().withSecond(0).withNano(0).toLocalTime();
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour()).minusMinutes(timeStart.getMinute());
		client.setPassedTime(timePassed);
		long hour = (long) timePassed.getHour();
		long min = (long) timePassed.getMinute();
		double priceTime;
		if (hour == 0) {
			priceTime = 300.0;//(min * 10.0 / 6.0) * 3.0;
		} else {
			priceTime = ((hour - 1.0) * 200.0) + (min * 10.0 / 6.0) * 2.0 + 300.0;
		}
		client.setPriceTime(Math.round(priceTime * 100) / 100.00);
	}

	public void addDiscountToAllPrice(Client client) {
		double allPrice = client.getPriceMenu() + client.getPriceTime();
		allPrice -=  (allPrice * (client.getDiscount() + client.getDiscountWithCard())) / 100;
		client.setAllPrice(allPrice);
	}

	public void round(Client client) {
		Long allLong = client.getAllPrice().longValue();
		Long two = allLong % 100;

		if (two > 50) {
			if (two >= 75) {
				client.setAllPrice((double) (allLong - two) + 100);
			} else {
				client.setAllPrice((double) (allLong - two) + 50);
			}
		} else if (two < 50) {
			if (two >= 25) {
				client.setAllPrice((double) (allLong - two) + 50);
			} else {
				client.setAllPrice((double) (allLong - two));
			}
		} else {
			client.setAllPrice((double) allLong);
		}
	}

	public void payWithCardAndCache(Client client) {
		Long payWithCard = client.getPayWithCard();
		Long allPrice = client.getAllPrice().longValue();


		client.setPayWithCard(allPrice < client.getPayWithCard() ? allPrice : payWithCard);
		client.setPayWithCard(client.getPayWithCard() < 0 ? 0 : client.getPayWithCard());
		client.setPayWithCard(client.getCard() == null ? 0 : client.getPayWithCard());
		client.setCache(allPrice - client.getPayWithCard());
	}

}
