package com.cafe.crm.dto;


import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.user.User;

import java.util.List;

public class TotalStatisticView {

	private double profit;
	private double salariesCosts;
	private double otherCosts;
	private List<User> users;
	private List<Client> clients;
	private List<Cost> listOfSalariesCosts;
	private List<Cost> listOfOtherCosts;

	public TotalStatisticView(double profit, double salariesCosts, double otherCosts, List<User> users,
							  List<Client> clients, List<Cost> listOfSalariesCosts, List<Cost> listOfOtherCosts) {
		this.profit = profit;
		this.salariesCosts = salariesCosts;
		this.otherCosts = otherCosts;
		this.users = users;
		this.clients = clients;
		this.listOfSalariesCosts = listOfSalariesCosts;
		this.listOfOtherCosts = listOfOtherCosts;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public List<Cost> getListOfSalariesCosts() {
		return listOfSalariesCosts;
	}

	public void setListOfSalariesCosts(List<Cost> listOfSalariesCosts) {
		this.listOfSalariesCosts = listOfSalariesCosts;
	}

	public List<Cost> getListOfOtherCosts() {
		return listOfOtherCosts;
	}

	public void setListOfOtherCosts(List<Cost> listOfOtherCosts) {
		this.listOfOtherCosts = listOfOtherCosts;
	}
}
