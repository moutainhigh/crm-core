package com.cafe.crm.service_impl.boardService;

import com.cafe.crm.dao.board.BoardRepository;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.service_abstract.boardService.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public void save(Board board) {
		boardRepository.saveAndFlush(board);
	}

	public void delete(Board board) {
		boardRepository.delete(board);
	}

	public List<Board> getAll() {
		return boardRepository.findAll();
	}

	public Board getOne(Long id) {
		return boardRepository.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		boardRepository.delete(id);
	}



}
