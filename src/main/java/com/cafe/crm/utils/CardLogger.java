package com.cafe.crm.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CardLogger {

	private final Logger logger = LoggerFactory.getLogger(CardLogger.class);

	@After("execution(* com.cafe.crm.controllers.card.CardProfileController.addMoneyToBalance(..))")
	public void logBefore(JoinPoint joinPoint) {
		Long idCard = (Long) joinPoint.getArgs()[0];
		Long money = (Long) joinPoint.getArgs()[1];
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		logger.info("Administrator " + userDetails.getUsername() + "  has credited " + money + " to the card # " + idCard);
	}
}
