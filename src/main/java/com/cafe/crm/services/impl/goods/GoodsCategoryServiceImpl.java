package com.cafe.crm.services.impl.goods;

import com.cafe.crm.exceptions.cost.category.CostCategoryDataException;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.repositories.goods.GoodsCategoryRepository;
import com.cafe.crm.services.interfaces.goods.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	private final GoodsCategoryRepository goodsCategoryRepository;

	@Autowired
	public GoodsCategoryServiceImpl(GoodsCategoryRepository goodsCategoryRepository) {
		this.goodsCategoryRepository = goodsCategoryRepository;
	}

	@Override
	public void save(GoodsCategory goodsCategory) {
		checkForUniqueName(goodsCategory);
		goodsCategoryRepository.save(goodsCategory);
	}

	@Override
	public void update(GoodsCategory goodsCategory) {
		checkForUniqueName(goodsCategory);
		GoodsCategory editedCategory = goodsCategoryRepository.findOne(goodsCategory.getId());
		if (editedCategory != null) {
			editedCategory.setName(goodsCategory.getName());
			goodsCategoryRepository.save(editedCategory);
		} else {
			throw new CostCategoryDataException("Вы пытаетесь обновить несуществующую категорию!");
		}
	}


	@Override
	public List<GoodsCategory> findAll() {
		return goodsCategoryRepository.findAll();
	}

	@Override
	public GoodsCategory find(Long id) {
		return goodsCategoryRepository.findOne(id);
	}

	@Override
	public GoodsCategory find(String name) {
		return goodsCategoryRepository.findByNameIgnoreCase(name);
	}

	@Override
	public List<GoodsCategory> findByNameStartingWith(String startName) {
		return goodsCategoryRepository.findByNameStartingWith(startName);
	}

	@Override
	public void delete(Long id) {
		goodsCategoryRepository.delete(id);
	}

	private void checkForUniqueName(GoodsCategory goodsCategory) {
		GoodsCategory goodsCategoryInDataBase = find(goodsCategory.getName());
		if (goodsCategoryInDataBase != null) {
			throw new CostCategoryDataException("Категория с таким названием уже существует!");
		}
	}
}
