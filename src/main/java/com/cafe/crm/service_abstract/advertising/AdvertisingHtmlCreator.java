package com.cafe.crm.service_abstract.advertising;


public interface AdvertisingHtmlCreator {
    String getFromImage(String advertisingUrl, String view, String urlToLink, Long id, String token);
    String getFromText(String advertisingText, String view, Long id, String token);
    String getForDisable(String view, Long id, String token);
}
