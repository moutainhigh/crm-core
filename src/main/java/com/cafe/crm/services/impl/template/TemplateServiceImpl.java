package com.cafe.crm.services.impl.template;

import com.cafe.crm.models.template.Template;
import com.cafe.crm.repositories.template.TemplateRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.template.TemplateService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {

	private final TemplateRepository templateRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public TemplateServiceImpl(TemplateRepository templateRepository, CompanyService companyService) {
		this.companyService = companyService;
		this.templateRepository = templateRepository;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Template template) {
		template.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public Template findByName(String name) {
		return templateRepository.findByNameAndCompanyId(name, companyIdCache.getCompanyId());
	}

	@Override
	public void save(Template template) {
//		setCompanyId(template);
		templateRepository.save(template);
	}
}
