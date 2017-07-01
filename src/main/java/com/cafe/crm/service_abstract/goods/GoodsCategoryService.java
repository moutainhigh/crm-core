package com.cafe.crm.service_abstract.goods;

import com.cafe.crm.models.estimate.GoodsCategory;

import java.util.Set;

public interface GoodsCategoryService {
    void save(GoodsCategory goodsCategory);
    Set<GoodsCategory> getAll();
    GoodsCategory findByNameIgnoreCase(String name);
    Set<GoodsCategory> findByNameStartingWith(String startName);
}
