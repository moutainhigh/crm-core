package com.cafe.crm.service_abstract.advertising;


import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String uploadAndGetUrl(MultipartFile file);
}
