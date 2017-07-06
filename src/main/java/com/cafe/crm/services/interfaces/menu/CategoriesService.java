package com.cafe.crm.services.interfaces.menu;


import com.cafe.crm.models.Menu.Category;

import java.util.List;

public interface CategoriesService {

	List<Category> findAll();

	Category getOne(Long id);

	void saveAndFlush(Category category);

	void delete(Long id);

}
