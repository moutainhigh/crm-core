package com.cafe.crm.configs.init;


import com.cafe.crm.models.menu.Category;
import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.models.menu.Menu;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.repositories.menu.CategoryRepository;
import com.cafe.crm.repositories.menu.MenuRepository;
import com.cafe.crm.repositories.menu.ProductRepository;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitMenu {

	@Autowired
	private MenuRepository repository;

	@Autowired
	private CategoryRepository category;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private IngredientsService ingredientsService;

	@PostConstruct
	public void init() {


		Ingredients ingredients1 = new Ingredients();
		Ingredients ingredients2 = new Ingredients();
		Ingredients ingredients3 = new Ingredients();
		Ingredients ingredients4 = new Ingredients();

		ingredients1.setName("Сметана");
		ingredients1.setDimension("граммы");
		ingredients1.setAmount(300);

		ingredients2.setName("Вишня");
		ingredients2.setDimension("кг");
		ingredients2.setAmount(3);

		ingredients3.setName("Зелень");
		ingredients3.setDimension("кг");
		ingredients3.setAmount(5);

		ingredients4.setName("Молоко");
		ingredients4.setDimension("литры");
		ingredients4.setAmount(15);

		ingredientsService.save(ingredients1);
		ingredientsService.save(ingredients2);
		ingredientsService.save(ingredients3);
		ingredientsService.save(ingredients4);

		Category category1 = new Category("Салаты");
		Category category2 = new Category("Напитки");
		Category category3 = new Category("Десерты");

		category.saveAndFlush(category1);
		category.saveAndFlush(category2);
		category.saveAndFlush(category3);


		Set<Product> products1 = new HashSet<>();
		Set<Product> products2 = new HashSet<>();
		Set<Product> products3 = new HashSet<>();


		Product pro1 = new Product("Цезарь", "вкусный", 100d);
		Product pro11 = new Product("Лёгкий", "вкусный", 100d);
		Product pro111 = new Product("Летний", "вкусный", 100d);

		Product pro2 = new Product("Кофе", "вкусный", 5d);
		Product pro22 = new Product("Кока-кола", "вкусный", 5d);
		Product pro222 = new Product("Кофе", "вкусный", 5d);

		Product pro3 = new Product("Фруктовый", "вкусный", 400d);
		Product pro33 = new Product("Терамису", "вкусный", 400d);
		Product pro333 = new Product("Шоколадный", "вкусный", 400d);


		pro1.setCategory(category1);
		pro11.setCategory(category1);
		pro111.setCategory(category1);

		pro2.setCategory(category2);
		pro22.setCategory(category2);
		pro222.setCategory(category2);

		pro3.setCategory(category3);
		pro33.setCategory(category3);
		pro333.setCategory(category3);

		productRepository.saveAndFlush(pro1);
		productRepository.saveAndFlush(pro11);
		productRepository.saveAndFlush(pro111);

		productRepository.saveAndFlush(pro2);
		productRepository.saveAndFlush(pro22);
		productRepository.saveAndFlush(pro222);

		productRepository.saveAndFlush(pro3);
		productRepository.saveAndFlush(pro33);
		productRepository.saveAndFlush(pro333);

		products1.add(pro1);
		products1.add(pro11);
		products1.add(pro111);

		products2.add(pro2);
		products2.add(pro22);
		products2.add(pro222);

		products3.add(pro3);
		products3.add(pro33);
		products3.add(pro333);


		category1.setProducts(products1);

		category2.setProducts(products2);

		category3.setProducts(products3);

		category.saveAndFlush(category1);
		category.saveAndFlush(category2);
		category.saveAndFlush(category3);

		Menu menu = new Menu("Обеденное");


		Set<Category> myMenu = new HashSet<>();

		myMenu.add(category1);
		myMenu.add(category2);
		myMenu.add(category3);

		menu.setCategories(myMenu);

		repository.saveAndFlush(menu);


	}
}
