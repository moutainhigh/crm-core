package com.cafe.crm.services.interfaces.calculation;


import com.cafe.crm.dto.DetailStatisticView;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.dto.TotalStatisticView;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.shift.UserSalaryDetail;
import com.cafe.crm.models.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ShiftCalculationService {
	UserSalaryDetail getUserSalaryDetail(User user, Shift shift);
	List<UserSalaryDetail> getUserSalaryDetail(List<User> users, Shift shift);
	double getTotalCashBox(Set<Shift> allShiftsBetween);
	TotalStatisticView createTotalStatisticView(LocalDate from, LocalDate to);
	DetailStatisticView createDetailStatisticView(Shift shift);
	ShiftView createShiftView(Shift shift);
	void transferFromBankToCashBox(Double transfer);
	void transferFromCashBoxToBank(Double transfer);
}
