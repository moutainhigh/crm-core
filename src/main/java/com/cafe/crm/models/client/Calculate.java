package com.cafe.crm.models.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="calculations")
public class Calculate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String description;

    private String menu;

    private Double allPrice;

    private boolean state = true;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinTable(name = "clients_calculations",
            joinColumns = {@JoinColumn(name = "calculate_id")},
            inverseJoinColumns = {@JoinColumn(name = "client_id")})
    private Set<Client> client;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Board.class)
    @JoinTable(name = "boards_calculations",
            joinColumns = {@JoinColumn(name = "calculate_id")},
            inverseJoinColumns = {@JoinColumn(name = "board_id")})
    private Board board;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Card.class)
    @JoinTable(name = "cards_calculations",
            joinColumns = {@JoinColumn(name = "calculate_id")},
            inverseJoinColumns = {@JoinColumn(name = "card_id")})
    private Card card;

    public Calculate() {
    }

    public Card getCard() {
        return card;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getMenu() {
        return menu;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public boolean isState() {
        return state;
    }

    public Set<Client> getClient() {
        return client;
    }

    public Board getBoard() {
        return board;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setClient(Set<Client> client) {
        this.client = client;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
