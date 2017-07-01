package com.cafe.crm.service_impl.goods;

import com.cafe.crm.dao.goods.GoodsCategoryRepository;
import com.cafe.crm.models.estimate.GoodsCategory;
import com.cafe.crm.service_abstract.goods.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	private final GoodsCategoryRepository goodsCategoryRepository;

	@Autowired
	public GoodsCategoryServiceImpl(GoodsCategoryRepository goodsCategoryRepository) {
		this.goodsCategoryRepository = goodsCategoryRepository;
	}

	@Override
	public void save(GoodsCategory goodsCategory) {
		goodsCategoryRepository.save(goodsCategory);
	}

	@Override
	public Set<GoodsCategory> getAll() {
		return new HashSet<>(goodsCategoryRepository.findAll());
	}

	@Override
	public GoodsCategory findByNameIgnoreCase(String name) {
		return goodsCategoryRepository.findByNameIgnoreCase(name);
	}

	@Override
	public Set<GoodsCategory> findByNameStartingWith(String startName) {
		return goodsCategoryRepository.findByNameStartingWith(startName);
	}

}
