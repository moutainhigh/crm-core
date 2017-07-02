package com.cafe.crm.service_abstract.position;


import com.cafe.crm.models.worker.Position;

import java.util.List;

public interface PositionService {

	void addPosition(Position position);

	List<Position> listAllPosition();

	void updatePosition(Position position);

	void deletePosition(Long id);

	Position findPositionById(Long id);

	Position findPositionByName(String name);

}
