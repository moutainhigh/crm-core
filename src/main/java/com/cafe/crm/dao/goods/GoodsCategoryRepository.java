package com.cafe.crm.dao.goods;

import com.cafe.crm.models.estimate.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {
    GoodsCategory findByNameIgnoreCase(String name);
    Set<GoodsCategory> findByNameStartingWith(String startName);
}
