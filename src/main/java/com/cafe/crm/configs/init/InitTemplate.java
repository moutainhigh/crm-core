package com.cafe.crm.configs.init;

import com.amazonaws.util.IOUtils;
import com.cafe.crm.models.template.Template;
import com.cafe.crm.services.interfaces.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class InitTemplate {

	private final TemplateService templateService;

	private final ResourceLoader resourceLoader;

	@Autowired
	public InitTemplate(ResourceLoader resourceLoader, TemplateService templateService) {
		this.resourceLoader = resourceLoader;
		this.templateService = templateService;
	}

	@PostConstruct
	public void init() throws IOException {
		//File tempFile = File.createTempFile("prefix", "suffix");
		Resource resourceDisable = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream inStream = resourceDisable.getInputStream();
		byte[] disableContext = IOUtils.toByteArray(inStream);

		//Resource resourceDisable = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		//byte[] disableContext = Files.readAllBytes(Paths.get(resourceDisable.getFile().getPath()));
		Template disable = new Template("disable-advertising", disableContext);

		Resource resourceText = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream textInStream = resourceText.getInputStream();
		byte[] textContext = IOUtils.toByteArray(textInStream);

		//Resource resourceText = resourceLoader.getResource("classpath:templates/media/mail/text-advertising.html");
		//byte[] textContext = Files.readAllBytes(Paths.get(resourceText.getFile().getPath()));
		Template text = new Template("text-advertising", textContext);

		Resource resourceImage = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream imageInStream = resourceImage.getInputStream();
		byte[] imageContext = IOUtils.toByteArray(imageInStream);

		//Resource resourceImage = resourceLoader.getResource("classpath:templates/media/mail/image-advertising.html");
		//byte[] imageContext = Files.readAllBytes(Paths.get(resourceImage.getFile().getPath()));
		Template image = new Template("image-advertising", imageContext);

		Resource resourceBalanceAfterDeduction = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream balanceInStream = resourceBalanceAfterDeduction.getInputStream();
		byte[] balanceAfterDeductionContext = IOUtils.toByteArray(balanceInStream);

		//Resource resourceBalanceAfterDeduction = resourceLoader.getResource("classpath:templates/balance/balance-info-deduction.html");
		//byte[] balanceAfterDeductionContext = Files.readAllBytes(Paths.get(resourceBalanceAfterDeduction.getFile().getPath()));
		Template balanceAfterDeduction = new Template("balance-info-deduction", balanceAfterDeductionContext);

		Resource resourceBalanceAfterRefill = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream balanceAfterInStream = resourceBalanceAfterRefill.getInputStream();
		byte[] balanceAfterRefillContext = IOUtils.toByteArray(balanceAfterInStream);

		//Resource resourceBalanceAfterRefill = resourceLoader.getResource("classpath:templates/balance/balance-info-refill.html");
		//byte[] balanceAfterRefillContext = Files.readAllBytes(Paths.get(resourceBalanceAfterRefill.getFile().getPath()));
		Template balanceAfterRefill = new Template("balance-info-refill", balanceAfterRefillContext);

		Resource resourceExampleCloseShift = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		InputStream exampleCloseShiftInStream = resourceExampleCloseShift.getInputStream();
		byte[] exampleCloseShiftAfterDebitingContext = IOUtils.toByteArray(exampleCloseShiftInStream);

		//Resource resourceExampleCloseShift = resourceLoader.getResource("classpath:templates/closeShiftEmailShortage.html");
		//byte[] exampleCloseShiftAfterDebitingContext = Files.readAllBytes(Paths.get(resourceExampleCloseShift.getFile().getPath()));
		Template exampleCloseShift = new Template("closeShiftEmailShortage", exampleCloseShiftAfterDebitingContext);

		templateService.save(disable);
		templateService.save(text);
		templateService.save(image);
		templateService.save(balanceAfterDeduction);
		templateService.save(balanceAfterRefill);
		templateService.save(exampleCloseShift);
	}
}
