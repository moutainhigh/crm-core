package com.cafe.crm.services.interfaces.calculation;


import com.cafe.crm.dto.ClientDetails;
import com.cafe.crm.dto.DetailStatisticView;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.dto.TotalStatisticView;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.shift.Shift;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface ShiftCalculationService {
	double getTotalCashBox(Set<Shift> allShiftsBetween);
	TotalStatisticView createTotalStatisticView(LocalDate from, LocalDate to);
	DetailStatisticView createDetailStatisticView(Shift shift);
	ShiftView createShiftView(Shift shift);
	void transferFromBankToCashBox(Double transfer);
	void transferFromCashBoxToBank(Double transfer);
	Map<Client, ClientDetails> getClientsOnDetails (Set<Calculate> allCalculate);
	Map<Calculate, String> getOtherMenu(Set<Calculate> calculates);
	Map<Calculate, String> getDirtyMenu(Set<Calculate> calculates);
}
