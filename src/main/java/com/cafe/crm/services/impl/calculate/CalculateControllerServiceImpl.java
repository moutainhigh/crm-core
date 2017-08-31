package com.cafe.crm.services.impl.calculate;

import ch.qos.logback.classic.Logger;
import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.calculate.CalculateControllerService;
import com.cafe.crm.services.interfaces.calculate.CalculatePriceService;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.card.CardService;
import com.cafe.crm.services.interfaces.client.ClientService;
import com.cafe.crm.services.interfaces.debt.DebtService;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.property.PropertyService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CalculateControllerServiceImpl implements CalculateControllerService {

	private final ClientService clientService;
	private final CalculateService calculateService;
	private final DebtService debtService;
	private final BoardService boardService;
	private final CardService cardService;
	private final CalculatePriceService calculatePriceService;
	private final EmailService emailService;
	private final TimeManager timeManager;
	private final PropertyService propertyService;
	private final ShiftService shiftService;
	private final Logger logger;

	@Autowired
	public CalculateControllerServiceImpl(DebtService debtService, CalculatePriceService calculatePriceService, EmailService emailService, TimeManager timeManager, ClientService clientService, CalculateService calculateService, @Qualifier(value = "logger") Logger logger, BoardService boardService, PropertyService propertyService, ShiftService shiftService, CardService cardService) {
		this.debtService = debtService;
		this.calculatePriceService = calculatePriceService;
		this.emailService = emailService;
		this.timeManager = timeManager;
		this.clientService = clientService;
		this.calculateService = calculateService;
		this.logger = logger;
		this.boardService = boardService;
		this.propertyService = propertyService;
		this.shiftService = shiftService;
		this.cardService = cardService;
	}

	@Override
	public void createCalculate(Long id, Long number, String description) {
		Board board = boardService.getOne(id);
		Calculate calculate = new Calculate();
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			client.setTimeStart(timeManager.getDateTime());
			list.add(client);
		}
		clientService.saveAll(list);
		calculate.setDescription(description);
		calculate.setBoard(board);
		calculate.setClient(list);
		shiftService.getLast().getCalculates().add(calculate);
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
			client.setTimeStart(timeManager.getDateTime());
			list.add(client);
		}
		clientService.saveAll(list);
		calculate.setCards(cards);
		calculate.setDescription(description);
		calculate.setBoard(board);
		calculate.setClient(list);
		shiftService.getLast().getCalculates().add(calculate);
		shiftService.getLast().getClients().addAll(list);
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
			client.setTimeStart(timeManager.getDateTime());
			client.setDescription(description);
			list.add(client);
		}
		clientService.saveAll(list);
		List<Client> list1 = calculate.getClient();
		list1.addAll(list);
		calculate.setClient(list1);
		shiftService.getLast().getClients().addAll(list);
		calculateService.save(calculate);
	}

	@Override
	public List<Client> calculatePrice() {
		long startTime = System.currentTimeMillis();/// для измерения скорости работы расчетов под ajax
		List<Calculate> calculates = calculateService.getAllOpen();
		List<Client> clients = new ArrayList<>();
		for (Calculate calculate : calculates) {
			for (Client client : calculate.getClient()) {
				if (client.isPausedIndex()) {
					calculatePriceService.calculatePriceTimeIfWasPause(client);
				} else {
					calculatePriceService.calculatePriceTime(client);
				}
				calculatePriceService.addDiscountOnPriceTime(client);
				calculatePriceService.getAllPrice(client);
				if (calculate.isRoundState()) {
					calculatePriceService.round(client, calculate.isRoundState());
				}
				clients.add(client);
			}
		}
		clientService.saveAll(clients);
		System.out.println(System.currentTimeMillis() - startTime);
		return clients;
	}

	@Override
	public List<Client> calculatePrice(Long calculateId) {
		Calculate calculate = calculateService.getAllOpenOnCalculate(calculateId);
		List<Client> clients = calculate.getClient();
		for (Client client : clients) {
			if (client.isPausedIndex()) {
				calculatePriceService.calculatePriceTimeIfWasPause(client);
			} else {
				calculatePriceService.calculatePriceTime(client);

			}
			calculatePriceService.addDiscountOnPriceTime(client);
			calculatePriceService.getAllPrice(client);
			if (calculate.isRoundState()) {
				calculatePriceService.round(client, calculate.isRoundState());
			}
		}
		clientService.saveAll(clients);
		return clients;
	}

	@Override
	public List<Client> outputClients(long[] clientsId) {
		if (clientsId == null) {
			return null;
		}
		List<Client> clients = clientService.findByIdIn(clientsId);
		for (Client client : clients) {
			calculatePriceService.payWithCardAndCache(client);
		}
		clientService.saveAll(clients);
		return clients;
	}

	@Override
	public void closeClient(long[] clientsId, Long calculateId) {

		if (clientsId == null) {
			return;
		}

		List<Client> listClient = clientService.findByIdIn(clientsId);
		List<Card> listCard = new ArrayList<>();
		Map<Long, Double> balanceBeforeDeduction = new HashMap<>();

		clientService.findCardByClientIdIn(clientsId)
				.forEach(card -> balanceBeforeDeduction.put(card.getId(), card.getBalance()));

		for (Client client : listClient) {
			client.setState(false);
			Card clientCard = client.getCard();
			if (clientCard == null) {
				continue;
			}

			setBalanceAndSaveInvitedCard(clientCard);

			clientCard.setVisitDate(timeManager.getDate());
			cardService.save(clientCard);

			clientCard.setBalance(clientCard.getBalance() - client.getPayWithCard());
			listCard.add(clientCard);
		}

		cardService.saveAll(listCard);
		clientService.saveAll(listClient);

		findLeastOneOpenClientAndCloseCalculation(calculateId);
		sendBalanceInfoAfterDeduction(listClient, balanceBeforeDeduction);
	}

	private void findLeastOneOpenClientAndCloseCalculation(Long calculateId) {
		Calculate calculate = calculateService.getOne(calculateId);
		List<Client> clients = calculate.getClient();
		for (Client client : clients) {
			if (client.isState() && !client.isDeleteState()) {
				calculate.setState(false);
				calculateService.save(calculate);
				return;
			}
		}
	}

	private void setBalanceAndSaveInvitedCard(Card clientCard) {
		if (clientCard.getWhoInvitedMe() != null && clientCard.getVisitDate() == null) {
			Card invitedCard = cardService.getOne(clientCard.getWhoInvitedMe());
			invitedCard.setBalance(invitedCard.getBalance() + propertyService.getOne(3L).getValue());
			cardService.save(invitedCard);
		}
	}

	@Override
	public void closeClientDebt(long[] clientsId, Long calculateId) {
		if (clientsId == null) {
			return;
		}

		List<Client> listClient = clientService.findByIdIn(clientsId);
		List<Debt> debtList = new ArrayList<>();
		Shift lastShift = shiftService.getLast();

		listClient.forEach(client -> {
			Debt debt = new Debt();
			debt.setDate(timeManager.getDate());
			debt.setDebtAmount(client.getAllPrice());
			debt.setDebtor(client.getDescription());
			debtList.add(debt);
			lastShift.addGivenDebtToList(debt);
		});

		findLeastOneOpenClientAndCloseCalculation(calculateId);
		debtService.saveAll(debtList);
	}

	@Override
	public void deleteClients(long[] clientsId, Long calculateId) {
		if (clientsId == null) {
			return;
		}
		List<Client> clients = clientService.findByIdIn(clientsId);
		for (Client client : clients) {
			client.setDeleteState(true);
			client.setState(false);
			logger.info("Удаление клиента c описанием:" + client.getDescription() + "и id: " + client.getId());
		}
		clientService.saveAll(clients);
		boolean flag = false;
		Calculate calculate = calculateService.getOne(calculateId);
		List<Client> clients1 = calculate.getClient();
		for (Client client : clients1) {
			if (client.isState() && !client.isDeleteState()) {
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

	private void sendBalanceInfoAfterDeduction(List<Client> clients, Map<Long, Double> mapOfBalanceBeforeDeduction) {
		Map<Long, Client> uniqueClientsForCard = new HashMap<>();
		clients
				.stream()
				.filter(client -> client.getCard() != null && client.getCard().getEmail() != null && client.getPayWithCard() > 0.0d)
				.forEach(client -> uniqueClientsForCard.put(client.getCard().getId(), client));

		uniqueClientsForCard.values().forEach(client -> {
			Double balanceAfterDeduction = client.getCard().getBalance();
			Double balanceBeforeDeduction = mapOfBalanceBeforeDeduction.get(client.getCard().getId());
			Double deductionAmount = balanceBeforeDeduction - balanceAfterDeduction;
			emailService.sendBalanceInfoAfterDeduction(balanceAfterDeduction, deductionAmount, client.getCard().getEmail());
		});
	}
}
