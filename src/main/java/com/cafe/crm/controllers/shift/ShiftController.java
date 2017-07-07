package com.cafe.crm.controllers.shift;


import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ShiftController {


	@Autowired
	private ShiftService shiftService;

	@Autowired
	private WorkerRepository workerService;

	@Autowired
	private TimeManager timeManager;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BossRepository bossRepository;

	@RequestMapping(value = "/manager/shift/", method = RequestMethod.GET)
	public ModelAndView getAdminPage() {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
		LocalDateTime date = timeManager.getDateTime();
		ModelAndView mv;
		if (shiftService.getLast() == null || !shiftService.getLast().getOpen()) {
			mv = new ModelAndView("shift/shiftPage");
			mv.addObject("list", workerService.getAllActiveWorker());
			mv.addObject("date", dateTimeFormatter.format(date));
		} else {
			mv = new ModelAndView("shift/editingShiftPage");
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
		ModelAndView mv = new ModelAndView("shift/editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	// delete worker from shift
	@RequestMapping(value = "/manager/shift/delWorker", method = RequestMethod.POST)
	public ModelAndView deleteWorkerFromShift(@RequestParam(name = "delWorker") String name) {
		shiftService.deleteWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("shift/editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	// add worker on shift
	@RequestMapping(value = "/manager/shift/addWorker", method = RequestMethod.POST)
	public ModelAndView addWorkerFromShift(@RequestParam(name = "addWorker") String name) {
		shiftService.addWorkerFromShift(name);
		ModelAndView mv = new ModelAndView("shift/editingShiftPage");
		mv.addObject("workersOfShift", shiftService.getLast().getUsers());
		mv.addObject("allWorkers", shiftService.getWorkers());
		return mv;
	}

	@RequestMapping(value = "manager/shift/endOfShift", method = RequestMethod.GET)
	public String closeShift(@RequestParam(name = "bonus") Long[] workerBonus,
							 @RequestParam(name = "idWorker") Long[] idWorker,
							 @RequestParam(name = "salaryShift") Double shiftProfit,
							 @RequestParam(name = "profitShift") Double profitShift,
							 @RequestParam(name = "cache") Long cache,
							 @RequestParam(name = "payWithCard") Long payWithCard) {

		Map<Long, Long> workerIdBonusMap = new HashMap<>();
		for (int i = 0; i < workerBonus.length; i++) {
			for (int j = 0; j < idWorker.length; j++) {
				workerIdBonusMap.put(idWorker[j], workerBonus[j]);
			}
		}
		for (Map.Entry<Long, Long> entry : workerIdBonusMap.entrySet()) {
			Worker worker = workerService.findOne(entry.getKey());
			Long bonus = worker.getBonus();
			bonus = bonus + entry.getValue();
			worker.setBonus(bonus);
			Long shiftSalary = worker.getShiftSalary();
			Long salaryWorker = worker.getSalary();
			salaryWorker = salaryWorker + shiftSalary;
			worker.setSalary(salaryWorker);
			workerService.saveAndFlush(worker);
		}
		if ((cache + payWithCard) > shiftProfit) {
			List<Boss> bossList = bossRepository.getAllActiveBoss();
			emailService.sendCloseShiftInfoFromText(shiftProfit, profitShift, cache, payWithCard, bossList);
		}
		shiftService.closeShift();
		return "redirect:/login";
	}
}

