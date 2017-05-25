package com.cafe.crm.initMet;

import com.cafe.crm.service_impl.client.BoardService;
import com.cafe.crm.service_impl.client.CardService;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class InitClient {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CardService cardService;

    public void init() {

        Board board = new Board();
        board.setName("Стол1");
        boardService.add(board);

        Board board1 = new Board();
        board1.setName("Стол2");
        boardService.add(board1);

        Board board2 = new Board();
        board2.setName("Стол3");
        boardService.add(board2);

        Board board3 = new Board();
        board3.setName("Стол4");
        boardService.add(board3);

        Card card = new Card();
        card.setName("Данил Джавер");
        card.setBalance(5000.0);
        card.setDiscount((long)10);
        card.setPhoto("https://pp.userapi.com/c636325/v636325810/41955/hBIdPv42Q38.jpg");
        card.setVisitDate(LocalDate.now());
        cardService.add(card);

        Card card1 = new Card();
        card1.setName("Кто-то Джавер");
        card1.setBalance(0.0);
        card1.setDiscount((long)15);
        card1.setPhoto("http://usiter.com/uploads/20111118/zhivotnie+koshki+kartinka+s+malenkim+kotyonkom+35121656913.jpg");
        card1.setVisitDate(LocalDate.now());
        cardService.add(card1);

    }
}
