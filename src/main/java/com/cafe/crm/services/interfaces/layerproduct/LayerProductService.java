package com.cafe.crm.services.interfaces.layerproduct;

import com.cafe.crm.models.client.LayerProduct;

import java.util.List;

public interface LayerProductService {

	void save(LayerProduct layerProduct);

	void delete(LayerProduct layerProduct);

	List<LayerProduct> getAll();

	LayerProduct getOne(Long id);

}
