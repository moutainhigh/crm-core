package com.cafe.crm.services.impl.calculate;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.repositories.calculate.CalculateRepository;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateServiceImpl implements CalculateService {

	private final CalculateRepository calculateRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public CalculateServiceImpl(CalculateRepository calculateRepository, CompanyService companyService) {
		this.calculateRepository = calculateRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Calculate calculate) {
		calculate.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void save(Calculate calculate) {
		setCompanyId(calculate);
		calculateRepository.save(calculate);
	}

	@Override
	public void delete(Calculate calculate) {
		calculateRepository.delete(calculate);
	}

	@Override
	public List<Calculate> getAll() {
		return calculateRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public Calculate getOne(Long id) {
		return calculateRepository.findOne(id);
	}

	@Override
	public List<Calculate> getAllOpen() {
		return calculateRepository.getAllOpenAndCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public Calculate getAllOpenOnCalculate(Long calculateId) {
		return calculateRepository.getAllOpenOnCalculateAndCompanyId(calculateId, companyIdCache.getCompanyId());
	}

	@Override
	public Calculate findByClientId(Long clientId) {
		return calculateRepository.findByClientId(clientId);
	}

}
