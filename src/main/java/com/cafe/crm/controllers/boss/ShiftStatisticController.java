package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.shift.ShiftView;
import com.cafe.crm.services.interfaces.goods.GoodsService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/boss/statistics")
public class ShiftStatisticController {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TimeManager timeManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
        ModelAndView mv = new ModelAndView("shift/shiftStatistics");
        List<Shift> dates = shiftService.findAll();
        LocalDate date = timeManager.getDate();
        mv.addObject("shifts", dates);
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
        Double allSalaryGoods = 0D;
        Double allOtherGoods = 0D;
        Shift shift = shiftService.findOne(id);
        ShiftView shiftView = shiftService.createShiftView(shift);
        List<Goods> salaryWorkerGoods = goodsService.findByDateAndCategoryNameAndVisibleTrue(shift.getDateShift(),
                "Зарплата сотрудников");
        List<Goods> otherGoods = goodsService.findByDateAndVisibleTrue(shift.getDateShift());
        otherGoods.removeAll(salaryWorkerGoods);
        for (Goods salaryWorkerGood : salaryWorkerGoods) {
            allSalaryGoods = allSalaryGoods + salaryWorkerGood.getPrice() * salaryWorkerGood.getQuantity();
        }
        for (Goods otherGood : otherGoods) {
            allOtherGoods = allOtherGoods + otherGood.getPrice() * otherGood.getQuantity();
        }
        mv.addObject("shiftView", shiftView);
        mv.addObject("salaryWorkerGoods", salaryWorkerGoods);
        mv.addObject("allSalaryGoods", allSalaryGoods);
        mv.addObject("allOtherGoods", allOtherGoods);
        mv.addObject("otherGoods", otherGoods);
        return mv;
    }
}
