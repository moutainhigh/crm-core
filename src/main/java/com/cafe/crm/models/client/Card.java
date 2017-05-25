package com.cafe.crm.models.client;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	private String token;

	private String name;

	private String photo;

	private Long discount = 0L;

	private Double balance = 0.0;

	private Double spend = 0.0;

	private LocalDate visitDate;


	public Card() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (id != null ? !id.equals(card.id) : card.id != null) return false;
		return name != null ? name.equals(card.name) : card.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setSpend(Double spend) {
		this.spend = spend;
	}

	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public String getName() {
		return name;
	}

	public String getPhoto() {
		return photo;
	}

	public Long getDiscount() {
		return discount;
	}

	public Double getBalance() {
		return balance;
	}

	public Double getSpend() {
		return spend;
	}

	public LocalDate getVisitDate() {
		return visitDate;
	}

}
