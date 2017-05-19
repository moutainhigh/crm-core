package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.Menu.Category;
import com.cafe.crm.models.Menu.Product;
import com.cafe.crm.service_abstract.menu_service.CategoriesService;
import com.cafe.crm.service_abstract.menu_service.MenuService;
import com.cafe.crm.service_abstract.menu_service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/boss/menu")
public class MenuBossContoller {

	@Autowired
	private MenuService menuService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private ProductService productService;

	@ModelAttribute(value = "product")
	public Product newProduct() {
		return new Product();
	}


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAdminPage() {
		ModelAndView mv = new ModelAndView("bossMenu");
		mv.addObject("menu", menuService.getOne(1L));
		mv.addObject("categories", categoriesService.findAll());
		mv.addObject("products", productService.findAll());

		return mv;
	}

	@RequestMapping(value = {"/deleteProduct"}, method = RequestMethod.POST)
	public String deleteProduct(ModelAndView modelAndView,
								@RequestParam(value = "del", required = false) Long id) throws IOException {

		productService.delete(id);
		modelAndView.addObject("menu", menuService.getOne(1L));
		modelAndView.addObject("categories", categoriesService.findAll());
		modelAndView.addObject("products", productService.findAll());
		return "redirect:/boss/menu";
	}

	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	public ModelAndView updProduct(Product product, @RequestParam(name = "cat") Long id) {
		product.setCategory(categoriesService.getOne(id));
		productService.saveAndFlush(product);

		return new ModelAndView("redirect:/boss/menu");
	}

	@RequestMapping(value = "/updCategory", method = RequestMethod.POST)
	public ModelAndView updCategory(@RequestParam(name = "upd") Long id,
									@RequestParam(name = "name") String name) {
		Category category = categoriesService.getOne(id);
		category.setName(name);
		categoriesService.saveAndFlush(category);
		return new ModelAndView("redirect:/boss/menu/");
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public String addProduct(Product product, @RequestParam(name = "add") Long id) {
		Category category = categoriesService.getOne(id);
		product.setCategory(category);
		category.getProducts().add(product);
		productService.saveAndFlush(product);
		categoriesService.saveAndFlush(category);
		return "redirect:/boss/menu";
	}

	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public String addCategories(@RequestParam(name = "name") String name) {
		Category category = new Category(name);
		Set<Product> setProducts = new HashSet<>();
		category.setProducts(setProducts);
		categoriesService.saveAndFlush(category);
		return "redirect:/boss/menu";
	}

	@RequestMapping(value = "/deleteCat", method = RequestMethod.POST)
	public String deleteCategories(@RequestParam(name = "del") Long id) {
		if (id != null) {
			menuService.getOne(1L).getCategories().remove(categoriesService.getOne(id));
			categoriesService.delete(id);
		}
		return "redirect:/boss/menu";
	}
}
