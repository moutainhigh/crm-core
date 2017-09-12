package com.cafe.crm.configs.init;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitClient {

	private final CardService cardService;
	private final BoardService boardService;

	@Autowired
	public InitClient(BoardService boardService, CardService cardService) {
		this.boardService = boardService;
		this.cardService = cardService;
	}

	//@PostConstruct
	public void init() {

		Board board = new Board();
		board.setName("Белый диван");
		board.setIsOpen(true);
		boardService.save(board);

		Board board1 = new Board();
		board1.setName("Xbox бочки");
		board1.setIsOpen(true);
		boardService.save(board1);

		Board board2 = new Board();
		board2.setName("Бар");
		board2.setIsOpen(true);
		boardService.save(board2);

		Board board3 = new Board();
		board3.setName("Пожарка");
		board3.setIsOpen(true);
		boardService.save(board3);

		Board board4 = new Board();
		board4.setName("Постер");
		board4.setIsOpen(true);
		boardService.save(board4);

		Board board5 = new Board();
		board5.setName("Белый Xbox");
		board5.setIsOpen(true);
		boardService.save(board5);


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
