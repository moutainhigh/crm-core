package com.cafe.crm.services.interfaces.email;


import com.cafe.crm.models.user.User;

import java.util.Collection;

public interface HtmlService {

	String getAdvertisingFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token);

	String getAdvertisingFromText(String advertisingText, String view, Long id, String token);

	String getAdvertisingForDisable(String view, Long id, String token);

	String getBalanceInfoAfterDeduction(Double newBalance, Double deductionAmount, String view);

	String getBalanceInfoAfterRefill(Double newBalance, Double refillAmount, String view);

	String getCloseShiftFromText(String Text, Double cashBox, Double cache, Double bankKart, Double payWithCard,
								 Double allPrice, String view, Collection<? extends User> recipients, Double shortage);

	String getInvalidToken(String view);

}
