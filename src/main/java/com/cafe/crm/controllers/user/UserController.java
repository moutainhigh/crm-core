package com.cafe.crm.controllers.user;

import com.cafe.crm.models.Role;
import com.cafe.crm.models.User;
import com.cafe.crm.service_abstract.user_service.RoleService;
import com.cafe.crm.service_abstract.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;


	@ModelAttribute(value = "user")
	public User newUser() {
		return new User();
	}

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String log() {return "redirect:/login";}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public void login() {
	}

	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	public void logout() {
	}

	@RequestMapping(value = {"/login/createUser"}, method = RequestMethod.POST)
	public String createUser(User user, @RequestParam(value = "secondPassword") String password) {
		if (user.getPassword().equals(password)) {
			roleService.getRoleByName("MANAGER");
			Set<Role> adminRoles = new HashSet<>();
			adminRoles.add(roleService.getRoleByName("MANAGER"));
			user.setRoles(adminRoles);
			userService.save(user);
		}

		return "redirect:/login";
	}

	@RequestMapping(value = "/manager/changePassword", method = RequestMethod.POST)
	public String showOverview(@RequestParam(name = "password") String password, @RequestParam(name = "secondPassword") String secondPassword) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();

			if (password.equals(secondPassword)) {
				User user = userService.getUserByLogin(userDetails.getUsername());
				user.setPassword(password);
				userService.save(user);
			}
		}
		return "redirect:/manager/shift/edit";
	}

}
