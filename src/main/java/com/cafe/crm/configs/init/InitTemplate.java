package com.cafe.crm.configs.init;

import com.amazonaws.util.IOUtils;
import com.cafe.crm.models.template.Template;
import com.cafe.crm.services.interfaces.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class InitTemplate {

	private final TemplateService templateService;
	private final ResourceLoader resourceLoader;

	@Autowired
	public InitTemplate(ResourceLoader resourceLoader, TemplateService templateService) {
		this.resourceLoader = resourceLoader;
		this.templateService = templateService;
	}

	//@PostConstruct
	public void init() throws IOException {
		Resource resourceDisable = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream inStream = resourceDisable.getInputStream();
		byte[] disableContext = IOUtils.toByteArray(inStream);
		Template disable = new Template("disable-advertising", disableContext);

		Resource resourceText = resourceLoader.getResource("classpath:templates/media/mail/text-advertising.html");
		InputStream textInStream = resourceText.getInputStream();
		byte[] textContext = IOUtils.toByteArray(textInStream);
		Template text = new Template("text-advertising", textContext);

		Resource resourceImage = resourceLoader.getResource("classpath:templates/media/mail/image-advertising.html");
		InputStream imageInStream = resourceImage.getInputStream();
		byte[] imageContext = IOUtils.toByteArray(imageInStream);
		Template image = new Template("image-advertising", imageContext);

		Resource resourceBalanceAfterDeduction = resourceLoader.getResource("classpath:templates/balance/balance-info-deduction.html");
		InputStream balanceInStream = resourceBalanceAfterDeduction.getInputStream();
		byte[] balanceAfterDeductionContext = IOUtils.toByteArray(balanceInStream);
		Template balanceAfterDeduction = new Template("balance-info-deduction", balanceAfterDeductionContext);

		Resource resourceBalanceAfterRefill = resourceLoader.getResource("classpath:templates/balance/balance-info-refill.html");
		InputStream balanceAfterInStream = resourceBalanceAfterRefill.getInputStream();
		byte[] balanceAfterRefillContext = IOUtils.toByteArray(balanceAfterInStream);
		Template balanceAfterRefill = new Template("balance-info-refill", balanceAfterRefillContext);

		Resource resourceInvalidToken = resourceLoader.getResource("classpath:templates/invalidToken.html");
		InputStream invalidTokenInStream = resourceInvalidToken.getInputStream();
		byte[] invalidTokenContext = IOUtils.toByteArray(invalidTokenInStream);
		Template invalidToken = new Template("invalid-token", invalidTokenContext);

		Resource resourceExampleCloseShift = resourceLoader.getResource("classpath:templates/closeShiftEmailShortage.html");
		InputStream exampleCloseShiftInStream = resourceExampleCloseShift.getInputStream();
		byte[] exampleCloseShiftAfterDebitingContext = IOUtils.toByteArray(exampleCloseShiftInStream);
		Template exampleCloseShift = new Template("closeShiftEmailShortage", exampleCloseShiftAfterDebitingContext);

		StringBuilder dailyReportMessage = new StringBuilder();
		dailyReportMessage.append("{0}\n");
		dailyReportMessage.append("{1} {2}\n");
		dailyReportMessage.append("Грязными: {3}\n\n");
		dailyReportMessage.append("Количество гостей:\n");
		dailyReportMessage.append("{4}\n");
		dailyReportMessage.append("Всего: {5}\n\n");
		dailyReportMessage.append("Зарплата Сотрудников:\n");
		dailyReportMessage.append("{6}\n\n");
		dailyReportMessage.append("Прочее расходы:\n");
		dailyReportMessage.append("{7}\n\n");
		dailyReportMessage.append("Всего расходов за день - {8}\n\n");
		dailyReportMessage.append("Наличными - {9}\n");
		dailyReportMessage.append("Карта - {10}\n");
		dailyReportMessage.append("Общая Сумма - {11}\n");
		dailyReportMessage.append("{12}\n\n");
		dailyReportMessage.append("Заметки\n");
		dailyReportMessage.append("{13}");
		byte[] byteOfDailyMessage = dailyReportMessage.toString().getBytes("UTF-8");
		Template dailyReportForVk = new Template("daily-report", byteOfDailyMessage);

		templateService.save(disable);
		templateService.save(text);
		templateService.save(image);
		templateService.save(balanceAfterDeduction);
		templateService.save(balanceAfterRefill);
		templateService.save(invalidToken);
		templateService.save(exampleCloseShift);
		templateService.save(dailyReportForVk);
	}
}
