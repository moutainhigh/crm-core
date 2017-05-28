package com.cafe.crm.service_abstract.role_service;

import com.cafe.crm.models.worker.Role;


public interface RoleService {

	void save(Role role);

	Role getRoleByName(String name);

}
