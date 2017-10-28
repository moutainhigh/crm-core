package com.cafe.crm.dto;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.cost.Cost;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class DetailStatisticView {

	private LocalDate shiftDate;

	private Double cashBox;

	private Double allPrice;

	private int clientsNumber;

	private List<UserDTO> usersOnShift;

	private Set<Calculate> allCalculate;

	private Double allSalaryCost;

	private Double allOtherCost;

	private List<Cost> otherCost;

	public DetailStatisticView(LocalDate shiftDate, Double cashBox, Double allPrice, int clientsNumber,
							   List<UserDTO> usersOnShift, Set<Calculate> allCalculate, Double allSalaryCost,
							   Double allOtherCost, List<Cost> otherCost) {
		this.shiftDate = shiftDate;
		this.cashBox = cashBox;
		this.allPrice = allPrice;
		this.clientsNumber = clientsNumber;
		this.usersOnShift = usersOnShift;
		this.allCalculate = allCalculate;
		this.allSalaryCost = allSalaryCost;
		this.allOtherCost = allOtherCost;
		this.otherCost = otherCost;
	}

	public LocalDate getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(LocalDate shiftDate) {
		this.shiftDate = shiftDate;
	}

	public Double getCashBox() {
		return cashBox;
	}

	public void setCashBox(Double cashBox) {
		this.cashBox = cashBox;
	}

	public Double getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}

	public int getClientsNumber() {
		return clientsNumber;
	}

	public void setClientsNumber(int clientsNumber) {
		this.clientsNumber = clientsNumber;
	}

	public List<UserDTO> getUsersOnShift() {
		return usersOnShift;
	}

	public void setUsersOnShift(List<UserDTO> usersOnShift) {
		this.usersOnShift = usersOnShift;
	}

	public Set<Calculate> getAllCalculate() {
		return allCalculate;
	}

	public void setAllCalculate(Set<Calculate> allCalculate) {
		this.allCalculate = allCalculate;
	}

	public Double getAllSalaryCost() {
		return allSalaryCost;
	}

	public void setAllSalaryCost(Double allSalaryCost) {
		this.allSalaryCost = allSalaryCost;
	}

	public Double getAllOtherCost() {
		return allOtherCost;
	}

	public void setAllOtherCost(Double allOtherCost) {
		this.allOtherCost = allOtherCost;
	}

	public List<Cost> getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(List<Cost> otherCost) {
		this.otherCost = otherCost;
	}
}
