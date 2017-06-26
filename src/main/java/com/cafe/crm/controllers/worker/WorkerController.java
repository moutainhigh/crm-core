package com.cafe.crm.controllers.worker;

import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.service_abstract.worker.BossService;
import com.cafe.crm.service_abstract.worker.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class WorkerController {

	@Autowired
	private BossService bossService;

	@Autowired
	private ManagerService managerService;

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String redirectToLoginPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}


	@RequestMapping(path = {"/manager/changePassword", "/boss/changePassword"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changePassword(@RequestParam(name = "old") String oldPassword,
											@RequestParam(name = "new") String newPassword,
											@RequestParam(name = "secondNew") String secondNewPassword, Authentication auth, HttpServletRequest request) {
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String password = userDetails.getPassword();
		String email = userDetails.getUsername();
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		if (newPassword.equals(secondNewPassword) && oldPassword.equals(password)) {
			roles.forEach(role -> {
				if (role.getAuthority().equals("BOSS")) {
					Boss boss = bossService.getUserByEmail(email);
					boss.setPassword(newPassword);
					bossService.save(boss);

				} else if (role.getAuthority().equals("MANAGER")) {
					Manager manager = managerService.getUserByEmail(email);
					manager.setPassword(newPassword);
					managerService.save(manager);
				}
			});
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
}
