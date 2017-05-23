package com.cafe.crm.models.client;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "clients")
public class Client {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	private String description = "Нет описания";

	private LocalTime timeStart = LocalTime.now().withSecond(0);

	private Long totalNumber = 0L;

	private Long discount = 0L;

	private Long calculateNumber = 0L;

	private Double allPrice = 0.0;

	private LocalTime passedTime = LocalTime.of(0,0);

	private Double priceMenu = 0.0;

	private Double priceTime = 0.0;

	private Long round = 0L;

	private Double payWithCard;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Calculate.class)
	@JoinTable(name = "clients_calculations",
			joinColumns = {@JoinColumn(name = "client_id")},
			inverseJoinColumns = {@JoinColumn(name = "calculate_id")})
	private Calculate calculate;

	public Client() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Client client = (Client) o;

		if (id != null ? !id.equals(client.id) : client.id != null) return false;
		return description != null ? description.equals(client.description) : client.description == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPayWithCard(Double payWithCard) {
		this.payWithCard = payWithCard;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public void setCalculateNumber(Long calculateNumber) {
		this.calculateNumber = calculateNumber;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}

	public void setPassedTime(LocalTime passedTime) {
		this.passedTime = passedTime;
	}

	public void setPriceMenu(Double priceMenu) {
		this.priceMenu = priceMenu;
	}

	public void setPriceTime(Double priceTime) {
		this.priceTime = priceTime;
	}

	public void setRound(Long round) {
		this.round = round;
	}

	public void setCalculate(Calculate calculate) {
		this.calculate = calculate;
	}

	public Double getPayWithCard() {
		return payWithCard;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public LocalTime getTimeStart() {
		return timeStart;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public Long getDiscount() {
		return discount;
	}

	public Long getCalculateNumber() {
		return calculateNumber;
	}

	public Double getAllPrice() {
		return allPrice;
	}

	public LocalTime getPassedTime() {
		return passedTime;
	}

	public Double getPriceMenu() {
		return priceMenu;
	}

	public Double getPriceTime() {
		return priceTime;
	}

	public Long getRound() {
		return round;
	}

	public Calculate getCalculate() {
		return calculate;
	}
}
