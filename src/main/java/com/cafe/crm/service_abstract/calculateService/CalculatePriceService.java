package com.cafe.crm.service_abstract.calculateService;

import com.cafe.crm.models.client.Client;


public interface CalculatePriceService {
	void payWithCardAndCache(Client client);

	void calculatePriceTime(Client client);

	void getAllPrice(Client client);

	void round(Client client);

	void addDiscountOnPriceTime(Client client);
}
