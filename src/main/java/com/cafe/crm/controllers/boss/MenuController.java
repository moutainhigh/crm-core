package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.menu.Category;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.services.interfaces.menu.CategoriesService;
import com.cafe.crm.services.interfaces.menu.MenuService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/boss/menu")
public class MenuController {

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
		ModelAndView mv = new ModelAndView("menu/bossMenu");
		mv.addObject("menu", menuService.getOne(1L));
		mv.addObject("categories", categoriesService.findAll());
		mv.addObject("products", productService.findAll());

		return mv;
	}

	@RequestMapping(value = {"/deleteProduct"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteProduct(@RequestParam(value = "del", required = false) Long id) throws IOException {

		productService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/updCategory", method = RequestMethod.POST)
	public String updCategory(@RequestParam(name = "upd") Long id,
							  @RequestParam(name = "name") String name) {
		Category category = categoriesService.getOne(id);
		if (category != null) {
			category.setName(name);
			categoriesService.saveAndFlush(category);
		}
		return "redirect:/boss/menu/";
	}

	@RequestMapping(value = "/getProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getProductForAjax(@RequestParam(name = "id") Long id) {

		Product product = productService.findOne(id);
		product.setCategory(null);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAjax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> ajax2(@RequestParam("idCat") Long idCat,
								   @RequestParam("name") String name,
								   @RequestParam("description") String des,
								   @RequestParam("cost") Double cost
	) {
		Category category = categoriesService.getOne(idCat);
		if (category != null) {
			Product product = new Product();
			product.setCategory(category);
			product.setName(name);
			product.setCost(cost);
			product.setDescription(des);
			productService.saveAndFlush(product);
			category.getProducts().add(product);
			categoriesService.saveAndFlush(category);
			return new ResponseEntity<>(product.getId(), HttpStatus.OK);
		} else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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
		Category category = categoriesService.getOne(id);
		if (category != null) {
			menuService.getOne(1L).getCategories().remove(categoriesService.getOne(id));
			categoriesService.delete(id);
		}
		return "redirect:/boss/menu";
	}

	@RequestMapping(value = "/updProd", method = RequestMethod.POST)
	@ResponseBody
	public Product ajax(@RequestBody Product product) {
		product.setCategory(productService.findOne(product.getId()).getCategory());
		productService.saveAndFlush(product);
		product.setCategory(null);

		return product;
	}
}
