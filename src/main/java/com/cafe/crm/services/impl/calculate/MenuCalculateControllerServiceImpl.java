package com.cafe.crm.services.impl.calculate;

import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.calculate.MenuCalculateControllerService;
import com.cafe.crm.services.interfaces.client.ClientService;
import com.cafe.crm.services.interfaces.layerproduct.LayerProductService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// TODO: 06.07.2017 Проверить почему не работает с @Transactional
@Service
public class MenuCalculateControllerServiceImpl implements MenuCalculateControllerService {

	@Autowired
	private LayerProductService layerProductService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CalculateService calculateService;

	@Override
	public LayerProduct createLayerProduct(long calculateId, long[] clientsId, long productId) {
		List<Client> clients = clientService.findByIdIn(clientsId);
		Product product = productService.findOne(productId);
		LayerProduct layerProduct = new LayerProduct();
		layerProduct.setCost(product.getCost());
		layerProduct.setName(product.getName());
		layerProduct.setDescription(product.getDescription());
		layerProduct.setClients(clients);
		if (!product.getCategory().isDirtyProfit()) {
			layerProduct.setDirtyProfit(false);
		}
		layerProductService.save(layerProduct);
		calculatePriceMenu(calculateId);
		return layerProduct;
	}

	@Override
	public LayerProduct createLayerProductWithFloatingPrice(long calculateId, long[] clientsId, long productId, double productPrice) {
		List<Client> clients = clientService.findByIdIn(clientsId);
		Product product = productService.findOne(productId);
		LayerProduct layerProduct = new LayerProduct();
		layerProduct.setCost(productPrice);
		layerProduct.setName(product.getName());
		layerProduct.setDescription(product.getDescription());
		layerProduct.setClients(clients);
		if (!product.getCategory().isDirtyProfit()) {
			layerProduct.setDirtyProfit(false);
		}
		layerProductService.save(layerProduct);
		calculatePriceMenu(calculateId);
		return layerProduct;
	}

	@Override
	public LayerProduct addClientOnLayerProduct(long calculateId, long[] clientsId, long layerProductId) {
		LayerProduct layerProduct = layerProductService.getOne(layerProductId);
		List<Client> clients = layerProduct.getClients();
		for (Client client : clients) {
			if (!client.isState()) {
				return layerProduct;
			}
		}
		clients.addAll(clientService.findByIdIn(clientsId));
		layerProduct.setClients(new ArrayList<Client>(new LinkedHashSet<Client>(clients)));
		// set на случай если продукт уже есть на клиенте, чтобы избежать дублирования
		layerProductService.save(layerProduct);
		calculatePriceMenu(calculateId);
		return layerProduct;
	}

	@Override
	public LayerProduct deleteProductOnClient(long calculateId, long[] clientsId, long layerProductId) {
		LayerProduct layerProduct = layerProductService.getOne(layerProductId);
		List<Client> сlients = layerProduct.getClients();
		List<Client> forDelClients = clientService.findByIdIn(clientsId);
		сlients.removeAll(forDelClients);
		if (layerProduct.getClients().isEmpty()) {
			layerProductService.delete(layerProduct);
		} else {
			layerProductService.save(layerProduct);
		}
		calculatePriceMenu(calculateId);
		layerProduct.setClients(forDelClients);//for json
		return layerProduct;
	}

	@Override
	public Set<LayerProduct> getProductOnCalculate(long calculateId) {
		Calculate calculate = calculateService.getAllOpenOnCalculate(calculateId);
		List<Client> listClient = calculate.getClient();
		Set<LayerProduct> products = new LinkedHashSet<>();
		for (Client client : listClient) {
			products.addAll(client.getLayerProducts());
		}
		return products;
	}

	private void calculatePriceMenu(long calculateId) {
		Calculate calculate = calculateService.getAllOpenOnCalculate(calculateId);
		List<Client> clients = calculate.getClient();
		for (Client client : clients) {
			client.setPriceMenu(0D);
			for (LayerProduct layerProduct : client.getLayerProducts()) {
				client.setPriceMenu(Math.round((client.getPriceMenu() + layerProduct.getCost() / layerProduct.getClients().size()) * 100) / 100.00);
			}
		}
		clientService.saveAll(clients);
	}
}
