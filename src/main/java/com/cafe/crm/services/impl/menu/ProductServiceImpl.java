package com.cafe.crm.services.impl.menu;

import com.cafe.crm.dto.WrapperOfProduct;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.repositories.menu.ProductRepository;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import com.cafe.crm.services.interfaces.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final PositionService positionService;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, PositionService positionService) {
		this.productRepository = productRepository;
		this.positionService = positionService;
	}

	@Autowired
	private IngredientsService ingredientsService;

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

	@Override
	public void reduceIngredientAmount(Product product) {
		ingredientsService.reduceIngredientAmount(product.getRecipe());
	}

	@Override
	public Map<Position, Integer> createStaffPercent(WrapperOfProduct wrapper) {
		Map<Position,Integer> staffPercent = new HashMap<>();

		List<Long> positionsId = wrapper.getStaffPercentPosition();
		List<Integer> percents = wrapper.getStaffPercentPercent();

		for (int i = 0; i < positionsId.size(); i++) {
			Position position = positionService.findById(positionsId.get(i));
			staffPercent.put(position,percents.get(i));
		}

		return staffPercent;
	}

}
