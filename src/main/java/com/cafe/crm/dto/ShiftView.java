package com.cafe.crm.dto;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ShiftView {

	private List<User> usersOnShift;
	private Set<Client> clients;
	private List<Calculate> activeCalculate;
	private Set<Calculate> allCalculate;
	private double cashBox;
	private double bankCashBox;
	private double totalCashBox;
	private int usersTotalShiftSalary;
	private Double card;
	private Double allPrice;
	private LocalDate shiftDate;
	private double otherCosts;

	public ShiftView(List<User> usersOnShift, Set<Client> clients, List<Calculate> activeCalculate,
					 Set<Calculate> allCalculate, double cashBox, double totalCashBox, int usersTotalShiftSalary,
					 Double card, Double allPrice, LocalDate shiftDate, Double otherCosts, Double bankCashBox) {
		this.usersOnShift = usersOnShift;
		this.clients = clients;
		this.activeCalculate = activeCalculate;
		this.allCalculate = allCalculate;
		this.cashBox = cashBox;
		this.totalCashBox = totalCashBox;
		this.usersTotalShiftSalary = usersTotalShiftSalary;
		this.card = card;
		this.allPrice = allPrice;
		this.shiftDate = shiftDate;
		this.otherCosts = otherCosts;
		this.bankCashBox = bankCashBox;
	}

	public List<User> getUsersOnShift() {
		return usersOnShift;
	}

	public void setUsersOnShift(List<User> usersOnShift) {
		this.usersOnShift = usersOnShift;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public List<Calculate> getActiveCalculate() {
		return activeCalculate;
	}

	public void setActiveCalculate(List<Calculate> activeCalculate) {
		this.activeCalculate = activeCalculate;
	}

	public Set<Calculate> getAllCalculate() {
		return allCalculate;
	}

	public void setAllCalculate(Set<Calculate> allCalculate) {
		this.allCalculate = allCalculate;
	}

	public double getCashBox() {
		return cashBox;
	}

	public void setCashBox(double cashBox) {
		this.cashBox = cashBox;
	}

	public double getTotalCashBox() {
		return totalCashBox;
	}

	public void setTotalCashBox(double totalCashBox) {
		this.totalCashBox = totalCashBox;
	}

	public int getUsersTotalShiftSalary() {
		return usersTotalShiftSalary;
	}

	public void setUsersTotalShiftSalary(int usersTotalShiftSalary) {
		this.usersTotalShiftSalary = usersTotalShiftSalary;
	}

	public Double getCard() {
		return card;
	}

	public void setCard(Double card) {
		this.card = card;
	}

	public Double getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}

	public LocalDate getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(LocalDate shiftDate) {
		this.shiftDate = shiftDate;
	}

	public Double getOtherCosts() {
		return otherCosts;
	}

	public void setOtherCosts(Double otherCosts) {
		this.otherCosts = otherCosts;
	}

	public Double getBankCashBox() {
		return bankCashBox;
	}

	public void setBankCashBox(Double bankCashBox) {
		this.bankCashBox = bankCashBox;
	}
}
