package com.cafe.crm.controllers.cost;

import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.services.interfaces.goods.GoodsCategoryService;
import com.cafe.crm.services.interfaces.goods.GoodsService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/manager")
public class CostsController {

	private final TimeManager timeManager;

	private final GoodsService goodsService;

	private final GoodsCategoryService goodsCategoryService;

	private final ShiftService shiftService;

	@Autowired
	public CostsController(GoodsService goodsService, GoodsCategoryService goodsCategoryService, TimeManager timeManager,
						   ShiftService shiftService) {
		this.goodsService = goodsService;
		this.goodsCategoryService = goodsCategoryService;
		this.timeManager = timeManager;
		this.shiftService = shiftService;
	}

	@RequestMapping(value = "/costs", method = RequestMethod.GET)
	public String showCostsPage(Model model) {
		LocalDate today = getShiftDate();
		List<Goods> goodsList = goodsService.findByDateBetween(today, today.plusYears(100));
		Double totalPrice = getTotalPrice(goodsList);

		model.addAttribute("goodsList", goodsList);
		model.addAttribute("categoryName", null);
		model.addAttribute("goodsName", null);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("formGoods", new Goods());
		model.addAttribute("today", today);
		model.addAttribute("fromDate", today);
		model.addAttribute("toDate", null);
		model.addAttribute("CloseShiftView", shiftService.createShiftView(shiftService.getLast()));
		return "costs/costs";
	}

	@RequestMapping(value = "/costs", method = RequestMethod.POST)
	public String showCostsPageWithParameters(@RequestParam(name = "fromDate") String fromDate,
											  @RequestParam(name = "toDate") String toDate,
											  @RequestParam(name = "goodsName") String goodsName,
											  @RequestParam(name = "categoryName") String categoryName,
											  Model model) {
		LocalDate today = getShiftDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		List<Goods> goodsList = getGoodses(goodsName, categoryName, fromDate, toDate, formatter);
		Double totalPrice = getTotalPrice(goodsList);

		LocalDate from = (fromDate == null || fromDate.isEmpty()) ? null : LocalDate.parse(fromDate, formatter);
		LocalDate to = (toDate == null || toDate.isEmpty()) ? null : LocalDate.parse(toDate, formatter);

		model.addAttribute("goodsList", goodsList);
		model.addAttribute("goodsName", goodsName);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("formGoods", new Goods());
		model.addAttribute("today", today);
		model.addAttribute("fromDate", from);
		model.addAttribute("toDate", to);
		model.addAttribute("ShiftView", shiftService.createShiftView(shiftService.getLast()));

		return "costs/costs";
	}

	private LocalDate getShiftDate() {
		LocalTime now = timeManager.getTime();
		if (now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.NOON)) {
			return timeManager.getDate().minusDays(1);
		}

		return timeManager.getDate();
	}

	private Double getTotalPrice(List<Goods> goodsList) {
		return goodsList
				.stream().mapToDouble(goods -> goods.getPrice() * goods.getQuantity()).sum();
	}

	private List<Goods> getGoodses(String goodsName, String categoryName, String fromDate, String toDate, DateTimeFormatter formatter) {
		goodsName = (goodsName == null) ? null : goodsName.trim();
		categoryName = (categoryName == null) ? null : categoryName.trim();

		LocalDate today = timeManager.getDate();
		LocalDate from = (fromDate == null || fromDate.isEmpty())
				? today.minusYears(100) : LocalDate.parse(fromDate, formatter);
		LocalDate to = (toDate == null || toDate.isEmpty())
				? today.plusYears(100) : LocalDate.parse(toDate, formatter);

		if ((goodsName == null || goodsName.isEmpty()) && (categoryName == null || categoryName.isEmpty())) {
			return goodsService.findByDateBetween(from, to);
		} else if (goodsName == null || goodsName.isEmpty()) {
			return goodsService.findByCategoryNameAndDateBetween(categoryName, from, to);
		} else if (categoryName == null || categoryName.isEmpty()) {
			return goodsService.findByNameAndDateBetween(goodsName, from, to);
		}
		return goodsService.findByNameAndCategoryNameAndDateBetween(goodsName, categoryName, from, to);
	}

	@RequestMapping(value = "/costs/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveGoods(@ModelAttribute @Valid Goods goods, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Не удалось добавить товар!");
		}
		goodsService.save(goods);

		return ResponseEntity.ok("Товар успешно добавлен!");
	}

	@RequestMapping(value = "/costs/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateGoods(@ModelAttribute @Valid Goods goods, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Не удалось изменить товар!");
		}
		goodsService.save(goods);

		return ResponseEntity.ok("Товар успешно изменен!");
	}

	@RequestMapping(value = "/costs/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteGoods(@RequestParam(name = "goodsId") Long id) {
		goodsService.offVisibleStatus(id);

		return ResponseEntity.ok("Товар успешно удален!");
	}

	@RequestMapping(value = "costs/delete/all", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteAllGoods(@RequestParam(name = "ids") String ids) {
		String[] strIds = ids.replace("[", "").replace("]", "").replace("\"", "").split(",");
		try {
			long[] longIds = Arrays.stream(strIds).mapToLong(Long::parseLong).toArray();
			goodsService.offVisibleStatus(longIds);
		} catch (NumberFormatException ex) {
			return ResponseEntity.badRequest().body("Не удалось удалить товары!");
		}

		return ResponseEntity.ok("Товары успешно удалены!");
	}

	@RequestMapping(value = "/costs/search/category", method = RequestMethod.GET)
	@ResponseBody
	public String[] getCategoryStartWith(@RequestParam(name = "name") String startName) {
		Set<GoodsCategory> categories = goodsCategoryService.findByNameStartingWith(startName);

		return categories.stream().map(GoodsCategory::getName).toArray(String[]::new);
	}

	@RequestMapping(value = "/costs/search/goods", method = RequestMethod.GET)
	@ResponseBody
	public String[] getGoodsStartWith(@RequestParam(name = "name") String startName) {
		Set<Goods> goodsList = goodsService.findByNameStartingWith(startName);

		return goodsList.stream().map(Goods::getName).toArray(String[]::new);
	}

}
