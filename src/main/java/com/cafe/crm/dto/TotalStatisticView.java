package com.cafe.crm.dto;


import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TotalStatisticView {

	private double profit;
	private double salariesCosts;
	private double otherCosts;
	private Set<User> users;
	private List<ClientDTO> clients;
	private List<Cost> listOfOtherCosts;
	private List<Debt> givenDebts;
	private List<Debt> repaidDebt;

	public TotalStatisticView(double profit, double salariesCosts, double otherCosts, Set<User> users,
							  List<ClientDTO> clientDTO, List<Cost> listOfOtherCosts,
							  List<Debt> givenDebts, List<Debt> repaidDebt) {
		this.profit = profit;
		this.salariesCosts = salariesCosts;
		this.otherCosts = otherCosts;
		this.users = users;
		this.clients = clientDTO;
		this.listOfOtherCosts = listOfOtherCosts;
		this.givenDebts = givenDebts;
		this.repaidDebt = repaidDebt;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getSalariesCosts() {
		return salariesCosts;
	}

	public void setSalariesCosts(double salariesCosts) {
		this.salariesCosts = salariesCosts;
	}

	public double getOtherCosts() {
		return otherCosts;
	}

	public void setOtherCosts(double otherCosts) {
		this.otherCosts = otherCosts;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public List<ClientDTO> getClients() {
		return clients;
	}

	public void setClients(List<ClientDTO> clientDTO) {
		this.clients = clientDTO;
	}

	public List<Debt> getGivenDebts() {
		return givenDebts;
	}

	public void setGivenDebts(List<Debt> givenDebts) {
		this.givenDebts = givenDebts;
	}

	public List<Debt> getRepaidDebt() {
		return repaidDebt;
	}

	public void setRepaidDebt(List<Debt> repaidDebt) {
		this.repaidDebt = repaidDebt;
	}

	public List<Cost> getListOfOtherCosts() {
		return listOfOtherCosts;
	}

	public void setListOfOtherCosts(List<Cost> listOfOtherCosts) {
		this.listOfOtherCosts = listOfOtherCosts;
	}
}