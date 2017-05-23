package com.cafe.crm.dao_impl.client.calculateService;

import com.cafe.crm.dao.client.calculateService.CalculateControllerService;
import com.cafe.crm.dao_impl.client.BoardService;
import com.cafe.crm.dao_impl.client.CalculateService;
import com.cafe.crm.dao_impl.client.CardService;
import com.cafe.crm.dao_impl.client.ClientService;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Card;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CalculateControllerService_impl implements CalculateControllerService{
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
		if (number != null) {
			client.setTotalNumber(number);
			client.setCalculateNumber(number);
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
			client.setTotalNumber(number);
			client.setCalculateNumber(number);
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

	public void calculatePrice(Long clientId, Long calculateNumber, Long discount, Boolean flag) {
		Client client = clientService.getOne(clientId);
		client.setPayWithCard(null);
		client.setCalculateNumber(calculateNumber);
		Double priceTime = calculatePriceService.calculatePrice(client);
		client.setPriceTime(priceTime);
		if (discount != null) {
			client.setDiscount(discount);
		}
		client.setAllPrice(calculatePriceService.addDiscountToAllPrice(client));

		Calculate calculate = client.getCalculate();
		if (flag) {
			if (client.getAllPrice() <= calculate.getCard().getBalance()) {
				client.setPayWithCard(client.getAllPrice());
				client.setAllPrice(0.0);
			} else {
				client.setPayWithCard(calculate.getCard().getBalance());
				client.setAllPrice(client.getAllPrice() - calculate.getCard().getBalance());
			}
		}

		clientService.add(client);
		/* Rounding the price (not yet used)
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
		*/
	}

	public void closeClient(Long id) {
		Client client = clientService.getOne(id);
		Calculate calculate = client.getCalculate();
		Card card = calculate.getCard();

		if (client.getTotalNumber() > 0) {
			client.setTotalNumber(client.getTotalNumber() - client.getCalculateNumber());
			clientService.add(client);
			calculate.setSpend(calculate.getSpend() + client.getAllPrice());
			calculateService.add(calculate);
		}
		if(client.getPayWithCard() != null) {
			card.setBalance(card.getBalance() - client.getPayWithCard());
			cardService.add(card);
		}
		if (client.getTotalNumber() == 0) {
			Set<Client> set = calculate.getClient();
			set.remove(client);
			calculate.setClient(set);
			calculateService.add(calculate);
		}
		if (!calculate.getClient().iterator().hasNext()) {
			calculate.setState(false);
			calculateService.add(calculate);
		}


	}
}
