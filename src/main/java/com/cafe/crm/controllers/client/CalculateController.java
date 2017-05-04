package com.cafe.crm.controllers.client;

import com.cafe.crm.dao_impl.client.BoardService;
import com.cafe.crm.dao_impl.client.CalculateService;
import com.cafe.crm.dao_impl.client.calculateService.CalculateControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public ModelAndView addCalculate(HttpServletRequest request) {//передаются по полю от 3 разных сущностей

		Long id = Long.parseLong(request.getParameter("boardId")); // id стола
		Long number = Long.parseLong(request.getParameter("number")); //количество человек в клиенте
		String descr = request.getParameter("description"); // описание расчета

		calculateControllerService.addCalculate(id,number,descr);
		return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/refresh-board"}, method = RequestMethod.POST)
	public ModelAndView refreshBoard(HttpServletRequest request) {
		Long idB = Long.parseLong(request.getParameter("boardId"));
		Long idC = Long.parseLong(request.getParameter("calculateId"));

		calculateControllerService.refreshBoard(idC,idB);
		return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/add-client"}, method = RequestMethod.POST)
	public ModelAndView addClient(HttpServletRequest request) {

		Long id = Long.parseLong(request.getParameter("calculateId"));
		Long number = Long.parseLong(request.getParameter("number")); // 2 поля клиента
		String descr = request.getParameter("description");

		calculateControllerService.addClient(id,number,descr);
		return new ModelAndView("redirect:/manager");
	}

	@RequestMapping(value = {"/calculate-price"}, method = RequestMethod.POST)
	public ModelAndView calculatePrice(HttpServletRequest request) {
		//Long calculateId = Long.parseLong(request.getParameter("calculateId")); // для того, чтобы узнать привязана ли карта к расчету, если да то будем зачислять потраченную сумму на карту
		Long discount = Long.parseLong(request.getParameter("discountInput")); //скидка которая написана в инпуте(не карты, а карта+скидка управляющего)
		Long clientId = Long.parseLong(request.getParameter("clientId"));// id клиента у которого считаем
		String numberToCalculate = request.getParameter("numberToCalculate");//количество человек для расчета клиента, пока не парсим, есть возможность строки "Всех"

		calculateControllerService.calculatePrice(discount,clientId,numberToCalculate);
		return new ModelAndView("redirect:/manager");
	}


}


