package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {
}
