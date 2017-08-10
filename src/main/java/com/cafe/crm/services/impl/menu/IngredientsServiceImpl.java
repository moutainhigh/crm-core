package com.cafe.crm.services.impl.menu;

import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.dto.WrapperOfProduct;
import com.cafe.crm.repositories.menu.IngredientsRepository;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsServiceImpl implements IngredientsService {

	private final IngredientsRepository ingredientsRepository;

	@Autowired
	public IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
		this.ingredientsRepository = ingredientsRepository;
	}


	@Override
	public List<Ingredients> getAll() {
		return ingredientsRepository.findAll();
	}

	@Override
	public void save(Ingredients ingredients) {
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
		return ingredientsRepository.findByName(name);
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
}
