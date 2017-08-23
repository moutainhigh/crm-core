package com.cafe.crm.services.interfaces.user;


import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {
	void save(User user);

	void save(User user, String positionsIds, String rolesIds, String isDefaultPassword);

	List<User> findAll();

	User findById(Long id);

	User findByEmail(String email);

	User findByPhone(String phone);

	List<User> findByPositionIdAndOrderByLastName(Long positionId);

	List<User> findByPositionIdWithAnyEnabledStatus(Long positionId);

	List<User> findByRoleName(String roleName);

	Map<Position, List<User>> findAndSortUserByPosition();

	void update(User user, String oldPassword, String newPassword, String repeatedPassword, String positionsIds, String rolesIds);

	List<User> findByEmailOrPhone(String email, String phone);

	void delete(Long id);

	List<User> findByIdIn(long[] ids);

	void changePassword(String username, String oldPassword, String newPasswword, String repeatedPassword);

	boolean isValidPassword(String email, String oldPassword);
}
