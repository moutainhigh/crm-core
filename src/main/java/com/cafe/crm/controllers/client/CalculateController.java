package com.cafe.crm.controllers.client;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.service_abstract.client.CalculateControllerService;
import com.cafe.crm.service_impl.client.BoardService;
import com.cafe.crm.service_impl.client.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CalculateController {

	@Autowired
	private CalculateControllerService calculateControllerService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = {"/manager"}, method = RequestMethod.GET)
	public ModelAndView manager() {
		ModelAndView modelAndView = new ModelAndView("clients");
		modelAndView.addObject("listBoard", boardService.getAll());
		modelAndView.addObject("listCalculate", calculateService.getAllOpen());
		return modelAndView;
	}

	@RequestMapping(value = {"/add-calculate"}, method = RequestMethod.POST)
	public String createCalculate(@RequestParam("boardId") Long id,
							   @RequestParam("number") Long number,
							   @RequestParam("description") String description) {
		calculateControllerService.createCalculate(id, number, description);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/refresh-board"}, method = RequestMethod.POST)
	// German, refresh board. See in HTML code, file client.html
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void refreshBoard(@RequestParam("boardId") Long idB,
							 @RequestParam("calculateId") Long idC) {
		calculateControllerService.refreshBoard(idC, idB);
	}

	@RequestMapping(value = {"/add-client"}, method = RequestMethod.POST)
	public String addClient(@RequestParam("calculateId") Long id,
							@RequestParam("number") Long number,
							@RequestParam("description") String description) {
		calculateControllerService.addClient(id, number, description);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/calculate-price"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Object> calculatePrice(@RequestParam("flag") Boolean flag,
							   @RequestParam("clientId") Long clientId,
							   @RequestParam("calculateNumber") Long number,
							   @RequestParam("discount") Long discount,
							   @RequestParam("calculateId") Long calculateId) {
		Calculate calculate = calculateControllerService.calculatePrice(clientId, number, discount, flag, calculateId);
		calculate.setBoard(null);
		calculate.setClient(null);
		calculate.setCard(null);
		List<Object> list = new ArrayList<>();
		list.add(calculate);
		list.add(calculate.getPassedTime().withSecond(0).withNano(0).toString());
		return list;
	}


	@RequestMapping(value = {"/close-client"}, method = RequestMethod.POST)
	public String closeClient(@RequestParam("calculateId") Long idCal,
							  @RequestParam("clientId") Long idCl) {
		calculateControllerService.closeClient(idCal, idCl);
		return "redirect:/manager";
	}

}


