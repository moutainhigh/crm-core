package com.cafe.crm.service_abstract.worker;


import com.cafe.crm.models.worker.Boss;

public interface BossService {

	Boss getUserByEmail(String email);

	void save(Boss boss);
}
