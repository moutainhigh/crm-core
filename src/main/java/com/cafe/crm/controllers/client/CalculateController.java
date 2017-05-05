package com.cafe.crm.controllers.client;

import com.cafe.crm.dao_impl.client.BoardService;
import com.cafe.crm.dao_impl.client.CalculateService;
import com.cafe.crm.dao_impl.client.calculateService.CalculateControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


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
		modelAndView.addObject("listCalculate", calculateService.getAll());
		return modelAndView;
	}

	@RequestMapping(value = {"/add-calculate"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addCalculate(HttpServletRequest request,
							 @RequestParam(name = "boardId") Long id,
							 @RequestParam(name = "number") Long number,
							 @RequestParam(name = "description") String descr) {//передаются по полю от 3 разных сущностей

		calculateControllerService.addCalculate(id, number, descr);
		//return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/refresh-board"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void refreshBoard(HttpServletRequest request,
							 @RequestParam(name = "boardId") Long idB,
							 @RequestParam(name = "calculateId") Long idC) {

		calculateControllerService.refreshBoard(idC, idB);
		//return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/add-client"}, method = RequestMethod.POST)
	public ModelAndView addClient(HttpServletRequest request,
								  @RequestParam(name = "calculateId") Long id,
								  @RequestParam(name = "number") Long number,
								  @RequestParam(name = "description") String descr) {

		calculateControllerService.addClient(id, number, descr);
		return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/calculate-price"}, method = RequestMethod.POST)
	public ModelAndView calculatePrice(HttpServletRequest request,
									   @RequestParam(name = "discountInput") Long discount, //скидка которая написана в инпуте(не карты, а карта+скидка управляющего)
									   @RequestParam(name = "clientId") Long clientId,        // id клиента у которого считаем
									   @RequestParam(name = "numberToCalculate") String numberToCalculate) { //количество человек для расчета клиента
		//Long calculateId = Long.parseLong(request.getParameter("calculateId"));
		calculateControllerService.calculatePrice(discount, clientId, numberToCalculate);
		return new ModelAndView("redirect:/manager");
	}


}


