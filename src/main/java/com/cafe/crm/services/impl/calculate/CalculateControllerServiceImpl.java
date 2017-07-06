package com.cafe.crm.services.impl.calculate;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.calculate.CalculateControllerService;
import com.cafe.crm.services.interfaces.calculate.CalculatePriceService;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.card.CardService;
import com.cafe.crm.services.interfaces.client.ClientService;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.property.PropertyService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// TODO: 06.07.2017 Проверить почему не работает с @Transactional
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

	@Autowired
	private EmailService emailService;

	@Autowired
	private TimeManager timeManager;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private ShiftService shiftService;

	@Override
	public void createCalculate(Long id, Long number, String description) {
		Board board = boardService.getOne(id);
		Calculate calculate = new Calculate();
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			client.setTimeStart(timeManager.getTime().withSecond(0).withNano(0));
			list.add(client);
		}
		clientService.saveAll(list);
		calculate.setDescription(description);
		calculate.setBoard(board);
		calculate.setClient(list);
		shiftService.getLast().getAllCalculate().add(calculate);
		shiftService.getLast().getClients().addAll(list);
		calculateService.save(calculate);
	}

	@Override
	public void createCalculateWithCard(Long id, Long number, String description, Long idCard) {

		Card card = cardService.getOne(idCard);
		List<Card> cards = new ArrayList<>();
		cards.add(card);

		Board board = boardService.getOne(id);
		Calculate calculate = new Calculate();
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			client.setTimeStart(timeManager.getTime().withSecond(0).withNano(0));
			list.add(client);
		}
		clientService.saveAll(list);
		calculate.setCards(cards);
		calculate.setDescription(description);
		calculate.setBoard(board);
		calculate.setClient(list);
		calculateService.save(calculate);
	}

	@Override
	public void refreshBoard(Long idC, Long idB) {
		Board board = boardService.getOne(idB);
		Calculate calculate = calculateService.getOne(idC);
		calculate.setBoard(board);
		calculateService.save(calculate);
	}

	@Override
	public void addClient(Long id, Long number, String description) {
		Calculate calculate = calculateService.getOne(id);
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			client.setTimeStart(timeManager.getTime().withSecond(0).withNano(0));
			client.setDescription(description);
			list.add(client);
		}
		clientService.saveAll(list);
		List<Client> list1 = calculate.getClient();
		list1.addAll(list);
		calculate.setClient(list1);
		calculateService.save(calculate);
	}

	@Override
	public List<Client> calculatePrice() {
		long startTime = System.currentTimeMillis();/// для измерения скорости работы расчетов под ajax

		List<Client> clients = clientService.getAllOpen();
		for (Client client : clients) {
			calculatePriceService.calculatePriceTime(client);
			calculatePriceService.addDiscountOnPriceTime(client);
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

	@Override
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

	@Override
	public void closeClient(Long[] clientsId, Long calculateId) {
		List<Client> listClient = new ArrayList<>();
		List<Card> listCard = new ArrayList<>();
		for (Long clientId : clientsId) {
			Client client = clientService.getOne(clientId);
			client.setState(false);
			listClient.add(client);
			Card clientCard = client.getCard();
			if (clientCard != null) {               // referral bonus
				if (clientCard.getWhoInvitedMe() != null && clientCard.getVisitDate() == null) {
					Card invitedCard = cardService.getOne(clientCard.getWhoInvitedMe());
					invitedCard.setBalance(invitedCard.getBalance() + propertyService.getOne(3L).getValue());
					cardService.save(invitedCard);
				}
				clientCard.setVisitDate(timeManager.getDate());
				cardService.save(clientCard);
			}
			if (clientCard != null) {
				clientCard.setBalance(clientCard.getBalance() - client.getPayWithCard());
				listCard.add(clientCard);
				sendBalanceInfoAfterDebiting(clientCard.getBalance(), client.getPayWithCard(), clientCard.getEmail());
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

	@Override
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

	private void sendBalanceInfoAfterDebiting(Long balance, Long distinction, String email) {
		if (email != null) {
			emailService.sendBalanceInfoAfterDebiting(balance, distinction, email);
		}
	}
}
