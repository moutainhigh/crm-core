package com.cafe.crm.repositories.user;


import com.cafe.crm.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAndEnabledIsTrue(String email);

	User findByPhoneAndEnabledIsTrue(String phone);

	List<User> findByEnabledIsTrue();

	List<User> findByPositionsIdAndEnabledIsTrue(Long positionId);

	List<User> findByPositionsId(Long positionId);

	List<User> findByRolesName(String roleName);

	List<User> findByEmailOrPhone(String email, String phone);

	List<User> findByIdIn(long[] ids);
}
