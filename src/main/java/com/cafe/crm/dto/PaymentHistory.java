package com.cafe.crm.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PaymentHistory {

	private final Long calculateId;
	private final String calculateDescription;
	private final String clientDescription;
	private final Double totalPrice;
	private final Double menuPrice;
	private final Double timePrice;
	private final Long cafeDiscount;
	private final Long cardDiscount;
	private final Double payWithCard;
	private final LocalDateTime dateStart;
	private final LocalTime spentTime;


	public PaymentHistory(Long calculateId, String calculateDescription, String clientDescription, Double totalPrice, Double menuPrice, Double timePrice, Long cafeDiscount, Long cardDiscount, Double payWithCard, LocalDateTime dateStart, LocalTime spentTime) {
		this.calculateId = calculateId;
		this.calculateDescription = calculateDescription;
		this.clientDescription = clientDescription;
		this.totalPrice = totalPrice;
		this.menuPrice = menuPrice;
		this.timePrice = timePrice;
		this.cafeDiscount = cafeDiscount;
		this.cardDiscount = cardDiscount;
		this.payWithCard = payWithCard;
		this.dateStart = dateStart;
		this.spentTime = spentTime;
	}

	public static PaymentHistoryBuilder builder() {
		return new PaymentHistoryBuilder();
	}

	public Long getCalculateId() {
		return calculateId;
	}

	public String getCalculateDescription() {
		return calculateDescription;
	}

	public String getClientDescription() {
		return clientDescription;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public Double getMenuPrice() {
		return menuPrice;
	}

	public Double getTimePrice() {
		return timePrice;
	}

	public Long getCafeDiscount() {
		return cafeDiscount;
	}

	public Long getCardDiscount() {
		return cardDiscount;
	}

	public Double getPayWithCard() {
		return payWithCard;
	}

	public LocalDateTime getDateStart() {
		return dateStart;
	}

	public LocalTime getSpentTime() {
		return spentTime;
	}

	public static class PaymentHistoryBuilder {

		private Long calculateId;

		private String calculateDescription;

		private String clientDescription;

		private Double totalPrice;

		private Double menuPrice;

		private Double timePrice;

		private Long cafeDiscount;

		private Long cardDiscount;

		private Double payWithCard;

		private LocalDateTime dateStart;

		private LocalTime spentTime;

		public PaymentHistory build() {
			return new PaymentHistory(calculateId, calculateDescription, clientDescription, totalPrice, menuPrice, timePrice, cafeDiscount, cardDiscount, payWithCard, dateStart, spentTime);
		}

		public PaymentHistoryBuilder calculateId(Long calculateId) {
			this.calculateId = calculateId;
			return this;
		}

		public PaymentHistoryBuilder calculateDescription(String calculateDescription) {
			this.calculateDescription = calculateDescription;
			return this;
		}

		public PaymentHistoryBuilder clientDescription(String clientDescription) {
			this.clientDescription = clientDescription;
			return this;
		}

		public PaymentHistoryBuilder totalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
			return this;
		}

		public PaymentHistoryBuilder menuPrice(Double menuPrice) {
			this.menuPrice = menuPrice;
			return this;
		}

		public PaymentHistoryBuilder timePrice(Double timePrice) {
			this.timePrice = timePrice;
			return this;
		}

		public PaymentHistoryBuilder cafeDiscount(Long totalDiscount) {
			this.cafeDiscount = totalDiscount;
			return this;
		}

		public PaymentHistoryBuilder cardDiscount(Long cardDiscount) {
			this.cardDiscount = cardDiscount;
			return this;
		}

		public PaymentHistoryBuilder payWithCard(Double payWithCard) {
			this.payWithCard = payWithCard;
			return this;
		}

		public PaymentHistoryBuilder dateStart(LocalDateTime dateStart) {
			this.dateStart = dateStart;
			return this;
		}

		public PaymentHistoryBuilder spentTime(LocalTime spentTime) {
			this.spentTime = spentTime;
			return this;
		}
	}
}
