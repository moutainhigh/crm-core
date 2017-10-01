package com.cafe.crm.controllers.boss.registration;

import com.cafe.crm.dto.registration.UserRegistrationForm;
import com.cafe.crm.exceptions.user.UserDataException;
import com.cafe.crm.models.company.Company;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private final UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showRegistrationPage() {
		return "registration/registrationPage";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity registerNewCompanyAndUser(@ModelAttribute @Valid UserRegistrationForm user) {
		userService.registerUser(user);
		return ResponseEntity.ok("Вы были успешно зарегистрированы на портале!");
	}

	@ExceptionHandler(value = UserDataException.class)
	public ResponseEntity<?> handleUserUpdateException(UserDataException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
