package com.cafe.crm.service_impl.shiftServiceImpl;

import com.cafe.crm.dao.ShiftRepository;
import com.cafe.crm.dao.WorkerRepository;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service_abstract.shift_service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	private ShiftRepository shiftRepository;

	@Autowired
	private WorkerRepository workerRepository;

	@Override
	public void saveAndFlush(Shift shift) {
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public Shift newShift(int[] box) {
		Set<Worker> users = new HashSet<>();
		for (int i = 0; i < box.length; i++) {
			Worker worker = workerRepository.getOne(Long.valueOf((long) box[i]));
			users.add(worker);
		}
		Shift shift = new Shift(LocalDate.now(), 0, users);
		shift.setOpen(true);
		shiftRepository.saveAndFlush(shift);
		return shift;
	}

	@Override
	public Shift findOne(Long L) {
		return shiftRepository.findOne(L);
	}

	@Override
	public List<Worker> findAllUsers() {
		return workerRepository.findAll();
	}

	@Override
	public List<Worker> getWorkers() {    // Рабочие не добавленные в открытую  смену

		List<Worker> workers = workerRepository.findAll();
		workers.removeAll(shiftRepository.getLast().getUsers());
		return workers;
	}

	@Override
	public void deleteWorkerFromShift(String name) {
		Shift shift = shiftRepository.getLast();
		Worker worker = workerRepository.getWorkerByNameForShift(name);
		shift.getUsers().remove(worker);
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public void addWorkerFromShift(String name) {
		Shift shift = shiftRepository.getLast();
		Worker worker = workerRepository.getWorkerByNameForShift(name);
		shift.getUsers().add(worker);
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public Shift getLast() {
		return shiftRepository.getLast();
	}

	@Override
	public List<Shift> findAll() {
		return shiftRepository.findAll();
	}

	@Override
	public void closeShift() {
		Shift shift = shiftRepository.getLast();
		shift.setOpen(false);
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public Set<Shift> findByDates(LocalDate start, LocalDate end) {
		return shiftRepository.findByDates(start, end);
	}
}
