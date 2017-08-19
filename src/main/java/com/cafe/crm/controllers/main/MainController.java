package com.cafe.crm.controllers.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String redirectToLoginPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showHomePage() {
		return "home/home";
	}
}
