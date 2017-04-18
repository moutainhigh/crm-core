package com.cafe.crm.initMet;


import com.cafe.crm.dao.*;
import com.cafe.crm.dao.dao_menu.CategoryRepository;
import com.cafe.crm.dao.dao_menu.MenuRepository;
import com.cafe.crm.dao.dao_menu.ProductRepository;
import com.cafe.crm.models.Menu.Category;
import com.cafe.crm.models.Menu.Menu;
import com.cafe.crm.models.Menu.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitMenu {
    /*cccccccccccccccccccccccccccccc
        @Autowired
        private RoleDao roleDao;

        @Autowired
        private UserDao userDao;*/
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
    /*    Role roleAdmin = new Role();
        roleAdmin.setName("BOSS");
        roleDao.saveAndFlush(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("MANAGER");
        roleDao.saveAndFlush(roleUser);

        User admin = new User();
        admin.setLogin("admin1");
        admin.setPassword("admin1");

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleUser);
        adminRoles.add(roleAdmin);
        admin.setRoles(adminRoles);

        userDao.saveAndFlush(admin);

        User user = new User();
        user.setLogin("manager1");
        user.setPassword("manager1");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);

        userDao.saveAndFlush(user);*/




       /* userRepository.saveAndFlush(new User("sasha","logn","123",12));
        userRepository.saveAndFlush(new User("ree","logn","123",12));
        userRepository.saveAndFlush(new User("4444","logn","123",12));

        roleRepository.saveAndFlush(new Role("BOSS"));
        roleRepository.saveAndFlush(new Role("MANAGER"));
*/


       Set<Product> MYproducts = new HashSet<>();


       Product pro1 = new Product("salat","taste",100);
       Product pro2 = new Product("cofe","whithmilk",5);
       Product pro3 = new Product("fish","big",400);

        productRepostitory.saveAndFlush(pro1);
        productRepostitory.saveAndFlush(pro2);
        productRepostitory.saveAndFlush(pro3);

       MYproducts.add(pro1);
       MYproducts.add(pro2);
       MYproducts.add(pro3);


        Category category2 = new Category("sushi");
        Category category1 = new Category("drinks");
        Category category3 = new Category("salats");


        category1.setProducts(MYproducts);
        category2.setProducts(MYproducts);
        category3.setProducts(MYproducts);

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
