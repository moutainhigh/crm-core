package com.cafe.crm.dao;

import com.cafe.crm.models.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
