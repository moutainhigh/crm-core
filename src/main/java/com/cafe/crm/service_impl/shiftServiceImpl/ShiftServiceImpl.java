package com.cafe.crm.service_impl.shiftServiceImpl;

import com.cafe.crm.dao.ShiftRepository;
import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.models.User;
import com.cafe.crm.models.shift.Shift;
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
	private UserRepository userRepository;


	@Override
	public void saveAndFlush(Shift shift) {
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public Shift newShift(int[] box) {
		Set<User> users = new HashSet<>();
		for (int i = 0; i < box.length; i++) {
			User user = userRepository.getOne(Long.valueOf((long) box[i]));
			users.add(user);
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
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getWorkers() {    // Рабочие не добавленные в открытую  смену

		List<User> users = userRepository.findAll();
		users.removeAll(shiftRepository.getLast().getUsers());
		return users;
	}

	@Override
	public void deleteWorkerFromShift(String name) {
		Shift shift = shiftRepository.getLast();
		User user = userRepository.getUserByNameForShift(name);
		shift.getUsers().remove(user);
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public void addWorkerFromShift(String name) {
		Shift shift = shiftRepository.getLast();
		User user = userRepository.getUserByNameForShift(name);
		shift.getUsers().add(user);
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
