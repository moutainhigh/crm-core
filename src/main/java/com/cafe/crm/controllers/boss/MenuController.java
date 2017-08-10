package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.menu.Category;
import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.menu.WrapperOfProduct;
import com.cafe.crm.services.interfaces.menu.CategoriesService;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import com.cafe.crm.services.interfaces.menu.MenuService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/boss/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private ProductService productService;

	@Autowired
	private IngredientsService ingredientsService;

	@ModelAttribute(value = "product")
	public Product newProduct() {
		return new Product();
	}

	@ModelAttribute(value = "ingredients")
	public List<Ingredients> getAll() {
		return ingredientsService.getAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAdminPage() {
		ModelAndView mv = new ModelAndView("menu/bossMenu");
		mv.addObject("menu", menuService.getOne(1L));
		mv.addObject("categories", categoriesService.findAll());
		mv.addObject("products", productService.findAllOrderByRatingDesc());

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
	                          @RequestParam(name = "name") String name,
	                          @RequestParam(name = "dirtyProfit", required = false, defaultValue = "true") String dirtyProfit) {
		Boolean isDirty = Boolean.valueOf(dirtyProfit);
		Category category = categoriesService.getOne(id);
		if (category != null) {
			category.setName(name);
			category.setDirtyProfit(isDirty);
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

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ResponseBody
	public WrapperOfProduct createProd(@RequestBody final WrapperOfProduct wrapper) {

		Category category = categoriesService.getOne(wrapper.getId());
		Map<Ingredients, Integer> recipe = ingredientsService.createRecipe(wrapper);
		double recipeCost = ingredientsService.getRecipeCost(recipe);
		if (category != null) {
			Product product = new Product();
			product.setCategory(category);
			product.setName(wrapper.getName());
			product.setCost(wrapper.getCost());
			product.setSelfCost(wrapper.getSelfCost() + recipeCost);
			product.setDescription(wrapper.getDescription());
			product.setRecipe(recipe);
			productService.saveAndFlush(product);
			category.getProducts().add(product);
			categoriesService.saveAndFlush(category);

			wrapper.setProductId(product.getId());
			wrapper.setSelfCost(wrapper.getSelfCost() + recipeCost);

			return wrapper;
		} else return wrapper;
	}

	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public String addCategories(@RequestParam(name = "name") String name,
	                            @RequestParam(name = "dirtyProfit", required = false, defaultValue = "true") String dirtyProfit,
	                            @RequestParam(name = "floatingPrice", required = false, defaultValue = "false") String floatingPrice) {
		Boolean isDirty = Boolean.valueOf(dirtyProfit);
		Boolean isFloatingPrice = Boolean.valueOf(floatingPrice);
		Category category = new Category(name);
		List<Product> listProducts = new ArrayList<>();
		category.setProducts(listProducts);
		category.setDirtyProfit(isDirty);
		category.setFloatingPrice(isFloatingPrice);
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

	@RequestMapping(value = "/updProd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public WrapperOfProduct updProd(@RequestBody WrapperOfProduct wrapper) {

		Product product = productService.findOne(wrapper.getId());
		if (product != null) {
			product.setName(wrapper.getName());
			product.setCost(wrapper.getCost());
			product.setSelfCost(wrapper.getSelfCost());
			product.setDescription(wrapper.getDescription());

			productService.saveAndFlush(product);
		}

		return wrapper;
	}

	@RequestMapping(value = "/get/recipe/", method = RequestMethod.GET)
	public ModelAndView getIngredientsForEdit(@RequestParam("id") Long idProduct) {

		ModelAndView modelAndView = new ModelAndView("menu/editRecipe");
		modelAndView.addObject("recipe", productService.findOne(idProduct).getRecipe());
		modelAndView.addObject("ingredients", ingredientsService.getAll());
		modelAndView.addObject("product", productService.findOne(idProduct));

		return modelAndView;
	}

	@RequestMapping(value = "/edit/recipe", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> editRecipe(@RequestBody WrapperOfProduct wrapper) {
		Product product = productService.findOne(wrapper.getId()); // id product
		Map<Ingredients, Integer> recipe = ingredientsService.createRecipe(wrapper);

		if (product != null) {

			product.setSelfCost(ingredientsService.getRecipeCost(recipe));
			product.setRecipe(recipe);
			productService.saveAndFlush(product);
		}
		return new ResponseEntity<>(1L, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/recipe/{id}", method = RequestMethod.POST)
	public String deleteRecipe(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		Product product = productService.findOne(id);
		if (product != null) {
			double recipeCost = ingredientsService.getRecipeCost(product.getRecipe());
			product.setSelfCost(product.getSelfCost() - recipeCost);
			product.getRecipe().clear();
			productService.saveAndFlush(product);
		}
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}
}
