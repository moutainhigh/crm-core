package com.cafe.crm.dto;


import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.user.User;

import java.util.List;

public class TotalStatisticView {

	private double profit;
	private double salariesCosts;
	private double otherCosts;
	private List<User> users;
	private List<Client> clients;
	private List<Goods> salariesGoods;
	private List<Goods> otherGoods;

	public TotalStatisticView(double profit, double salariesCosts, double otherCosts, List<User> users,
							  List<Client> clients, List<Goods> salariesGoods, List<Goods> otherGoods) {
		this.profit = profit;
		this.salariesCosts = salariesCosts;
		this.otherCosts = otherCosts;
		this.users = users;
		this.clients = clients;
		this.salariesGoods = salariesGoods;
		this.otherGoods = otherGoods;
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

	public List<Goods> getSalariesGoods() {
		return salariesGoods;
	}

	public void setSalariesGoods(List<Goods> salariesGoods) {
		this.salariesGoods = salariesGoods;
	}

	public List<Goods> getOtherGoods() {
		return otherGoods;
	}

	public void setOtherGoods(List<Goods> otherGoods) {
		this.otherGoods = otherGoods;
	}
}
