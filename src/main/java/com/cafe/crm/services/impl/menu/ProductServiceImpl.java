package com.cafe.crm.services.impl.menu;

import com.cafe.crm.models.menu.Product;
import com.cafe.crm.repositories.menu.ProductRepository;
import com.cafe.crm.services.interfaces.menu.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public void saveAndFlush(Product product) {
		productRepository.saveAndFlush(product);
	}

	@Override
	public Product findOne(Long id) {
		return productRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		productRepository.delete(id);
	}

	@Override
	public Product findByNameAndDescriptionAndCost(String name, String description, Double cost) {
		return productRepository.findByNameAndDescriptionAndCost(name, description, cost);
	}

	@Override
	public List<Product> findAllOrderByRatingDesc() {
		return productRepository.findAllByOrderByRatingDescNameAsc();
	}

}
