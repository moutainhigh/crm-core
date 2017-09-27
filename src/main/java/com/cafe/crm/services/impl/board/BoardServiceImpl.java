package com.cafe.crm.services.impl.board;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.repositories.board.BoardRepository;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public BoardServiceImpl(BoardRepository boardRepository, CompanyService companyService) {
		this.boardRepository = boardRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Board board){
		board.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void save(Board board) {
		setCompanyId(board);
		boardRepository.saveAndFlush(board);
	}

	@Override
	public void delete(Board board) {
		boardRepository.delete(board);
	}

	@Override
	public List<Board> getAll() {
		return boardRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public Board getOne(Long id) {
		return boardRepository.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		boardRepository.delete(id);
	}

	@Override
	public List<Board> getAllOpen() {
		return boardRepository.getAllOpen(companyIdCache.getCompanyId());
	}
}
