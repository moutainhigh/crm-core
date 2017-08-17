package com.cafe.crm.controllers.cost;

import com.cafe.crm.exceptions.cost.category.CostCategoryException;
import com.cafe.crm.exceptions.user.UserDataException;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.services.interfaces.goods.GoodsCategoryService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping(value = "/boss/cost/category")
public class CostCategoryController {

	private final GoodsCategoryService categoryService;
	private final TimeManager timeManager;

	@Autowired
	public CostCategoryController(GoodsCategoryService categoryService, TimeManager timeManager) {
		this.categoryService = categoryService;
		this.timeManager = timeManager;
	}


	@RequestMapping(method = RequestMethod.GET)
	public String showCategoryPage(Model model) {
		LocalDate today = getShiftDate();
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("formCategory", new GoodsCategory());
		model.addAttribute("today", today);

		return "/costs/categories/category";
	}

	private LocalDate getShiftDate() {
		LocalTime now = timeManager.getTime();
		if (now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.NOON)) {
			return timeManager.getDate().minusDays(1);
		}

		return timeManager.getDate();
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public ResponseEntity<?> addCategory(@ModelAttribute @Valid GoodsCategory category) {
		GoodsCategory existingCategory = categoryService.findByNameIgnoreCase(category.getName());
		if (existingCategory == null) {
			categoryService.save(category);
			return ResponseEntity.ok("Категория успешно добавлена!");
		} else {
			throw new CostCategoryException("Категория с таким именем уже существует");
		}

	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public ResponseEntity<?> editCategory(@ModelAttribute GoodsCategory category) {
		GoodsCategory editedCategory = categoryService.find(category.getId());
		if (editedCategory != null) {
			editedCategory.setName(category.getName());
			categoryService.save(editedCategory);
			return ResponseEntity.ok("Категория успешно обновлена!");
		} else {
			throw new CostCategoryException("Вы пытаетесь обновить несуществующую категорию!");
		}

	}

	@RequestMapping(value = "/delete")
	public ResponseEntity<?> deleteCategory(@RequestParam(name = "id") Long id) {
		categoryService.delete(id);
		return ResponseEntity.ok("Категория успешно удалена!");
	}

	@ExceptionHandler(value = CostCategoryException.class)
	public ResponseEntity<?> handleUserUpdateException(CostCategoryException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

}
