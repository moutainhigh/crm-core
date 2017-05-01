package com.cafe.crm.initMet;


import com.cafe.crm.dao.*;
import com.cafe.crm.dao.dao_menu.CategoryRepository;
import com.cafe.crm.dao.dao_menu.MenuRepository;
import com.cafe.crm.dao.dao_menu.ProductRepository;
import com.cafe.crm.models.Menu.Category;
import com.cafe.crm.models.Menu.Menu;
import com.cafe.crm.models.Menu.Product;
import com.cafe.crm.models.Role;
import com.cafe.crm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	public void init() {

		Role roleBoss = new Role();
		roleBoss.setName("BOSS");
		roleRepository.saveAndFlush(roleBoss);

		Role roleUser = new Role();
		roleUser.setName("MANAGER");
		roleRepository.saveAndFlush(roleUser);

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("admin");
		admin.setName("Jim");

		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(roleBoss);
		admin.setRoles(adminRoles);

		userRepository.saveAndFlush(admin);

		User user = new User();
		user.setLogin("manager");
		user.setPassword("manager");
		user.setName("Anna");

		Set<Role> userRoles = new HashSet<>();
		userRoles.add(roleUser);
		user.setRoles(userRoles);

		userRepository.saveAndFlush(user);


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

		Set<Category> setCat1 = new HashSet<>();
		Set<Category> setCat2 = new HashSet<>();
		Set<Category> setCat3 = new HashSet<>();

		setCat1.add(category1);
		setCat2.add(category2);
		setCat3.add(category3);

		pro1.setCategory(setCat1);

		pro11.setCategory(setCat1);
		pro111.setCategory(setCat1);

		pro2.setCategory(setCat2);
		pro22.setCategory(setCat2);
		pro222.setCategory(setCat2);

		pro3.setCategory(setCat3);
		pro33.setCategory(setCat3);
		pro333.setCategory(setCat3);

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
