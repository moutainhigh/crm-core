package com.cafe.crm.services.impl.role;

import com.cafe.crm.models.user.Role;
import com.cafe.crm.repositories.role.RoleRepository;
import com.cafe.crm.services.interfaces.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void save(Role role) {
		roleRepository.saveAndFlush(role);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findByIdIn(Long[] ids) {
		return roleRepository.findByIdIn(ids);
	}

	@Override
	public Role find(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Role find(Long id) {
		return roleRepository.findOne(id);
	}
}
