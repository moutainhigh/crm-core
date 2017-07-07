package com.cafe.crm.services.interfaces.email;


public interface HtmlService {

	String getAdvertisingFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token);

	String getAdvertisingFromText(String advertisingText, String view, Long id, String token);

	String getAdvertisingForDisable(String view, Long id, String token);

	String getBalanceInfoAfterDebiting(Double newBalance, Double debited, String view);

	String getCloseShiftFromText(String Text, Double salaryShift, Double profitShift, Long cache, Long payWithCard, String view);

}
