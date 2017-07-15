package com.cafe.crm.services.impl.calculate;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.TimerOfPause;
import com.cafe.crm.services.interfaces.calculate.CalculatePriceService;
import com.cafe.crm.services.interfaces.calculate.TimerOfPauseService;
import com.cafe.crm.services.interfaces.property.PropertyService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class CalculatePriceServiceImpl implements CalculatePriceService {

	@Autowired
	private TimeManager timeManager;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private TimerOfPauseService timerOfPauseService;

	@Override
	public void calculatePriceTimeIfWasPause(Client client) {
		TimerOfPause timer = timerOfPauseService.findTimerOfPauseByIdOfClient(client.getId());
		Long timeOfPause = timer.getWholeTimePause();
		LocalTime timeStart = client.getTimeStart().toLocalTime().withSecond(0).withNano(0);
		LocalTime timeNow = timeManager.getTime().withSecond(0).withNano(0);
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour()).minusMinutes(timeStart.getMinute() + timeOfPause);
		client.setPassedTime(timePassed);
		double priceTime;
		long passedHours = (long) timePassed.getHour();
		long passedMinutes = (long) timePassed.getMinute();
		double firstHour = propertyService.getOne(1L).getValue(); //  ставка за первый час
		double secondHour = propertyService.getOne(2L).getValue(); // ставка за второй час
		if (passedHours == 0) {
			priceTime = firstHour;
		} else {
			priceTime = firstHour + ((passedHours - 1.0) * secondHour) + (passedMinutes / 60.0) * secondHour;
		}
		client.setPriceTime((double) Math.round(priceTime));
	}

	@Override
	public void calculatePriceTime(Client client) {
		LocalTime timeStart = client.getTimeStart().toLocalTime().withSecond(0).withNano(0);
		LocalTime timeNow = timeManager.getTime().withSecond(0).withNano(0);
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour()).minusMinutes(timeStart.getMinute());
		client.setPassedTime(timePassed);
		double priceTime;
		long passedHours = (long) timePassed.getHour();
		long passedMinutes = (long) timePassed.getMinute();
		double firstHour = propertyService.getOne(1L).getValue(); //  ставка за первый час
		double secondHour = propertyService.getOne(2L).getValue(); // ставка за второй час
		if (passedHours == 0) {
			priceTime = firstHour;
		} else {
			priceTime = firstHour + ((passedHours - 1.0) * secondHour) + (passedMinutes / 60.0) * secondHour;
		}
		client.setPriceTime((double) Math.round(priceTime));
	}


	@Override
	public void addDiscountOnPriceTime(Client client) {
		double priceTime = client.getPriceTime();
		priceTime -= (priceTime * (client.getDiscount() + client.getDiscountWithCard())) / 100;
		client.setPriceTime((double) Math.round(priceTime));
	}

	@Override
	public void getAllPrice(Client client) {
		client.setAllPrice((double) Math.round(client.getPriceMenu() + client.getPriceTime()));
	}

	@Override
	public void round(Client client, boolean stateRound) {
		if (!stateRound) {
			client.setAllPrice((double) Math.round(client.getAllPrice()));
			return;
		}
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

	@Override
	public void payWithCardAndCache(Client client) {
		Double payWithCard = client.getPayWithCard();
		Double allPrice = client.getAllPrice();
		client.setPayWithCard(allPrice < client.getPayWithCard() ? allPrice : payWithCard);
		if (client.getCard() == null) {
			client.setPayWithCard(0D);
		}
		client.setCache(allPrice - (double) client.getPayWithCard());
	}

}
