package com.cafe.crm.dto;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.models.discount.Discount;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ClientDTO {
	private Long id;
	private String description;
	private boolean isPause;
	private boolean pausedIndex;
	private LocalDateTime timeStart;
	private boolean state;
	private boolean deleteState;
	private Long discount;
	private Long discountWithCard;
	private Double allPrice;
	private Double allDirtyPrice;
	private Double cache;
	private LocalTime passedTime;
	private double otherPriceMenu;
	private double dirtyPriceMenu;
	private Double priceMenu;
	private Double priceTime;
	private Double payWithCard;
	private List<LayerProduct> layerProducts;
	private Card card;
	private Discount discountObj;

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean pause) {
		isPause = pause;
	}

	public boolean isPausedIndex() {
		return pausedIndex;
	}

	public void setPausedIndex(boolean pausedIndex) {
		this.pausedIndex = pausedIndex;
	}

	public Discount getDiscountObj() {
		return discountObj;
	}

	public void setDiscountObj(Discount discountObj) {
		this.discountObj = discountObj;
	}

	public boolean isDeleteState() {
		return deleteState;
	}

	public void setDeleteState(boolean deleteState) {
		this.deleteState = deleteState;
	}

	public List<LayerProduct> getLayerProducts() {
		return layerProducts;
	}

	public void setLayerProducts(List<LayerProduct> layerProducts) {
		this.layerProducts = layerProducts;
	}

	public Long getDiscountWithCard() {
		return discountWithCard;
	}

	public void setDiscountWithCard(Long discountWithCard) {
		this.discountWithCard = discountWithCard;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Double getCache() {
		return cache;
	}

	public void setCache(Double cache) {
		this.cache = cache;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Double getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}

	public Double getAllDirtyPrice() {
		return allDirtyPrice;
	}

	public void setAllDirtyPrice(Double allDirtyPrice) {
		this.allDirtyPrice = allDirtyPrice;
	}

	public LocalTime getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(LocalTime passedTime) {
		this.passedTime = passedTime;
	}

	public double getOtherPriceMenu() {
		return otherPriceMenu;
	}

	public void setOtherPriceMenu(double otherPriceMenu) {
		this.otherPriceMenu = otherPriceMenu;
	}

	public double getDirtyPriceMenu() {
		return dirtyPriceMenu;
	}

	public void setDirtyPriceMenu(double dirtyPriceMenu) {
		this.dirtyPriceMenu = dirtyPriceMenu;
	}

	public Double getPriceMenu() {
		return priceMenu;
	}

	public void setPriceMenu(Double priceMenu) {
		this.priceMenu = priceMenu;
	}

	public Double getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(Double priceTime) {
		this.priceTime = priceTime;
	}

	public Double getPayWithCard() {
		return payWithCard;
	}

	public void setPayWithCard(Double payWithCard) {
		this.payWithCard = payWithCard;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(LocalDateTime timeStart) {
		this.timeStart = timeStart;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClientDTO clientDTO = (ClientDTO) o;

		if (id != null ? !id.equals(clientDTO.id) : clientDTO.id != null) return false;
		return description != null ? description.equals(clientDTO.description) : clientDTO.description == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
