package com.cafe.crm.dao.client.calculateService;

import com.cafe.crm.models.client.Client;

public interface CalculatePriceService {
	public Double calculatePrice(Client client);
	public Double addDiscountToAllPrice(Client client);
}
