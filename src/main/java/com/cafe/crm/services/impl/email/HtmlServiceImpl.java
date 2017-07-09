package com.cafe.crm.services.impl.email;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.services.interfaces.email.HtmlService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Set;

@Service
public class HtmlServiceImpl implements HtmlService {

	private final TemplateEngine templateEngine;

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private BossRepository bossRepository;

	@Value("${site.address}")
	private String siteAddress;

	@Autowired
	public HtmlServiceImpl(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
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
	public String getCloseShiftFromText(String text, Double salaryShift, Double profitShift, Long cache, Long payWithCard, String view) {
		Set<Worker> allWorker = shiftService.getAllActiveWorkers();
		Set<Calculate> calculate = shiftService.getLast().getAllCalculate();
		List<Client> clients = shiftService.getLast().getClients();
		List<Boss> allBoss = bossRepository.getAllActiveBoss();
		Double shortage = salaryShift - (cache + payWithCard);
		Context context = new Context();
		context.setVariable("message", text);
		context.setVariable("workers", allWorker);
		context.setVariable("calculate", calculate.size());
		context.setVariable("clients", clients.size());
		context.setVariable("allBoss", allBoss);
		context.setVariable("cashBox", profitShift);
		context.setVariable("shortage", shortage);
		return templateEngine.process(view, context);
	}
}
