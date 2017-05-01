package com.cafe.crm.service_abstract.menu_service;

import com.cafe.crm.models.Menu.Product;

import java.util.List;

/**
 * Created by User on 01.05.2017.
 */
public interface ProductService {


	List<Product> findAll();

	void saveAndFlush(Product product);

	Product findOne(Long id);

	void delete(Long id);

}
