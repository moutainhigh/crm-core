package com.cafe.crm.services.impl.goods;

import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.repositories.goods.GoodsRepository;
import com.cafe.crm.services.interfaces.goods.GoodsCategoryService;
import com.cafe.crm.services.interfaces.goods.GoodsService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class GoodsServiceImpl implements GoodsService {

	private final GoodsRepository goodsRepository;
	private final ShiftService shiftService;
	private final GoodsCategoryService goodsCategoryService;


	@Autowired
	public GoodsServiceImpl(GoodsRepository goodsRepository, ShiftService shiftService, GoodsCategoryService goodsCategoryService) {
		this.goodsRepository = goodsRepository;
		this.shiftService = shiftService;
		this.goodsCategoryService = goodsCategoryService;
	}

	@Override
	public void save(Goods goods) {
		String categoryName = goods.getCategory().getName().trim();
		GoodsCategory categoryInDb = goodsCategoryService.findByNameIgnoreCase(categoryName);
		if (categoryInDb == null) {
			goods.getCategory().setName(categoryName);
			goodsCategoryService.save(goods.getCategory());
		} else {
			goods.setCategory(categoryInDb);
		}
		if (goods.getShift() == null) {
			Shift shift = shiftService.getLast();
			if (shift.isOpen()) {
				goods.setShift(shift);
			}
		}
		goodsRepository.save(goods);
	}

	@Override
	public void update(Goods goods) {
		if (goods.getId() != null && goods.getShift() == null) {
			Goods goodsInDb = goodsRepository.findOne(goods.getId());
			goods.setShift(goodsInDb.getShift());
		}
		save(goods);
	}

	@Override
	public void offVisibleStatus(Long id) {
		Goods goods = goodsRepository.getOne(id);
		goods.setVisible(false);
		goodsRepository.save(goods);
	}

	@Override
	public void offVisibleStatus(long[] ids) {
		List<Goods> goodsList = goodsRepository.findByIdIn(ids);
		goodsList.forEach(goods -> goods.setVisible(false));
		goodsRepository.save(goodsList);
	}

	@Override
	public List<Goods> findByCategoryNameAndDateBetween(String categoryName, LocalDate from, LocalDate to) {
		return goodsRepository.findByCategoryNameIgnoreCaseAndVisibleIsTrueAndDateBetween(categoryName, from, to);
	}

	@Override
	public List<Goods> findByNameAndDateBetween(String categoryName, LocalDate from, LocalDate to) {
		return goodsRepository.findByNameIgnoreCaseAndVisibleIsTrueAndDateBetween(categoryName, from, to);
	}

	@Override
	public List<Goods> findByNameAndCategoryNameAndDateBetween(String name, String categoryName, LocalDate from, LocalDate to) {
		return goodsRepository.findByNameIgnoreCaseAndCategoryNameIgnoreCaseAndVisibleIsTrueAndDateBetween(name, categoryName, from, to);
	}

	@Override
	public List<Goods> findByDateBetween(LocalDate from, LocalDate to) {
		return goodsRepository.findByVisibleIsTrueAndDateBetween(from, to);
	}

	@Override
	public Set<Goods> findByNameStartingWith(String startName) {
		return goodsRepository.findByNameStartingWith(startName);
	}

	@Override
	public List<Goods> findByDateAndVisibleTrue(LocalDate date) {
		return goodsRepository.findByDateAndVisibleTrue(date);
	}

	@Override
	public List<Goods> findByDateAndCategoryNameAndVisibleTrue(LocalDate date, String name) {
		return goodsRepository.findByDateAndCategoryNameAndVisibleTrue(date, name);
	}

	@Override
	public List<Goods> findByShiftIdAndCategoryNameNot(Long shiftId, String name) {
		return goodsRepository.findByShiftIdAndCategoryNameNotAndVisibleIsTrue(shiftId, name);
	}

	@Override
	public List<Goods> findByShiftId(Long shiftId) {
		return goodsRepository.findByShiftIdAndVisibleIsTrue(shiftId);
	}

}
