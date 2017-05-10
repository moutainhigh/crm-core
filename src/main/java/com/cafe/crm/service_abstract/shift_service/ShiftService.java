package com.cafe.crm.service_abstract.shift_service;

import com.cafe.crm.models.User;
import com.cafe.crm.models.shift.Shift;
import java.util.List;

/**
 * Created by User on 01.05.2017.
 */
public interface ShiftService {

	void saveAndFlush(Shift shift);

	Shift newShift(int[] box);

	Shift findOne(Long L);

	List<User> findAll();


	// Рабочие не добавленные в открытую  смену
	List<User> getWorkers();

	void deleteWorkerFromShift(String name);

	void addWorkerFromShift(String name);

}
