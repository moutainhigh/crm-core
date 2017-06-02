package com.cafe.crm.service_abstract.client;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;


public interface CalculatePriceService {
	public Double calculatePriceTime(Calculate calculate);
	public Double calculatePriceTime(Client client, Calculate calculate);
	public Double addDiscountToAllPrice(Calculate calculate);
}
