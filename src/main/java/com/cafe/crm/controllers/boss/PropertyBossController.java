package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.service_abstract.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/boss/property")
public class PropertyBossController {

	@Autowired
	private PropertyService propertyService;


	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String map(@ModelAttribute PropertyWrapper wrapper, HttpServletRequest request) {
		propertyService.saveCollection(wrapper.getProperties());
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
