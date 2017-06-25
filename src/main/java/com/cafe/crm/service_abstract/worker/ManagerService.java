package com.cafe.crm.service_abstract.worker;

import com.cafe.crm.models.worker.Manager;


public interface ManagerService {


	Manager getUserByLogin(String login);


	void save(Manager manager);
}
