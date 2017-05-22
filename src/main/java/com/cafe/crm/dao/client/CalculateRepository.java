package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Calculate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface CalculateRepository extends JpaRepository<Calculate, Long> {

	@Query("SELECT c FROM Calculate c where c.state = true")
	 List<Calculate> getAllOpen();
}
