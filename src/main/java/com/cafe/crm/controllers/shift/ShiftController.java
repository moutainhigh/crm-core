package com.cafe.crm.controllers.shift;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.shift.ShiftView;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
	public String getAdminPage(Model model) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
		LocalDateTime date = timeManager.getDateTime();
		if (shiftService.getLast() != null && shiftService.getLast().getOpen()) {
			return "redirect:/manager";
		} else if (shiftService.getLast() != null) {
			model.addAttribute("shiftCashBox", shiftService.getLast().getCashBox());
			model.addAttribute("list", workerService.getAllActiveWorker());
			model.addAttribute("date", dateTimeFormatter.format(date));
			return "shift/shiftPage";

		} else if (shiftService.getLast() == null || !shiftService.getLast().getOpen()) {
			model.addAttribute("list", workerService.getAllActiveWorker());
			model.addAttribute("date", dateTimeFormatter.format(date));
			return "shift/shiftPage";
		} else {
			return "shift/shiftPage";
		}
	}

	@RequestMapping(value = "/manager/shift/begin", method = RequestMethod.POST)
	public String beginShift(@RequestParam(name = "box", required = false) int[] box,
							 @RequestParam(name = "cashBox", required = false) Double cashBox) {
		if (shiftService.getLast() == null) {                     // if this first shift
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray, cashBox);
			} else {
				shiftService.newShift(box, cashBox);
			}
			return "redirect:/manager";
		}
		if (!shiftService.getLast().getOpen()) {                 // if shift is closed
			if (box == null) {
				int[] nullArray = new int[0];
				shiftService.newShift(nullArray, shiftService.getLast().getCashBox());
			} else {
				shiftService.newShift(box, shiftService.getLast().getCashBox());
			}
			return "redirect:/manager";

		} else {                                                  // if shift is open
			return "redirect:/manager";
		}
	}

	// get all workers of shift
	@RequestMapping(value = "/manager/shift/edit", method = RequestMethod.GET)
	public String editPage(Model model) {
		Shift shift = shiftService.getLast();
		Set<Calculate> calculateSet = shift.getAllCalculate();
		for (Calculate calculate : calculateSet) {
			List<Client> clientsOnCalculate = calculate.getClient();
			model.addAttribute("clientsOnCalculate", clientsOnCalculate);
		}
		model.addAttribute("workersOfShift", shiftService.getLast().getUsers());
		model.addAttribute("allWorkers", shiftService.getWorkers());
		model.addAttribute("CloseShiftView", shiftService.createShiftView(shiftService.getLast()));
		model.addAttribute("calculate", calculateSet);
		model.addAttribute("client", shift.getClients());
		return "shift/editingShiftPage";
	}

	// delete worker from shift
	@RequestMapping(value = "/manager/shift/delWorker", method = RequestMethod.POST)
	public String deleteWorkerFromShift(@RequestParam(name = "delWorker") String name) {
		shiftService.deleteWorkerFromShift(name);;
		return "redirect:/manager/shift/edit";
	}

	// add worker on shift
	@RequestMapping(value = "/manager/shift/addWorker", method = RequestMethod.POST)
	public String addWorkerFromShift(@RequestParam(name = "addWorker") String name) {
		shiftService.addWorkerFromShift(name);
		return "redirect:/manager/shift/edit";
	}

	@RequestMapping(value = "/endOfShift", method = RequestMethod.GET)
	public String closeShift(@RequestParam(name = "bonus") Long[] workerBonus,
							 @RequestParam(name = "idWorker") Long[] idWorker,
							 @RequestParam(name = "cache") Double cache,
							 @RequestParam(name = "bankKart") Double bankKart) {

		Map<Long, Long> workerIdBonusMap = new HashMap<>();
		for (int i = 0; i < workerBonus.length; i++) {
			for (int j = 0; j < idWorker.length; j++) {
				workerIdBonusMap.put(idWorker[j], workerBonus[j]);
			}
		}
		ShiftView shiftView = shiftService.createShiftView(shiftService.getLast());
		Double primaryCashBox = shiftView.getCashBox();
		Double allPrice = shiftView.getAllPrice();
		Long salaryWorkerOnShift = shiftView.getSalaryWorker();
		Double otherCosts = shiftView.getOtherCosts();
		Double payWithCard = shiftView.getCard();
		Double totalCashBox = (primaryCashBox + allPrice) - (salaryWorkerOnShift + otherCosts);
		Double shortage = totalCashBox - (cache + payWithCard + bankKart); //недосдача
		if ((cache + bankKart + payWithCard) >= totalCashBox) {
			shiftService.closeShift(totalCashBox, workerIdBonusMap, allPrice, shortage);
			return "redirect:/login";
		} else {
			List<Boss> bossList = bossRepository.getAllActiveBoss();
			emailService.sendCloseShiftInfoFromText(totalCashBox, cache, bankKart, payWithCard, allPrice, bossList, shortage);
			shiftService.closeShift(totalCashBox, workerIdBonusMap, allPrice, shortage);
		}
		return "redirect:/login";
	}
}

