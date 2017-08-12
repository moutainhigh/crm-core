package com.cafe.crm.services.interfaces.goods;


import com.cafe.crm.models.goods.Goods;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface GoodsService {

	void save(Goods goods);

	void update(Goods goods);

	void offVisibleStatus(Long id);

	void offVisibleStatus(long[] ids);

	List<Goods> findByCategoryNameAndDateBetween(String categoryName, LocalDate from, LocalDate to);

	List<Goods> findByNameAndDateBetween(String name, LocalDate from, LocalDate to);

	List<Goods> findByNameAndCategoryNameAndDateBetween(String name, String categoryName, LocalDate from, LocalDate to);

	List<Goods> findByDateBetween(LocalDate from, LocalDate to);

	Set<Goods> findByNameStartingWith(String startName);

	List<Goods> findByDateAndVisibleTrue(LocalDate date);

	List<Goods> findByDateAndCategoryNameAndVisibleTrue(LocalDate date, String name);

	List<Goods> findByShiftIdAndCategoryNameNot(Long shiftId, String name);

	List<Goods> findByShiftId(Long shiftId);

}
