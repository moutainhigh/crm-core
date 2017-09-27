package com.cafe.crm.controllers.boss.settings;


import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class ChangePasswordController {

	private final UserService userService;
	private final SessionRegistry sessionRegistry;

	@Autowired
	public ChangePasswordController(UserService userService, SessionRegistry sessionRegistry) {
		this.userService = userService;
		this.sessionRegistry = sessionRegistry;
	}

	@RequestMapping(value = "/boss/settings/change-password", method = RequestMethod.GET)
	public String discountSetting() {
		return "settingPages/changePasswordPage";
	}

	@RequestMapping(path = {"/manager/changePassword", "/boss/changePassword"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changePassword(@RequestParam(name = "old") String oldPassword,
											@RequestParam(name = "new") String newPassword,
											@RequestParam(name = "secondNew") String repeatedPassword, Principal principal) {
		if (!newPassword.equals(repeatedPassword)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		String username = principal.getName();
		userService.changePassword(username, oldPassword, newPassword, repeatedPassword);
		for (Object elem : sessionRegistry.getAllPrincipals()) {
			sessionRegistry.getAllSessions(elem, false).forEach(org.springframework.security.core.session.SessionInformation::expireNow);
		}
		return ResponseEntity.ok("Пароль успешно изменен!");
	}
}
