package com.cafe.crm.controllers.worker;

import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.service_abstract.worker_service.BossService;
import com.cafe.crm.service_abstract.worker_service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WorkerController {

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
	@ResponseBody
	public ResponseEntity<?> managerPasword(@RequestParam(name = "old") String oldPassword,
											@RequestParam(name = "new") String newPassword,
											@RequestParam(name = "secondNew") String secondNewPassword) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			Manager manager = managerService.getUserByLogin(userDetails.getUsername());
			if (oldPassword.equals(manager.getPassword())) {
				if (newPassword.equals(secondNewPassword)) {
					manager = managerService.getUserByLogin(userDetails.getUsername());

					manager.setPassword(newPassword);
					managerService.save(manager);
				}
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неизвестная ошибка");
		}
	}


	@RequestMapping(value = "/boss/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> bossPassword(@RequestParam(name = "new") String newPassword,
										  @RequestParam(name = "old") String oldPassword,
										  @RequestParam(name = "secondNew") String secondNewPassword) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			Boss boss = bossService.getUserByLogin(userDetails.getUsername());
			if (oldPassword.equals(boss.getPassword())) {
				if (newPassword.equals(secondNewPassword)) {
					boss = bossService.getUserByLogin(userDetails.getUsername());

					boss.setPassword(newPassword);
					bossService.save(boss);
				}
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неизвестная ошибка");
		}
	}
}
