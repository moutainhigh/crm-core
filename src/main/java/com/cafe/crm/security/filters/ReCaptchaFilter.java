package com.cafe.crm.security.filters;

import com.cafe.crm.dto.ReCaptchaResponseDTO;
import com.cafe.crm.models.loginData.LoginData;
import com.cafe.crm.services.interfaces.login.LoginDataService;
import com.cafe.crm.services.interfaces.recaptcha.ReCaptchaService;
import com.cafe.crm.utils.ReCaptchaInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class ReCaptchaFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	private ReCaptchaService reCaptchaService;
	@Autowired
	private LoginDataService loginDataService;

	public ReCaptchaFilter() {
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginData loginData = loginDataService.findByRemoteAddress(request.getRemoteAddr());
		if (loginData != null) {
			if (loginData.getErrorCount() > 2) {
				return doVerifying(request, response);
			} else {
				return super.attemptAuthentication(request, response);
			}
		} else {
			loginDataService.saveData(new LoginData(request.getRemoteAddr(), 0, new Date()));
			return super.attemptAuthentication(request, response);
		}
	}

	private Authentication doVerifying(HttpServletRequest request, HttpServletResponse response) {
		ReCaptchaResponseDTO reCaptchaResponse = reCaptchaService.verify(request.getParameter("reCaptchaAnswer"));
		if (reCaptchaResponse.isSuccess()) {
			return super.attemptAuthentication(request, response);
		} else {
			try {
				super.unsuccessfulAuthentication(request, response, new ReCaptchaInvalidException("Invalid recaptcha response"));
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

