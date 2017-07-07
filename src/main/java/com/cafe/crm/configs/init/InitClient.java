package com.cafe.crm.configs.init;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitClient {

	@Autowired
	private BoardService boardService;

	@Autowired
	private CardService cardService;

	@PostConstruct
	public void init() {

		Board board = new Board();
		board.setName("Стол1");
		boardService.save(board);

		Board board1 = new Board();
		board1.setName("Стол2");
		boardService.save(board1);

		Board board2 = new Board();
		board2.setName("Стол3");
		boardService.save(board2);

		Board board3 = new Board();
		board3.setName("Стол4");
		boardService.save(board3);

		Card card = new Card();
		card.setName("Данила");
		card.setBalance(5000D);
		card.setDiscount(10L);
		card.setSurname("Питерский");
		card.setPhoneNumber("82222222222");
		card.setEmail("cafe.crm.test@gmail.com");
		cardService.save(card);

		Card card1 = new Card();
		card1.setName("Кот");
		card1.setSurname("Барсик");
		card1.setBalance(0D);
		card1.setDiscount(15L);
		card1.setPhoneNumber("81111111111");
		cardService.save(card1);

	}
}
