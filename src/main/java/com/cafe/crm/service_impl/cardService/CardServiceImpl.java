package com.cafe.crm.service_impl.cardService;

import com.cafe.crm.dao.card.CardRepository;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.service_abstract.cardService.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService{
	@Autowired
	private CardRepository cardRepository;

	public void saveAll(List<Card> card) {
		cardRepository.save(card);
	}

	public void save(Card card) {
		cardRepository.saveAndFlush(card);
	}

	public void delete(Card card) {
		cardRepository.delete(card);
	}

	public List<Card> getAll() {
		return cardRepository.findAll();
	}

	public Card getOne(Long id) {
		return cardRepository.findOne(id);
	}
}



