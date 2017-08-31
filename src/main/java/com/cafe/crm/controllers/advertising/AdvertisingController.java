package com.cafe.crm.controllers.advertising;

import com.cafe.crm.exceptions.advertising.*;
import com.cafe.crm.services.interfaces.advertising.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailPreparationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdvertisingController {

	private final AdvertisingService advertisingService;

	@Autowired
	public AdvertisingController(AdvertisingService advertisingService) {
		this.advertisingService = advertisingService;
	}

	@RequestMapping(path = "/advertising/file", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> sendAdvertisingFromImageFile(@RequestParam("subject") String subject, @RequestParam("urlToLink") String urlToLink, @RequestParam("file") MultipartFile file) {
		advertisingService.sendAdvertisingFromImageFile(file, subject, urlToLink);

		return ResponseEntity.ok("Реклама успешно разослана!");
	}

	@RequestMapping(path = "/advertising/url", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> sendAdvertisingFromImageUrl(@RequestParam("subject") String subject, @RequestParam("urlToLink") String urlToLink, @RequestParam("url") String advertisingUrl) {
		advertisingService.sendAdvertisingFromImageUrl(advertisingUrl, subject, urlToLink);

		return ResponseEntity.ok("Реклама успешно разослана!");
	}

	@RequestMapping(path = "/advertising/text", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> sendAdvertisingFromText(@RequestParam("subject") String subject,
													 @RequestParam("text") String advertisingText) {
		advertisingService.sendAdvertisingFromText(advertisingText, subject);

		return ResponseEntity.ok("Реклама успешно разослана!");
	}

	@RequestMapping(path = "/advertising/toggle", method = RequestMethod.GET)
	public String toggleAdvertisingDispatchStatus(@RequestParam("number") String id,
												  @RequestParam("token") String token, Model model) {
		boolean status = advertisingService.toggleAdvertisingDispatchStatus(id, token);
		model.addAttribute("status", status);

		return "media/advertising-status";
	}

	@ExceptionHandler(value = AdvertisingUrlIncorrectException.class)
	@ResponseBody
	public ResponseEntity<?> handleIncorrectUrl(AdvertisingUrlIncorrectException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(value = AdvertisingFileNotImageException.class)
	@ResponseBody
	public ResponseEntity<?> handleIncorrectUrl(AdvertisingFileNotImageException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(value = AdvertisingTemplateNotFoundException.class)
	@ResponseBody
	public ResponseEntity<?> handleTemplateNotFound(AdvertisingTemplateNotFoundException ex) {
		System.out.println(ex.getMessage());
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(value = MailPreparationException.class)
	@ResponseBody
	public ResponseEntity<?> handleMailPreparation(MailPreparationException ex) {
		return ResponseEntity.badRequest().body("Не удалось отправить рассылку!");
	}

	@ExceptionHandler(value = AdvertisingTokenNotMatchException.class)
	public String handleTokenNotMatch(AdvertisingTokenNotMatchException ex, Model model) {
		model.addAttribute("message", ex.getMessage());
		return "media/advertising-status";
	}

	@ExceptionHandler(value = AdvertisingClientIdIncorrectException.class)
	public String handleIncorrectClientId(AdvertisingClientIdIncorrectException ex, Model model) {
		model.addAttribute("message", ex.getMessage());
		return "media/advertising-status";
	}

}
