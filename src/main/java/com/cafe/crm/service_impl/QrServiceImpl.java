package com.cafe.crm.service_impl;

import com.cafe.crm.models.QrTest;
import com.cafe.crm.service_abstract.QrService;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import net.glxn.qrgen.core.AbstractQRCode;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.Charset;
import java.util.UUID;


public class QrServiceImpl implements QrService {

	private AmazonServiceImpl amazonService = new AmazonServiceImpl();

	@Override
	public void generateQrLink(String link) {
		Charset charset = Charset.forName("UTF-8");
		AbstractQRCode bout =
				QRCode.from(link)
						.withSize(500, 500)
						.to(ImageType.PNG)
						.withCharset(String.valueOf(charset));
		try {
			File file = new File("src/main/resources/static/images/v1-String.png");
			OutputStream out = new FileOutputStream(file.getAbsolutePath());
			bout.writeTo(out);
			out.flush();
			out.close();
			String bucketName = "cafe-crm/qrCode";
			String keyName = "cafe-crm-content-stringQr";
			amazonService.putObject(amazonService.getConnection(), file, bucketName, keyName);//get object from amazon
			File file2 = new File("src/main/resources/static/images/copy/v1-StringCopy.png");
			amazonService.saveObjectToFile(amazonService.getConnection(),
					file2, bucketName, keyName);//save to local file into /copy
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generateQrInstance(QrTest qrTest) {
		Charset charset = Charset.forName("UTF-8");
		AbstractQRCode bout =
				QRCode.from(String.valueOf(qrTest))
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
			String bucketName = "cafe-crm/qrCode";
			String keyName = "cafe-crm-content-instanceQr";
			amazonService.putObject(amazonService.getConnection(), file, bucketName, keyName);
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


