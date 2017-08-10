package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.configs.property.AdvertisingCustomSettings;
import com.cafe.crm.models.advertising.AdvertisingSettings;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/boss/settings/advert-setting")
public class AdvertisingSettingsController {

	private final AdvertisingSettingsService advertisingSettingsService;
	private final AdvertisingCustomSettings customSettings;

	@Autowired
	public AdvertisingSettingsController(AdvertisingSettingsService advertisingSettingsService, AdvertisingCustomSettings customSettings) {
		this.advertisingSettingsService = advertisingSettingsService;
		this.customSettings = customSettings;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView bardSettingPage() {
		ModelAndView modelAndView = new ModelAndView("/settingPages/smtpSettingsPage");
		modelAndView.addObject("advertSettings", customSettings.getCustomSettings());
		modelAndView.addObject("listSMTPSettings", advertisingSettingsService.getAll());
		return modelAndView;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> setAdvertisingCustomSettings(
			@RequestParam(name = "settingsName") String settingsName,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "email") String email) {

		AdvertisingSettings settingsInDB = advertisingSettingsService.findByEmail(email);

		if (settingsInDB == null) {
			AdvertisingSettings newSettings = new AdvertisingSettings(settingsName, email, password, "smtp.gmail.com");

			advertisingSettingsService.save(newSettings);
			JavaMailSenderImpl senderImpl = customSettings.getCustomSettings();
			senderImpl.setUsername(email);
			senderImpl.setPassword(password);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/existing-settings", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> setExistingSettings(@RequestParam(name = "settingsId") Long id) {
		AdvertisingSettings settings = advertisingSettingsService.get(id);
		JavaMailSenderImpl senderImpl = customSettings.getCustomSettings();

		if (settings.getEmail().equalsIgnoreCase(senderImpl.getUsername())) {
			return ResponseEntity.badRequest().body("Эти настройки уже применены!");
		} else {
			senderImpl.setUsername(settings.getEmail());
			senderImpl.setPassword(settings.getPassword());
			return ResponseEntity.ok("Настройки успешно применены!");
		}
	}

	@RequestMapping(value = "/del-settings", method = RequestMethod.POST)
	public String delExistingSettings(@RequestParam(name = "settingsId") Long id) {

		advertisingSettingsService.delete(id);

		return "redirect:";
	}

}
