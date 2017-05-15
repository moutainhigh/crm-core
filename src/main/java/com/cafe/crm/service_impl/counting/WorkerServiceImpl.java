package com.cafe.crm.service_impl.counting;

import com.cafe.crm.dao.ManagerRepository;
import com.cafe.crm.dao.WorkerRepository;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service.counting.WorkerService;
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


	// Запрос по дате (Тестовый вариант)
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
	public Worker saveWorker(Worker worker) {
		return workerRepository.saveAndFlush(worker);
	}

	@Override
	public Manager saveManager(Manager manager) {
		return managerRepository.saveAndFlush(manager);
	}

	@Override
	public void delete(Long id) {
		workerRepository.delete(id);
	}
}





