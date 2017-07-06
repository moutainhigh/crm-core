package com.cafe.crm.services.interfaces.worker;


import com.cafe.crm.models.worker.Boss;

public interface BossService {

	Boss getUserByEmail(String email);

	void save(Boss boss);

}
