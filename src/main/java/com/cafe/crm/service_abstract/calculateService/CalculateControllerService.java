package com.cafe.crm.service_abstract.calculateService;

import com.cafe.crm.models.client.Client;

import java.util.List;

public interface CalculateControllerService {
	void createCalculate(Long id, Long number, String descr);
	void createCalculateWithCard(Long id, Long number, String descr, Long idCard);
	void refreshBoard(Long idC, Long idB);
	void addClient(Long id, Long number, String descr);
	List<Client> calculatePrice();
	List<Client> outputClients(Long[] clientsId);
	void closeClient(Long[] clientsId, Long calculateId);
	Long addCardOnClient(Long calculateId, Long clientId, Long cardId);
}
