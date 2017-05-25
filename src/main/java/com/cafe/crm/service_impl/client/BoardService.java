package com.cafe.crm.service_impl.client;

import com.cafe.crm.dao.client.BoardRepository;
import com.cafe.crm.models.client.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public void add(Board board) {
        boardRepository.saveAndFlush(board);
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }

    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    public Board getOne(Long id) {
        return boardRepository.getOne(id);
    }

}
