package com.cafe.crm.initMet;


import com.cafe.crm.dao.BossRepository;
import com.cafe.crm.dao.ManagerRepository;
import com.cafe.crm.dao.RoleRepository;
import com.cafe.crm.dao.WorkerRepository;
import com.cafe.crm.dao.dao_menu.CategoryRepository;
import com.cafe.crm.dao.dao_menu.MenuRepository;
import com.cafe.crm.dao.dao_menu.ProductRepository;
import com.cafe.crm.models.Menu.Category;
import com.cafe.crm.models.Menu.Menu;
import com.cafe.crm.models.Menu.Product;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Role;
import com.cafe.crm.models.worker.Worker;
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
	private WorkerRepository workerRepository;

	@Autowired
	private BossRepository bossRepository;

	@Autowired
	private ManagerRepository managerRepository;


	public void init() {

		Role roleBoss = new Role();
		roleBoss.setName("BOSS");
		roleRepository.saveAndFlush(roleBoss);

		Role roleUser = new Role();
		roleUser.setName("MANAGER");
		roleRepository.saveAndFlush(roleUser);
        Set<Shift> test = new HashSet<>();
        Set<Shift> test2 = new HashSet<>();

		Boss admin = new Boss();
		admin.setLogin("boss");
		admin.setPassword("boss");
		admin.setFirstName("Martin");
		admin.setLastName("Jons");
		admin.setSalary(200L);
		admin.setPosition("Владелец");
		admin.setCountShift(2L);
		admin.setShiftSalary(1L);
		admin.setAllShifts(test);


		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(roleBoss);
		admin.setRoles(adminRoles);

		Worker worker=new Worker();
		worker.setFirstName("Max");
		worker.setLastName("Smith");
		worker.setPosition("Кальянщик");
		worker.setShiftSalary(10L);
		worker.setCountShift(2L);
		worker.setSalary(20L);

		workerRepository.saveAndFlush(worker);
		workerRepository.saveAndFlush(admin);
		bossRepository.saveAndFlush(admin);

		Manager manager = new Manager();
		manager.setLogin("manager");
		manager.setPassword("manager");
		manager.setFirstName("Anna");
		manager.setLastName("Jons");
		manager.setSalary(100L);
		manager.setPosition("Управляющий");
		manager.setCountShift(2L);
		manager.setShiftSalary(1L);
		manager.setAllShifts(test2);


		Set<Role> userRoles = new HashSet<>();
		userRoles.add(roleUser);
		manager.setRoles(userRoles);

		workerRepository.saveAndFlush(manager);
		managerRepository.saveAndFlush(manager);

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
