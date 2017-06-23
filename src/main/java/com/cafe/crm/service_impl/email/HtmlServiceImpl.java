package com.cafe.crm.service_impl.email;


import com.cafe.crm.service_abstract.email.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class HtmlServiceImpl implements HtmlService {

    private final TemplateEngine templateEngine;

    @Value("${site.address}")
    private String siteAddress;

    @Autowired
    public HtmlServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String getAdvertisingFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token) {
        Context context = new Context();
        context.setVariable("advertisingUrl", advertisingUrl);
        context.setVariable("urlToLink", urlToLink);
        context.setVariable("number", id);
        context.setVariable("token", token);
        context.setVariable("siteAddress", siteAddress);
        return templateEngine.process(view, context);
    }

    public String getAdvertisingFromText(String advertisingText, String view, Long id, String token) {
        Context context = new Context();
        context.setVariable("advertisingText", advertisingText);
        context.setVariable("number", id);
        context.setVariable("token", token);
        context.setVariable("siteAddress", siteAddress);
        return templateEngine.process(view, context);
    }

    public String getAdvertisingForDisable(String view, Long id, String token) {
        Context context = new Context();
        context.setVariable("number", id);
        context.setVariable("token", token);
        context.setVariable("siteAddress", siteAddress);
        return templateEngine.process(view, context);
    }

    @Override
    public String getBalanceInfoAfterDebiting(Long newBalance, Long debited, String view) {
        Context context = new Context();
        context.setVariable("newBalance", newBalance);
        context.setVariable("debited", debited);
        return templateEngine.process(view, context);
    }
}
