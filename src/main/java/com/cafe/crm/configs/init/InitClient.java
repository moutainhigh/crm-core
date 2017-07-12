package com.cafe.crm.configs.init;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.card.CardService;
import com.cafe.crm.services.interfaces.debt.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class InitClient {

	@Autowired
	private DebtService debtService;

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

		Debt debt = new Debt();
		debt.setDebtor("Саша");
		debt.setDebtAmount(2600D);
		debt.setDate(LocalDate.now());
		debtService.save(debt);

		Debt debt1 = new Debt();
		debt1.setDebtor("Юля");
		debt1.setDebtAmount(400D);
		debt1.setDate(LocalDate.now());
		debtService.save(debt1);

		Debt debt2 = new Debt();
		debt2.setDebtor("Барсик");
		debt2.setDebtAmount(900D);
		debt2.setDate(LocalDate.now());
		debtService.save(debt2);

		Debt debt3 = new Debt();
		debt3.setDebtor("Коля");
		debt3.setDebtAmount(1500D);
		debt3.setDate(LocalDate.of(2017,7,10));
		debtService.save(debt3);

		Debt debt4 = new Debt();
		debt4.setDebtor("Жора");
		debt4.setDebtAmount(900D);
		debt4.setDate(LocalDate.of(2017,8,15));
		debtService.save(debt4);
	}
}
