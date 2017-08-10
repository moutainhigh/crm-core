package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.dto.TotalStatisticView;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/boss/totalStatistics")
public class TotalStatistic {

	private final ShiftService shiftService;
	private final TimeManager timeManager;
	private final GoodsService goodsService;

	@Value("${cost-category-name.salary-for-shift}")
	private String categoryNameSalaryForShift;

	@Autowired
	public TotalStatistic(GoodsService goodsService, ShiftService shiftService, TimeManager timeManager) {
		this.goodsService = goodsService;
		this.shiftService = shiftService;
		this.timeManager = timeManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showStatisticForAllTimePage() {
		ModelAndView modelAndView = new ModelAndView("totalStatistics");
		LocalDate now = timeManager.getDate();
		TotalStatisticView totalStatisticView = createTotalStatisticView(shiftService.getLast());
		if (shiftService.getLast() == null) {
			modelAndView.addObject("cashBox", 0D);
		} else {
			modelAndView.addObject("cashBox", shiftService.getLast().getCashBox());
			modelAndView.addObject("shift", shiftService.getLast());
		}
		modelAndView.addObject("date", now);
		modelAndView.addObject("totalStat", totalStatisticView);
		return modelAndView;

	}

	@RequestMapping(value = "/searchByDate", method = RequestMethod.POST)
	public ModelAndView searchByDateStat(@RequestParam("start") String start) {
		ModelAndView modelAndView = new ModelAndView("totalStatistics");
		Shift shift = shiftService.findByDateShift(LocalDate.parse(start));
		TotalStatisticView totalStatisticView = createTotalStatisticView(shift);
		if (shift != null) {
			modelAndView.addObject("cashBox", shiftService.getLast().getCashBox());
			modelAndView.addObject("shift", shiftService.getLast());
		} else {
			modelAndView.addObject("cashBox", 0D);
		}
		modelAndView.addObject("date", start);
		modelAndView.addObject("totalStat", totalStatisticView);
		return modelAndView;
	}

	private TotalStatisticView createTotalStatisticView(Shift shift) {
		double profit = 0D;
		double totalShiftSalary = 0D;
		double otherCosts = 0D;
		List<User> users = new ArrayList<>();
		List<Client> clients = new ArrayList<>();
		List<Goods> salaryGoods = new ArrayList<>();
		List<Goods> otherGoods = new ArrayList<>();
		if (shift == null) {
			return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clients, salaryGoods, otherGoods);
		}
		users.addAll(shift.getUsers());
		clients.addAll(shift.getClients());
		salaryGoods.addAll(goodsService.findByDateAndCategoryNameAndVisibleTrue(shift.getShiftDate(),
				categoryNameSalaryForShift));
		otherGoods.addAll(goodsService.findByDateAndVisibleTrue(shift.getShiftDate()));
		otherGoods.removeAll(salaryGoods);
		for (Goods goods : salaryGoods) {
			totalShiftSalary += (goods.getPrice() * goods.getQuantity());
		}
		for (Goods goods : otherGoods) {
			otherCosts += (goods.getPrice() * goods.getQuantity());
		}
		for (Client client : clients) {
			profit += client.getAllPrice();
		}
		return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clients, salaryGoods, otherGoods);
	}

}
