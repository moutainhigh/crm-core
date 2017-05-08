package com.cafe.crm.dao_impl.client.calculateService;

import com.cafe.crm.dao_impl.client.BoardService;
import com.cafe.crm.dao_impl.client.CalculateService;
import com.cafe.crm.dao_impl.client.CardService;
import com.cafe.crm.dao_impl.client.ClientService;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class CalculateControllerService {
	@Autowired
	private ClientService clientService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private BoardService boardService;


	public void addCalculate(Long id, Long number, String descr) {//передаются по полю от 3 разных сущностей
		Board board = boardService.getOne(id);

		Client client = new Client();
		if (number != null) {
			client.setNumber(number);
		}
		clientService.add(client);

		Calculate calculate = new Calculate();
		if (!descr.equals("")) {
			calculate.setDescription(descr);
		}
		calculate.setBoard(board);

		Set<Client> list = new HashSet<>();
		list.add(client);
		calculate.setClient(list);
		calculateService.add(calculate);
	}

	public void refreshBoard(Long idC, Long idB) {
		Board board = boardService.getOne(idB);
		Calculate calculate = calculateService.getOne(idC);
		System.out.println(calculate);
		calculate.setBoard(board);
		calculateService.add(calculate);
	}

	public void addClient(Long id, Long number, String descr) {
		Client client = new Client();

		if (number != null) {
			client.setNumber(number);
		}

		if (!descr.equals("")) {
			client.setDescription(descr);
		}
		clientService.add(client);

		Calculate calculate = calculateService.getOne(id);

		Set<Client> clients = calculate.getClient();
		clients.add(client);

		calculate.setClient(clients);
		calculateService.add(calculate);

	}

	public void calculatePrice(Long discount, Long clientId, String numberToCalculate) {
		Client client = clientService.getOne(clientId);

		LocalTime timeStart = client.getTimeStart();//получаем время создания клиента
		LocalTime timeNow = LocalTime.now().withSecond(0); //время сейчас
		LocalTime timePassed = timeNow.minusHours(timeStart.getHour()); //расчет количества пройденного времени с создания
		timePassed = timePassed.minusMinutes(timeStart.getMinute());
		Long hour = (long) timePassed.getHour();// делим пройденное время на часы и минуты
		Long min = (long) timePassed.getMinute();
		Double priceTime;

		if (hour == 0) {
			priceTime = (min * 10.0 / 6.0) * 3.0; // первая считает если прошло меньше часа
		} else {
			priceTime = ((hour - 1.0) * 200.0) + (min * 10.0 / 6.0) * 2.0 + 300.0; // если прошло больше часа, считает по этой
		}

		if (numberToCalculate.equals("Всех")) { //если нужно посчтитать всех, то умножаем на кол-во человек, после обнуляем
			priceTime *= client.getNumber();
			client.setNumber((long) 0);
		} else if (client.getNumber() > 0 && Long.parseLong(numberToCalculate) <= client.getNumber()) { //если запрашиваемое число больше чем есть, выводит 0 цену, людей не отнимает
			Long count = Long.parseLong(numberToCalculate);
			priceTime *= count;
			client.setNumber(client.getNumber() - count);
		} else {
			priceTime = 0.0;
		}

		client.setPriceTime(Math.round(priceTime * 100) / 100.00);

		client.setAllPrice(client.getPriceMenu() + client.getPriceTime());//высчитывается из меню + цена времени и округляем до 2 знаков

		if (discount > 0 && discount <= 100){ //скидка не может быть больше 100%
					client.setAllPrice(client.getAllPrice() - (client.getAllPrice()*discount/100));
					client.setAllPrice(Math.round(client.getAllPrice() * 100) / 100.00);
				}

		Double all = client.getAllPrice();
		Long allLong = all.longValue();
		Long two = all.longValue() % 100;

		if (two > 50) {
			if (two >= 75) {
				client.setRound((allLong - two) + 100);
			} else {
				client.setRound((allLong - two) + 50);
			}
		} else if (two < 50) {
			if (two >= 25) {
				client.setRound((allLong - two) + 50);
			} else {
				client.setRound(allLong - two);
			}
		} else {
			client.setRound(allLong);
		}

		clientService.add(client);
	}

	public void state(Long id) {
		Calculate calculate = calculateService.getOne(id);
		if (calculate.isStatePanel()) {
			calculate.setStatePanel(false);
		} else {
			calculate.setStatePanel(true);
		}
		calculateService.add(calculate);
	}
}
