package com.cafe.crm.controllers.rest;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.services.interfaces.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableRestController {

	@Autowired
	BoardService boardService;

	@RequestMapping(value = "/manager/rest/Table", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getListOpenTables() {
		return boardService.getAllOpen();
	}
}
