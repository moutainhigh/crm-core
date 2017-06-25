package com.cafe.crm.service_impl.menu;

import com.cafe.crm.dao.menu.MenuRepository;
import com.cafe.crm.models.Menu.Menu;
import com.cafe.crm.service_abstract.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;


    @Override
    public Menu getOne(Long id) {
        return menuRepository.findOne(id);
    }
}
