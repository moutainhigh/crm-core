package com.cafe.crm.models.client;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "calculations")
public class Calculate {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	private String description = "Нет описания";

	private boolean state = true;

	private Double spend = 0.0;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Client.class)
	@JoinTable(name = "clients_calculations",
			joinColumns = {@JoinColumn(name = "calculate_id")},
			inverseJoinColumns = {@JoinColumn(name = "client_id")})
	private Set<Client> client;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Board.class)
	private Board board;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Card.class)
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

	public void setSpend(Double spend) {
		this.spend = spend;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setCard(Card card) {
		this.card = card;
	}

	public Double getSpend() {
		return spend;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
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

	public Card getCard() {

		return card;
	}
}
