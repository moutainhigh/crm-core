package com.cafe.crm.dao;

import com.cafe.crm.models.worker.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BossRepository extends JpaRepository<Boss, Long> {
	// for login
	@Query("SELECT u FROM Boss u WHERE u.login =:name")
	Boss getUserByLogin(@Param("name") String login);
}

