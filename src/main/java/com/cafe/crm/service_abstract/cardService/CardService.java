package com.cafe.crm.service_abstract.cardService;


import com.cafe.crm.models.card.Card;

import java.util.List;

public interface CardService {
	void saveAll(List<Card> card);

	void save(Card card);

	void delete(Card card);

	List<Card> getAll();

	Card getOne(Long id);

	Card checkWhoInvitedMe(String searchParam);

	Card findByPhone(String phone);

	List<Card> findByListSurname(String name);
}
