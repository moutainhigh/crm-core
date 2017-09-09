package com.cafe.crm.controllers.boss;

import com.cafe.crm.dto.TotalStatisticView;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/boss/totalStatistics")
public class StatisticController {

	private final ShiftService shiftService;
	private final TimeManager timeManager;
	private final CostService costService;

	@Autowired
	public StatisticController(CostService costService, ShiftService shiftService, TimeManager timeManager) {
		this.costService = costService;
		this.shiftService = shiftService;
		this.timeManager = timeManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showStatisticForAllTimePage() {
		ModelAndView modelAndView = new ModelAndView("totalStatistics");
		LocalDate now = getShiftDate();
		TotalStatisticView totalStatisticView = createTotalStatisticView(now, now);
		Shift lastShift = shiftService.getLast();
		Set<Shift> allShiftsBetween = shiftService.findByDates(now, now);
		if (lastShift != null) {
			modelAndView.addObject("cashBox", lastShift.getCashBox());
			modelAndView.addObject("shifts", allShiftsBetween);
		} else {
			modelAndView.addObject("cashBox", 0D);
		}
		modelAndView.addObject("from", now);
		modelAndView.addObject("to", now);
		modelAndView.addObject("totalStat", totalStatisticView);
		return modelAndView;

	}

	@RequestMapping(value = "/searchByDate", method = RequestMethod.POST)
	public ModelAndView searchByDateStat(@RequestParam("from") String from, @RequestParam("to") String to) {
		ModelAndView modelAndView = new ModelAndView("totalStatistics");
		TotalStatisticView totalStatisticView = createTotalStatisticView(LocalDate.parse(from), LocalDate.parse(to));
		Set<Shift> allShiftsBetween = shiftService.findByDates(LocalDate.parse(from), LocalDate.parse(to));
		Shift lastShift = new Shift();
		int count = 0;
		for (Shift shift: allShiftsBetween) {
			count++;
			if (allShiftsBetween.size() == count)
				lastShift = shift;
		}
		if (lastShift != null) {
			modelAndView.addObject("cashBox", lastShift.getCashBox());
			modelAndView.addObject("shifts", allShiftsBetween);
		} else {
			modelAndView.addObject("cashBox", 0D);
		}
		modelAndView.addObject("from", from);
		modelAndView.addObject("to", to);
		modelAndView.addObject("totalStat", totalStatisticView);
		return modelAndView;
	}

	private LocalDate getShiftDate() {
		LocalTime now = timeManager.getTime();
		if (now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.NOON)) {
			return timeManager.getDate().minusDays(1);
		}

		return timeManager.getDate();
	}

	private TotalStatisticView createTotalStatisticView(LocalDate from, LocalDate to) {
		Set<Shift> shifts = shiftService.findByDates(from, to);
		double profit = 0D;
		double totalShiftSalary = 0D;
		double otherCosts = 0D;
		Set<User> users = new HashSet<>();
		List<Client> clients = new ArrayList<>();
		List<Cost> otherCost = new ArrayList<>();
		if (shifts == null) {
			return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clients, otherCost);
		}
		for(Shift shift : shifts) {
			users.addAll(shift.getUsers());
			clients.addAll(shift.getClients());
			otherCost.addAll(costService.findByShiftId(shift.getId()));
		}
		for (User user : users) {
			user.getShifts().retainAll(shifts);
			int totalSalary = user.getShiftSalary() * user.getShifts().size() + user.getBonus();
			user.setSalary(totalSalary);
			totalShiftSalary += totalSalary;
		}
		for (Cost cost : otherCost) {
			otherCosts += cost.getPrice() * cost.getQuantity();
		}
		for (Client client : clients) {
			profit += client.getAllPrice();
		}
		return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clients, otherCost);
	}

}
