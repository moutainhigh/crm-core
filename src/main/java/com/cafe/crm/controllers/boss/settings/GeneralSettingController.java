package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.services.interfaces.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/boss/settings/general-setting")
public class GeneralSettingController {
	@Autowired
	private PropertyService propertyService;

	@ModelAttribute(value = "wrapper")
	public PropertyWrapper addClass() {
		PropertyWrapper propertyWrapper = new PropertyWrapper();
		propertyWrapper.setProperties(propertyService.findAll());
		return propertyWrapper;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView generalSettingPage() {
		ModelAndView modelAndView = new ModelAndView("/settingPages/generalSettingPage");
		modelAndView.addObject("properties", propertyService.findAll());
		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String map(@ModelAttribute PropertyWrapper wrapper, HttpServletRequest request) {
		propertyService.saveCollection(wrapper.getProperties());
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}
}

