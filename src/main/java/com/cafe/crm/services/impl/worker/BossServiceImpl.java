package com.cafe.crm.services.impl.worker;


import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.services.interfaces.worker.BossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BossServiceImpl implements BossService {

	@Autowired
	private BossRepository bossRepository;

	@Override
	public Boss getUserByEmail(String email) {
		return bossRepository.findByEmail(email);
	}

	@Override
	public void save(Boss boss) {
		bossRepository.saveAndFlush(boss);
	}

}
