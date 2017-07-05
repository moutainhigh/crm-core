package com.cafe.crm.service_abstract.calculateService;

import com.cafe.crm.models.client.LayerProduct;

import java.util.Set;

public interface MenuCalculateControllerService {
	LayerProduct createLayerProduct(long calculateId, long[] clientsId, long productId);

	LayerProduct addClientOnLayerProduct(long calculateId, long[] clientsId, long layerProductId);

	LayerProduct deleteProductOnClient(long calculateId, long[] clientsId, long layerProductId);

	Set<LayerProduct> getProductOnCalculate(long calculateId);

}



