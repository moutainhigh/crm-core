package com.cafe.crm.services.impl.card;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.repositories.card.CardRepository;
import com.cafe.crm.services.interfaces.card.CardService;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

	private final CardRepository cardRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public CardServiceImpl(CardRepository cardRepository, CompanyService companyService) {
		this.cardRepository = cardRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Card card){
		card.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	public void saveAll(List<Card> cards) {
		for (Card card: cards){
			setCompanyId(card);
		}
		cardRepository.save(cards);
	}

	public void save(Card card) {
		setCompanyId(card);
		cardRepository.saveAndFlush(card);
	}

	public void delete(Card card) {
		cardRepository.delete(card);
	}

	public List<Card> getAll() {
		return cardRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	public Card getOne(Long id) {
		return cardRepository.findOne(id);
	}

	public Card findByPhone(String phone) {
		return cardRepository.findByPhoneNumberAndCompanyId(phone, companyIdCache.getCompanyId());
	}

	@Override
	public List<Card> findByListSurname(String name) {
		return cardRepository.findByListSurnameAndCompanyId(name, companyIdCache.getCompanyId());
	}

	@Override
	public List<Card> findByEmailNotNullAndAdvertisingIsTrue() {
		return cardRepository.findByCompanyIdAndEmailNotNullAndAdvertisingIsTrue(companyIdCache.getCompanyId());
	}

	@Override
	public Card checkWhoInvitedMe(String searchParam) {

		Card card = cardRepository.findByPhoneNumberAndCompanyId(searchParam, companyIdCache.getCompanyId());
		if (card != null) {
			return card;
		}
		card = cardRepository.findByEmailAndCompanyId(searchParam, companyIdCache.getCompanyId());
		if (card != null) {
			return card;
		}
		String[] split = searchParam.split(" ");
		if (split.length == 2) {
			card = cardRepository.findByNameAndSurnameAndCompanyId(split[0], split[1], companyIdCache.getCompanyId());
			if (card != null) {
				return card;
			}
			card = cardRepository.findBySurnameAndNameAndCompanyId(split[0], split[1], companyIdCache.getCompanyId());
			if (card != null) {
				return card;
			}
		}
		List<Card> list = cardRepository.findByListSurnameAndCompanyId(searchParam, companyIdCache.getCompanyId());
		if (list.size() > 1) {
			return null;
		}
		card = cardRepository.findBySurnameAndCompanyId(searchParam, companyIdCache.getCompanyId());
		if (card != null) {
			return card;
		}
		return null;
	}


}



