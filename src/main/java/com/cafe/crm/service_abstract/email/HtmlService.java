package com.cafe.crm.service_abstract.email;


public interface HtmlService {
    String getAdvertisingFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token);
    String getAdvertisingFromText(String advertisingText, String view, Long id, String token);
    String getAdvertisingForDisable(String view, Long id, String token);
    String getBalanceInfoAfterDebiting(Long newBalance, Long debited, String view);
}
