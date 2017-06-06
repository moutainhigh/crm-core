package com.cafe.crm.security.service;


import com.cafe.crm.dao.boss.BossRepository;
import com.cafe.crm.dao.manager.ManagerRepository;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
	@Autowired
	private BossRepository bossRepository;

	@Autowired
	private ManagerRepository managerRepository;

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Boss boss = bossRepository.getUserByLogin(login);
		Manager manager = managerRepository.getUserByLogin(login);
		if (boss == null) {
			if (manager == null) {
			} else {
				return manager;
			}
			throw new UsernameNotFoundException("Username " + login + " not found");
		} else {
			return boss;
		}
	}
}
