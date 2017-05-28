package com.cafe.crm.service_abstract.worker_service;


import com.cafe.crm.models.worker.Boss;

public interface BossService {
	Boss getUserByLogin(String login);


	void save(Boss boss);
}
