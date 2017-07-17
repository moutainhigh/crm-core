package com.cafe.crm.controllers.boss.settings;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/boss/settings/logger-setting")
public class LoggerController {
	@Autowired
	@Qualifier(value = "logger")
	private Logger log;

	@Autowired
	@Qualifier(value = "hibLogger")
	private Logger hibLog;

	@ModelAttribute(name = "logLevel")
	public String logLevel() {
		return log.getLevel().levelStr;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loggerSettingPage() {
		ModelAndView modelAndView = new ModelAndView("/settingPages/loggerSettingPage");
		modelAndView.addObject(logLevel());
		return modelAndView;
	}

	@RequestMapping(value = "/logLevel", method = RequestMethod.POST)
	public String setLogLevel(@RequestParam(name = "level") String logLevel, HttpServletRequest request) {
		log.info("Log level: " + logLevel);
		switch (logLevel) {
			case ("ERROR"):
				log.setLevel(Level.ERROR);
				hibLog.setLevel(Level.ERROR);
				break;
			case ("INFO"):
				log.setLevel(Level.INFO);
				hibLog.setLevel(Level.INFO);
				break;
			case ("DEBUG"):
				log.setLevel(Level.DEBUG);
				hibLog.setLevel(Level.DEBUG);
				break;
			case ("WARN"):
				log.setLevel(Level.WARN);
				hibLog.setLevel(Level.WARN);
				break;
			default:
				log.error("Unknown log level: " + logLevel);
				break;
		}
		return "redirect:" + request.getHeader("Referer");
	}
}
