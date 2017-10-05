package com.cafe.crm.services.impl.user;


import com.cafe.crm.exceptions.user.UserDataException;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.Role;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.user.UserRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.services.interfaces.role.RoleService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private PositionService positionService;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Value("${user.default-password}")
	private String defaultPassword;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, CompanyService companyService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.companyService = companyService;
	}

	@Autowired
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	@Override
	public void save(User user) {
		user.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
		userRepository.saveAndFlush(user);
	}

	private void setCompanyId(User user) {
		user.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Autowired
	CacheManager cacheManager;

	@Override
	public void save(User user, String positionsIds, String rolesIds, String isDefaultPassword) {
		checkForUniqueEmailAndPhone(user);
		setPositionsToUser(user, positionsIds);
		setRolesToUser(user, rolesIds);
		setPasswordToUser(user, isDefaultPassword);
		setCompanyId(user);
		userRepository.saveAndFlush(user);
		cacheManager.getCache("user").clear();
	}

	@Override
	public void saveNewUser(User user) {
		checkForUniqueEmailAndPhone(user);
		companyService.save(user.getCompany());
		userRepository.saveAndFlush(user);
		cacheManager.getCache("user").clear();
	}

	@Override
	public List<User> findAll() {
		return userRepository.findByEnabledIsTrueAndCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmailAndEnabledIsTrueAndCompanyId(email, companyIdCache.getCompanyId());
	}

	@Override
	public User findByPhone(String phone) {
		return userRepository.findByPhoneAndEnabledIsTrueAndCompanyId(phone, companyIdCache.getCompanyId());
	}

	@Override
	public List<User> findByPositionIdAndOrderByLastName(Long positionId) {
		return userRepository.findByPositionsIdAndEnabledIsTrueAndCompanyId(positionId, companyIdCache.getCompanyId());
	}

	@Override
	public List<User> findByPositionIdWithAnyEnabledStatus(Long positionId) {
		return userRepository.findByPositionsIdAndCompanyId(positionId, companyIdCache.getCompanyId());
	}

	@Override
	public List<User> findByRoleName(String roleName) {
		return userRepository.findByRolesNameAndCompanyId(roleName, companyIdCache.getCompanyId());
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


	private <T> boolean listsEqual(List<T> list1, List<T> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		for (int i = 0; i < list1.size(); i++) {
			if (!list1.get(i).equals(list2.get(i))) {
				return false;
			}
		}
		return true;
	}

	private void updateSessionRegistryAndCache(User updatedUser) {
//		get user from database or cache
		User storedUser = findById(updatedUser.getId());

		cacheManager.getCache("user").evict(storedUser.getEmail());
		cacheManager.getCache("user").put(updatedUser.getEmail(), updatedUser);

//		if security related fields of user will be updated invalidate his\her session
		boolean positionsEqual = listsEqual(storedUser.getPositions(), updatedUser.getPositions());
		boolean rolesEqual = listsEqual(storedUser.getRoles(), updatedUser.getRoles());
		if (!positionsEqual || !rolesEqual || !storedUser.getPassword().equals(updatedUser.getPassword())
				|| !storedUser.getPhone().equals(updatedUser.getPhone())
				|| !storedUser.getEmail().equals(updatedUser.getEmail())) {

			for (Object principal : sessionRegistry.getAllPrincipals()) {
				String usernameFromSession;
				if (principal instanceof UserDetails) {
					usernameFromSession = ((UserDetails) principal).getUsername();
					if (storedUser.getEmail().equals(usernameFromSession)) {
						sessionRegistry.getAllSessions(principal, false).forEach(SessionInformation::expireNow);
					}
				}
			}
		}
	}

	@Override
	public void update(User user, String oldPassword, String newPassword, String repeatedPassword, String
			positionsIds, String rolesIds) {
		checkForNotNew(user);
		checkForUniqueEmailAndPhone(user);
		if (isValidPasswordsData(user, oldPassword, newPassword, repeatedPassword)) {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		setPositionsToUser(user, positionsIds);
		setRolesToUser(user, rolesIds);
		setDataFromDatabaseToUser(user);
		setCompanyId(user);
		updateSessionRegistryAndCache(user);
		userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> findByEmailOrPhoneAndCompanyId(String email, String phone, Long companyId) {
		return userRepository.findByEmailOrPhoneAndCompanyId(email, phone, companyId);
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
		return userRepository.findByIdInAndCompanyId(ids, companyIdCache.getCompanyId());
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword, String repeatedPassword) {
		User user = findByEmail(username);
		if (user == null) {
			throw new UserDataException("Не удалось найти пользователя с username = " + username);
		}
		if (isValidPasswordsData(user, oldPassword, newPassword, repeatedPassword)) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.saveAndFlush(user);
		}
	}

	public boolean isValidPassword(String email, String oldPassword) {
		User userInDataBase = findByEmail(email);
		if (isBlank(oldPassword)) {
			return false;
		}
		if (userInDataBase == null) {
			throw new UserDataException("Пользователь с таким e-mail не найден в базе!");
		} else {
			if (passwordEncoder.matches(oldPassword, userInDataBase.getPassword())) {
				return true;
			} else {
				throw new UserDataException("Старый пароль не верный!");
			}
		}
	}

	private void checkForNotNew(User user) {
		if (user.getId() == null) {
			throw new UserDataException("Не был передан Id пользователя!");
		}
	}

	private void checkForUniqueEmailAndPhone(User user) {
		List<User> usersInDatabase = findByEmailOrPhone(user.getEmail(), user.getPhone());

		for (User userInDb : usersInDatabase) {
			if (user.getId() == null) {
				if (userInDb.getEmail().equalsIgnoreCase(user.getEmail())) {
					throw new UserDataException("Пользователь с таким e-mail существует!");
				} else if (userInDb.getPhone().equalsIgnoreCase(user.getPhone())) {
					throw new UserDataException("Пользователь с таким телефоном существует!");
				}
			} else if (!userInDb.getId().equals(user.getId())) {
				throw new UserDataException("Пользователь с таким e-mail или телефоном существует!");
			}

		}
	}


	@Override
	@Cacheable("user")
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	private boolean isValidPasswordsData(User user, String oldPassword, String newPassword, String repeatedPassword) {
		if (isBlank(oldPassword) || isBlank(newPassword) || isBlank(repeatedPassword)) {
			return false;
		}
		if (!newPassword.equals(repeatedPassword)) {
			throw new UserDataException("Новый пароль и повтор не совпадают!");
		}
		User userInDatabase = userRepository.findOne(user.getId());
		if (!passwordEncoder.matches(oldPassword, userInDatabase.getPassword())) {
			throw new UserDataException("Старый пароль не верный!");
		}
		return true;
	}

	private Long[] parseIds(String ids) {
		if (isBlank(ids)) {
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

	private void setPasswordToUser(User user, String isDefaultPassword) {
		Boolean isDefault = Boolean.valueOf(isDefaultPassword);
		String password;
		password = isDefault ? passwordEncoder.encode(defaultPassword) : passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
	}

	private void setDataFromDatabaseToUser(User user) {
		User userInDatabase = userRepository.findOne(user.getId());
		if (userInDatabase != null) {
			user.setShifts(userInDatabase.getShifts());
			user.setSalary(userInDatabase.getSalary());
			user.setBonus(userInDatabase.getBonus());
			user.setEnabled(userInDatabase.isEnabled());
			user.setActivated(userInDatabase.isActivated());
		}
	}
}
