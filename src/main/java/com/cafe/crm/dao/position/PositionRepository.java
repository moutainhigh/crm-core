package com.cafe.crm.dao.position;

import com.cafe.crm.models.worker.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PositionRepository extends JpaRepository<Position, Long> {

	@Query("SELECT e FROM Position e WHERE e.jobName=:jobName")
	Position getPositionByName(@Param("jobName") String jobName);
}
