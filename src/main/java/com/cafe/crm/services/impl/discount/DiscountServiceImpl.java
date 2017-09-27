package com.cafe.crm.services.impl.discount;


import com.cafe.crm.models.discount.Discount;
import com.cafe.crm.repositories.discount.DiscountRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.discount.DiscountService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

	private final DiscountRepository discountRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public DiscountServiceImpl(DiscountRepository discountRepository, CompanyService companyService) {
		this.discountRepository = discountRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Discount discount) {
		discount.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void save(Discount discount) {
		setCompanyId(discount);
		discountRepository.save(discount);
	}

	@Override
	public void delete(Discount discount) {
		discountRepository.delete(discount);
	}

	@Override
	public List<Discount> getAll() {
		return discountRepository.findByCompanyId(companyIdCache.getCompanyId());
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
		return discountRepository.getAllOpenAndCompanyId(companyIdCache.getCompanyId());
	}
}

