package com.cafe.crm.repositories.goods;

import com.cafe.crm.models.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

	List<Goods> findByCategoryNameIgnoreCaseAndVisibleIsTrueAndDateBetween(String name, LocalDate from, LocalDate to);

	List<Goods> findByNameIgnoreCaseAndVisibleIsTrueAndDateBetween(String name, LocalDate from, LocalDate to);

	List<Goods> findByNameIgnoreCaseAndCategoryNameIgnoreCaseAndVisibleIsTrueAndDateBetween(String name, String categoryName, LocalDate from, LocalDate to);

	List<Goods> findByVisibleIsTrueAndDateBetween(LocalDate from, LocalDate to);

	Set<Goods> findByNameStartingWith(String startName);

	List<Goods> findByIdIn(long[] ids);

	Set<Goods> findByDate(LocalDate date);

}
