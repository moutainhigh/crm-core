package com.cafe.crm.service_impl.cardService;

import com.cafe.crm.service_abstract.calculateService.CalculateService;
import com.cafe.crm.service_abstract.cardService.CardControllerService;
import com.cafe.crm.service_abstract.cardService.CardService;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardControllerServiceImpl implements CardControllerService{

	@Autowired
	private CardService cardService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private TimeManager timeManager;

	public void addCardToCalculate(Long idCard,Long idCalculate) {
		Card card = cardService.getOne(idCard);
		card.setVisitDate(timeManager.getDate().toLocalDate());
		cardService.save(card);
		Calculate calculate = calculateService.getOne(idCalculate);
		List<Card> cards = calculate.getCards();
		if (!cards.contains(card)) {
			cards.add(card);
			calculate.setCards(cards);
			calculateService.save(calculate);
		}
	}

}
