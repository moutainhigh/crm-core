package com.cafe.crm.services.interfaces.goods;

import com.cafe.crm.models.goods.GoodsCategory;

import java.util.Set;

public interface GoodsCategoryService {

	void save(GoodsCategory goodsCategory);

	Set<GoodsCategory> getAll();

	GoodsCategory findByNameIgnoreCase(String name);

	Set<GoodsCategory> findByNameStartingWith(String startName);

}
