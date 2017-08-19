package com.cafe.crm.models.shift;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "shifts")
public class Shift {

	@Id
	@GeneratedValue
	private Long id;

	private boolean opened;

	@Column(name = "shift_date")
	private LocalDate shiftDate;

	@Column(name = "checkValue")
	private Integer checkValue;// after change on set<>

	@OneToMany
	private Set<Calculate> calculates;

	@OneToMany
	private Set<Client> clients;

	@Column(name = "cash_box")
	private double cashBox;

	private double profit;

	@Column(name = "bank_cash_box")
	private double bankCashBox;

	@ManyToMany(mappedBy = "shifts", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<User> users;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Debt> debts = new ArrayList<>();

	@OneToMany(mappedBy = "shift")
	private List<Cost> costs;

	// TODO: 26.07.2017 Подумать над размером
	private String comment;

	public Shift(LocalDate shiftDate, List<User> users, double bankCashBox) {
		this.shiftDate = shiftDate;
		this.users = users;
		this.bankCashBox = bankCashBox;
	}

	public Shift() {
	}

	public double getBankCashBox() {
		return bankCashBox;
	}

	public void setBankCashBox(double bankCashBox) {
		this.bankCashBox = bankCashBox;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUsersNames() {
		StringBuilder usersNames = new StringBuilder();
		for (User user : users) {
			usersNames.append(user.getFirstName()).append(" ");
		}
		return usersNames.toString();
	}

	public double getCashBox() {
		return cashBox;
	}

	public void setCashBox(double cashBox) {
		this.cashBox = cashBox;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public Set<Calculate> getCalculates() {
		return calculates;
	}

	public void setCalculates(Set<Calculate> calculates) {
		this.calculates = calculates;
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

	public LocalDate getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(LocalDate shiftDate) {
		this.shiftDate = shiftDate;
	}

	public Integer getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(Integer checkValue) {
		this.checkValue = checkValue;
	}

	public boolean isOpen() {
		return opened;
	}

	public void setOpen(boolean open) {
		opened = open;
	}

	public List<Debt> getDebts() {
		return debts;
	}

	public void addDebtToList(Debt debt) {
		this.debts.add(debt);
	}

	public List<Cost> getCosts() {
		return costs;
	}

	public void setCosts(List<Cost> costs) {
		this.costs = costs;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Shift shift = (Shift) o;

		if (opened != shift.opened) return false;
		if (id != null ? !id.equals(shift.id) : shift.id != null) return false;
		if (shiftDate != null ? !shiftDate.equals(shift.shiftDate) : shift.shiftDate != null) return false;
		return checkValue != null ? checkValue.equals(shift.checkValue) : shift.checkValue == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (opened ? 1 : 0);
		result = 31 * result + (shiftDate != null ? shiftDate.hashCode() : 0);
		result = 31 * result + (checkValue != null ? checkValue.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Shift{" +
				"id=" + id +
				", shiftDate=" + shiftDate +
				", opened=" + opened +
				'}';
	}
}
