package com.cafe.crm.service_abstract.client;


public interface CalculateControllerService {
	public void addCalculate(Long id, Long number, String descr);
	public void refreshBoard(Long idC, Long idB);
	public void addClient(Long id, Long number, String descr);
	public void calculatePrice(Long clientId, Long calculateNumber, Long discount, Boolean flag, Long calculateId);
	public void closeClient(Long id);
}
