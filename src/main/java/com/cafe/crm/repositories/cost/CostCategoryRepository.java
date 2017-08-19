package com.cafe.crm.repositories.cost;

import com.cafe.crm.models.cost.CostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CostCategoryRepository extends JpaRepository<CostCategory, Long> {

	CostCategory findByNameIgnoreCase(String name);

	List<CostCategory> findByNameStartingWith(String startName);

}
