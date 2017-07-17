package com.cafe.crm.controllers.worker;

import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.services.interfaces.worker.BossService;
import com.cafe.crm.services.interfaces.worker.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
public class WorkerController {

	@Autowired
	private BossService bossService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SessionRegistry sessionRegistry;

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String redirectToLoginPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}


	@RequestMapping(value = "/boss/settings/change-password", method = RequestMethod.GET)
	public ModelAndView discountSetting() {
		ModelAndView modelAndView = new ModelAndView("settingPages/changePasswordPage");
		return modelAndView;
	}

	@RequestMapping(path = {"/manager/changePassword", "/boss/changePassword"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changePassword(@RequestParam(name = "old") String oldPassword,
	                                        @RequestParam(name = "new") String newPassword,
	                                        @RequestParam(name = "secondNew") String secondNewPassword, Authentication auth, HttpServletRequest request) {
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		HttpSession session = request.getSession();
		String password = userDetails.getPassword();
		String email = userDetails.getUsername();
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		if (newPassword.equals(secondNewPassword) && passwordEncoder.matches(oldPassword, password)) {
			roles.forEach(role -> {
				if (role.getAuthority().equals("BOSS")) {
					Boss boss = bossService.getUserByEmail(email);
					String hashedPassword = passwordEncoder.encode(newPassword);
					boss.setPassword(hashedPassword);
					bossService.save(boss);

				} else if (role.getAuthority().equals("MANAGER")) {
					Manager manager = managerService.getUserByEmail(email);
					String hashedPassword = passwordEncoder.encode(newPassword);
					manager.setPassword(hashedPassword);
					managerService.save(manager);
				}
			});

			for (Object principal : sessionRegistry.getAllPrincipals()) {
				sessionRegistry.getAllSessions(principal, false).forEach(org.springframework.security.core.session.SessionInformation::expireNow);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
}
