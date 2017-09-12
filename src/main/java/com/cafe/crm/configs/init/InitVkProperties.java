package com.cafe.crm.configs.init;

import com.cafe.crm.configs.property.VkProperties;
import com.cafe.crm.models.property.AllSystemProperty;
import com.cafe.crm.services.interfaces.property.SystemPropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class InitVkProperties {

    private final VkProperties generalVkProperties;
    private final SystemPropertyService systemPropertyService;

    @Autowired
    public InitVkProperties(VkProperties vkProperties, SystemPropertyService systemPropertyService) {
        this.generalVkProperties = vkProperties;
        this.systemPropertyService = systemPropertyService;
    }

    @PostConstruct
    public void init() throws IOException {

        AllSystemProperty vkAllSystemProperty = systemPropertyService.findOne("vk");

        if (vkAllSystemProperty == null) {
            String jsonStrVkProperties =
                    new ObjectMapper().writeValueAsString(generalVkProperties);

            AllSystemProperty allSystemPropertyFromYml = new AllSystemProperty();
            allSystemPropertyFromYml.setName("vk");
            allSystemPropertyFromYml.setProperty(jsonStrVkProperties);

            systemPropertyService.save(allSystemPropertyFromYml);
        } else {
            VkProperties vkPropertiesFromDB =
                    new ObjectMapper().readValue(vkAllSystemProperty.getProperty(), VkProperties.class);
            VkProperties.copy(vkPropertiesFromDB, generalVkProperties);
        }
    }
}
