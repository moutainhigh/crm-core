package com.cafe.crm.controllers.card;

import com.cafe.crm.service_abstract.calculateService.CalculateService;
import com.cafe.crm.service_abstract.cardService.CardControllerService;
import com.cafe.crm.service_abstract.cardService.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/manager")
public class CardProfileController {
	@Autowired
	private CardService cardService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private CardControllerService cardControllerService;

	@RequestMapping(value = {"/card/{id}"}, method = RequestMethod.GET)
	public ModelAndView getCard(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("card");
		modelAndView.addObject("card", cardService.getOne(id));
		modelAndView.addObject("listCalculate", calculateService.getAllOpen());
		return modelAndView;
	}

	@RequestMapping(value = {"/add-card-to-calculate"}, method = RequestMethod.POST)
	public String addCardToCalculate(@RequestParam("idCard") Long idCard,
									 @RequestParam("idCalculate") Long idCalculate) {
		cardControllerService.addCardToCalculate(idCard, idCalculate);
		return "redirect:/manager/card/" + idCard;
	}

}
