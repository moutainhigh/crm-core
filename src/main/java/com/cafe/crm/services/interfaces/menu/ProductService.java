package com.cafe.crm.services.interfaces.menu;

import com.cafe.crm.models.menu.Product;

import java.util.List;


public interface ProductService {

	List<Product> findAll();

	void saveAndFlush(Product product);

	Product findOne(Long id);

	void delete(Long id);

	Product findByNameAndDescriptionAndCost(String name, String description, Double cost);

	List<Product> findAllOrderByRatingDesc();

}
