package com.cafe.crm.controllers.client;

import com.cafe.crm.dao_impl.client.CalculateService;
import com.cafe.crm.dao_impl.client.CardService;
import com.cafe.crm.dao_impl.client.calculateService.CardControllerService;
import com.cafe.crm.models.client.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CardController {
	@Autowired
	private CardService cardService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private CardControllerService cardControllerService;

	@RequestMapping(value = {"/card"}, method = RequestMethod.GET)
	public ModelAndView getCard(@RequestParam(name = "token") Long id) {

		ModelAndView modelAndView = new ModelAndView("card");
		Card card = cardService.getOne(id);
		modelAndView.addObject("card", card);
		modelAndView.addObject("listCalculate", calculateService.getAll());
		return modelAndView;
	}

	@RequestMapping(value = {"/add-card-to-calculate"}, method = RequestMethod.POST)
	public ModelAndView addCardToCalculate(@RequestParam(name = "idCard") Long idCard,
										   @RequestParam(name = "idCalculate") Long idCalculate) {

		cardControllerService.addCardToCalculate(idCard, idCalculate);
		return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/add-money"}, method = RequestMethod.POST)
	public ModelAndView addMoney(@RequestParam(name = "idCard") Long idCard,
								 @RequestParam(name = "money") Double money) {

		cardControllerService.addMoney(idCard, money);
		return new ModelAndView("redirect:/manager");
	}

}
