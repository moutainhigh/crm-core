package com.cafe.crm.service_impl.menu;

import com.cafe.crm.dao.menu.ProductRepository;
import com.cafe.crm.models.Menu.Product;
import com.cafe.crm.service_abstract.menu_service.ProductService;
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
     return  productRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		productRepository.delete(id);
	}
}
