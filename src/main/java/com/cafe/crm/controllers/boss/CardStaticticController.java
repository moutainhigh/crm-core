package com.cafe.crm.controllers.boss;

import com.cafe.crm.services.interfaces.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/boss/cardStatistic")
public class CardStaticticController {

	@Autowired
	private CardService cardService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getCards() {
		ModelAndView view = new ModelAndView("pages/CardStatistic");
		view.addObject("cards", cardService.getAll());
		return view;
	}
}
