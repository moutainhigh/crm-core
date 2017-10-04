package com.cafe.crm.dto;


public class ClientDetails {
	private Double allDirtyPrice;
	private Double otherPriceMenu;
	private Double dirtyPriceMenu;

	public ClientDetails(Double allDirtyPrice, Double otherPriceMenu, Double dirtyPriceMenu) {
		this.allDirtyPrice = allDirtyPrice;
		this.otherPriceMenu = otherPriceMenu;
		this.dirtyPriceMenu = dirtyPriceMenu;
	}

	public Double getAllDirtyPrice() {
		return allDirtyPrice;
	}

	public void setAllDirtyPrice(Double allDirtyPrice) {
		this.allDirtyPrice = allDirtyPrice;
	}

	public Double getOtherPriceMenu() {
		return otherPriceMenu;
	}

	public void setOtherPriceMenu(Double otherPriceMenu) {
		this.otherPriceMenu = otherPriceMenu;
	}

	public Double getDirtyPriceMenu() {
		return dirtyPriceMenu;
	}

	public void setDirtyPriceMenu(Double dirtyPriceMenu) {
		this.dirtyPriceMenu = dirtyPriceMenu;
	}
}
