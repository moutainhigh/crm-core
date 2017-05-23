package com.cafe.crm.dao.client.calculateService;


public interface CalculateControllerService {
	public void addCalculate(Long id, Long number, String descr);
	public void refreshBoard(Long idC, Long idB);
	public void addClient(Long id, Long number, String descr);
	public void calculatePrice(Long clientId, Long calculateNumber, Long discount, Boolean flag);
	public void closeClient(Long id);
}
