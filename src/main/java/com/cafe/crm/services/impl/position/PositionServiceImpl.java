package com.cafe.crm.services.impl.position;

import com.cafe.crm.exceptions.user.PositionDataException;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.position.PositionRepository;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

	private final PositionRepository positionRepository;
	private final UserService userService;

	@Autowired
	public PositionServiceImpl(PositionRepository positionRepository, UserService userService) {
		this.positionRepository = positionRepository;
		this.userService = userService;
	}

	@Override
	public void save(Position position) {
		checkForUniqueName(position);
		positionRepository.saveAndFlush(position);
	}

	@Override
	public List<Position> findAll() {
		return positionRepository.findAll();
	}

	@Override
	public void update(Position position) {
		checkForNotNew(position);
		checkForUniqueName(position);
		positionRepository.saveAndFlush(position);
	}

	@Override
	public void delete(Long id) {
		Position position = positionRepository.findOne(id);
		List<User> users = userService.findByPositionIdWithAnyEnabledStatus(id);
		for (User user : users) {
			user.getPositions().remove(position);
			List<Position> newUserPositionList = user.getPositions();
			user.setPositions(newUserPositionList);
			userService.save(user);
		}
		positionRepository.delete(id);
	}

	@Override
	public Position findByName(String name) {
		return positionRepository.findByName(name);
	}

	@Override
	public List<Position> findByIdIn(Long[] ids) {
		return positionRepository.findByIdIn(ids);
	}

	@Override
	public Position findById(Long id) {
		return positionRepository.findOne(id);
	}

	private void checkForNotNew(Position position) {
		if (position.getId() == null) {
			throw new PositionDataException("Не был передан Id пользователя!");
		}
	}

	private void checkForUniqueName(Position position) {
		Position positionInDatabase = findByName(position.getName());
		if ((positionInDatabase != null) && !positionInDatabase.getId().equals(position.getId())) {
			throw new PositionDataException("Должность с таким названием уже существует!");
		}
	}
}
