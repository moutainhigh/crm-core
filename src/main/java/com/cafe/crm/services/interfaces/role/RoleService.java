package com.cafe.crm.services.interfaces.role;

import com.cafe.crm.models.user.Role;

import java.util.List;


public interface RoleService {

    void save(Role role);

	List<Role> findAll();

	List<Role> findByIdIn(Long[] ids);
}
