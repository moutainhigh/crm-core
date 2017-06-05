package com.cafe.crm.service_abstract.qrService;


import com.cafe.crm.models.card.Card;
import com.google.zxing.NotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface QrService {

	File generateQrImage(String link);

	void generateQrInstance(Card card);

	String readQrFile(String link, String charset) throws FileNotFoundException, IOException, NotFoundException;
}
