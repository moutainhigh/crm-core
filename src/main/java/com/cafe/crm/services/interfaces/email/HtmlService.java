package com.cafe.crm.services.interfaces.email;


public interface HtmlService {

	String getAdvertisingFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token);

	String getAdvertisingFromText(String advertisingText, String view, Long id, String token);

	String getAdvertisingForDisable(String view, Long id, String token);

	String getBalanceInfoAfterDeduction(Double newBalance, Double deductionAmount, String view);

	String getBalanceInfoAfterRefill(Double newBalance, Double refillAmount, String view);

	String getCloseShiftFromText(String Text, Double cashBox, Double cache, Double bankKart, Double payWithCard,
								 Double allPrice, String view, Double shortage);

}
