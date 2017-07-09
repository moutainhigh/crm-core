package com.cafe.crm.controllers.card;

import com.cafe.crm.dto.PaymentHistory;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.card.CardService;
import com.cafe.crm.services.interfaces.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/boss")
public class CardStatisticController {

	private final CardService cardService;

	private final CalculateService calculateService;

	private final ClientService clientService;

	@Autowired
	public CardStatisticController(CardService cardService, CalculateService calculateService, ClientService clientService) {
		this.cardService = cardService;
		this.calculateService = calculateService;
		this.clientService = clientService;
	}

	@RequestMapping(value = "/card/statistic/all", method = RequestMethod.GET)
	public String showAllCardStatistic(Model model) {
		List<Card> cards = cardService.getAll();

		model.addAttribute("cards", cards);

		return "card/statistic/all-cards";
	}

	@RequestMapping(value = "/card/statistic/{id}", method = RequestMethod.GET)
	public String showCardStatisitic(@PathVariable(name = "id") Long cardId, Model model) {
		Card card = cardService.getOne(cardId);
		List<Client> clients = clientService.findByCardId(cardId);
		double totalPaid = clients.stream()
				.mapToDouble(Client::getPayWithCard).sum();
		List<PaymentHistory> listOfPaymentHistory = clients.stream()
				.map(this::convertToPaymentHistory)
				.collect(Collectors.toList());

		model.addAttribute("card", card);
		model.addAttribute("totalPaid", totalPaid);
		model.addAttribute("listOfPaymentHistory", listOfPaymentHistory);

		return "card/statistic/specific-card";
	}

	private PaymentHistory convertToPaymentHistory(Client client) {
		Calculate calculate = calculateService.findByClientId(client.getId());
		return PaymentHistory.builder()
				.calculateId(calculate.getId())
				.calculateDescription(calculate.getDescription())
				.clientDescription(client.getDescription())
				.totalPrice(client.getAllPrice())
				.menuPrice(client.getPriceMenu())
				.timePrice(client.getPriceTime())
				.cafeDiscount(client.getDiscount())
				.cardDiscount(client.getDiscountWithCard())
				.payWithCard(client.getPayWithCard())
				.dateStart(client.getTimeStart())
				.spentTime(client.getPassedTime()).build();
	}

	@RequestMapping(value = "/card/image/{id}", produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long cardId) {
		byte[] imageContent = cardService.getOne(cardId).getPhoto();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
	}


}
