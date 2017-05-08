package com.cafe.crm.models.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "calculations")
public class Calculate {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	private String description = "Нет описания";

	private String menu;

	private Double allPrice = 0.0;

	private boolean state = true;

	private boolean statePanel = false;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Calculate calculate = (Calculate) o;

		if (id != null ? !id.equals(calculate.id) : calculate.id != null) return false;
		return description != null ? description.equals(calculate.description) : calculate.description == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
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

	public boolean isStatePanel() {
		return statePanel;
	}

	public Set<Client> getClient() {
		return client;
	}

	public Board getBoard() {
		return board;
	}

	public Card getCard() {
		return card;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setStatePanel(boolean statePanel) {
		this.statePanel = statePanel;
	}

	public void setClient(Set<Client> client) {
		this.client = client;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
