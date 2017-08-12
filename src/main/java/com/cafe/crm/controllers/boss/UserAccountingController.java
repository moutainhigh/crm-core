package com.cafe.crm.controllers.boss;

import com.cafe.crm.exceptions.user.PositionDataException;
import com.cafe.crm.exceptions.user.UserDataException;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.Role;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.services.interfaces.role.RoleService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class UserAccountingController {

	private final UserService userService;
	private final PositionService positionService;
	private final RoleService roleService;
	private final SessionRegistry sessionRegistry;

	@Autowired
	public UserAccountingController(UserService userService, PositionService positionService, RoleService roleService, SessionRegistry sessionRegistry) {
		this.userService = userService;
		this.positionService = positionService;
		this.roleService = roleService;
		this.sessionRegistry = sessionRegistry;
	}

	@RequestMapping(value = {"/boss/user/accounting"}, method = RequestMethod.GET)
	public String showAllUser(Model model) {
		List<User> allUsers = userService.findAll();
		Map<Position, List<User>> usersByPositions = userService.findAndSortUserByPosition();
		List<Position> allPositions = positionService.findAll();
		List<Role> allRoles = roleService.findAll();

		model.addAttribute("allUsers", allUsers);
		model.addAttribute("usersByPositions", usersByPositions);
		model.addAttribute("allPositions", allPositions);
		model.addAttribute("allRoles", allRoles);

		return "userAccounting/userAccounting";
	}

	@RequestMapping(value = {"/boss/user/add"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
									 @RequestParam(name = "positionsIds") String positionsIds,
									 @RequestParam(name = "rolesIds") String rolesIds) {
		if (bindingResult.hasErrors()) {
			String fieldError = bindingResult.getFieldError().getDefaultMessage();
			throw new UserDataException("Не удалось добавить пользователя!\n" + fieldError);
		}
		userService.save(user, positionsIds, rolesIds);
		return ResponseEntity.ok("Пользователь успешно обновлен!");
	}

	@RequestMapping(value = {"/boss/user/edit"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> editUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
									  @RequestParam(name = "oldPassword") String oldPassword,
									  @RequestParam(name = "newPassword") String newPassword,
									  @RequestParam(name = "repeatedPassword") String repeatedPassword,
									  @RequestParam(name = "positionsIds") String positionsIds,
									  @RequestParam(name = "rolesIds") String rolesIds) {
		if (bindingResult.hasErrors()) {
			String fieldError = bindingResult.getFieldError().getDefaultMessage();
			throw new UserDataException("Не удалось изменить данные пользователя!\n" + fieldError);
		}
		userService.update(user, oldPassword, newPassword, repeatedPassword, positionsIds, rolesIds);
		return ResponseEntity.ok("Пользователь успешно обновлен!");
	}

	@RequestMapping(value = {"/boss/user/delete/{id}"}, method = RequestMethod.POST)
	public String deleteUser(@PathVariable("id") Long id) throws IOException {
		userService.delete(id);
		return "redirect:/boss/user/accounting";
	}

	@RequestMapping(path = {"/manager/user/changePassword", "/boss/user/changePassword"}, method = RequestMethod.POST)
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

	@RequestMapping(value = {"/boss/user/position/add"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addPosition(Position position) throws IOException {
		positionService.save(position);
		return ResponseEntity.ok("Должность успешно добавлена!");
	}

	@RequestMapping(value = {"/boss/user/position/edit"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> editPosition(Position position) throws IOException {
		positionService.update(position);
		return ResponseEntity.ok("Должность успешно обновлена!");
	}

	@RequestMapping(value = {"/boss/user/position/delete/{id}"}, method = RequestMethod.GET)
	public String deletePosition(@PathVariable("id") Long id) throws IOException {
		positionService.delete(id);
		return "redirect:/boss/user/accounting";
	}

	@ExceptionHandler(value = UserDataException.class)
	public ResponseEntity<?> handleUserUpdateException(UserDataException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(value = PositionDataException.class)
	public ResponseEntity<?> handleUserUpdateException(PositionDataException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
