package com.cafe.crm.service_impl.client;

import com.cafe.crm.dao.card.CardRepository;
import com.cafe.crm.models.card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;

	public void add(Card card) {
		cardRepository.saveAndFlush(card);
	}

	public void delete(Card card) {
		cardRepository.delete(card);
	}

	public List<Card> getAll() {
		return cardRepository.findAll();
	}

	public Card getOne(Long id) {
		return cardRepository.getOne(id);
	}
}



