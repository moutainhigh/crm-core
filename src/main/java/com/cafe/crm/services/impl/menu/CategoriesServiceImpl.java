package com.cafe.crm.services.impl.menu;

import com.cafe.crm.models.menu.Category;
import com.cafe.crm.repositories.menu.CategoryRepository;
import com.cafe.crm.services.interfaces.menu.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getOne(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public void saveAndFlush(Category category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

}
