package com.cafe.crm.models.card;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {

	@Id
	@GeneratedValue
	private Long id;

	private String token;

	private String name;

	private String photo;

	private String email;

	private Long discount = 0L;

	private Long balance = 0L;

	private Long spend = 0L;

	private LocalDate visitDate;

	private File qrCode;

	private String keyNameQrCode;

	private String accessKey;

	private String secretKey;

	private String link;

	public Card() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getSpend() {
		return spend;
	}

	public void setSpend(Long spend) {
		this.spend = spend;
	}

	public LocalDate getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}

	public File getQrCode() {
		return qrCode;
	}

	public void setQrCode(File qrCode) {
		this.qrCode = qrCode;
	}

	public String getKeyNameQrCode() {
		return keyNameQrCode;
	}

	public void setKeyNameQrCode(String keyNameQrCode) {
		this.keyNameQrCode = keyNameQrCode;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (id != null ? !id.equals(card.id) : card.id != null) return false;
		return token != null ? token.equals(card.token) : card.token == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (token != null ? token.hashCode() : 0);
		return result;
	}
}
