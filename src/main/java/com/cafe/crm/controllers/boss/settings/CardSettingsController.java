package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.configs.filters.CardFilter;
import com.cafe.crm.controllers.GlobalControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(method = RequestMethod.GET)
	public String showCardSettingsPage() {
		return "settingPages/cardSettingPage";
	}

	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public String changeCardEnableStatus() {
		String oldCardStatus = globalControllerAdvice.isCardEnable();
		String newCardStatus = getReverseStatus(oldCardStatus);
		cardFilter.setEnable(!Boolean.valueOf(newCardStatus));
		globalControllerAdvice.setCardEnable(newCardStatus);

		return "redirect:/boss/settings/card";
	}

	private String getReverseStatus(String current) {
		return current.equals("true") ? "false" : "true";
	}

}
