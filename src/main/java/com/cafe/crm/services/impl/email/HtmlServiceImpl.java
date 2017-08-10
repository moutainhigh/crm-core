package com.cafe.crm.services.impl.email;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.email.HtmlService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class HtmlServiceImpl implements HtmlService {

	private final TemplateEngine templateEngine;
	private final ShiftService shiftService;

	@Value("${site.address}")
	private String siteAddress;

	@Autowired
	public HtmlServiceImpl(TemplateEngine templateEngine, ShiftService shiftService) {
		this.templateEngine = templateEngine;
		this.shiftService = shiftService;
	}

	@Override
	public String getAdvertisingFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token) {
		Context context = new Context();
		context.setVariable("advertisingUrl", advertisingUrl);
		context.setVariable("urlToLink", urlToLink);
		context.setVariable("number", id);
		context.setVariable("token", token);
		context.setVariable("siteAddress", siteAddress);
		return templateEngine.process(view, context);
	}

	@Override
	public String getAdvertisingFromText(String advertisingText, String view, Long id, String token) {
		Context context = new Context();
		context.setVariable("advertisingText", advertisingText);
		context.setVariable("number", id);
		context.setVariable("token", token);
		context.setVariable("siteAddress", siteAddress);
		return templateEngine.process(view, context);
	}

	@Override
	public String getAdvertisingForDisable(String view, Long id, String token) {
		Context context = new Context();
		context.setVariable("number", id);
		context.setVariable("token", token);
		context.setVariable("siteAddress", siteAddress);
		return templateEngine.process(view, context);
	}

	@Override
	public String getBalanceInfoAfterDeduction(Double newBalance, Double deductionAmount, String view) {
		Context context = new Context();
		context.setVariable("newBalance", newBalance);
		context.setVariable("deductionAmount", deductionAmount);
		return templateEngine.process(view, context);
	}

	@Override
	public String getBalanceInfoAfterRefill(Double newBalance, Double refillAmount, String view) {
		Context context = new Context();
		context.setVariable("newBalance", newBalance);
		context.setVariable("refillAmount", refillAmount);
		return templateEngine.process(view, context);
	}

	@Override
	public String getCloseShiftFromText(String text, Double cashBox, Double cache, Double bankKart, Double payWithCard, Double allPrice, String view, Collection<? extends User> recipients, Double shortage) {
		List<User> usersOnShift = shiftService.getUsersOnShift();
		Set<Calculate> calculates = shiftService.getLast().getCalculates();
		Set<Client> clients = shiftService.getLast().getClients();
		Context context = new Context();
		context.setVariable("message", text);
		context.setVariable("usersOnShift", usersOnShift);
		context.setVariable("calculate", calculates.size());
		context.setVariable("clients", clients.size());
		context.setVariable("recipients", recipients);
		context.setVariable("shortage", shortage);
		context.setVariable("allPrice", allPrice);
		context.setVariable("cashBox", (cashBox - shortage));
		return templateEngine.process(view, context);
	}

	@Override
	public String getInvalidToken(String view) {
		return templateEngine.process(view, new Context());
	}
}
