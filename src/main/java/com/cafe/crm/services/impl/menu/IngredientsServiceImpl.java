package com.cafe.crm.services.impl.menu;

import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.dto.WrapperOfProduct;
import com.cafe.crm.repositories.menu.IngredientsRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsServiceImpl implements IngredientsService {

	private final IngredientsRepository ingredientsRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public IngredientsServiceImpl(IngredientsRepository ingredientsRepository, CompanyService companyService) {
		this.ingredientsRepository = ingredientsRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Ingredients ingredients){
		ingredients.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public List<Ingredients> getAll() {
		return ingredientsRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public void save(Ingredients ingredients) {
		setCompanyId(ingredients);
		ingredientsRepository.saveAndFlush(ingredients);
	}

	@Override
	public Ingredients getOne(Long id) {
		return ingredientsRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		ingredientsRepository.delete(id);
	}

	@Override
	public Ingredients findByName(String name) {
		return ingredientsRepository.findByNameAndCompanyId(name, companyIdCache.getCompanyId());
	}

	@Override
	public Map<Ingredients, Integer> createRecipe(WrapperOfProduct wrapper) {
		Map<Ingredients, Integer> recipe = new HashMap<>();
		List<String> names = wrapper.getNames();
		List<Integer> amount = wrapper.getAmount();
		Ingredients ingredients;
		if (names != null && amount != null) {
			if (!(amount.get(0) == 0 && names.size() == 1)) {
				for (int i = 0; i < names.size(); i++) {
					ingredients = findByName(names.get(i));
					if (ingredients != null) {
						recipe.put(ingredients, amount.get(i));
					}
				}
			}
		}
		return recipe;
	}

	@Override
	public Double getRecipeCost(Map<Ingredients, Integer> recipe) {
		double result = 0L;

		for (Map.Entry<Ingredients, Integer> entry : recipe.entrySet()) {
			result += entry.getKey().getPrice() * entry.getValue();
		}
		return result;
	}

	@Override
	public void reduceIngredientAmount(Map<Ingredients, Integer> recipe) {

		for (Map.Entry<Ingredients, Integer> entry : recipe.entrySet()) {
			entry.getKey().setAmount(entry.getKey().getAmount() - entry.getValue());
			save(entry.getKey());
		}

	}
}
