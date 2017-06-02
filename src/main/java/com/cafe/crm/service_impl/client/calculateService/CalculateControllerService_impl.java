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

	public void createCalculate(Long id, Long number, String descr) {
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

	public Calculate calculatePrice(Long clientId, Long calculateNumber, Long discount, Boolean flagCard, Long calculateId) {
		Calculate calculate = calculateService.getOne(calculateId); //if clientId = -1 - all calculate.
		Double priceTime;
		if (clientId != -1) {
			Client client = clientService.getOne(clientId);
			calculate.setDescriptionCheck(calculate.getDescription() + "(" + client.getDescription() + ")");
			calculate.setTotalNumber(client.getTotalNumber()); //Duplication field totalNumber in calculate, is needed for frontend
			calculate.setCalculateNumber(calculateNumber);
			priceTime = calculatePriceService.calculatePriceTime(client, calculate);
			clientService.add(client);
		} else {
			calculate.setDescriptionCheck(calculate.getDescription() + "(" + "общий расчет" + ")");
			priceTime = calculatePriceService.calculatePriceTime(calculate);
		}
		calculate.setPriceTime(priceTime);
		calculate.setDiscount(discount);
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
		return calculate;
	}

	public void closeClient(Long idCal, Long idCl) {
		Calculate calculate = calculateService.getOne(idCal);

		Client client = clientService.getOne(idCl);
		Card card = calculate.getCard();

		if (calculate.getPayWithCard() > 0) {
			card.setBalance(card.getBalance() - calculate.getPayWithCard());
			cardService.add(card);
		}

		if (idCl != -1) {
			if (client.getTotalNumber() > 0) {
				client.setTotalNumber(client.getTotalNumber() - calculate.getCalculateNumber());
				clientService.add(client);
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
