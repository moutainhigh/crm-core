package com.cafe.crm.models.client;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	private Double allPrice = 0.0;

	private Double priceMenu = 0.0;

	private Double priceTime = 0.0;

	private Long round = (long)0;

	private String description = "Нет описания";

	private LocalTime timeStart = LocalTime.now().withSecond(0);

	private Long number = (long)0;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Calculate.class)
	@JoinTable(name = "clients_calculate",
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

	public Long getId() {
		return id;
	}

	public Double getAllPrice() {
		return allPrice;
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

	public String getDescription() {
		return description;
	}

	public LocalTime getTimeStart() {
		return timeStart;
	}

	public Long getNumber() {
		return number;
	}

	public Calculate getCalculate() {
		return calculate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setCalculate(Calculate calculate) {
		this.calculate = calculate;
	}
}
