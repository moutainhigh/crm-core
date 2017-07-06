package com.cafe.crm.services.interfaces.template;


import com.cafe.crm.models.template.Template;

public interface TemplateService {

	Template findByName(String name);

	void save(Template template);

}
