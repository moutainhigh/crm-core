package com.cafe.crm.services.interfaces.position;


import com.cafe.crm.models.user.Position;

import java.util.List;

public interface PositionService {

	void save(Position position);

	List<Position> findAll();

	void update(Position position);

	void delete(Long id);

	Position findByName(String name);

	List<Position> findByIdIn(Long[] ids);

}
