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

	public Calculate getCalculate() {
		return calculate;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setCalculate(Calculate calculate) {
		this.calculate = calculate;
	}
}
