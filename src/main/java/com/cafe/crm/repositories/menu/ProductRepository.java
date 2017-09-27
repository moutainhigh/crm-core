package com.cafe.crm.repositories.menu;

import com.cafe.crm.models.menu.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByNameAndDescriptionAndCost(String name, String description, Double cost);

	List<Product> findAllByOrderByRatingDescNameAsc();
}
