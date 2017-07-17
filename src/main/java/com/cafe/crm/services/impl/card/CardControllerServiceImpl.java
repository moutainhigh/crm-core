package com.cafe.crm.services.impl.card;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.card.CardControllerService;
import com.cafe.crm.services.interfaces.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CardControllerServiceImpl implements CardControllerService {

    @Autowired
    private CardService cardService;

    @Autowired
    private CalculateService calculateService;

    @Override
    public void addCardToCalculate(Long idCard, Long idCalculate) {
        Card card = cardService.getOne(idCard);
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
