package com.cafe.crm.controllers.boss;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.service_abstract.boardService.BoardService;
import com.cafe.crm.service_abstract.calculateService.CalculateService;
import com.cafe.crm.service_abstract.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/boss")
public class EditController {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private CalculateService calculateService;

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

	@ModelAttribute(value = "wrapper")
	public PropertyWrapper addClass() {
		PropertyWrapper PropertyWrapper = new PropertyWrapper();
		PropertyWrapper.setProperties(propertyService.findAll());
		return PropertyWrapper;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView mainPage() {
		ModelAndView modelAndView = new ModelAndView("bossSettingsMenu");
		modelAndView.addObject("boards", boardService.getAll());
		return modelAndView;
	}

	@RequestMapping(value = "/property/edit", method = RequestMethod.POST)
	public String map(@ModelAttribute PropertyWrapper wrapper, HttpServletRequest request) {
		propertyService.saveCollection(wrapper.getProperties());
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value = "/board/edit", method = RequestMethod.GET)
	public ModelAndView getAllBoard() {
		ModelAndView modelAndView = new ModelAndView("pages/editBoard");
		modelAndView.addObject("boards", boardService.getAll());
		return modelAndView;
	}

	@RequestMapping(value = "/board/edit", method = RequestMethod.POST)
	public String editBoard(Board board, HttpServletRequest request) {
		Board br = boardService.getOne(board.getId());
		if (br != null) {
			boardService.save(board);
		}
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value = "/board/delete/{id}", method = RequestMethod.POST)
	public String deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		List<Calculate> calc = calculateService.getAllOpen();
		for (Calculate calculate : calc) {
			if(calculate.getBoard().getId().equals(id)){
				return "redirect:" + referer;
			}
		}
		boardService.deleteById(id);
		return "redirect:" + referer;
	}

	@RequestMapping(value = "/board/new", method = RequestMethod.POST)
	public String newBoard(Board board, HttpServletRequest request) {
		boardService.save(board);
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@ResponseBody
	@RequestMapping(value = "/property/logLevel", method = RequestMethod.POST)
	public ResponseEntity<?> setLogLevel(@RequestParam(name = "level") String logLevel) {
		log.info("Log level: " + logLevel);
		if (logLevel.equalsIgnoreCase("ERROR")) {
			log.setLevel(Level.ERROR);
			hibLog.setLevel(Level.ERROR);
		} else if(logLevel.equalsIgnoreCase("INFO")) {
			log.setLevel(Level.INFO);
			hibLog.setLevel(Level.INFO);
		} else if(logLevel.equalsIgnoreCase("DEBUG")) {
			log.setLevel(Level.DEBUG);
			hibLog.setLevel(Level.DEBUG);
		} else if(logLevel.equalsIgnoreCase("TRACE")) {
			log.setLevel(Level.TRACE);
			hibLog.setLevel(Level.TRACE);
		} else {
			log.error("Unknown log level: " + logLevel);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}