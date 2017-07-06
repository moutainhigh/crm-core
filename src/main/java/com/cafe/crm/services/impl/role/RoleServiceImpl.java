package com.cafe.crm.services.impl.role;

import com.cafe.crm.models.worker.Role;
import com.cafe.crm.repositories.role.RoleRepository;
import com.cafe.crm.services.interfaces.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void save(Role role) {
		roleRepository.saveAndFlush(role);
	}

}
