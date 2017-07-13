package com.cafe.crm.controllers.debt;

import com.cafe.crm.models.client.Debt;
import com.cafe.crm.services.interfaces.debt.DebtService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class DebtController {

	private DebtService debtService;

	private TimeManager timeManager;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@Autowired
	public DebtController(DebtService debtService, TimeManager timeManager) {
		this.debtService = debtService;
		this.timeManager = timeManager;
	}

	@RequestMapping(value = "/manager/tableDebt", method = RequestMethod.GET)
	public ModelAndView showDebtPage() {
		LocalDate today = timeManager.getDate();
		List<Debt> debtList = debtService.findByVisibleIsTrueAndDateBetween(today, today.plusYears(100));
		Double totalDebtAmount = getTotalPrice(debtList);

		ModelAndView modelAndView = new ModelAndView("/debt/debt");
		modelAndView.addObject("debtsList", debtList);
		modelAndView.addObject("totalDebtAmount", totalDebtAmount);
		modelAndView.addObject("debtorName", null);
		modelAndView.addObject("formDebt", new Debt());
		modelAndView.addObject("today", today);
		modelAndView.addObject("fromDate", today);
		modelAndView.addObject("toDate", null);

		return modelAndView;
	}

	@RequestMapping(value = "/manager/tableDebt", method = RequestMethod.POST)
	public ModelAndView updatePageAfterSearch(@RequestParam(name = "fromDate") String fromDate,
	                                          @RequestParam(name = "toDate") String toDate,
	                                          @RequestParam(name = "debtorName") String debtorName) {

		LocalDate today = timeManager.getDate();
		List<Debt> debtList = filter(debtorName, fromDate, toDate);
		Double totalDebtAmount = getTotalPrice(debtList);

		LocalDate from = (fromDate == null || fromDate.isEmpty()) ? null : LocalDate.parse(fromDate, formatter);
		LocalDate to = (toDate == null || toDate.isEmpty()) ? null : LocalDate.parse(toDate, formatter);

		ModelAndView modelAndView = new ModelAndView("/debt/debt");
		modelAndView.addObject("debtsList", debtList);
		modelAndView.addObject("totalDebtAmount", totalDebtAmount);
		modelAndView.addObject("debtorName", debtorName);
		modelAndView.addObject("formDebt", new Debt());
		modelAndView.addObject("today", today);
		modelAndView.addObject("fromDate", from);
		modelAndView.addObject("toDate", to);

		return modelAndView;
	}

	private List<Debt> filter(String debtorName, String fromDate, String toDate) {
		debtorName = (debtorName == null) ? null : debtorName.trim();

		LocalDate today = timeManager.getDate();
		LocalDate from = (fromDate == null || fromDate.isEmpty())
				? today.minusYears(100) : LocalDate.parse(fromDate, formatter);
		LocalDate to = (toDate == null || toDate.isEmpty())
				? today.plusYears(100) : LocalDate.parse(toDate, formatter);

		if (debtorName == null || debtorName.isEmpty()) {
			return debtService.findByVisibleIsTrueAndDateBetween(from, to);
		} else {
			return debtService.findByDebtorAndDateBetween(debtorName, from, to);
		}

	}

	private Double getTotalPrice(List<Debt> goodsList) {
		return goodsList
				.stream().mapToDouble(Debt::getDebtAmount).sum();
	}

	@RequestMapping(value = "/manager/tableDebt/addDebt", method = RequestMethod.POST)
	public String saveGoods(@ModelAttribute @Valid Debt debt) {

		debtService.save(debt);

		return "redirect:";
	}

	@RequestMapping(value = "/manager/tableDebt/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteGoods(@RequestParam(name = "debtId") Long id) {

		debtService.delete(id);

		return ResponseEntity.ok("Товар успешно удален!");
	}
}
