package com.cafe.crm.controllers.main;

import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class MainController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final SessionRegistry sessionRegistry;

	@Autowired
	public MainController(SessionRegistry sessionRegistry, UserService userService, PasswordEncoder passwordEncoder) {
		this.sessionRegistry = sessionRegistry;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String redirectToLoginPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}


	@RequestMapping(value = "/boss/settings/change-password", method = RequestMethod.GET)
	public String discountSetting() {
		return "settingPages/changePasswordPage";
	}

	@RequestMapping(path = {"/manager/changePassword", "/boss/changePassword"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changePassword(@RequestParam(name = "old") String oldPassword,
											@RequestParam(name = "new") String newPassword,
											@RequestParam(name = "secondNew") String secondNewPassword, Principal principal) {
		if (!newPassword.equals(secondNewPassword)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		String username = principal.getName();
		User user = userService.findByEmail(username);

		if (user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userService.save(user);
		}
		for (Object elem : sessionRegistry.getAllPrincipals()) {
			sessionRegistry.getAllSessions(elem, false).forEach(org.springframework.security.core.session.SessionInformation::expireNow);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
