package com.cafe.crm.models.client;

import com.cafe.crm.models.card.Card;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "calculations")
public class Calculate {
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 30)
	private String description;

	private String descriptionCheck;

	private boolean state = true;

	private Double spend = 0.0;
	@NotNull
	private Long discount = 0L;
	@NotNull
	private Long calculateNumber = 0L;

	private Double allPrice = 0.0;

	private LocalTime passedTime = LocalTime.of(0,0,0);

	private Double priceMenu = 0.0;

	private Double priceTime = 0.0;

	private Double payWithCard = 0.0;

	private Long clientId;

	private Long totalNumber = 0L;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionCheck() {
		return descriptionCheck;
	}

	public void setDescriptionCheck(String descriptionCheck) {
		this.descriptionCheck = descriptionCheck;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Double getSpend() {
		return spend;
	}

	public void setSpend(Double spend) {
		this.spend = spend;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getCalculateNumber() {
		return calculateNumber;
	}

	public void setCalculateNumber(Long calculateNumber) {
		this.calculateNumber = calculateNumber;
	}

	public Double getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}

	public LocalTime getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(LocalTime passedTime) {
		this.passedTime = passedTime;
	}

	public Double getPriceMenu() {
		return priceMenu;
	}

	public void setPriceMenu(Double priceMenu) {
		this.priceMenu = priceMenu;
	}

	public Double getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(Double priceTime) {
		this.priceTime = priceTime;
	}

	public Double getPayWithCard() {
		return payWithCard;
	}

	public void setPayWithCard(Double payWithCard) {
		this.payWithCard = payWithCard;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Set<Client> getClient() {
		return client;
	}

	public void setClient(Set<Client> client) {
		this.client = client;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
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
}
