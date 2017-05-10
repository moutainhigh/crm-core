package com.cafe.crm.service_impl.ShiftServiceImpl;

import com.cafe.crm.dao.MainClassRepository;
import com.cafe.crm.dao.ShiftRepository;
import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.models.MainClass;
import com.cafe.crm.models.User;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.service_abstract.shift_service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Created by User on 01.05.2017.
 */
@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	private ShiftRepository shiftRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MainClassRepository mainClassRepository;




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
		shiftRepository.saveAndFlush(shift);
		MainClass mainClass = mainClassRepository.getOne(1L);
		mainClass.setOpen(true);
		mainClass.setShiftId(shift.getId());
		mainClassRepository.saveAndFlush(mainClass);
		return shift;
	}

	@Override
	public Shift findOne(Long L) {
		return shiftRepository.findOne(L);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getWorkers() {    // Рабочие не добавленные в открытую  смену

		List<User> users = userRepository.findAll();
		users.removeAll(shiftRepository.findOne(mainClassRepository.getOne(1L).getShiftId()).getUsers());
		return users;
	}

	@Override
	public void deleteWorkerFromShift(String name) {
		Shift shift = shiftRepository.findOne(mainClassRepository.getOne(1L).getShiftId());
		User user = userRepository.getUserByNameForShift(name);
		shift.getUsers().remove(user);
	    shiftRepository.saveAndFlush(shift);
	}

	@Override
	public void addWorkerFromShift(String name) {
		Shift shift = shiftRepository.findOne(mainClassRepository.getOne(1L).getShiftId());
		User user = userRepository.getUserByNameForShift(name);
		shift.getUsers().add(user);
		shiftRepository.saveAndFlush(shift);
	}
}
