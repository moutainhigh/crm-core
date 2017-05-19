package com.cafe.crm.service_abstract.menu_service;


import com.cafe.crm.models.Menu.Category;

import java.util.List;

public interface CategoriesService {

	List<Category> findAll();

	Category getOne(Long id);

	void saveAndFlush(Category category);

	void delete(Long id);

}
