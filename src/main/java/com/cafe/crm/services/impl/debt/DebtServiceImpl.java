package com.cafe.crm.services.impl.debt;


import com.cafe.crm.models.client.Debt;
import com.cafe.crm.repositories.debt.DebtRepository;
import com.cafe.crm.services.interfaces.debt.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {

	@Autowired
	DebtRepository repository;

	@Override
	public void save(Debt debt) {
		repository.save(debt);
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
	public void offVisibleStatus(Long id) {
		Debt debt = repository.findOne(id);
		debt.setVisible(false);
		repository.save(debt);
	}

	@Override
	public List<Debt> findByDebtorAndDateBetween(String debtor, LocalDate from, LocalDate to) {
		return repository.findByDebtorAndDateBetween(debtor, from, to);
	}

}
