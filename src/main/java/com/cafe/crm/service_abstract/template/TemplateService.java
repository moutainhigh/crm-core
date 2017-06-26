package com.cafe.crm.service_abstract.template;


import com.cafe.crm.models.template.Template;

public interface TemplateService {
	Template findByName(String name);
	void save(Template template);
}
