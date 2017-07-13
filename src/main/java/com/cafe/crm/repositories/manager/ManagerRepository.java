package com.cafe.crm.repositories.manager;

import com.cafe.crm.models.worker.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ManagerRepository extends JpaRepository<Manager, Long> {

	Manager findByEmail(String email);

	Manager findByPhone(String phone);

	@Query("SELECT c FROM Manager c where c.enabled = true")
	List<Manager> getAllActiveManager();

}
