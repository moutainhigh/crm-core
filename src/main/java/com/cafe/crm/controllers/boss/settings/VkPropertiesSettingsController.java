package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.configs.property.VkProperties;
import com.cafe.crm.models.property.AllSystemProperty;
import com.cafe.crm.services.interfaces.property.SystemPropertyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by homesandbox on 22.08.2017.
 */
@Controller
@RequestMapping("/boss/settings/vk-properties-setting")
public class VkPropertiesSettingsController {

    @Autowired
    private SystemPropertyService systemPropertyService;

    @Autowired
    private VkProperties generalVkProperties;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView vkPropertiesSettingPage() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/settingPages/vkPropertiesSettingPage");
        modelAndView.addObject("vk_properties",getVkPropertiesFromDB());
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editVkProperties(@ModelAttribute(value="vk_properties") VkProperties newVkProperties,
                                   HttpServletRequest request) throws JsonProcessingException {

        String jsonStrProperty = new ObjectMapper().writeValueAsString(newVkProperties);
        AllSystemProperty systemProperty = systemPropertyService.findOne("vk");
        systemProperty.setProperty(jsonStrProperty);

        systemPropertyService.save(systemProperty);
        VkProperties.copy(newVkProperties,generalVkProperties);

        return "redirect:" + request.getHeader("Referer");
    }

    public VkProperties getVkPropertiesFromDB() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AllSystemProperty allSystemProperty = systemPropertyService.findOne("vk");
        return mapper.readValue(allSystemProperty.getProperty(), VkProperties.class);
    }
}
