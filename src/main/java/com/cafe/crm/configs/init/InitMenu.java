package com.cafe.crm.configs.init;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.menu.Category;
import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.models.menu.Menu;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.repositories.menu.CategoryRepository;
import com.cafe.crm.repositories.menu.MenuRepository;
import com.cafe.crm.repositories.menu.ProductRepository;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitMenu {

	private final MenuRepository repository;
	private final CategoryRepository category;
	private final ProductRepository productRepository;
	private final IngredientsService ingredientsService;
	private final BoardService boardService;

	@Autowired
	public InitMenu(MenuRepository repository, CategoryRepository category, ProductRepository productRepository, IngredientsService ingredientsService, BoardService boardService) {
		this.repository = repository;
		this.category = category;
		this.productRepository = productRepository;
		this.ingredientsService = ingredientsService;
		this.boardService = boardService;
	}

	@PostConstruct
	public void init() {

		Ingredients ingredients1 = new Ingredients();
		Ingredients ingredients2 = new Ingredients();
		Ingredients ingredients3 = new Ingredients();
		Ingredients ingredients4 = new Ingredients();

		ingredients1.setName("Сметана");
		ingredients1.setDimension("граммы");
		ingredients1.setAmount(300);
		ingredients1.setPrice(0.076D);

		ingredients2.setName("Вишня");
		ingredients2.setDimension("кг");
		ingredients2.setAmount(3);
		ingredients2.setPrice(278D);

		ingredients3.setName("Зелень");
		ingredients3.setDimension("кг");
		ingredients3.setAmount(5);
		ingredients3.setPrice(120.5D);

		ingredients4.setName("Молоко");
		ingredients4.setDimension("литры");
		ingredients4.setAmount(15);
		ingredients4.setPrice(69.2D);

		ingredientsService.save(ingredients1);
		ingredientsService.save(ingredients2);
		ingredientsService.save(ingredients3);
		ingredientsService.save(ingredients4);

		Category category1 = new Category("Салаты");
		Category category2 = new Category("Напитки");
		Category category3 = new Category("Десерты");
		Category category4 = new Category("Доставка");
		category4.setDirtyProfit(false);
		category4.setFloatingPrice(true);

		category.saveAndFlush(category1);
		category.saveAndFlush(category2);
		category.saveAndFlush(category3);
		category.saveAndFlush(category4);


		List<Product> products1 = new ArrayList<>();
		List<Product> products2 = new ArrayList<>();
		List<Product> products3 = new ArrayList<>();
		List<Product> products4 = new ArrayList<>();


		Product pro1 = new Product("Цезарь", "вкусный", 400d);
		pro1.setSelfCost(200d);
		Product pro11 = new Product("Лёгкий", "вкусный", 100d);
		pro11.setSelfCost(50d);
		Product pro111 = new Product("Летний", "вкусный", 100d);

		Product pro2 = new Product("Кофе", "вкусный", 15d);
		Product pro22 = new Product("Кока-кола", "вкусный", 5d);
		Product pro222 = new Product("Кофе", "вкусный", 5d);

		Product pro3 = new Product("Фруктовый", "вкусный", 400d);
		Product pro33 = new Product("Терамису", "вкусный", 400d);
		Product pro333 = new Product("Шоколадный", "вкусный", 400d);

		Product pro4 = new Product("Пицца роял", "деревенская пицца", 0d);
		Product pro44 = new Product("Суши шоп", "филадельфия", 0d);
		Product pro444 = new Product("Макдак", "биг мак", 0d);


		pro1.setCategory(category1);
		pro11.setCategory(category1);
		pro111.setCategory(category1);

		pro2.setCategory(category2);
		pro22.setCategory(category2);
		pro222.setCategory(category2);

		pro3.setCategory(category3);
		pro33.setCategory(category3);
		pro333.setCategory(category3);

		pro4.setCategory(category4);
		pro44.setCategory(category4);
		pro444.setCategory(category4);

		productRepository.saveAndFlush(pro1);
		productRepository.saveAndFlush(pro11);
		productRepository.saveAndFlush(pro111);

		productRepository.saveAndFlush(pro2);
		productRepository.saveAndFlush(pro22);
		productRepository.saveAndFlush(pro222);

		productRepository.saveAndFlush(pro3);
		productRepository.saveAndFlush(pro33);
		productRepository.saveAndFlush(pro333);

		productRepository.saveAndFlush(pro4);
		productRepository.saveAndFlush(pro44);
		productRepository.saveAndFlush(pro444);

		products1.add(pro1);
		products1.add(pro11);
		products1.add(pro111);

		products2.add(pro2);
		products2.add(pro22);
		products2.add(pro222);

		products3.add(pro3);
		products3.add(pro33);
		products3.add(pro333);

		products4.add(pro4);
		products4.add(pro44);
		products4.add(pro444);


		category1.setProducts(products1);

		category2.setProducts(products2);

		category3.setProducts(products3);

		category4.setProducts(products4);

		category.saveAndFlush(category1);
		category.saveAndFlush(category2);
		category.saveAndFlush(category3);
		category.saveAndFlush(category4);

		Menu menu = new Menu("Обеденное");


		Set<Category> myMenu = new HashSet<>();

		myMenu.add(category1);
		myMenu.add(category2);
		myMenu.add(category3);
		myMenu.add(category4);

		menu.setCategories(myMenu);
		repository.saveAndFlush(menu);

		Board board = new Board();
		board.setName("Стол");
		boardService.save(board);

	}
}
