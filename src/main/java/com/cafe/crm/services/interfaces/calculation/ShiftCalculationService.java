package com.cafe.crm.services.interfaces.calculation;


import com.cafe.crm.dto.*;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.shift.Shift;

import java.time.LocalDate;
import java.util.List;
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
	List<CalculateDTO> getCalculates(Shift shift);
}
