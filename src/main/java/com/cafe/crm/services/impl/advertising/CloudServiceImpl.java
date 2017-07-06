package com.cafe.crm.services.impl.advertising;


import com.cafe.crm.exceptions.advertising.AdvertisingFileNotImageException;
import com.cafe.crm.services.interfaces.advertising.CloudService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudServiceImpl implements CloudService {

	private final Cloudinary cloudinary;

	@Autowired
	public CloudServiceImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	@Override
	public String uploadAndGetUrl(MultipartFile file) {
		Map data;
		try {
			data = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
					"transformation", new Transformation().width("550")
			));
		} catch (IOException e) {
			throw new AdvertisingFileNotImageException("Не удалось загрузить файл на сервер!");
		}

		if (data == null) {
			throw new AdvertisingFileNotImageException("Не удалось загрузить файл на сервер!");
		}
		return (String) data.get("url");

	}
}
