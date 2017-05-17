package com.cafe.crm.service_abstract.shift_service;

import com.cafe.crm.models.User;
import com.cafe.crm.models.shift.Shift;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface ShiftService {

	void saveAndFlush(Shift shift);

	Shift newShift(int[] box);

	Shift findOne(Long L);

	List<User> findAllUsers();

	// Рабочие не добавленные в открытую  смену
	List<User> getWorkers();

	void deleteWorkerFromShift(String name);

	void addWorkerFromShift(String name);

	Shift getLast();

	List<Shift> findAll();

	void closeShift();

	Set<Shift> findByDates(LocalDate start, LocalDate end);


}
