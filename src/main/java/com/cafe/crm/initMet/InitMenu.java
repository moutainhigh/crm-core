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


       Product pro1 = new Product("salat","taste",100);
       Product pro2 = new Product("cofe","whithoutsugar",5);
       Product pro3 = new Product("fish","big",400);

        productRepostitory.saveAndFlush(pro1);
        productRepostitory.saveAndFlush(pro2);
        productRepostitory.saveAndFlush(pro3);

       products.add(pro1);
       products.add(pro2);
       products.add(pro3);


        Category category2 = new Category("sushi");
        Category category1 = new Category("drinks");
        Category category3 = new Category("salats");


        category1.setProducts(products);
        category2.setProducts(products);
        category3.setProducts(products);

        category.saveAndFlush(category2);
        category.saveAndFlush(category1);
        category.saveAndFlush(category3);

        Menu menu = new Menu("Dinner");


        Set<Category> mymenu = new HashSet<>();

        mymenu.add(category1);
        mymenu.add(category2);
        mymenu.add(category3);

        menu.setCategories(mymenu);


        repository.saveAndFlush(menu);


    }
}
