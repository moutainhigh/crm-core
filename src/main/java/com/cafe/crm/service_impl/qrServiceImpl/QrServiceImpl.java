package com.cafe.crm.service_impl.qrServiceImpl;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.service_abstract.qrService.QrService;
import com.cafe.crm.service_impl.amazonServiceImpl.AmazonServiceImpl;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import net.glxn.qrgen.core.AbstractQRCode;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.Charset;

@Service
public class QrServiceImpl implements QrService {

	@Autowired
	private AmazonServiceImpl amazonService;

	@Override
	public File generateQrImage(String link) {
		Charset charset = Charset.forName("UTF-8");
		AbstractQRCode bout =
				QRCode.from(link)
						.withSize(500, 500)
						.to(ImageType.PNG)
						.withCharset(String.valueOf(charset));
		return bout.file();
	}

	@Override
	public void generateQrInstance(Card card) {
		Charset charset = Charset.forName("UTF-8");
		AbstractQRCode bout =
				QRCode.from(String.valueOf(card))
						.withSize(500, 500)
						.to(ImageType.PNG)
						.withCharset(String.valueOf(charset));
		try {
			File file = new File("src/main/resources/static/images/v1-Instance.png");
			OutputStream out = new FileOutputStream
					(file.getAbsolutePath());
			bout.writeTo(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readQrFile(String link, String charset)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(
								link)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		return qrCodeResult.getText();
	}

}


