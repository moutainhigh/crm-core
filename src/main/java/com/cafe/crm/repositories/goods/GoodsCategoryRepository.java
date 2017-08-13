package com.cafe.crm.repositories.goods;

import com.cafe.crm.models.goods.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {

	GoodsCategory findByNameIgnoreCase(String name);

	List<GoodsCategory> findByNameStartingWith(String startName);

}
