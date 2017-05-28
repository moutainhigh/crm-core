package com.cafe.crm.service_abstract.worker_service;

import com.cafe.crm.models.worker.Manager;


public interface ManagerService {


	Manager getUserByLogin(String login);


	void save(Manager manager);
}
