package com.cafe.crm.dto;


public class ClientDetails {
	private Double allDirtyPrice;
	private Long otherPriceMenu;
	private Long dirtyPriceMenu;

	public ClientDetails(Double allDirtyPrice, Long otherPriceMenu, Long dirtyPriceMenu) {
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

	public Long getOtherPriceMenu() {
		return otherPriceMenu;
	}

	public void setOtherPriceMenu(Long otherPriceMenu) {
		this.otherPriceMenu = otherPriceMenu;
	}

	public Long getDirtyPriceMenu() {
		return dirtyPriceMenu;
	}

	public void setDirtyPriceMenu(Long dirtyPriceMenu) {
		this.dirtyPriceMenu = dirtyPriceMenu;
	}
}
