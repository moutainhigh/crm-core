package com.cafe.crm.service_abstract.menu;

import com.cafe.crm.models.Menu.Product;

import java.util.List;


public interface ProductService {


	List<Product> findAll();

	void saveAndFlush(Product product);

	Product findOne(Long id);

	void delete(Long id);

}
