package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.service_abstract.cardService.CardService;
import com.cafe.crm.service_abstract.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/boss/cardStatistic")
public class CardStaticticController {

	@Autowired
	private CardService cardService;

	@Autowired
	private PropertyService propertyService;

	@ModelAttribute(value = "wrapper")
	public PropertyWrapper addProperties() {
		PropertyWrapper PropertyWrapper = new PropertyWrapper();
		PropertyWrapper.setProperties(propertyService.findAll());
		return PropertyWrapper;
	}

	@RequestMapping(method = RequestMethod.GET)
   	public ModelAndView getCards() {
		ModelAndView view = new ModelAndView("pages/CardStatistic");
		view.addObject("cards",cardService.getAll());
		return view;
	}
}
