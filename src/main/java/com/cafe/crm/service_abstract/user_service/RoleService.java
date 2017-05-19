package com.cafe.crm.service_abstract.user_service;

import com.cafe.crm.models.Role;


public interface RoleService {

	void save(Role role);

	Role getRoleByName(String name);

}
