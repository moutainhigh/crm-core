package com.cafe.crm.controllers.boss;

import com.cafe.crm.dto.TotalStatisticView;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.goods.GoodsService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/boss/totalStatistics")
public class StatisticController {

	private final ShiftService shiftService;
	private final TimeManager timeManager;
	private final GoodsService goodsService;

	@Value("${cost-category-name.salary-for-shift}")
	private String categoryNameSalaryForShift;

	@Autowired
	public StatisticController(GoodsService goodsService, ShiftService shiftService, TimeManager timeManager) {
		this.goodsService = goodsService;
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
			modelAndView.addObject("shift", allShiftsBetween);
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
		Shift lastShift = shiftService.getLast();
		Set<Shift> allShiftsBetween = shiftService.findByDates(LocalDate.parse(from), LocalDate.parse(to));
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
		List<User> users = new ArrayList<>();
		List<Client> clients = new ArrayList<>();
		List<Goods> salaryGoods = new ArrayList<>();
		List<Goods> otherGoods = new ArrayList<>();

		if (shifts == null) {
			return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clients, salaryGoods, otherGoods);
		}
		for (Shift shift : shifts) {
			users.addAll(shift.getUsers());
			clients.addAll(shift.getClients());
			otherGoods.addAll(goodsService.findByShiftId(shift.getId()));
		}

		salaryGoods.addAll(goodsService.findByCategoryNameAndDateBetween(categoryNameSalaryForShift, from, to));
		otherGoods.removeAll(salaryGoods);
		for (Goods goods : salaryGoods) {
			if (goods.getCategory().getName().equalsIgnoreCase(categoryNameSalaryForShift)) {
				totalShiftSalary += (goods.getPrice() * goods.getQuantity());
			} else {
				otherCosts += (goods.getPrice() * goods.getQuantity());
			}
		}
		for (Client client : clients) {
			profit += client.getAllPrice();
		}
		return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clients, salaryGoods, otherGoods);
	}

}
