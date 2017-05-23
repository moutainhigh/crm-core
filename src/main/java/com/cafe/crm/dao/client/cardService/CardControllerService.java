package com.cafe.crm.dao.client.cardService;

public interface CardControllerService {
	public void addCardToCalculate(Long idCard,Long idCalculate);
	public void addMoney(Long idCard, Double money);
}
