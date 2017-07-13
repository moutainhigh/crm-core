package com.cafe.crm.models.menu;

import java.io.Serializable;
import java.util.List;


public class WrapperOfProduct implements Serializable {  // wrapper for menuController

	private List<String> names;

	private List<Integer> amount;

	private Long idCat;

	private String name;

	private String description;

	private Double cost;

	public WrapperOfProduct() {

	}

	public Long getIdCat() {
		return idCat;
	}

	public void setIdCat(Long idCat) {
		this.idCat = idCat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}


	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Integer> getAmount() {
		return amount;
	}

	public void setAmount(List<Integer> amount) {
		this.amount = amount;
	}
}

