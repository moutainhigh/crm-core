package com.cafe.crm.services.impl.cost;

import com.cafe.crm.exceptions.cost.category.CostCategoryDataException;
import com.cafe.crm.models.cost.CostCategory;
import com.cafe.crm.repositories.cost.CostCategoryRepository;
import com.cafe.crm.services.interfaces.cost.CostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostCategoryServiceImpl implements CostCategoryService {

	private final CostCategoryRepository costCategoryRepository;

	@Autowired
	public CostCategoryServiceImpl(CostCategoryRepository goodsCategoryRepository) {
		this.costCategoryRepository = goodsCategoryRepository;
	}

	@Override
	public void save(CostCategory costCategory) {
		checkForUniqueName(costCategory);
		costCategoryRepository.save(costCategory);
	}

	@Override
	public void update(CostCategory costCategory) {
		checkForUniqueName(costCategory);
		CostCategory editedCategory = costCategoryRepository.findOne(costCategory.getId());
		if (editedCategory != null) {
			editedCategory.setName(costCategory.getName());
			costCategoryRepository.save(editedCategory);
		} else {
			throw new CostCategoryDataException("Вы пытаетесь обновить несуществующую категорию!");
		}
	}


	@Override
	public List<CostCategory> findAll() {
		return costCategoryRepository.findAll();
	}

	@Override
	public CostCategory find(Long id) {
		return costCategoryRepository.findOne(id);
	}

	@Override
	public CostCategory find(String name) {
		return costCategoryRepository.findByNameIgnoreCase(name);
	}

	@Override
	public List<CostCategory> findByNameStartingWith(String startName) {
		return costCategoryRepository.findByNameStartingWith(startName);
	}

	@Override
	public void delete(Long id) {
		costCategoryRepository.delete(id);
	}

	private void checkForUniqueName(CostCategory costCategory) {
		CostCategory costCategoryInDataBase = find(costCategory.getName());
		if (costCategoryInDataBase != null) {
			throw new CostCategoryDataException("Категория с таким названием уже существует!");
		}
	}
}
