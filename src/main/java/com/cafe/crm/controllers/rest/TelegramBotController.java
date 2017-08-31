package com.cafe.crm.controllers.rest;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cafe.crm.utils.PatternMatcherHandler.matchEmail;
import static com.cafe.crm.utils.PatternMatcherHandler.matchPhone;

@RestController
public class TelegramBotController {

	private final BoardService boardService;
	private final UserService userService;

	@Autowired
	public TelegramBotController(BoardService boardService, UserService userService) {
		this.boardService = boardService;
		this.userService = userService;
	}

	@Autowired
	private CalculateService calculateService;

	@RequestMapping(value = "/manager/rest/Table", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getListOpenTables() {
		return boardService.getAllOpen();
	}

	@RequestMapping(value = "/manager/rest/clientsNumber", method = RequestMethod.GET)
	@ResponseBody
	public Integer getIdLastClient() {
		return calculateService.getAllOpen().stream().map(Calculate::getClient).mapToInt(List::size).sum();
	}

	@RequestMapping(value = "/authenticationTelegramBotUsers", method = RequestMethod.POST)
	@ResponseBody
	public User checkAuthCredentials(@RequestParam(value = "username") String username,
	                                        @RequestParam(value = "password") String password) {
		User userInDB;
		if (matchEmail(username)) {
			userInDB = userService.findByEmail(username);
		} else if (matchPhone(username)) {
			userInDB = userService.findByPhone(username);
		} else {
			return new User();
		}
		if (userService.isValidPassword(userInDB.getEmail(), password)) {
			return userInDB;
		} else {
			return new User();
		}
	}
}
