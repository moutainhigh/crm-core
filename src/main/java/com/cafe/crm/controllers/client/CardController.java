package com.cafe.crm.controllers.client;

import com.cafe.crm.dao_impl.client.CalculateService;
import com.cafe.crm.dao_impl.client.CardService;
import com.cafe.crm.dao_impl.client.calculateService.CardControllerService;
import com.cafe.crm.models.client.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public ModelAndView getCard(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("card");

		Card card = cardService.getOne(Long.parseLong(request.getParameter("token")));
		modelAndView.addObject("card", card);
		modelAndView.addObject("listCalculate", calculateService.getAll());

		return modelAndView;
	}

	@RequestMapping(value = {"/add-card-to-calculate"}, method = RequestMethod.POST)
	public ModelAndView addCardToCalculate(HttpServletRequest request) {
		Long idCard = Long.parseLong(request.getParameter("idCard"));
		Long idCalculate = Long.parseLong(request.getParameter("idCalculate"));

		cardControllerService.addCardToCalculate(idCard, idCalculate);

		return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/add-money"}, method = RequestMethod.POST)
	public ModelAndView addMoney(HttpServletRequest request) {
		Long idCard = Long.parseLong(request.getParameter("idCard"));
		Double money = Double.parseDouble(request.getParameter("money"));
		cardControllerService.addMoney(idCard, money);
		return new ModelAndView("redirect:/manager");
	}


}
