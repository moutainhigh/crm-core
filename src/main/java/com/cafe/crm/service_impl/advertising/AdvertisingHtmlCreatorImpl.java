package com.cafe.crm.service_impl.advertising;


import com.cafe.crm.service_abstract.advertising.AdvertisingHtmlCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class AdvertisingHtmlCreatorImpl implements AdvertisingHtmlCreator {

    private final TemplateEngine templateEngine;

    @Value("${site.address}")
    private String siteAddress;

    @Autowired
    public AdvertisingHtmlCreatorImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String getFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token) {
        Context context = new Context();
        context.setVariable("advertisingUrl", advertisingUrl);
        context.setVariable("urlToLink", urlToLink);
        context.setVariable("number", id);
        context.setVariable("token", token);
        context.setVariable("siteAddress", siteAddress);
        return templateEngine.process(view, context);
    }

    public String getFromText(String advertisingText, String view, Long id, String token) {
        Context context = new Context();
        context.setVariable("advertisingText", advertisingText);
        context.setVariable("number", id);
        context.setVariable("token", token);
        context.setVariable("siteAddress", siteAddress);
        return templateEngine.process(view, context);
    }

    public String getForDisable(String view, Long id, String token) {
        Context context = new Context();
        context.setVariable("number", id);
        context.setVariable("token", token);
        context.setVariable("siteAddress", siteAddress);
        return templateEngine.process(view, context);
    }
}
