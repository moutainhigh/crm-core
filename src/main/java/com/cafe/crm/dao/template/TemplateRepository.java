package com.cafe.crm.dao.template;


import com.cafe.crm.models.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
	Template findByName(String name);
}
