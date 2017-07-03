package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
	@Query("SELECT c FROM Client c where c.state = true")
	List<Client> getAllOpen();

	List<Client> findByIdIn(long[] ids);
}
