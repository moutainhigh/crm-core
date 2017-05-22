package com.cafe.crm.service_impl;


import com.cafe.crm.models.QrTest;
import com.google.zxing.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class QrServiceTest {

	public static void main(String[] args) throws IOException, NotFoundException, URISyntaxException {
		List<String> list = new ArrayList<>();
		list.add("один");
		list.add("два");
		QrTest qrTest = new QrTest(1, "Дмитрий", list, "https://www.google.ru");
		String link = "https://www.google.ru/";
		QrServiceImpl qrService = new QrServiceImpl();
		qrService.generateQrLink(link);
		qrService.generateQrInstance(qrTest);
	}
}

