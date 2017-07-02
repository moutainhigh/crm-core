package com.cafe.crm.dao.boss;

import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BossRepository extends JpaRepository<Boss, Long> {
	// for login
	@Query("SELECT u FROM Boss u WHERE u.email =:name")
	Boss getUserByLogin(@Param("name") String login);

	Boss findByEmail(String email);

	Boss findByPhone(Long phone);

	@Query("SELECT c FROM Boss c where c.enabled = true")
	List<Boss> getAllActiveBoss();
}

