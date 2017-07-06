package com.cafe.crm.services.impl.board;

import com.cafe.crm.models.client.Board;
import com.cafe.crm.repositories.board.BoardRepository;
import com.cafe.crm.services.interfaces.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public void save(Board board) {
		boardRepository.saveAndFlush(board);
	}

	@Override
	public void delete(Board board) {
		boardRepository.delete(board);
	}

	@Override
	public List<Board> getAll() {
		return boardRepository.findAll();
	}

	@Override
	public Board getOne(Long id) {
		return boardRepository.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		boardRepository.delete(id);
	}

}
