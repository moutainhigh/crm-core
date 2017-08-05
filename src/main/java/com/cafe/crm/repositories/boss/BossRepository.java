package com.cafe.crm.repositories.boss;

import com.cafe.crm.models.worker.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BossRepository extends JpaRepository<Boss, Long> {

	Boss findByEmail(String email);

	Boss findByPhone(String phone);

	@Query("SELECT c FROM Boss c where c.enabled = true")
	List<Boss> getAllActiveBoss();

	List<Boss> findByEnabledIsTrue();

}

