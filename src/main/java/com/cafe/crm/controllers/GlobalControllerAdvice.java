package com.cafe.crm.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Value("${card.enable}")
	private String cardEnable;

	@ModelAttribute("cardEnable")
	public String isCardEnable() {
		return cardEnable;
	}

	public void setCardEnable(String cardEnable) {
		this.cardEnable = cardEnable;
	}
}
