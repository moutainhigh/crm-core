package com.cafe.crm.service_impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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


public class QrServiceImpl implements QrService {

	private BasicAWSCredentials awsCreds = new BasicAWSCredentials
			("AKIAJPQMJ3CS5JROOTVA", "kY9sjvr2Ju5eKv2w5ZM/MK42z9r40eecpBukGttJ");
	private AmazonS3 s3client = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
			.withRegion(Regions.DEFAULT_REGION)
			.build();

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
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead));
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
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readQrFile(String link, String charset)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(link)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		return qrCodeResult.getText();
	}
}


