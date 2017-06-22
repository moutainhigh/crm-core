package com.cafe.crm.dao.board;

import com.cafe.crm.models.client.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {


}
