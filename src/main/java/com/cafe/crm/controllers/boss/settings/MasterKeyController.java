package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.exceptions.user.UserDataException;
import com.cafe.crm.services.interfaces.property.SystemPropertyService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/boss/settings/masterKey")
public class MasterKeyController {

	private final SystemPropertyService propertyService;
	private final UserService userService;

	@Autowired
	public MasterKeyController(SystemPropertyService propertyService, UserService userService) {
		this.propertyService = propertyService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showMasterKeySettingsPage() {
		return "settingPages/masterKeySettingsPage";
	}

	@RequestMapping(value = "/addMasterKey", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addMasterKey(@RequestParam(name = "old") String oldPassword,
	                                   @RequestParam(name = "new") String newMasterKey,
	                                   Principal principal) {
		if (userService.isValidPassword(principal.getName(), oldPassword)) {
			propertyService.saveMasterKey(newMasterKey);
			return ResponseEntity.ok("Мастер ключ успешно добавлен!");
		} else {
			throw new UserDataException("Не получилось сохранить новый мастер - ключ!");
		}
	}

	@ExceptionHandler(value = UserDataException.class)
	public ResponseEntity<?> handleUserUpdateException(UserDataException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
