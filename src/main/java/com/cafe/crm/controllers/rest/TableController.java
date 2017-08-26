package com.cafe.crm.controllers.rest;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableController {

	private final BoardService boardService;

	@Autowired
	public TableController(BoardService boardService) {
		this.boardService = boardService;
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
}
