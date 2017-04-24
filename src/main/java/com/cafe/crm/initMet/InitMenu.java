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
    private ProductRepository productRepostitory;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired

    private UserRepository userRepository;

    public void init() {
        Role roleAdmin = new Role();
        roleAdmin.setName("BOSS");
        roleRepository.saveAndFlush(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("MANAGER");
        roleRepository.saveAndFlush(roleUser);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setName("Jim");

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleUser);
        adminRoles.add(roleAdmin);
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



       Set<Product> products = new HashSet<>();



        Set<Product> products1 = new HashSet<>();
        Set<Product> products2 = new HashSet<>();
        Set<Product> products3 = new HashSet<>();


        Product pro1 = new Product("Цезарь","taste",100);

        Product pro11 = new Product("Лёгкий","taste",100);
        Product pro111 = new Product("Летний","taste",100);

        Product pro2 = new Product("Кофе","test",5);
        Product pro22 = new Product("Кока-кола","test",5);
        Product pro222 = new Product("Кофе","test",5);

        Product pro3 = new Product("Фруктовый","big",400);
        Product pro33 = new Product("Терамису","big",400);
        Product pro333 = new Product("Шоколадный","big",400);

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

        productRepostitory.saveAndFlush(pro1);

        productRepostitory.saveAndFlush(pro11);
        productRepostitory.saveAndFlush(pro111);

        productRepostitory.saveAndFlush(pro2);
        productRepostitory.saveAndFlush(pro22);
        productRepostitory.saveAndFlush(pro222);

        productRepostitory.saveAndFlush(pro3);
        productRepostitory.saveAndFlush(pro33);
        productRepostitory.saveAndFlush(pro333);

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
