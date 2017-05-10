package com.cafe.crm.controllers.shift;


import com.cafe.crm.dao.MainClassRepository;
import com.cafe.crm.service_abstract.UserService;
import com.cafe.crm.service_abstract.shift_service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Created by Sasha ins on 01.05.2017.
 */
@Controller
public class ShiftController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private MainClassRepository mainClassRepository;

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
		ModelAndView mv;
		if (!mainClassRepository.findOne(1L).getOpen()) {
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray);
			} else {
				shiftService.newShift(box);
			}
			mv = new ModelAndView("redirect:/manager/shift/edit");
		} else {
			mv = new ModelAndView("redirect:/manager/shift/");
		}
		return mv;
	}

	@RequestMapping(value = "/manager/shift/edit", method = RequestMethod.GET)
	public ModelAndView editPage() {
		ModelAndView mv = new ModelAndView("editingShiftPage");
		// Получаем список сотрудников открытой смены
		mv.addObject("workersOfShift", shiftService.findOne(mainClassRepository.getOne(1L).getShiftId()).getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	@RequestMapping(value = "/manager/shift/delWorker", method = RequestMethod.POST)
	public ModelAndView deleteWorkerFromShift(@RequestParam(name = "delWorker") String name) {
		// удаляем работника из списка работников смены
		shiftService.deleteWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.findOne(mainClassRepository.getOne(1L).getShiftId()).getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	@RequestMapping(value = "/manager/shift/addWorker", method = RequestMethod.POST)
	public ModelAndView addWorkerFromShift(@RequestParam(name = "addWorker") String name) {
		//добавляем работника на смену
		shiftService.addWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.findOne(mainClassRepository.getOne(1L).getShiftId()).getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	@RequestMapping(value = "manager/shift/endOfShift")
	public ModelAndView endOfShift() {
		ModelAndView mv = new ModelAndView();
		mainClassRepository.getOne(1L).setOpen(false);
		return mv;
	}
}
