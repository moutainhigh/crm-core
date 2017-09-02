package com.cafe.crm.services.impl.debt;


import com.cafe.crm.exceptions.debt.DebtDataException;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.repositories.debt.DebtRepository;
import com.cafe.crm.services.interfaces.debt.DebtService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {

	private final DebtRepository repository;

	private final ShiftService shiftService;

	@Autowired
	public DebtServiceImpl(DebtRepository repository, ShiftService shiftService) {
		this.repository = repository;
		this.shiftService = shiftService;
	}

	@Override
	public void save(Debt debt) {
		if (debt.getDebtAmount() > 0) {
			repository.save(debt);
		} else {
			throw new DebtDataException("Введена недопустимая сумма долга");
		}
	}


	@Override
	public void delete(Debt debt) {
		repository.delete(debt);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Debt get(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Debt> getAll() {
		return repository.findAll();
	}

	@Override
	public List<Debt> findByVisibleIsTrueAndDateBetween(LocalDate from, LocalDate to) {
		return repository.findByVisibleIsTrueAndDateBetween(from, to);
	}

	@Override
	public void offVisibleStatus(Debt debt) {
		debt.setVisible(false);
		repository.save(debt);
	}

	@Override
	public List<Debt> findByDebtorAndDateBetween(String debtor, LocalDate from, LocalDate to) {
		return repository.findByDebtorAndDateBetween(debtor, from, to);
	}

	@Override
	public void repayDebt(Long id) {
		Shift lastShift = shiftService.getLast();
		Debt debt = repository.findOne(id);
		lastShift.addRepaidDebtToList(debt);
		shiftService.saveAndFlush(lastShift);
		offVisibleStatus(debt);
	}
}
