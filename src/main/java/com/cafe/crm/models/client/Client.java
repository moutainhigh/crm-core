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

	private Double price;

	private String description;

	private LocalTime timeStart;

	private Long number;

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

	public Double getPrice() {
		return price;
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

	public void setPrice(Double price) {
		this.price = price;
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
