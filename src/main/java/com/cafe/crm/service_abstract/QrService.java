package com.cafe.crm.service_abstract;


import com.cafe.crm.models.QrTest;
import com.google.zxing.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface QrService {
	void generateQrLink(String link);

	void generateQrInstance(QrTest qrTest);

	String readQrFile(String link, String charset) throws FileNotFoundException, IOException, NotFoundException;
}
