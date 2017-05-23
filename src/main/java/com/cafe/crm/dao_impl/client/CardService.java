package com.cafe.crm.dao_impl.client;

import com.cafe.crm.dao.client.CardRepository;
import com.cafe.crm.models.client.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
	@Autowired
	CardRepository cardRepository;

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



