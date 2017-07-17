package com.cafe.crm.configs.init;

import com.cafe.crm.models.menu.Menu;
import com.cafe.crm.repositories.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitMenu {

    @Autowired
    private MenuRepository repository;

    @PostConstruct
    public void init() {
        Menu menu = new Menu("Обеденное");

        repository.saveAndFlush(menu);
    }
}
