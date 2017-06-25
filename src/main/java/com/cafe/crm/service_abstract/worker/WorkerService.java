package com.cafe.crm.service_abstract.worker;


import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Worker;

import java.time.LocalDate;
import java.util.List;

public interface WorkerService {

	void shiftByDate(LocalDate start, LocalDate end);

	List<Worker> listAllWorker();

	List<Manager> listAllManager();

	List<Boss> listAllBoss();

	void saveWorker(Worker worker);

	void saveManager(Manager manager);

	void saveBoss(Boss boss);

	void delete(Long id);

	Worker getUserById(long id);

	Worker getUserByLogin(String name);

	List<Worker> findAll();

	Worker getUserByNameForShift(String name);

	void save(Worker worker);

}
