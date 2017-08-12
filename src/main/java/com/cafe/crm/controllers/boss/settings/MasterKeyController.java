package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.services.interfaces.property.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/boss/settings/masterKey")
public class MasterKeyController {

	@Autowired
	SystemPropertyService propertyService;

	@Autowired
	PasswordEncoder encoder;

	@RequestMapping(method = RequestMethod.GET)
	public String showMasterKeySettingsPage() {
		return "settingPages/masterKeySettingsPage";
	}

	@RequestMapping(value = "/addMasterKey", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addMasterKey(@RequestParam(name = "old") String oldPassword,
	                                   @RequestParam(name = "new") String newMasterKey,
	                                   Authentication auth) {

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String bossPassword = userDetails.getPassword();

		if (encoder.matches(oldPassword, bossPassword)) {

			propertyService.saveMasterKey(newMasterKey);

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
