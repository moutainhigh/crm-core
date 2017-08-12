package com.cafe.crm.configs.filters;


import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShiftOpenFilter extends GenericFilterBean {

	private ShiftService shiftService;

	@Autowired
	public void setShiftService(ShiftService shiftService) {
		this.shiftService = shiftService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String requestURI = request.getRequestURI();
		if (requestURI.startsWith("/manager") && (!requestURI.equals("/manager/shift/") && !requestURI.equals("/manager/shift/begin"))) {
			Shift lastShift = shiftService.getLast();
			if (lastShift == null || !lastShift.isOpen()) {
				HttpServletResponse response = (HttpServletResponse) servletResponse;
				response.sendRedirect("/manager/shift/");
				return;
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}
}
