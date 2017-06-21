package com.cafe.crm.service_abstract.boardService;

import com.cafe.crm.models.client.Board;

import java.util.List;

public interface BoardService {
	 void save(Board board);
	 void delete(Board board);
	 List<Board> getAll();
	 Board getOne(Long id);
}
