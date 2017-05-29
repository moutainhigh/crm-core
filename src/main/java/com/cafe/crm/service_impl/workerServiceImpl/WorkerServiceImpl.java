package com.cafe.crm.service_impl.workerServiceImpl;

import com.cafe.crm.dao.BossRepository;
import com.cafe.crm.dao.ManagerRepository;
import com.cafe.crm.dao.WorkerRepository;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Worker;

import com.cafe.crm.service_abstract.worker_service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private BossRepository bossRepository;


	// query by date (test)
	@Override
	public void shiftByDate(LocalDate start, LocalDate end) {
		List<Manager> managerList = managerRepository.findAll();
		for (Manager manager : managerList) {
			Set<Shift> allShifts = managerRepository.findByDates(start, end);
		}
	}

	@Override
	public List<Worker> listAllWorker() {
		return workerRepository.findAll();
	}

	@Override
	public List<Manager> listAllManager() {
		return managerRepository.findAll();
	}

	@Override
	public List<Boss> listAllBoss() {
		return bossRepository.findAll();
	}

	@Override
	public void saveWorker(Worker worker) {
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void saveManager(Manager manager) {
		managerRepository.saveAndFlush(manager);
	}

	@Override
	public void saveBoss(Boss boss) {
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void delete(Long id) {
		workerRepository.delete(id);
	}

	@Override
	public Worker getUserById(long id) {
		return workerRepository.findOne(id);
	}

	@Override
	public Worker getUserByLogin(String name) {
		return workerRepository.getUserByLogin(name);
	}

	@Override
	public List<Worker> findAll() {
		return workerRepository.findAll();
	}

	@Override
	public Worker getUserByNameForShift(String name) {
		return workerRepository.getWorkerByNameForShift(name);
	}

	@Override
	public void save(Worker worker) {
		workerRepository.saveAndFlush(worker);
	}


}





