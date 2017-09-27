package com.cafe.crm.repositories.role;

import com.cafe.crm.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	List<Role> findByIdIn(Long[] ids);
}
