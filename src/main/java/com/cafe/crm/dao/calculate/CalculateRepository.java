package com.cafe.crm.dao.calculate;

import com.cafe.crm.models.client.Calculate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface CalculateRepository extends JpaRepository<Calculate, Long> {

	@Query("SELECT DISTINCT c FROM Calculate c JOIN FETCH c.client cc WHERE c.state = true AND cc.state = true")
	 List<Calculate> getAllOpen();
}
