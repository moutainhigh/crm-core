package com.cafe.crm.controllers.calculate;

import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.calculate.MenuCalculateControllerService;
import com.cafe.crm.services.interfaces.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/manager")
public class MenuCalculateController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private MenuCalculateControllerService menuCalculateService;

	@RequestMapping(value = {"/create-layer-product"}, method = RequestMethod.POST)
	@ResponseBody
	public LayerProduct createLayerProduct(@RequestParam("calculateId") long calculateId,
	                                       @RequestParam("clientsId") long[] clientsId,
	                                       @RequestParam("productId") long productId) {
		return menuCalculateService.createLayerProduct(calculateId, clientsId, productId);
	}

	@RequestMapping(value = {"/add-client-on-layer-product"}, method = RequestMethod.POST)
	@ResponseBody
	public LayerProduct addClientOnLayerProduct(@RequestParam("calculateId") long calculateId,
	                                            @RequestParam("clientsId") long[] clientsId,
	                                            @RequestParam("productId") long layerProductId) {
		return menuCalculateService.addClientOnLayerProduct(calculateId, clientsId, layerProductId);
	}

	@RequestMapping(value = {"/delete-product-with-client"}, method = RequestMethod.POST)
	@ResponseBody
	public LayerProduct deleteProductOnClient(@RequestParam("calculateId") long calculateId,
	                                          @RequestParam("clientsId") long[] clientsId,
	                                          @RequestParam("productId") long layerProductId) {
		return menuCalculateService.deleteProductOnClient(calculateId, clientsId, layerProductId);
	}

	@RequestMapping(value = {"/get-products-on-calculate"}, method = RequestMethod.POST)
	@ResponseBody
	public Set<LayerProduct> getProductOnCalculate(@RequestParam("calculateId") long calculateId) {
		return menuCalculateService.getProductOnCalculate(calculateId);
	}

	@RequestMapping(value = {"/get-layer-products-on-client"}, method = RequestMethod.POST)
	@ResponseBody
	public List<LayerProduct> getLayerProductsOnClient(@RequestParam("clientId") long clientId) {
		return clientService.getOne(clientId).getLayerProducts();
	}

	@RequestMapping(value = {"/get-open-clients-on-calculate"}, method = RequestMethod.POST)
	@ResponseBody
	public List<Client> getOpenClientsOnCalculateAjax(@RequestParam("calculateId") long calculateId) {
		return calculateService.getAllOpenOnCalculate(calculateId).getClient();
	}
}
