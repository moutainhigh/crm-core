package com.cafe.crm.repositories.goods;

import com.cafe.crm.models.goods.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {

    GoodsCategory findByNameIgnoreCase(String name);

    Set<GoodsCategory> findByNameStartingWith(String startName);

}
