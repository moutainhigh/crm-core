package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.discount.Discount;
import com.cafe.crm.services.interfaces.client.ClientService;
import com.cafe.crm.services.interfaces.discount.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/boss/settings/discount-setting")
public class DiscountController {

	@Autowired
	private DiscountService discountService;

	@Autowired
	private ClientService clientServicel;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView discountSettingPage() {
		ModelAndView modelAndView = new ModelAndView("/settingPages/discountSettingPage");
		modelAndView.addObject("discounts", discountService.getAllOpen());
		return modelAndView;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newDiscount(HttpServletRequest request, Discount discount) {
		discountService.save(discount);
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editDiscount(HttpServletRequest request, Discount discount) {
		discountService.save(discount);
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteDiscount(@RequestParam("id") Long id, HttpServletRequest request) {
		String referrer = request.getHeader("Referer");
		Discount discount = discountService.getOne(id);
		List<Client> clientList = clientServicel.getAllOpen();
		boolean flag = false;
		for (Client client : clientList) {
			if (client.getDiscountObj().equals(discount)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			discount.setIsOpen(false);
			discountService.save(discount);
		}
		return "redirect:" + referrer;
	}
}
