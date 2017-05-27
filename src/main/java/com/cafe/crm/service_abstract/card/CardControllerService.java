package com.cafe.crm.service_abstract.card;

public interface CardControllerService {
	public void addCardToCalculate(Long idCard,Long idCalculate);
	public void addMoney(Long idCard, Double money);
}
