package com.cafe.crm.services.interfaces.goods;

import com.cafe.crm.models.goods.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService {

	void save(GoodsCategory goodsCategory);

	void update(GoodsCategory goodsCategory);

	List<GoodsCategory> findAll();

	GoodsCategory find(Long id);

	GoodsCategory find(String name);

	List<GoodsCategory> findByNameStartingWith(String startName);

	void delete(Long id);
}
