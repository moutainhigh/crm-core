package com.cafe.crm.services.impl.discount;


import com.cafe.crm.models.discount.Discount;
import com.cafe.crm.repositories.discount.DiscountRepository;
import com.cafe.crm.services.interfaces.discount.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

	private final DiscountRepository discountRepository;

	@Autowired
	public DiscountServiceImpl(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}

	@Override
	public void save(Discount discount) {
		discountRepository.save(discount);
	}

	@Override
	public void delete(Discount discount) {
		discountRepository.delete(discount);
	}

	@Override
	public List<Discount> getAll() {
		return discountRepository.findAll();
	}

	@Override
	public Discount getOne(Long id) {
		return discountRepository.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		discountRepository.delete(id);
	}

	@Override
	public List<Discount> getAllOpen() {
		return discountRepository.getAllOpen();
	}
}

