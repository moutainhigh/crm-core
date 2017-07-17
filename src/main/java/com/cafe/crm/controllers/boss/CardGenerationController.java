package com.cafe.crm.controllers.boss;


import com.amazonaws.services.s3.AmazonS3;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.repositories.card.CardRepository;
import com.cafe.crm.services.impl.amazon.AmazonServiceImpl;
import com.cafe.crm.services.impl.qr.QrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Controller
@RequestMapping("/boss")
public class CardGenerationController {

    @Autowired
    private QrServiceImpl qrService;
    @Autowired
    private AmazonServiceImpl amazonService;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(value = "/cardGenerationPage", method = RequestMethod.GET)
    public String cardGenerationPage() throws IOException {
        return "cardGenerationPage";
    }

    @RequestMapping(value = "/generateCard", method = RequestMethod.POST)
    public String generateCard(@RequestParam(value = "accessKey") String accessKey,
                               @RequestParam(value = "secretKey") String secretKey,
                               @RequestParam(value = "link") String link,
                               @RequestParam(value = "amountOfCards") Integer amountOfCards) throws IOException {
        AmazonS3 s3client = amazonService.getConnection(accessKey, secretKey);
        for (int i = 1; i <= amountOfCards; i++) {
            Card card = new Card();
            card.setAccessKey(accessKey);
            card.setSecretKey(secretKey);
            card.setLink(link);
            UUID uuid = UUID.randomUUID();
            String bucketName = "cafe-com.cafe.crm/qrCode";
            String keyNameQrCode = "cafe-com.cafe.crm-content-stringQr" + uuid;
            File file = qrService.generateQrImage(link);
            card.setQrCode(file);
            card.setKeyNameQrCode(keyNameQrCode);
            cardRepository.saveAndFlush(card);
            amazonService.putObject(s3client, file, bucketName, keyNameQrCode);
        }
        return "cardGenerationPage";
    }

    @RequestMapping(value = "/getQr", method = RequestMethod.GET)
    public ModelAndView getQrFromCardById(@RequestParam(value = "id") Long id) throws IOException {
        Card card = cardRepository.getCardById(id);
        String bucketName = "cafe-com.cafe.crm/qrCode";
        AmazonS3 s3client = amazonService.getConnection(card.getAccessKey(), card.getSecretKey());
        String keyNameQrCode = card.getKeyNameQrCode();
        URL s3clientUrl = s3client.getUrl(bucketName, keyNameQrCode);
        File file = new File("src/main/resources/static/images/copy" + keyNameQrCode + ".png");
        amazonService.saveObjectToFile(s3client, file, bucketName, keyNameQrCode);
        ModelAndView modelAndView = new ModelAndView("cardGenerationPage");
        modelAndView.addObject("url", s3clientUrl);
        return modelAndView;
    }
}
