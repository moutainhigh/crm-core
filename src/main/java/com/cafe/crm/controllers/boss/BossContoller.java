package com.cafe.crm.controllers.boss;

import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.dao.dao_menu.CategoryRepository;
import com.cafe.crm.dao.dao_menu.MenuRepository;
import com.cafe.crm.dao.dao_menu.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by User on 20.04.2017.
 */
@Controller
public class BossContoller {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = {"/boss/create-menu"}, method = RequestMethod.GET)
    public ModelAndView createMenu(ModelMap modelMap) {

        modelMap.addAttribute("menu",menuRepository.getOne(1l));
        return new ModelAndView("bossview");
    }


    @RequestMapping(value = {"/boss/edit"}, method = RequestMethod.GET)
    public ModelAndView getLoginPage(ModelMap modelMap) {
        modelMap.addAttribute("menu",menuRepository.getOne(1l));
        return new ModelAndView("bossview");
    }

    @RequestMapping(value = {"/boss"}, method = RequestMethod.GET)
    public ModelAndView getAdminPage(ModelMap modelMap) {
        modelMap.addAttribute("menu",menuRepository.getOne(1l));
        modelMap.addAttribute("categories",categoryRepository.findAll());
        modelMap.addAttribute("products",productRepository.findAll());
        return new ModelAndView("bossview");
    }

    @RequestMapping(value = {"/boss"}, method = RequestMethod.POST)
    public ModelAndView getAdminPagePost(ModelMap modelMap) {
        modelMap.addAttribute("menu",menuRepository.getOne(1l));
        return new ModelAndView("index");
    }



}
