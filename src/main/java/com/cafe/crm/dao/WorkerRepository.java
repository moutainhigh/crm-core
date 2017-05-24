package com.cafe.crm.dao;

import com.cafe.crm.models.User;
import com.cafe.crm.models.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface WorkerRepository extends JpaRepository<Worker, Long> {

	// for shift
	@Query("SELECT u FROM Worker u WHERE u.firstName =:name")
	Worker getWorkerByNameForShift(@Param("name")String name);

}
