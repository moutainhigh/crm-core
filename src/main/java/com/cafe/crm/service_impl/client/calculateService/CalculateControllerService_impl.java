package com.cafe.crm.service_impl.client.calculateService;

import com.cafe.crm.service_abstract.client.CalculateControllerService;
import com.cafe.crm.service_impl.client.BoardService;
import com.cafe.crm.service_impl.client.CalculateService;
import com.cafe.crm.service_impl.CardService;
import com.cafe.crm.service_impl.client.ClientService;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CalculateControllerService_impl implements CalculateControllerService {
	@Autowired
	private ClientService clientService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private CardService cardService;

	@Autowired
	private CalculatePriceService_impl calculatePriceService;

	public void addCalculate(Long id, Long number, String descr) {
		Board board = boardService.getOne(id);
		Client client = new Client();
		client.setTotalNumber(number);
		clientService.add(client);
		Calculate calculate = new Calculate();
		calculate.setDescription(descr);
		calculate.setBoard(board);
		Set<Client> list = new HashSet<>();
		list.add(client);
		calculate.setClient(list);
		calculateService.add(calculate);
	}

	public void refreshBoard(Long idC, Long idB) {
		Board board = boardService.getOne(idB);
		Calculate calculate = calculateService.getOne(idC);
		calculate.setBoard(board);
		calculateService.add(calculate);
	}

	public void addClient(Long id, Long number, String descr) {
		Client client = new Client();
		client.setTotalNumber(number);
		client.setDescription(descr);
		clientService.add(client);
		Calculate calculate = calculateService.getOne(id);
		Set<Client> clients = calculate.getClient();
		clients.add(client);
		calculate.setClient(clients);
		calculateService.add(calculate);
	}

	public void calculatePrice(Long clientId, Long calculateNumber, Long discount, Boolean flagCard, Long calculateId) {
		Calculate calculate = calculateService.getOne(calculateId); //-1 this all calculate. null Recalculation.

		/* в общем логика такая. Жмем на кнопку кого нужно расчитать. Передается айди клиента и мы его достатем и считаем,
		 если айди клиента не передается  (null), значит мы пересчитываем по старому айди, которое занесено в поле, потому что это значит, что человек находится
		 в модальном окне и настраивает параметры. Если же передается -1 значит он жмет на кнопку общего расчета,
		 будем использовать другую логику и метод расчета, который я перегрузил. У карты есть флаг, который обозначает оплачиваем
		 ли мы с карты или нет*/

		if (clientId != null) {
			calculate.setClientId(clientId);
		}
		if (calculate.getClientId() != -1) {
			Client client = clientService.getOne(calculate.getClientId());
			calculate.setDescriptionCheck(calculate.getDescription() + "(" + client.getDescription() + ")");
			calculate.setTotalNumber(client.getTotalNumber()); //Duplication field totalNumber in calculate, is needed for frontend
			if (clientId != null) {
				calculate.setCalculateNumber(client.getTotalNumber());
				calculate.setDiscount(0L);
			} else {
				calculate.setCalculateNumber(calculateNumber);
				calculate.setDiscount(discount);
			}
			Double priceTime = calculatePriceService.calculatePriceTime(client, calculate);
			calculate.setPriceTime(priceTime);
			clientService.add(client);
		} else {
			calculate.setDescriptionCheck(calculate.getDescription() + "(" + "общий расчет" + ")");
			if (clientId != null) {
				calculate.setDiscount(0L);
			} else {
				calculate.setDiscount(discount);
			}
			Double priceTime = calculatePriceService.calculatePriceTime(calculate);
			calculate.setPriceTime(priceTime);
		}
		calculate.setAllPrice(calculatePriceService.addDiscountToAllPrice(calculate));
		calculate.setAllPrice(calculatePriceService.round(calculate.getAllPrice()));
		if (flagCard) {
			if (calculate.getAllPrice() <= calculate.getCard().getBalance()) {
				calculate.setPayWithCard(calculate.getAllPrice());
				calculate.setAllPrice(0.0);
			} else {
				calculate.setPayWithCard(calculate.getCard().getBalance());
				calculate.setAllPrice(calculate.getAllPrice() - calculate.getCard().getBalance());
			}
		} else {
			calculate.setPayWithCard(0.0);
		}
		calculateService.add(calculate);
	}

	public void closeClient(Long id) {
		Calculate calculate = calculateService.getOne(id);
		Client client = clientService.getOne(calculate.getClientId());
		Card card = calculate.getCard();

		if (calculate.getPayWithCard() != 0) {
			card.setBalance(card.getBalance() - calculate.getPayWithCard());
			cardService.add(card);
		}

		if (calculate.getClientId() != -1) {
			if (client.getTotalNumber() > 0) {
				client.setTotalNumber(client.getTotalNumber() - calculate.getCalculateNumber());
				clientService.add(client);

				calculateService.add(calculate);
			}
			if (client.getTotalNumber() == 0) { // not else. Both conditions are used
				Set<Client> set = calculate.getClient();
				set.remove(client);
				calculate.setClient(set);
				calculateService.add(calculate);
			}
		} else {
			Set<Client> set = new HashSet<>();
			calculate.setClient(set);
			calculateService.add(calculate);
		}
		calculate.setSpend(calculate.getSpend() + calculate.getAllPrice() + calculate.getPayWithCard());
		if (!calculate.getClient().iterator().hasNext()) {
			calculate.setState(false);
			calculateService.add(calculate);

		}

	}
}
