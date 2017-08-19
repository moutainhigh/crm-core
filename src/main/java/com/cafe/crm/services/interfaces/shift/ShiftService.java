package com.cafe.crm.services.interfaces.shift;

import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.models.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface ShiftService {

	void saveAndFlush(Shift shift);

	Shift crateNewShift(Double cashBox, Double bankCashBox, long... usersId);

	Shift findOne(Long L);

	List<User> getUsersNotOnShift();

	List<User> getUsersOnShift();

	void deleteUserFromShift(Long userId);

	void addUserToShift(Long userId);

	Shift getLast();

	List<Shift> findAll();

	Shift closeShift(Map<Long, Integer> mapOfUsersIdsAndBonuses, Double allPrice, Double shortage, Double bankCashBox, String comment);

	Set<Shift> findByDates(LocalDate start, LocalDate end);

	ShiftView createShiftView(Shift shift);

	Shift findByDateShift(LocalDate date);

	void transferFromBankToCashBox(Double transfer);

	void transferFromCashBoxToBank(Double transfer);


}
