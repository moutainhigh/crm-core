package com.cafe.crm.controllers.client;

import com.cafe.crm.service_abstract.client.CalculateControllerService;
import com.cafe.crm.service_impl.client.BoardService;
import com.cafe.crm.service_impl.client.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
	public String addCalculate(@RequestParam(name = "boardId") Long id,
							   @RequestParam(name = "number") Long number,
							   @RequestParam(name = "description") String descr) {
		calculateControllerService.addCalculate(id, number, descr);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/refresh-board"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void refreshBoard(@RequestParam(name = "boardId") Long idB,
							 @RequestParam(name = "calculateId") Long idC) {
		calculateControllerService.refreshBoard(idC, idB);
	}

	@RequestMapping(value = {"/add-client"}, method = RequestMethod.POST)
	public String addClient(@RequestParam(name = "calculateId") Long id,
							@RequestParam(name = "number") Long number,
							@RequestParam(name = "description") String descr) {
		calculateControllerService.addClient(id, number, descr);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/calculate-price"}, method = RequestMethod.POST)
	public String calculatePrice(@RequestParam(name = "flag") Boolean flag,
								 @RequestParam(name = "clientId") Long clientId,
								 @RequestParam(name = "calculateNumber") Long number,
								 @RequestParam(name = "discount") Long discount,
								 @RequestParam(name = "calculateId") Long calculateId) {
		calculateControllerService.calculatePrice(clientId, number, discount, flag, calculateId);
		return "redirect:/manager";
	}


	@RequestMapping(value = {"/close-client"}, method = RequestMethod.POST)
	public String closeClient(@RequestParam(name = "calculateId") Long id) {
		calculateControllerService.closeClient(id);
		return "redirect:/manager";
	}

}


