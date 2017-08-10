package com.cafe.crm.services.interfaces.menu;


import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.dto.WrapperOfProduct;

import java.util.List;
import java.util.Map;

public interface IngredientsService {

	List<Ingredients> getAll();

	void save(Ingredients ingredients);

	Ingredients getOne(Long id);

	void delete(Long id);

	Ingredients findByName(String name);

	Map<Ingredients, Integer> createRecipe(WrapperOfProduct wrapper);
}
