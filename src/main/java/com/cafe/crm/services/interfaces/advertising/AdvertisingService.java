package com.cafe.crm.services.interfaces.advertising;


import org.springframework.web.multipart.MultipartFile;

public interface AdvertisingService {

    void sendAdvertisingFromImageUrl(String imageUrl, String subject, String urlToLink);

    void sendAdvertisingFromImageFile(MultipartFile imageFile, String subject, String urlToLink);

    void sendAdvertisingFromText(String text, String subject);

    boolean toggleAdvertisingDispatchStatus(String id, String token);

}
