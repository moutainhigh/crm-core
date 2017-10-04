package com.cafe.crm.services.impl.advertising;


import com.cafe.crm.models.advertising.AdvertisingSettings;
import com.cafe.crm.models.company.Company;
import com.cafe.crm.repositories.advertising.AdvertisingSettingsRepository;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisingSettingsServiceImpl implements AdvertisingSettingsService {

	private final AdvertisingSettingsRepository repository;
	private final CompanyService companyService;
	private final CompanyIdCache companyIdCache;

	@Autowired
	public AdvertisingSettingsServiceImpl(AdvertisingSettingsRepository repository, CompanyService companyService, CompanyIdCache companyIdCache) {
		this.repository = repository;
		this.companyService = companyService;
		this.companyIdCache = companyIdCache;
	}

	private void setCompany(AdvertisingSettings advertisingSettings) {
		Long companyId = companyIdCache.getCompanyId();
		Company company = companyService.findOne(companyId);
		advertisingSettings.setCompany(company);
	}

	@Override
	public void save(AdvertisingSettings settings) {
		setCompany(settings);
		repository.save(settings);
	}

	@Override
	public void delete(AdvertisingSettings settings) {
		repository.delete(settings);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public AdvertisingSettings get(Long id) {
		return repository.findOne(id);
	}

	@Override
	public AdvertisingSettings findByEmail(String email) {
		return repository.findByEmailIgnoreCaseAndCompanyId(email, companyIdCache.getCompanyId());
	}

	@Override
	public List<AdvertisingSettings> getAll() {
		return repository.findByCompanyId(companyIdCache.getCompanyId());
	}

}
