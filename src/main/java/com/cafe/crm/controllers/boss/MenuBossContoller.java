package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.Menu.Category;
import com.cafe.crm.models.Menu.Product;
import com.cafe.crm.service_abstract.UserService;
import com.cafe.crm.service_abstract.menu_service.CategoriesService;
import com.cafe.crm.service_abstract.menu_service.MenuService;
import com.cafe.crm.service_abstract.menu_service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 20.04.2017.
 */
@Controller
public class MenuBossContoller {

	@Autowired
	private MenuService menuService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private ProductService productService;


	@RequestMapping(value = {"/boss/menu"}, method = RequestMethod.GET)
	public ModelAndView getAdminPage(ModelAndView modelAndView) {
		ModelAndView mv = new ModelAndView("bossMenu");
		mv.addObject("menu", menuService.getOne(1L));
		mv.addObject("categories", categoriesService.findAll());
		mv.addObject("products", productService.findAll());
		return mv;
	}

	@RequestMapping(value = {"/boss/menu"}, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getAdminPagePost(ModelAndView modelAndView, @RequestParam(value = "del", required = false) Long id) throws IOException {
		ModelAndView mv = new ModelAndView("bossMenu");
		if (id != null) {

			Product product = productService.findOne(id);
			if (product != null)
				productService.delete(id);

		}
		modelAndView.addObject("menu", menuService.getOne(1L));
		modelAndView.addObject("categories", categoriesService.findAll());
		modelAndView.addObject("products", productService.findAll());

		return mv;
	}

	@RequestMapping(value = "/boss/menu/upd", method = RequestMethod.POST)
	public ModelAndView updProduct(@RequestParam(name = "upd") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "cost") Double cost, @RequestParam(name = "des") String des) {

		Product product = productService.findOne(id);
		product.setName(name);
		product.setCost(cost);
		product.setDescription(des);

		productService.saveAndFlush(product);

		return new ModelAndView("redirect:/boss/menu");

	}

	@RequestMapping(value = "/boss/menu/updCat", method = RequestMethod.POST)
	public ModelAndView updCategory(@RequestParam(name = "upd") Long id, @RequestParam(name = "name") String name) {

		Category category = categoriesService.getOne(id);
		category.setName(name);
		categoriesService.saveAndFlush(category);

		return new ModelAndView("redirect:/boss/menu/");

	}

	@RequestMapping(value = "/boss/menu/addProd", method = RequestMethod.POST)
	public ModelAndView addProd(@RequestParam(name = "add") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "cost") Double cost, @RequestParam(name = "des") String des) {

		Category category = categoriesService.getOne(id);
		Product product = new Product(name, des, cost);
		Set<Category> setCat = new HashSet<>();
		setCat.add(category);
		product.setCategory(setCat);

		productService.saveAndFlush(product);
		categoriesService.saveAndFlush(category);

		return new ModelAndView("redirect:/boss/menu");

	}

	@RequestMapping(value = "/boss/menu/addCat", method = RequestMethod.POST)
	public ModelAndView addCategories(@RequestParam(name = "name") String name) {

		Category category = new Category(name);
		Set<Product> setProducts = new HashSet<>();
		category.setProducts(setProducts);

		categoriesService.saveAndFlush(category);

		return new ModelAndView("redirect:/boss/menu");

	}

	@RequestMapping(value = "/boss/menu/deleteCat", method = RequestMethod.POST)
	public ModelAndView deleteCategories(@RequestParam(name = "del") Long id) {

		if (id != null) {

			menuService.getOne(1L).getCategories().remove(categoriesService.getOne(id));
			categoriesService.delete(id);
		}
		return new ModelAndView("redirect:/boss/menu");

	}

}
