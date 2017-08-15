package com.cafe.crm.controllers.shift;


import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.services.interfaces.vk.VkService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(path = "/manager")
public class ShiftController {

	private static final String EMAIL_RECIPIENT_ROLE_IN_CASE_SHORTAGE = "BOSS";
	private final ShiftService shiftService;
	private final UserService userService;
	private final TimeManager timeManager;
	private final EmailService emailService;
	private final VkService vkService;

	@Autowired
	public ShiftController(ShiftService shiftService, TimeManager timeManager, EmailService emailService, VkService vkService, UserService userService) {
		this.shiftService = shiftService;
		this.timeManager = timeManager;
		this.emailService = emailService;
		this.vkService = vkService;
		this.userService = userService;
	}

	@Transactional
	@RequestMapping(value = "/shift/", method = RequestMethod.GET)
	public String showStartShiftPage(Model model) {
		Shift lastShift = shiftService.getLast();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.YYYY");
		LocalDateTime date = timeManager.getDateTime();
		if (lastShift != null && lastShift.isOpen()) {
			return "redirect:/manager";
		} else if (lastShift != null) {
			model.addAttribute("shiftCashBox", shiftService.getLast().getCashBox());
			model.addAttribute("bankCashBox", shiftService.getLast().getBankCashBox());
		}
		model.addAttribute("users", userService.findAll());
		model.addAttribute("date", dateTimeFormatter.format(date));
		return "shift/shiftPage";
	}


	@Transactional
	@RequestMapping(value = "/shift/begin", method = RequestMethod.POST)
	public String startShift(@RequestParam(name = "userId", required = false) long[] usersIdsOnShift,
							 @RequestParam(name = "cashBox", required = false) Double cashBox,
							 @RequestParam(name = "bankCashBox", required = false) Double bankCashBox,
							 Authentication authentication) {
		Shift lastShift = shiftService.getLast();
		if (lastShift != null && lastShift.isOpen()) {
			return "redirect:/manager";
		}
		if (lastShift != null) {
			cashBox = lastShift.getCashBox();
			bankCashBox = lastShift.getBankCashBox();
		}
		if (usersIdsOnShift == null) {
			User user = userService.findByEmail(authentication.getName());
			shiftService.crateNewShift(cashBox, bankCashBox, user.getId());
		} else {
			shiftService.crateNewShift(cashBox, bankCashBox, usersIdsOnShift);
		}

		return "redirect:/manager";
	}

	@RequestMapping(value = "/shift/settings", method = RequestMethod.GET)
	public String showShiftSettingsPage(Model model) {
		Shift lastShift = shiftService.getLast();
		model.addAttribute("usersOnShift", lastShift.getUsers());
		model.addAttribute("usersNotOnShift", shiftService.getUsersNotOnShift());
		model.addAttribute("closeShiftView", shiftService.createShiftView(lastShift));
		model.addAttribute("calculates", lastShift.getCalculates());
		model.addAttribute("clients", lastShift.getClients());
		return "shift/shiftSettings";
	}

	@RequestMapping(value = "/shift/deleteUser", method = RequestMethod.POST)
	public String deleteUserFromShift(@RequestParam(name = "userId") Long userId) {
		shiftService.deleteUserFromShift(userId);

		return "redirect:/manager/shift/settings";
	}

	@RequestMapping(value = "/shift/addUser", method = RequestMethod.POST)
	public String addUserToShift(@RequestParam(name = "userId") Long userId) {
		shiftService.addUserToShift(userId);

		return "redirect:/manager/shift/settings";
	}

	@RequestMapping(value = "/shift/end", method = RequestMethod.POST)
	public String closeShift(@RequestParam(name = "usersBonus") Integer[] usersBonuses,
							 @RequestParam(name = "usersIds") Long[] usersIds,
							 @RequestParam(name = "cashBox") Double cashBox,
							 @RequestParam(name = "bankCashBox") Double bankCashBox,
							 @RequestParam(name = "comment") String comment) {
		Shift lastShift = shiftService.getLast();
		Map<Long, Integer> mapOfUsersIdsAndBonuses = new HashMap<>();
		for (int i = 0; i < usersIds.length; i++) {
			mapOfUsersIdsAndBonuses.put(usersIds[i], usersBonuses[i]);
		}

		ShiftView shiftView = shiftService.createShiftView(lastShift);
		lastShift.getDebts().clear();
		Double allPrice = shiftView.getAllPrice();
		Double payWithCard = shiftView.getCard();
		Integer totalBonusSum = 0;
		for (Integer userBonus : usersBonuses) {
			totalBonusSum = totalBonusSum + userBonus;
		}
		Double totalCashBox = shiftView.getTotalCashBox() - totalBonusSum;
		Double shortage = totalCashBox - (cashBox + bankCashBox);

		if (shortage < 0) {
			Shift shift = shiftService.closeShift(mapOfUsersIdsAndBonuses, allPrice, cashBox, bankCashBox, comment);
			vkService.sendDailyReportToConference(shift);
		} else {
			List<User> users = userService.findByRoleName(EMAIL_RECIPIENT_ROLE_IN_CASE_SHORTAGE);
			emailService.sendCloseShiftInfoFromText(totalCashBox, cashBox, bankCashBox, payWithCard, allPrice, users, shortage);
			Shift shift = shiftService.closeShift(mapOfUsersIdsAndBonuses, allPrice, cashBox, bankCashBox, comment);
			vkService.sendDailyReportToConference(shift);
		}

		return "redirect:/login";
	}


	@ResponseBody
	@RequestMapping(value = "/recalculation", method = RequestMethod.POST)
	public List<Object> recalculation(@RequestParam(name = "usersBonus") Integer[] usersBonuses) {
		Shift lastShift = shiftService.getLast();
		Integer totalBonusSum = 0;
		for (Integer userBonus : usersBonuses) {
			totalBonusSum += userBonus;
		}
		ShiftView shiftView = shiftService.createShiftView(lastShift);
		int salaryWorkerOnShift = shiftView.getUsersTotalShiftSalary() + totalBonusSum;
		Double totalCashBox = shiftView.getTotalCashBox() - totalBonusSum;
		List<Object> result = new ArrayList<>();
		result.add(salaryWorkerOnShift);
		result.add(totalCashBox);
		return result;
	}
}

