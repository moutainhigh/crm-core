package com.cafe.crm.services.interfaces.calculation;


import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.dto.TotalStatisticView;
import com.cafe.crm.models.shift.Shift;

import java.time.LocalDate;
import java.util.Set;

public interface ShiftCalculationService {
	double getTotalCashBox(Set<Shift> allShiftsBetween);
	TotalStatisticView createTotalStatisticView(LocalDate from, LocalDate to);
	ShiftView createShiftView(Shift shift);
	void transferFromBankToCashBox(Double transfer);
	void transferFromCashBoxToBank(Double transfer);
}
