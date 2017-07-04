package com.cafe.crm.initMet;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.service_abstract.boardService.BoardService;
import com.cafe.crm.service_abstract.cardService.CardService;
import com.cafe.crm.service_abstract.clientService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class InitClient {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

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
        card.setBalance(5000L);
        card.setDiscount(10L);
        card.setSurname("Питерский");



        card.setPhoneNumber("82222222222");
        card.setEmail("cafe.com.cafe.crm.test@gmail.com");
        cardService.save(card);

        Card card1 = new Card();
        card1.setName("Кот");
        card1.setSurname("Барсик");
        card1.setBalance(0L);
        card1.setDiscount(15L);


        card1.setPhoneNumber("81111111111");
        cardService.save(card1);

        Client client1 = new Client();
        client1.setEmail("cafe.crm.test@gmail.com");
        clientService.save(client1);

    }
}
