package com.cafe.crm.services.impl.menu;

import com.cafe.crm.models.menu.Category;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.repositories.menu.CategoryRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.menu.CategoriesService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoriesServiceImpl implements CategoriesService {

	private final CategoryRepository categoryRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public CategoriesServiceImpl(CategoryRepository categoryRepository, CompanyService companyService) {
		this.categoryRepository = categoryRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public Category getOne(Long id) {
		return categoryRepository.findOne(id);
	}

	private void setCompanyId(Category category){
		category.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void saveAndFlush(Category category) {
		setCompanyId(category);
		categoryRepository.saveAndFlush(category);
	}

	@Override
	public void delete(Long id) {
		categoryRepository.delete(id);
	}

	@Override
	public List<Category> sortProductListAndGetAllCategories() {
		List<Category> allCategories = categoryRepository.findByCompanyId(companyIdCache.getCompanyId());
		List<Category> categoriesWithSortedProducts = new ArrayList<>();
		for (Category category : allCategories) {
			List<Product> products = category.getProducts();
			products.sort((product1, product2) -> {
				int result;
				result = product2.getRating() - product1.getRating();
				if (result == 0) {
					result = product1.getName().compareTo(product2.getName());
				}
				return result;
			});
			categoriesWithSortedProducts.add(category);
		}
		return categoriesWithSortedProducts;
	}
}
