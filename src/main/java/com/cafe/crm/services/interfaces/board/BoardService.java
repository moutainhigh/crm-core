package com.cafe.crm.services.interfaces.board;

import com.cafe.crm.models.client.Board;

import java.util.List;

public interface BoardService {

	void save(Board board);

	void delete(Board board);

	List<Board> getAll();

	Board getOne(Long id);

	void deleteById(Long id);

}
