package com.cafe.crm.service_abstract.worker;

import com.cafe.crm.models.worker.Manager;


public interface ManagerService {

	Manager getUserByEmail(String login);


	void save(Manager manager);
}
