package com.cafe.crm.service_impl.calculateService;

import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.service_abstract.boardService.BoardService;
import com.cafe.crm.service_abstract.calculateService.CalculateControllerService;
import com.cafe.crm.service_abstract.calculateService.CalculatePriceService;
import com.cafe.crm.service_abstract.calculateService.CalculateService;
import com.cafe.crm.service_abstract.cardService.CardService;
import com.cafe.crm.service_abstract.clientService.ClientService;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateControllerServiceImpl implements CalculateControllerService {
	@Autowired
	private ClientService clientService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private CardService cardService;

	@Autowired
	private CalculatePriceService calculatePriceService;

	public void createCalculate(Long id, Long number, String description) {
		Board board = boardService.getOne(id);
		Calculate calculate = new Calculate();
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			list.add(client);
		}
		clientService.saveAll(list);
		calculate.setDescription(description);
		calculate.setBoard(board);
		calculate.setClient(list);
		calculateService.save(calculate);
	}

	public void refreshBoard(Long idC, Long idB) {
		Board board = boardService.getOne(idB);
		Calculate calculate = calculateService.getOne(idC);
		calculate.setBoard(board);
		calculateService.save(calculate);
	}

	public void addClient(Long id, Long number, String description) {
		Calculate calculate = calculateService.getOne(id);
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			client.setDescription(description);
			list.add(client);
		}
		clientService.saveAll(list);
		List<Client> list1 = calculate.getClient();
		list1.addAll(list);
		calculate.setClient(list1);
		calculateService.save(calculate);
	}

	public List<Client> calculatePrice() {
		long startTime = System.currentTimeMillis();/// для измерения скорости работы расчетов под ajax

		List<Client> clients = clientService.getAllOpen();
		for (Client client : clients) {
			calculatePriceService.calculatePriceTime(client);
			calculatePriceService.addDiscountOnPriceTime(client);
			//calculatePriceService.calculateMenuPrice(client);
			calculatePriceService.getAllPrice(client);
			calculatePriceService.round(client);
		}
		clientService.saveAll(clients);
		for (Client client : clients) {
			client.setLayerProducts(null);//для перелачи json
		}
		System.out.println(System.currentTimeMillis() - startTime);
		return clients;
	}

	public List<Client> outputClients(Long[] clientsId) {
		if (clientsId == null) {
			return null;
		}

		List<Client> clients = new ArrayList<>();
		for (Long clientId : clientsId) {
			Client client = clientService.getOne(clientId);
			calculatePriceService.payWithCardAndCache(client);
			clients.add(client);
		}
		clientService.saveAll(clients);
		List<Client> listClients = new ArrayList<>();
		for (Client client : clients) {
			listClients.add(client);
		}

		return listClients;
	}

	public void closeClient(Long[] clientsId, Long calculateId) {
		List<Client> listClient = new ArrayList<>();
		List<Card> listCard = new ArrayList<>();
		for (Long clientId : clientsId) {
			Client client = clientService.getOne(clientId);
			client.setState(false);
			listClient.add(client);
			if (client.getCard() != null) {
				Card card = client.getCard();
				card.setBalance(card.getBalance() - client.getPayWithCard());
				listCard.add(card);
			}
		}
		cardService.saveAll(listCard);
		clientService.saveAll(listClient);

		boolean flag = false;
		Calculate calculate = calculateService.getOne(calculateId);
		List<Client> clients = calculate.getClient();
		for (Client client : clients) {
			if (client.isState()) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			calculate.setState(false);
			calculateService.save(calculate);
		}
	}

	public Long addCardOnClient(Long calculateId, Long clientId, Long cardId) {
		Client client = clientService.getOne(clientId);
		Calculate calculate = calculateService.getOne(calculateId);
		List<Client> clients = calculate.getClient();
		boolean flag = false;
		if (cardId == -1) {
			client.setDiscountWithCard(0L);
			client.setCard(null);
		} else {
			Card card = cardService.getOne(cardId);
			for (Client cl : clients) {
				if (cl.getCard() == card) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				client.setDiscountWithCard(card.getDiscount());
			}
			client.setCard(card);
		}
		clientService.save(client);
		return client.getDiscountWithCard();
	}

}
