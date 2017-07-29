package com.cafe.crm.controllers.shift;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.shift.ShiftView;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.vk.VkService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

	@Autowired
	private VkService vkService;

	private final Pattern VALID_CACHE_SALARY_REGEX =
			Pattern.compile("\\d+");

	@Transactional
	@RequestMapping(value = "/manager/shift/", method = RequestMethod.GET)
	public String getAdminPage(Model model) {

		Shift lastShift = shiftService.getLast();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
		LocalDateTime date = timeManager.getDateTime();
		if (lastShift != null && lastShift.getOpen()) {
			return "redirect:/manager";
		} else if (lastShift != null) {
			model.addAttribute("shiftCashBox", shiftService.getLast().getCashBox());
			model.addAttribute("bankCashBox", shiftService.getLast().getBankCashBox());
			model.addAttribute("list", workerService.getAllActiveWorker());
			model.addAttribute("date", dateTimeFormatter.format(date));
			return "shift/shiftPage";

		} else {
			model.addAttribute("list", workerService.getAllActiveWorker());
			model.addAttribute("date", dateTimeFormatter.format(date));
			return "shift/shiftPage";
		}
	}


	@Transactional
	@RequestMapping(value = "/manager/shift/begin", method = RequestMethod.POST)
	public String beginShift(@RequestParam(name = "box", required = false) long[] box,
	                         @RequestParam(name = "cashBox", required = false) Double cashBox,
	                         @RequestParam(name = "bankCashBox", required = false) Double bankCashBox) {

		Shift lastShift = shiftService.getLast();
		Worker worker = (Worker) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (lastShift == null) {                     // if this first shift
			if (box == null) {
				shiftService.newShift(cashBox, bankCashBox, worker.getId());
			} else {
				shiftService.newShift(cashBox, bankCashBox, box);
			}
			return "redirect:/manager";
		}
		if (!lastShift.getOpen()) {                 // if shift is closed
			if (box == null) {
				shiftService.newShift(lastShift.getCashBox(), lastShift.getBankCashBox(), worker.getId());
			} else {
				shiftService.newShift(lastShift.getCashBox(), lastShift.getBankCashBox(), box);
			}
			return "redirect:/manager";
		} else {                                                  // if shift is open
			return "redirect:/manager";
		}
	}

	// get all workers of shift
	@RequestMapping(value = "/manager/shift/edit", method = RequestMethod.GET)
	public String editPage(Model model) {
		Shift lastShift = shiftService.getLast();
		Set<Calculate> calculateSet = lastShift.getAllCalculate();
		for (Calculate calculate : calculateSet) {
			List<Client> clientsOnCalculate = calculate.getClient();
			model.addAttribute("clientsOnCalculate", clientsOnCalculate);
		}
		model.addAttribute("workersOfShift", lastShift.getUsers());
		model.addAttribute("allWorkers", shiftService.getWorkers());
		model.addAttribute("CloseShiftView", shiftService.createShiftView(lastShift));
		model.addAttribute("calculate", calculateSet);
		model.addAttribute("client", lastShift.getClients());
		return "shift/editingShiftPage";
	}

	// delete worker from shift
	@RequestMapping(value = "/manager/shift/delWorker", method = RequestMethod.POST)
	public String deleteWorkerFromShift(@RequestParam(name = "delWorker") String name) {

		shiftService.deleteWorkerFromShift(name);

		return "redirect:/manager/shift/edit";
	}

	// add worker on shift
	@RequestMapping(value = "/manager/shift/addWorker", method = RequestMethod.POST)
	public String addWorkerFromShift(@RequestParam(name = "addWorker") String name) {

		shiftService.addWorkerFromShift(name);

		return "redirect:/manager/shift/edit";
	}

	@RequestMapping(value = "/manager/endOfShift", method = RequestMethod.GET)
	public String closeShift(@RequestParam(name = "bonus") Long[] workerBonus,
	                         @RequestParam(name = "idWorker") Long[] idWorker,
	                         @RequestParam(name = "cache") Double cache,
	                         @RequestParam(name = "bankKart") Double bankKart,
							 @RequestParam(name = "comment") String comment) {
		Shift lastShift = shiftService.getLast();
		Matcher matcherCache = VALID_CACHE_SALARY_REGEX.matcher(String.valueOf(cache));
		Matcher matcherBankKart = VALID_CACHE_SALARY_REGEX.matcher(String.valueOf(bankKart));

		if (matcherCache.find() && matcherBankKart.find()) {
			Double bonus = 0D;
			Map<Long, Long> workerIdBonusMap = new HashMap<>();
			for (int i = 0; i < idWorker.length; i++) {
				workerIdBonusMap.put(idWorker[i], workerBonus[i]);
			}

			ShiftView shiftView = shiftService.createShiftView(lastShift);
			Double primaryCashBox = shiftView.getCashBox();
			Double allPrice = shiftView.getAllPrice();
			Long salaryWorkerOnShift = shiftView.getSalaryWorker();
			Double otherCosts = shiftView.getOtherCosts();
			Double payWithCard = shiftView.getCard();
			for (int i = 0; workerBonus.length > i; i++) {
				bonus = bonus + workerBonus[i];
			}

			Double totalCashBox = (primaryCashBox + allPrice) - (salaryWorkerOnShift + otherCosts + bonus);
			Double shortage = totalCashBox - (cache + payWithCard + bankKart); //недосдача

			if ((cache + bankKart + payWithCard) >= totalCashBox) {
				Shift shift = shiftService.closeShift(totalCashBox, workerIdBonusMap, allPrice, cache, bankKart, comment);
				vkService.sendDailyReportToConference(shift);
				return "redirect:/login";
			} else {
				List<Boss> bossList = bossRepository.getAllActiveBoss();
				emailService.sendCloseShiftInfoFromText(totalCashBox, cache, bankKart, payWithCard, allPrice, bossList, shortage);
				Shift shift = shiftService.closeShift(totalCashBox, workerIdBonusMap, allPrice, cache, bankKart, comment);
				vkService.sendDailyReportToConference(shift);
			}
		} else {
			return "redirect:/login";
		}
		return "redirect:/login";
	}


	@ResponseBody
	@RequestMapping(value = "/manager/recalculation", method = RequestMethod.POST)
	public List<Object> recalculation(@RequestParam(name = "bonus") Long[] workerBonus) {
		Shift lastShift = shiftService.getLast();
		Double bonus = 0D;

		for (int i = 0; workerBonus.length > i; i++) {
			bonus = bonus + workerBonus[i];
		}
		ShiftView shiftView = shiftService.createShiftView(lastShift);
		Double primaryCashBox = shiftView.getCashBox();
		Double allPrice = shiftView.getAllPrice();
		Double salaryWorkerOnShift = shiftView.getSalaryWorker() + bonus;
		Double otherCosts = shiftView.getOtherCosts();
		Double totalCashBox = (primaryCashBox + allPrice) - (salaryWorkerOnShift + otherCosts);
		List<Object> testList = new ArrayList<>();
		testList.add(salaryWorkerOnShift);
		testList.add(totalCashBox);
		return testList;
	}


}

