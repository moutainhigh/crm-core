package com.cafe.crm.services.interfaces.advertising;


import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

	String uploadAndGetUrl(MultipartFile file);

}
