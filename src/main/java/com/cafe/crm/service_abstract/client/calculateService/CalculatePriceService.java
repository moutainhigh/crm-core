package com.cafe.crm.service_abstract.client.calculateService;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;

public interface CalculatePriceService {
	public Double calculatePriceTime(Client client, Calculate calculate);
	public Double addDiscountToAllPrice(Client client, Calculate calculate);
}
