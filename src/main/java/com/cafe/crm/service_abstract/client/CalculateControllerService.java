package com.cafe.crm.service_abstract.client;


import com.cafe.crm.models.client.Calculate;

public interface CalculateControllerService {
	public void createCalculate(Long id, Long number, String descr);
	public void refreshBoard(Long idC, Long idB);
	public void addClient(Long id, Long number, String descr);
	public Calculate calculatePrice(Long clientId, Long calculateNumber, Long discount, Boolean flag, Long calculateId);
	public void closeClient(Long idCal, Long idCl);
}
