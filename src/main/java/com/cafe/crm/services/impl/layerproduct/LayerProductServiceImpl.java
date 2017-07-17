package com.cafe.crm.services.impl.layerproduct;


import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.repositories.layerproduct.LayerProductRepository;
import com.cafe.crm.services.interfaces.layerproduct.LayerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LayerProductServiceImpl implements LayerProductService {

    @Autowired
    private LayerProductRepository layerProductRepository;

    @Override
    public void save(LayerProduct layerProduct) {
        layerProductRepository.saveAndFlush(layerProduct);
    }

    @Override
    public void delete(LayerProduct layerProduct) {
        layerProductRepository.delete(layerProduct);
    }

    @Override
    public List<LayerProduct> getAll() {
        return layerProductRepository.findAll();
    }

    @Override
    public LayerProduct getOne(Long id) {
        return layerProductRepository.findOne(id);
    }

}
