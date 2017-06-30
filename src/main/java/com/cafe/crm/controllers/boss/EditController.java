package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.client.Board;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.service_abstract.boardService.BoardService;
import com.cafe.crm.service_abstract.calculateService.CalculateService;
import com.cafe.crm.service_abstract.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@ModelAttribute(value = "wrapper")
	public PropertyWrapper addClass() {
		PropertyWrapper PropertyWrapper = new PropertyWrapper();
		PropertyWrapper.setProperties(propertyService.findAll());
		return PropertyWrapper;
	}

	@RequestMapping(value = "/property/edit", method = RequestMethod.POST)
	public String map(@ModelAttribute PropertyWrapper wrapper, HttpServletRequest request) {
		propertyService.saveCollection(wrapper.getProperties());
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
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
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
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
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}
}