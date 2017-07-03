package com.cafe.crm.models.client;

import com.cafe.crm.models.card.Card;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

// TODO: 30.06.2017 поменять на серверное время при мерже

@Entity
@Table(name = "client")
public class Client {
	@Id
	@GeneratedValue
	private Long id;

	@Size(max = 30)
	private String description = "";

	private LocalTime timeStart = LocalTime.now().withSecond(0).withNano(0);

	private boolean state = true;    // Open or Closed

	@NotNull
	private Long discount = 0L;

	private Long discountWithCard = 0L;

	private Double allPrice = 0D;//initial amount

	private Long cache = 0L;// ready money

	private LocalTime passedTime = LocalTime.of(0, 0, 0);

	private Double priceMenu = 0D;

	private Double priceTime = 0D;

	private Long payWithCard = 0L;

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinTable(name = "client_layer_product",
			joinColumns = {@JoinColumn(name = "client_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "layer_product_id", referencedColumnName = "id")})
	private List<LayerProduct> layerProducts;

	@ManyToOne
	private Card card;

	public Client() {
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

	public Long getCache() {
		return cache;
	}

	public void setCache(Long cache) {
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

	public LocalTime getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(LocalTime passedTime) {
		this.passedTime = passedTime;
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

	public Long getPayWithCard() {
		return payWithCard;
	}

	public void setPayWithCard(Long payWithCard) {
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

	public LocalTime getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Client client = (Client) o;

		if (id != null ? !id.equals(client.id) : client.id != null) return false;
		return description != null ? description.equals(client.description) : client.description == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
