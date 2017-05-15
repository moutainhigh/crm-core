package com.cafe.crm.service.counting;


import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Worker;

import java.time.LocalDate;
import java.util.List;

public interface WorkerService {

	void shiftByDate(LocalDate start, LocalDate end);

	List<Worker> listAllWorker();

	List<Manager> listAllManager();

	Worker saveWorker(Worker worker);

	Manager saveManager(Manager manager);

	void delete(Long id);
}
