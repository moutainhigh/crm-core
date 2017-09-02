package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.configs.filters.CardFilter;
import com.cafe.crm.controllers.GlobalControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/boss/settings/card")
public class CardSettingsController {

	private final CardFilter cardFilter;
	private final GlobalControllerAdvice globalControllerAdvice;

	@Autowired
	public CardSettingsController(GlobalControllerAdvice globalControllerAdvice, CardFilter cardFilter) {
		this.globalControllerAdvice = globalControllerAdvice;
		this.cardFilter = cardFilter;
	}

	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public String changeCardEnableStatus(@RequestParam(name = "cardEnable", required = false) String cardEnable) {
		Boolean newCardStatus = Boolean.valueOf(cardEnable);
		cardFilter.setEnable(newCardStatus);
		globalControllerAdvice.setCardEnable(newCardStatus.toString());

		return "redirect:/boss/settings/general-setting";
	}

}
