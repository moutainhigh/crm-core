package com.cafe.crm.service_impl.template;

import com.cafe.crm.dao.template.TemplateRepository;
import com.cafe.crm.models.template.Template;
import com.cafe.crm.service_abstract.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {

	private final TemplateRepository templateRepository;

	@Autowired
	public TemplateServiceImpl(TemplateRepository templateRepository) {
		this.templateRepository = templateRepository;
	}

	@Override
	public Template findByName(String name) {
		return templateRepository.findByName(name);
	}

	@Override
	public void save(Template template) {
		templateRepository.save(template);
	}
}
