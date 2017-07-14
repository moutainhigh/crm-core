package com.cafe.crm.models.shift;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.worker.Worker;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Shift")
public class Shift {

	@Column(name = "isOpen") // shift is open ?
			Boolean isOpen;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "dateShift")
	private LocalDate dateShift;
	@Column(name = "checkValue")
	private Integer checkValue;// after change on set<>
	@OneToMany
	private Set<Calculate> allCalculate;

	@OneToMany
	private Set<Client> clients;

	@Column(name = "cashBox")
	private Double cashBox = 0D;

	@Column(name = "profit")
	private Double profit = 0D;

	@Column(name = "bankCashBox")
	private Double bankCashBox = 0D;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Worker.class)
	@JoinTable(name = "permissions_allShifts",
			joinColumns = {@JoinColumn(name = "shift_id")},
			inverseJoinColumns = {@JoinColumn(name = "worker_id")})
	private Set<Worker> users;

	public Shift(LocalDate dateShift, Set<Worker> users, Double bankCashBox) {
		this.dateShift = dateShift;
		this.users = users;
		this.bankCashBox = bankCashBox;
	}

	public Shift() {
	}

	public Double getBankCashBox() {
		return bankCashBox;
	}

	public void setBankCashBox(Double bankCashBox) {
		this.bankCashBox = bankCashBox;
	}

	public Set<Worker> getUsers() {
		return users;
	}

	public void setUsers(Set<Worker> users) {
		this.users = users;
	}

	public String getUsersNames() {   // return only names of workers of shift

		String names = "";
		for (Worker worker : users) {
			names += worker.getFirstName() + " ";

		}
		return names;
	}

	public Double getCashBox() {
		return cashBox;
	}

	public void setCashBox(Double cashBox) {
		this.cashBox = cashBox;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Set<Calculate> getAllCalculate() {
		return allCalculate;
	}

	public void setAllCalculate(Set<Calculate> allCalculate) {
		this.allCalculate = allCalculate;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateShift() {
		return dateShift;
	}

	public void setDateShift(LocalDate dateShift) {
		this.dateShift = dateShift;
	}

	public Integer getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(Integer checkValue) {
		this.checkValue = checkValue;
	}

	public Boolean getOpen() {
		return isOpen;
	}

	public void setOpen(Boolean open) {
		isOpen = open;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Shift shift = (Shift) o;

		if (id != null ? !id.equals(shift.id) : shift.id != null) return false;
		if (dateShift != null ? !dateShift.equals(shift.dateShift) : shift.dateShift != null) return false;
		if (checkValue != null ? !checkValue.equals(shift.checkValue) : shift.checkValue != null) return false;
		return isOpen != null ? isOpen.equals(shift.isOpen) : shift.isOpen == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (dateShift != null ? dateShift.hashCode() : 0);
		result = 31 * result + (checkValue != null ? checkValue.hashCode() : 0);
		result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Shift{" +
				"id=" + id +
				", dateShift=" + dateShift +
				", isOpen=" + isOpen +
				", users=" + users +
				'}';
	}
}
