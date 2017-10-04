package com.cafe.crm.controllers.boss;

import com.cafe.crm.dto.TotalStatisticView;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.dto.ClientDetails;
import com.cafe.crm.utils.RoundUpper;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
        LocalDate dateFrom = shiftService.getLastShiftDate();
        LocalDate dateTo = timeManager.getDate();
        TotalStatisticView totalStatisticView = createTotalStatisticView(dateFrom, dateTo);
        Shift lastShift = shiftService.getLast();
        Set<Shift> allShiftsBetween = shiftService.findByDates(dateFrom, dateTo);
        if (lastShift != null) {
			double totalCashBox = lastShift.getCashBox() + lastShift.getBankCashBox();
            modelAndView.addObject("cashBox", totalCashBox);
            modelAndView.addObject("shifts", allShiftsBetween);
            dateFrom = lastShift.getShiftDate();
        } else {
            modelAndView.addObject("cashBox", 0D);
        }
        modelAndView.addObject("from", dateFrom);
        modelAndView.addObject("to", dateTo);
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
        for (Shift shift : allShiftsBetween) {
            count++;
            if (allShiftsBetween.size() == count)
                lastShift = shift;
        }
        if (lastShift != null) {
			double totalCashBox = lastShift.getCashBox() + lastShift.getBankCashBox();
            modelAndView.addObject("cashBox", totalCashBox);
            modelAndView.addObject("shifts", allShiftsBetween);
        } else {
            modelAndView.addObject("cashBox", 0D);
        }
        modelAndView.addObject("from", from);
        modelAndView.addObject("to", to);
        modelAndView.addObject("totalStat", totalStatisticView);
        return modelAndView;
    }

    private TotalStatisticView createTotalStatisticView(LocalDate from, LocalDate to) {
        Set<Shift> shifts = shiftService.findByDates(from, to);
        double profit = 0D;
        double totalShiftSalary = 0D;
        double otherCosts = 0D;
        Set<User> users = new HashSet<>();
		Set<Calculate> allCalculate = new HashSet<>();
		Map<Client, ClientDetails> clientsOnDetails = new HashMap<>();
		Set<Client> roundedClients = new HashSet<>();
        Set<Client> notRoundedClients = new HashSet<>();
        List<Cost> otherCost = new ArrayList<>();
        List<Debt> givenDebts = new ArrayList<>();
        List<Debt> repaidDebt = new ArrayList<>();
        if (shifts == null) {
            return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clientsOnDetails, otherCost,
					givenDebts, repaidDebt);
        }
        for (Shift shift : shifts) {
			allCalculate = shift.getCalculates();
            users.addAll(shift.getUsers());
            otherCost.addAll(costService.findByShiftId(shift.getId()));
            givenDebts.addAll(shift.getGivenDebts());
            repaidDebt.addAll(shift.getRepaidDebts());
        }
        for (Calculate calculate : allCalculate) {
        	if (calculate.isRoundState()) {
        		roundedClients.addAll(calculate.getClient());
			} else {
				notRoundedClients.addAll(calculate.getClient());
			}
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
        for (Client roundClient : roundedClients) {
			ClientDetails details = getClientDetails(roundClient, true);
			clientsOnDetails.put(roundClient, details);
			profit += details.getAllDirtyPrice();
		}
		for (Client notRoundClient : notRoundedClients) {
			ClientDetails details = getClientDetails(notRoundClient, false);
			clientsOnDetails.put(notRoundClient, details);
			profit += details.getAllDirtyPrice();
		}
        for (Debt repDebt : repaidDebt) {
        	profit += repDebt.getDebtAmount();
		}
		for (Debt givDebt : givenDebts) {
        	profit -= givDebt.getDebtAmount();
		}
        return new TotalStatisticView(profit, totalShiftSalary, otherCosts, users, clientsOnDetails, otherCost,
				givenDebts, repaidDebt);
    }

    private ClientDetails getClientDetails(Client client, boolean isRounded) {
    	Double allDirtyPrice;
    	Double dirtyPriceMenu = 0D;
    	Double otherPriceMenu = 0D;
    	for (LayerProduct product : client.getLayerProducts()) {
    		if (product.isDirtyProfit()) {
    			dirtyPriceMenu += product.getCost() / product.getClients().size();
			} else {
    			otherPriceMenu += product.getCost() / product.getClients().size();
			}
		}
		allDirtyPrice = client.getPriceTime() + Math.round(dirtyPriceMenu) - client.getPayWithCard();
		if (isRounded) {
			allDirtyPrice = RoundUpper.roundDouble(allDirtyPrice);
			dirtyPriceMenu = RoundUpper.roundDouble(dirtyPriceMenu);
			otherPriceMenu = RoundUpper.roundDouble(otherPriceMenu);
		}

		return new ClientDetails(allDirtyPrice, Math.round(otherPriceMenu), Math.round(dirtyPriceMenu));
	}

}
