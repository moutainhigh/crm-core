package com.cafe.crm.controllers.user;

import com.cafe.crm.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by User on 24.04.2017.
 */
@Controller
public class UserController {


    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public void login() {


    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public void logout() {
    }

}
