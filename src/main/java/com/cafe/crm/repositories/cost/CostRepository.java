package com.cafe.crm.repositories.cost;

import com.cafe.crm.models.cost.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CostRepository extends JpaRepository<Cost, Long> {

	List<Cost> findByCategoryNameIgnoreCaseAndVisibleIsTrueAndDateBetween(String name, LocalDate from, LocalDate to);

	List<Cost> findByNameIgnoreCaseAndVisibleIsTrueAndDateBetween(String name, LocalDate from, LocalDate to);

	List<Cost> findByNameIgnoreCaseAndCategoryNameIgnoreCaseAndVisibleIsTrueAndDateBetween(String name, String categoryName, LocalDate from, LocalDate to);

	List<Cost> findByVisibleIsTrueAndDateBetween(LocalDate from, LocalDate to);

	Set<Cost> findByNameStartingWith(String startName);

	List<Cost> findByIdIn(long[] ids);

	List<Cost> findByDateAndVisibleTrue(LocalDate date);

	List<Cost> findByDateAndCategoryNameAndVisibleTrue(LocalDate date, String name);

	List<Cost> findByShiftIdAndCategoryNameNotAndVisibleIsTrue(Long shiftId, String name);

	List<Cost> findByShiftIdAndVisibleIsTrue(Long shiftId);

}
