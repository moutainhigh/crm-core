package com.cafe.crm.service_impl.calculateService;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.service_abstract.calculateService.CalculatePriceService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

// TODO: 30.06.2017  изменить время calculatePriceTime на серверное при мерже
@Service
public class CalculatePriceServiceImpl implements CalculatePriceService {

	@Autowired
	private TimeManager timeManager;

	public void calculatePriceTime(Client client) {
		LocalTime timeStart = client.getTimeStart();
		LocalTime timeNow = timeManager.getDate().withSecond(0).withNano(0).toLocalTime();
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour()).minusMinutes(timeStart.getMinute());
		client.setPassedTime(timePassed);
		double priceTime;
		long passedHours = (long) timePassed.getHour();
		long passedMinutes = (long) timePassed.getMinute();
		double firstHour = 300; //  ставка за первый час
		double secondHour = 200; // ставка за второй час
		if (passedHours == 0) {
			priceTime = firstHour;
		} else {
			priceTime = firstHour + ((passedHours - 1.0) * secondHour) + (passedMinutes / 60.0) * secondHour;
		}
		client.setPriceTime(Math.round(priceTime * 100) / 100.00);
	}

	public void addDiscountOnPriceTime(Client client) {
		double priceTime = client.getPriceTime();
		priceTime -= (priceTime * (client.getDiscount() + client.getDiscountWithCard())) / 100;
		client.setPriceTime(priceTime);
	}

	public void getAllPrice(Client client) {
		client.setAllPrice(client.getPriceMenu() + client.getPriceTime());
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
		if (client.getPayWithCard() < 0 || client.getCard() == null) {
			client.setPayWithCard(0L);
		}
		client.setCache(allPrice - client.getPayWithCard());
	}

}
