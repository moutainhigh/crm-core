package com.cafe.crm.repositories.board;

import com.cafe.crm.models.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("SELECT c FROM Board c WHERE c.isOpen = true")
	List<Board> getAllOpen();

}
