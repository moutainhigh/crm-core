package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.shift.TotalStatisticView;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.services.interfaces.goods.GoodsService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/boss/totalStatistics")
public class TotalStatistic {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private TimeManager timeManager;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewTotalStatisticPageForAllTime() {
        ModelAndView modelAndView = new ModelAndView("totalStatistics");
        LocalDate now = timeManager.getDate();
        TotalStatisticView totalStatisticView = createTotalStatisticView(shiftService.getLast());
        if (shiftService.getLast() == null) {
            modelAndView.addObject("cashBox", 0D);
            modelAndView.addObject("totalStat", totalStatisticView);
            modelAndView.addObject("date", now);
            return modelAndView;
        } else {
            modelAndView.addObject("date", now);
            modelAndView.addObject("totalStat", totalStatisticView);
            modelAndView.addObject("cashBox", shiftService.getLast().getCashBox());
            modelAndView.addObject("shift", shiftService.getLast());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/searchByDate", method = RequestMethod.POST)
    public ModelAndView searchByDateStat(@Param("start") String start) {
        ModelAndView modelAndView = new ModelAndView("totalStatistics");
        Shift shift = shiftService.findByDateShift(LocalDate.parse(start));
        TotalStatisticView totalStatisticView = createTotalStatisticView(shift);
        if (shift != null) {
            modelAndView.addObject("date", start);
            modelAndView.addObject("totalStat", totalStatisticView);
            modelAndView.addObject("cashBox", shiftService.getLast().getCashBox());
            modelAndView.addObject("shift", shiftService.getLast());
        } else {
            modelAndView.addObject("date", start);
            modelAndView.addObject("cashBox", 0D);
            modelAndView.addObject("totalStat", totalStatisticView);
            return modelAndView;
        }
        return modelAndView;
    }

    private TotalStatisticView createTotalStatisticView(Shift shift) {
        Double profit = 0D;
        Double costSalary = 0D;
        Double otherCosts = 0D;
        Set<Worker> workers = new HashSet<>();
        Set<Client> clients = new HashSet<>();
        Set<Goods> salaryGoods = new HashSet<>();
        Set<Goods> otherGoods = new HashSet<>();
        if (shift == null) {
            return new TotalStatisticView(profit, costSalary, otherCosts, workers, clients, salaryGoods, otherGoods);
        } else if (shift.getUsers() != null && shift.getClients() == null) {
            workers.addAll(shift.getUsers());
            return new TotalStatisticView(profit, costSalary, otherCosts, workers, clients, salaryGoods, otherGoods);
        } else if (shift.getUsers() == null && shift.getClients() == null) {
            return new TotalStatisticView(profit, costSalary, otherCosts, workers, clients, salaryGoods, otherGoods);
        } else {
            workers.addAll(shift.getUsers());
            clients.addAll(shift.getClients());
            salaryGoods.addAll(goodsService.findByDateAndCategoryNameAndVisibleTrue(shift.getDateShift(),
                    "Зарплата сотрудников"));
            otherGoods.addAll(goodsService.findByDateAndVisibleTrue(shift.getDateShift()));
            otherGoods.removeAll(salaryGoods);
            for (Goods goods : salaryGoods) {
                costSalary = (goods.getPrice() * goods.getQuantity()) + costSalary;
            }
            for (Goods goods : otherGoods) {
                otherCosts = otherCosts + (goods.getPrice() * goods.getQuantity());
            }
            for (Client client : clients) {
                profit = profit + client.getAllPrice();
            }
            return new TotalStatisticView(profit, costSalary, otherCosts, workers, clients, salaryGoods, otherGoods);
        }
    }
}
