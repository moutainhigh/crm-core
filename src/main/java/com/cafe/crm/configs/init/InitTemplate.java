package com.cafe.crm.configs.init;

import com.cafe.crm.models.template.Template;
import com.cafe.crm.services.interfaces.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
		Resource resourceDisable = resourceLoader.getResource("classpath:templates/media/mail/disable-advertising.html");
		byte[] disableContext = Files.readAllBytes(Paths.get(resourceDisable.getFile().getPath()));
		Template disable = new Template("disable-advertising", disableContext);

		Resource resourceText = resourceLoader.getResource("classpath:templates/media/mail/text-advertising.html");
		byte[] textContext = Files.readAllBytes(Paths.get(resourceText.getFile().getPath()));
		Template text = new Template("text-advertising", textContext);

		Resource resourceImage = resourceLoader.getResource("classpath:templates/media/mail/image-advertising.html");
		byte[] imageContext = Files.readAllBytes(Paths.get(resourceImage.getFile().getPath()));
		Template image = new Template("image-advertising", imageContext);

		Resource resourceBalanceAfterDebiting = resourceLoader.getResource("classpath:templates/balance-info-debiting.html");
		byte[] balanceAfterDebitingContext = Files.readAllBytes(Paths.get(resourceBalanceAfterDebiting.getFile().getPath()));
		Template balanceAfterDebiting = new Template("balance-info-debiting", balanceAfterDebitingContext);

		Resource resourceExampleCloseShift = resourceLoader.getResource("classpath:templates/closeShiftEmailShortage.html");
		byte[] exampleCloseShiftAfterDebitingContext = Files.readAllBytes(Paths.get(resourceExampleCloseShift.getFile().getPath()));
		Template exampleCloseShift = new Template("closeShiftEmailShortage", exampleCloseShiftAfterDebitingContext);

		templateService.save(disable);
		templateService.save(text);
		templateService.save(image);
		templateService.save(balanceAfterDebiting);
		templateService.save(exampleCloseShift);
	}
}
