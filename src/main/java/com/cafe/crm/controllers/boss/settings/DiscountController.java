package com.cafe.crm.controllers.boss.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/boss/settings/discount-setting")
public class DiscountController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView discountSettingPage() {
		ModelAndView modelAndView = new ModelAndView("/settingPages/discountSettingPage");
		return modelAndView;
	}

}
