package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.dto.PropertyWrapper;
import com.cafe.crm.services.interfaces.property.PropertyService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/boss/settings/general-setting")
public class GeneralSettingController {

	private final PropertyService propertyService;
	private final TimeManager timeManager;

	@Autowired
	public GeneralSettingController(TimeManager timeManager, PropertyService propertyService) {
		this.timeManager = timeManager;
		this.propertyService = propertyService;
	}

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
	public String editProperty(@ModelAttribute PropertyWrapper wrapper, HttpServletRequest request) {
		propertyService.saveCollection(wrapper.getProperties());
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}

	@PostMapping("/get-server-time-date")
	@ResponseBody
	public List<Object> getServerTimeDate() {
		List<Object> list = new ArrayList<>();
		list.add(timeManager.getDateTime());
		list.add(timeManager.getIsServerDateTime());
		return list;
	}
}

