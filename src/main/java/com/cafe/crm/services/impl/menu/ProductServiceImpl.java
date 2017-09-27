package com.cafe.crm.services.impl.menu;

import com.cafe.crm.dto.WrapperOfProduct;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.repositories.menu.ProductRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final PositionService positionService;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, PositionService positionService, CompanyService companyService) {
		this.productRepository = productRepository;
		this.positionService = positionService;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	@Autowired
	private IngredientsService ingredientsService;

	@Override
	public List<Product> findAll() {
		return productRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	private void setCompanyId(Product product){
		product.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void saveAndFlush(Product product) {
		setCompanyId(product);
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
		return productRepository.findByNameAndDescriptionAndCostAndCompanyId(name, description, cost, companyIdCache.getCompanyId());
	}

	@Override
	public List<Product> findAllOrderByRatingDesc() {
		return productRepository.findByCompanyIdOrderByRatingDescNameAsc(companyIdCache.getCompanyId());
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
