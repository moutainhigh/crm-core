package com.cafe.crm.configs.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardFilter extends GenericFilterBean {

	private final List<String> blockedPaths = new ArrayList<>();
	private boolean enable;

	//init method
	@PostConstruct
	private void init() {
		blockedPaths.add("/manager/card");
		blockedPaths.add("/boss/card");
		blockedPaths.add("/advertising");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		if (!enable) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String requestURI = request.getRequestURI();
		for (String path : blockedPaths) {
			if (requestURI.startsWith(path)) {
				HttpServletResponse response = (HttpServletResponse) servletResponse;
				response.sendError(404);
				return;
			}
		}

		chain.doFilter(servletRequest, servletResponse);
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
