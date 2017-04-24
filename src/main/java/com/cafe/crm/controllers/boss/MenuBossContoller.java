package com.cafe.crm.controllers.boss;

import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.dao.dao_menu.CategoryRepository;
import com.cafe.crm.dao.dao_menu.MenuRepository;
import com.cafe.crm.dao.dao_menu.ProductRepository;
import com.cafe.crm.models.Menu.Category;
import com.cafe.crm.models.Menu.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 20.04.2017.
 */
@Controller
public class MenuBossContoller {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping(value = {"/boss"}, method = RequestMethod.GET)
    public ModelAndView getAdminPage(ModelMap modelMap) {
        modelMap.addAttribute("menu", menuRepository.getOne(1l));
        modelMap.addAttribute("categories", categoryRepository.findAll());
        modelMap.addAttribute("products", productRepository.findAll());
        return new ModelAndView("bossview");
    }

    @RequestMapping(value = {"/boss"}, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getAdminPagePost(ModelMap modelMap, @RequestParam(value = "del", required = false) Long id) throws IOException {
        if (id != null) {

            Product product = productRepository.findOne(id);
            if (product != null)
                productRepository.delete(id);

        }
        modelMap.addAttribute("menu", menuRepository.getOne(1l));
        modelMap.addAttribute("categories", categoryRepository.findAll());
        modelMap.addAttribute("products", productRepository.findAll());


        return new ModelAndView("redirect:/boss");
    }

    @RequestMapping(value = "/boss/upd", method = RequestMethod.POST)
    public ModelAndView updProduct(@RequestParam(name = "upd") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "cost") Long cost, @RequestParam(name = "des") String des) {


        Product product = productRepository.getOne(id);
        product.setName(name);
        product.setCost(cost);
        product.setDescription(des);

        productRepository.saveAndFlush(product);

        return new ModelAndView("redirect:/boss");

    }

    @RequestMapping(value = "/boss/updCat", method = RequestMethod.POST)
    public ModelAndView updCategory(@RequestParam(name = "upd") Long id, @RequestParam(name = "name") String name) {

        Category category = categoryRepository.getOne(id);
        category.setName(name);

        categoryRepository.saveAndFlush(category);


        return new ModelAndView("redirect:/boss");

    }

    @RequestMapping(value = "/boss/addProd", method = RequestMethod.POST)
    public ModelAndView addProd(@RequestParam(name = "add") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "cost") Long cost, @RequestParam(name = "des") String des) {

        Category category = categoryRepository.getOne(id);
        Product product = new Product(name, des, cost);
        Set<Category> setCat = new HashSet<>();
        setCat.add(category);
        product.setCategory(setCat);

        productRepository.saveAndFlush(product);
        categoryRepository.saveAndFlush(category);


        return new ModelAndView("redirect:/boss");

    }

}
