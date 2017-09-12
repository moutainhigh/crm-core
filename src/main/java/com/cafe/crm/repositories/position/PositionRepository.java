package com.cafe.crm.repositories.position;

import com.cafe.crm.models.user.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PositionRepository extends JpaRepository<Position, Long> {

	Position findByName(String name);

	List<Position> findByIdIn(Long[] ids);

	List<Position> findByIsPositionUsePercentOfSalesIsTrue();

}
