package com.cafe.crm.controllers.shift;


import com.cafe.crm.dao.worker.WorkerRepository;
import com.cafe.crm.service_abstract.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class ShiftController {


	@Autowired
	private ShiftService shiftService;

	@Autowired
	private WorkerRepository workerService;

	@Autowired
	private TimeManager timeManager;

	@RequestMapping(value = "/manager/shift/", method = RequestMethod.GET)
	public ModelAndView getAdminPage() {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
		LocalDateTime date = timeManager.getDate();
		ModelAndView mv;
		if (shiftService.getLast() == null || !shiftService.getLast().getOpen()) {
			mv = new ModelAndView("shiftPage");
			mv.addObject("list", workerService.findAll());
			mv.addObject("date", dateTimeFormatter.format(date));
		} else {
			mv = new ModelAndView("editingShiftPage");
		}
		return mv;
	}

	@RequestMapping(value = "/manager/shift/begin", method = RequestMethod.POST)
	public String beginShift(@RequestParam(name = "box", required = false) int[] box) {
		if (shiftService.getLast() == null) {                     // if this first shift
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray);
			} else {
				shiftService.newShift(box);
			}
			return "redirect:/manager";
		}
		if (!shiftService.getLast().getOpen()) {                 // if shift is closed
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray);
			} else {
				shiftService.newShift(box);
			}
			return "redirect:/manager";

		} else {                                                  // if shift is open
			return "redirect:/manager";
		}
	}

	// get all workers of shift
	@RequestMapping(value = "/manager/shift/edit", method = RequestMethod.GET)
	public ModelAndView editPage() {
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	// delete worker from shift
	@RequestMapping(value = "/manager/shift/delWorker", method = RequestMethod.POST)
	public ModelAndView deleteWorkerFromShift(@RequestParam(name = "delWorker") String name) {
		shiftService.deleteWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	// add worker on shift
	@RequestMapping(value = "/manager/shift/addWorker", method = RequestMethod.POST)
	public ModelAndView addWorkerFromShift(@RequestParam(name = "addWorker") String name) {
		shiftService.addWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	@RequestMapping(value = "manager/shift/endOfShift", method = RequestMethod.GET)
	public String endOfShift() {
		shiftService.closeShift();
		return "redirect:/login";
	}
}
