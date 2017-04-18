package com.cafe.crm.controllers;

import com.cafe.crm.dao.dao_menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by User on 18.04.2017.
 */
@Controller("/")
public class test_controller {

@Autowired
 private MenuRepository repository;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView getAllUsers(ModelMap modelMap) {
        modelMap.addAttribute("List", repository.findAll());
        return new ModelAndView("index");
    }


}
