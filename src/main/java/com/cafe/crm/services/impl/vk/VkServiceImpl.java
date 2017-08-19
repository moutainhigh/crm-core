package com.cafe.crm.services.impl.vk;


import com.cafe.crm.configs.property.VkProperties;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.template.Template;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.template.TemplateService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.services.interfaces.vk.VkService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VkServiceImpl implements VkService {

	private static final String DAILY_REPORT_URL = "https://api.vk.com/method/messages.send?chat_id={chat_id}&message={message}&access_token={access_token}&v={v}";
	private static final String EMAIL_RECIPIENT_ROLE_IN_CASE_ERROR = "BOSS";
	private static final int ERROR_CODE_INVALID_TOKEN = 5;

	private final TemplateService templateService;
	private final RestTemplate restTemplate;
	private final VkProperties vkProperties;
	private final EmailService emailService;
	private final UserService userService;

	@Value("${cost-category-name.salary-for-shift}")
	private String categoryNameSalaryForShift;

	@Autowired
	public VkServiceImpl(TemplateService templateService, RestTemplate restTemplate, VkProperties vkProperties, EmailService emailService, UserService userService) {
		this.templateService = templateService;
		this.restTemplate = restTemplate;
		this.vkProperties = vkProperties;
		this.emailService = emailService;
		this.userService = userService;
	}

	@Override
	public void sendDailyReportToConference(Shift shift) {
		Template messageTemplate = templateService.findByName(vkProperties.getMessageName());
		if (messageTemplate == null) {
			return;
		}
		String message = formatMessage(shift, new String(messageTemplate.getContent(), Charset.forName("UTF-8")));
		// TODO: 05.08.2017 Убрать sout при релизе новой версии
		System.out.println(message);
		Map<String, String> variables = new HashMap<>();
		variables.put("chat_id", vkProperties.getChatId());
		variables.put("message", message);
		variables.put("access_token", vkProperties.getAccessToken());
		variables.put("v", vkProperties.getApiVersion());
		ResponseEntity<String> response = restTemplate.postForEntity(DAILY_REPORT_URL, null, String.class, variables);
		checkForInvalidToken(response);
	}

	private void checkForInvalidToken(ResponseEntity<String> response) {
		JSONObject jsonObject = new JSONObject(response.getBody());
		if (jsonObject.has("error")) {
			int code = jsonObject.getJSONObject("error").getInt("error_code");
			if (code == ERROR_CODE_INVALID_TOKEN) {
				List<User> users = userService.findByRoleName(EMAIL_RECIPIENT_ROLE_IN_CASE_ERROR);
				if (!users.isEmpty()) {
					emailService.sendInvalidTokenNotification(users);
				}
			}
		}
	}

	private String formatMessage(Shift shift, String raw) {
		Object[] params = new Object[13];
		DecimalFormat df = new DecimalFormat("#.##");

		StringBuilder salaryCosts = new StringBuilder();
		StringBuilder otherCosts = new StringBuilder();
		double totalCosts = formatCostsAndGetTotalCosts(shift.getGoodses(), salaryCosts, otherCosts);
		double shortage = shift.getProfit() - totalCosts - shift.getCashBox() - shift.getBankCashBox();

		params[0] = shortage < 0d ? "" : "НЕДОСТАЧА!";
		params[1] = getDayOfWeek(shift.getShiftDate());
		params[2] = getDate(shift.getShiftDate());
		params[3] = df.format(shift.getProfit());
		params[4] = getAmountOfClients(shift.getClients());
		params[5] = shift.getClients().size();
		params[6] = salaryCosts.toString();
		params[7] = otherCosts.toString();
		params[8] = df.format(totalCosts);
		params[9] = df.format(shift.getCashBox());
		params[10] = df.format(shift.getBankCashBox());
		params[11] = df.format(shift.getBankCashBox() + shift.getCashBox());
		params[12] = getComment(shift.getComment());

		return MessageFormat.format(raw, params);
	}

	private String getComment(String comment) {
		String result = "";
		if ((comment != null) && !comment.isEmpty()) {
			result = "\nКомментарий :\n" + comment;
		}
		return result;
	}

	private double formatCostsAndGetTotalCosts(List<Goods> goodses, StringBuilder salaries, StringBuilder otherCosts) {
		DecimalFormat df = new DecimalFormat("#.##");
		double totalCosts = 0d;
		for (Goods goods : goodses) {
			totalCosts += goods.getPrice() * goods.getQuantity();
			if (goods.getCategory().getName().equals(categoryNameSalaryForShift)) {
				salaries
						.append(goods.getName())
						.append(" - ").append(df.format(goods.getPrice() * goods.getQuantity()))
						.append(System.getProperty("line.separator"));
			} else {
				otherCosts
						.append(goods.getName())
						.append(" - ").append(df.format(goods.getPrice() * goods.getQuantity()))
						.append(System.getProperty("line.separator"));
			}
		}
		if (salaries.length() > 0) {
			salaries.deleteCharAt(salaries.length() - 1);
		} else {
			salaries.append("Отсутствует!");
		}
		if (otherCosts.length() > 0) {
			otherCosts.deleteCharAt(otherCosts.length() - 1);
		} else {
			otherCosts.append("Отсутствуют!");
		}
		return totalCosts;
	}

	private String getAmountOfClients(Collection<? extends Client> clients) {
		long amountOfClientsFrom12To16 = clients.stream().filter(client -> isTimeBetween(client.getTimeStart().toLocalTime(), LocalTime.of(12, 00), LocalTime.of(16, 00))).count();
		long amountOfClientsFrom16To18 = clients.stream().filter(client -> isTimeBetween(client.getTimeStart().toLocalTime(), LocalTime.of(16, 00), LocalTime.of(18, 00))).count();
		long amountOfClientsFrom18To20 = clients.stream().filter(client -> isTimeBetween(client.getTimeStart().toLocalTime(), LocalTime.of(18, 00), LocalTime.of(20, 00))).count();
		long amountOfClientsFrom20To22 = clients.stream().filter(client -> isTimeBetween(client.getTimeStart().toLocalTime(), LocalTime.of(20, 00), LocalTime.of(22, 00))).count();
		long amountOfClientsFrom22To00 = clients.stream().filter(client -> {
			LocalTime timeStart = client.getTimeStart().toLocalTime();
			return timeStart.isAfter(LocalTime.of(22, 00)) && isBeforeMidnight(timeStart);
		}).count();
		long amountOfClientsFrom00To06 = clients.stream().filter(client -> isTimeBetween(client.getTimeStart().toLocalTime(), LocalTime.of(00, 00), LocalTime.of(06, 00))).count();
		StringBuilder sb = new StringBuilder();
		sb.append("12-16 x ").append(amountOfClientsFrom12To16).append(System.getProperty("line.separator"));
		sb.append("16-18 x ").append(amountOfClientsFrom16To18).append(System.getProperty("line.separator"));
		sb.append("18-20 x ").append(amountOfClientsFrom18To20).append(System.getProperty("line.separator"));
		sb.append("20-22 x ").append(amountOfClientsFrom20To22).append(System.getProperty("line.separator"));
		sb.append("22-00 x ").append(amountOfClientsFrom22To00).append(System.getProperty("line.separator"));
		sb.append("00-06 x ").append(amountOfClientsFrom00To06);
		return sb.toString();
	}

	private String getDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(formatter);
	}

	private String getDayOfWeek(LocalDate date) {
		String dayOfWeek = DateTimeFormatter.ofPattern("EEEE").withLocale(new Locale("ru")).format(date);
		dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();
		return dayOfWeek;
	}

	private boolean isTimeBetween(LocalTime timeStart, LocalTime after, LocalTime before) {
		return timeStart.isAfter(after) && timeStart.isBefore(before);
	}

	private boolean isBeforeMidnight(LocalTime timeStart) {
		int cmp = Integer.compare(timeStart.getHour(), 24);
		if (cmp == 0) {
			cmp = Integer.compare(timeStart.getMinute(), 0);
			if (cmp == 0) {
				cmp = Integer.compare(timeStart.getSecond(), 0);
				if (cmp == 0) {
					cmp = Integer.compare(timeStart.getNano(), 0);
				}
			}
		}
		return cmp < 0;
	}
}
