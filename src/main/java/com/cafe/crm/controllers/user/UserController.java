package com.cafe.crm.controllers.user;

import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.service_abstract.worker_service.BossService;
import com.cafe.crm.service_abstract.worker_service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private BossService bossService;

	@Autowired
	private ManagerService managerService;

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String log() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public void login() {
	}

	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	public void logout() {
	}

	@RequestMapping(value = "/manager/changePassword", method = RequestMethod.POST)
	public String managerPasword(@RequestParam(name = "password") String password, @RequestParam(name = "secondPassword") String secondPassword) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();

			if (password.equals(secondPassword)) {
				Manager manager = managerService.getUserByLogin(userDetails.getUsername());

				manager.setPassword(password);
				managerService.save(manager);
			}
		}
		return "redirect:/manager/shift/edit";
	}

	@RequestMapping(value = "/boss/changePassword", method = RequestMethod.POST)
	public String bossPassword(@RequestParam(name = "password") String password, @RequestParam(name = "secondPassword") String secondPassword) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();

			if (password.equals(secondPassword)) {
				Boss boss = bossService.getUserByLogin(userDetails.getUsername());

				boss.setPassword(password);
				bossService.save(boss);
			}
		}
		return "redirect:/boss/menu";
	}
}
