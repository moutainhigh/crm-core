package com.cafe.crm.repositories.debt;


import com.cafe.crm.models.client.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {

	List<Debt> findByVisibleIsTrueAndDateBetween(LocalDate from, LocalDate to);

	List<Debt> findByDebtorAndDateBetween(String debtor, LocalDate from, LocalDate to);

}
