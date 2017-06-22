package com.cafe.crm.service_abstract.calculateService;

import com.cafe.crm.models.client.Calculate;

import java.util.List;

public interface CalculateService {
	 void save(Calculate calculate);
	 void delete(Calculate calculate);
	 List<Calculate> getAll();
	 Calculate getOne(Long id);
	 List<Calculate> getAllOpen();


}
