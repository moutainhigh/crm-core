package com.cafe.crm.services.impl.user;


import com.cafe.crm.exceptions.user.UserDataException;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.Role;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.user.UserRepository;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.services.interfaces.role.RoleService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private PositionService positionService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public void save(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public void save(User user, String positionsIds, String rolesIds) {
		checkForUniqueEmailAndPhone(user);
		setPositionsToUser(user, positionsIds);
		setRolesToUser(user, rolesIds);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findByEnabledIsTrue();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmailAndEnabledIsTrue(email);
	}

	@Override
	public User findByPhone(String phone) {
		return userRepository.findByPhoneAndEnabledIsTrue(phone);
	}

	@Override
	public List<User> findByPositionIdAndOrderByLastName(Long positionId) {
		return userRepository.findByPositionsIdAndEnabledIsTrue(positionId);
	}

	@Override
	public List<User> findByPositionIdWithAnyEnabledStatus(Long positionId) {
		return userRepository.findByPositionsId(positionId);
	}

	@Override
	public List<User> findByPositionName(String positionName) {
		return userRepository.findByPositionsName(positionName);
	}

	@Override
	public Map<Position, List<User>> findAndSortUserByPosition() {
		List<Position> allPositions = positionService.findAll();
		Map<Position, List<User>> usersByPositions = new HashMap<>();
		for (Position position : allPositions) {
			List<User> users = findByPositionIdAndOrderByLastName(position.getId());
			usersByPositions.put(position, users);
		}
		return usersByPositions;
	}

	@Override
	public void update(User user, String positionsIds, String rolesIds) {
		checkForNotNew(user);
		checkForUniqueEmailAndPhone(user);
		checkForChangingPassword(user);
		setPositionsToUser(user, positionsIds);
		setRolesToUser(user, rolesIds);
		userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> findByEmailOrPhone(String email, String phone) {
		return userRepository.findByEmailOrPhone(email, phone);
	}

	@Override
	public void delete(Long id) {
		User user = userRepository.findOne(id);
		if (user != null) {
			user.setEnabled(false);
			userRepository.saveAndFlush(user);
		}
	}

	@Override
	public List<User> findByIdIn(long[] ids) {
		return userRepository.findByIdIn(ids);
	}

	private void checkForNotNew(User user) {
		if (user.getId() == null) {
			throw new UserDataException("Не был передан Id пользователя!");
		}
	}

	private void checkForUniqueEmailAndPhone(User user) {
		List<User> usersInDb = findByEmailOrPhone(user.getEmail(), user.getPhone());
		for (User userInDb : usersInDb) {
			if (!userInDb.getId().equals(user.getId())) {
				throw new UserDataException("Пользователь с таким e-mail или телефоном существует!");
			}
		}
	}

	private void checkForChangingPassword(User user) {
		User userInDb = userRepository.findOne(user.getId());
		if (!user.getPassword().equals(userInDb.getPassword())) {
			throw new UserDataException("Был изменен пароль!");
		}
	}

	private Long[] parseIds(String ids) {
		if (ids == null || ids.isEmpty()) {
			return null;
		}
		Long[] longIds;
		try {
			String[] stringIds = ids.split("\\,");
			int idsAmount = stringIds.length;
			longIds = new Long[idsAmount];
			for (int i = 0; i < idsAmount; i++) {
				longIds[i] = Long.valueOf(stringIds[i]);
			}
		} catch (Exception e) {
			throw new UserDataException("Некоррекный формат переданных Id");
		}
		return longIds;
	}

	private void setPositionsToUser(User user, String positionsIds) {
		Long[] longPositionsIds = parseIds(positionsIds);
		if (longPositionsIds != null) {
			List<Position> positions = positionService.findByIdIn(longPositionsIds);
			user.setPositions(positions);
		}
	}

	private void setRolesToUser(User user, String rolesIds) {
		Long[] longRolesIds = parseIds(rolesIds);
		if (longRolesIds != null) {
			List<Role> roles = roleService.findByIdIn(longRolesIds);
			user.setRoles(roles);
		}
	}
}
