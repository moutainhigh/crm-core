package com.cafe.crm.controllers.shift;


import com.cafe.crm.service_abstract.user_service.UserService;
import com.cafe.crm.service_abstract.shift_service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class ShiftController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShiftService shiftService;

	@RequestMapping(value = "/manager/shift/", method = RequestMethod.GET)
	public ModelAndView getAdminPage() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
		LocalDate date = LocalDate.now();
		ModelAndView mv = new ModelAndView("shiftPage");
		mv.addObject("list", userService.findAll());
		mv.addObject("date", dateTimeFormatter.format(date));
		return mv;
	}

	@RequestMapping(value = "/manager/shift/begin", method = RequestMethod.POST)
	public ModelAndView beginShift(@RequestParam(name = "box", required = false) int[] box) {
		if (shiftService.getLast() == null) {                     // если это первая смена в работе приложения
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray);
			} else {
				shiftService.newShift(box);
			}
			return new ModelAndView("redirect:/manager/shift/edit");
		}
		if (!shiftService.getLast().getOpen()) {                 // если смена не открыта
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray);
			} else {
				shiftService.newShift(box);
			}
			return new ModelAndView("redirect:/manager/shift/edit");

		} else {                                                  // если смена открыта
			return new ModelAndView("redirect:/manager/shift/");
		}
	}

	// Получаем список сотрудников открытой смены
	@RequestMapping(value = "/manager/shift/edit", method = RequestMethod.GET)
	public ModelAndView editPage() {
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	// удаляем работника из списка работников смены
	@RequestMapping(value = "/manager/shift/delWorker", method = RequestMethod.POST)
	public ModelAndView deleteWorkerFromShift(@RequestParam(name = "delWorker") String name) {
		shiftService.deleteWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	//добавляем работника на смену
	@RequestMapping(value = "/manager/shift/addWorker", method = RequestMethod.POST)
	public ModelAndView addWorkerFromShift(@RequestParam(name = "addWorker") String name) {
		shiftService.addWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	@RequestMapping(value = "manager/shift/endOfShift", method = RequestMethod.POST)
	public ModelAndView endOfShift() {
		shiftService.closeShift();
		return new ModelAndView("redirect:/login");
	}
}
