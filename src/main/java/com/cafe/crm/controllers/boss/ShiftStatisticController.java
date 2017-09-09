package com.cafe.crm.controllers.boss;

import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/boss/statistics")
public class ShiftStatisticController {

	private final ShiftService shiftService;
	private final CostService costService;
	private final TimeManager timeManager;


	@Autowired
	public ShiftStatisticController(ShiftService shiftService, CostService costService, TimeManager timeManager) {
		this.shiftService = shiftService;
		this.costService = costService;
		this.timeManager = timeManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAdminPage() {
		ModelAndView mv = new ModelAndView("shift/shiftStatistics");
		List<Shift> allShifts = shiftService.findAll();
		LocalDate date = timeManager.getDate();
		mv.addObject("shifts", allShifts);
		mv.addObject("date", date);
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@Param("fromDate") String start, @Param("toDate") String end) {
		ModelAndView mv = new ModelAndView("shift/shiftStatistics");
		Set<Shift> dates = shiftService.findByDates(LocalDate.parse(start), LocalDate.parse(end));
		mv.addObject("shifts", dates);
		mv.addObject("date", start);
		return mv;
	}

	@RequestMapping(value = "/search/shiftDetail/{id}", method = RequestMethod.GET)
	public ModelAndView shiftDetail(@PathVariable("id") Long id) throws IOException {
		ModelAndView mv = new ModelAndView("shift/shiftDetail");
		Double allSalaryCost = 0D;
		Double allOtherCost = 0D;
		Shift shift = shiftService.findOne(id);
		ShiftView shiftView = shiftService.createShiftView(shift);
		for (User user : shift.getUsers()) {
				allSalaryCost += user.getShiftSalary() + user.getBonus();
		}
		List<Cost> otherCost = costService.findByDateAndVisibleTrue(shift.getShiftDate());
		for (Cost otherGood : otherCost) {
			allOtherCost = allOtherCost + otherGood.getPrice() * otherGood.getQuantity();
		}
		mv.addObject("shiftView", shiftView);
		mv.addObject("allSalaryCost", allSalaryCost);
		mv.addObject("allOtherCost", allOtherCost);
		mv.addObject("listOfOtherCosts", otherCost);
		return mv;
	}
}
