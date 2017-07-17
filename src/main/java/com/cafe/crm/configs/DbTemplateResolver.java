package com.cafe.crm.configs;


import com.cafe.crm.exceptions.advertising.AdvertisingTemplateNotFoundException;
import com.cafe.crm.models.template.Template;
import com.cafe.crm.services.interfaces.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;

public class DbTemplateResolver extends TemplateResolver {

    private final static String PREFIX = "db:";

    @Autowired
    private TemplateService templateService;

    public DbTemplateResolver() {
        setResourceResolver(new DbResourceResolver());
        HashSet<String> p = new HashSet<>();
        p.add(PREFIX + "*");
        setResolvablePatterns(p);
        setCharacterEncoding("UTF-8");
    }

    @Override
    protected String computeResourceName(TemplateProcessingParameters templateProcessingParameters) {
        String templateName = templateProcessingParameters.getTemplateName();
        return templateName.substring(PREFIX.length());
    }

    private class DbResourceResolver implements IResourceResolver {

        @Override
        public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
            Template template = templateService.findByName(resourceName);
            if (template == null || (template.getContent().length == 0)) {
                throw new AdvertisingTemplateNotFoundException("Шаблон для отслыки рекламы не найден или пустой!");
            }
            return new ByteArrayInputStream(template.getContent());
        }

        @Override
        public String getName() {
            return "dbResourceResolver";
        }

    }
}
