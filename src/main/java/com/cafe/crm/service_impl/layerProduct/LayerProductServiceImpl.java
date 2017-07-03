package com.cafe.crm.service_impl.layerProduct;


import com.cafe.crm.dao.layerProduct.LayerProductRepository;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.service_abstract.layerProductService.LayerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LayerProductServiceImpl implements LayerProductService {
	@Autowired
	private LayerProductRepository layerProductRepository;

	public void save(LayerProduct layerProduct) {
		layerProductRepository.saveAndFlush(layerProduct);
	}

	public void delete(LayerProduct layerProduct) {
		layerProductRepository.delete(layerProduct);
	}

	public List<LayerProduct> getAll() {
		return layerProductRepository.findAll();
	}

	public LayerProduct getOne(Long id) {
		return layerProductRepository.findOne(id);
	}

}
