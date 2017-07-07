package com.cafe.crm.services.impl.card;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.repositories.card.CardRepository;
import com.cafe.crm.services.interfaces.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
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

	public Card findByPhone(String phone) {
		return cardRepository.findByPhoneNumber(phone);
	}

	@Override
	public List<Card> findByListSurname(String name) {
		return cardRepository.findByListSurname(name);
	}

	@Override
	public List<Card> findByEmailNotNullAndAdvertisingIsTrue() {
		return cardRepository.findByEmailNotNullAndAdvertisingIsTrue();
	}

	@Override
	public Card checkWhoInvitedMe(String searchParam) {

		Card card = cardRepository.findByPhoneNumber(searchParam);
		if (card != null) {
			return card;
		}
		card = cardRepository.findByEmail(searchParam);
		if (card != null) {
			return card;
		}
		String[] split = searchParam.split(" ");
		if (split.length == 2) {
			card = cardRepository.findByNameAndSurname(split[0], split[1]);
			if (card != null) {
				return card;
			}
			card = cardRepository.findBySurnameAndName(split[0], split[1]);
			if (card != null) {
				return card;
			}
		}
		List<Card> list = cardRepository.findByListSurname(searchParam);
		if (list.size() > 1) {
			return null;
		}
		card = cardRepository.findBySurname(searchParam);
		if (card != null) {
			return card;
		}
		return null;
	}


}



