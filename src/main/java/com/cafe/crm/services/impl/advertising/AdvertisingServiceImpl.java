package com.cafe.crm.services.impl.advertising;

import com.cafe.crm.exceptions.advertising.AdvertisingClientIdIncorrectException;
import com.cafe.crm.exceptions.advertising.AdvertisingFileNotImageException;
import com.cafe.crm.exceptions.advertising.AdvertisingTokenNotMatchException;
import com.cafe.crm.exceptions.advertising.AdvertisingUrlIncorrectException;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.services.interfaces.advertising.AdvertisingService;
import com.cafe.crm.services.interfaces.advertising.CloudService;
import com.cafe.crm.services.interfaces.card.CardService;
import com.cafe.crm.services.interfaces.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@Service
@Transactional
public class AdvertisingServiceImpl implements AdvertisingService {

    private final CloudService cloudService;

    private final EmailService emailService;

    private final CardService cardService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdvertisingServiceImpl(CardService cardService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService, CloudService cloudService) {
        this.cardService = cardService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
        this.cloudService = cloudService;
    }

    @Transactional(readOnly = true)
    @Override
    public void sendAdvertisingFromImageUrl(String imageUrl, String subject, String urlToLink) {
        checkForUrl(imageUrl);
        List<Card> cards = cardService.findByEmailNotNullAndAdvertisingIsTrue();
        emailService.sendAdvertisingFromImage(imageUrl, subject, urlToLink, cards);
    }

    @Transactional(readOnly = true)
    @Override
    public void sendAdvertisingFromImageFile(MultipartFile imageFile, String subject, String urlToLink) {
        checkForImage(imageFile);
        String imageUrl = cloudService.uploadAndGetUrl(imageFile);
        List<Card> cards = cardService.findByEmailNotNullAndAdvertisingIsTrue();
        emailService.sendAdvertisingFromImage(imageUrl, subject, urlToLink, cards);
    }

    @Transactional(readOnly = true)
    @Override
    public void sendAdvertisingFromText(String text, String subject) {
        List<Card> cards = cardService.findByEmailNotNullAndAdvertisingIsTrue();
        emailService.sendAdvertisingFromText(text, subject, cards);
    }

    @Override
    public boolean toggleAdvertisingDispatchStatus(String id, String token) {
        Long cardId = parseCardId(id);
        Card card = cardService.getOne(cardId);
        checkForMatch(card, token);
        boolean isEnable = card.isAdvertising();
        changeAdvertisingStatus(card, isEnable);
        cardService.save(card);
        return !isEnable;
    }

    private void changeAdvertisingStatus(Card card, boolean isEnable) {
        card.setAdvertising(!isEnable);
        if (!isEnable) {
            return;
        }
        emailService.sendDispatchStatusNotification(card);
    }

    private void checkForMatch(Card card, String encodedString) {
        if (card == null || !bCryptPasswordEncoder.matches(card.getEmail(), encodedString)) {
            throw new AdvertisingTokenNotMatchException("Переданная информация не действительна.");
        }
    }

    private Long parseCardId(String id) {
        Long cardId;
        try {
            cardId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new AdvertisingClientIdIncorrectException("Переданная информация не действительна.");
        }
        return cardId;
    }

    private void checkForImage(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            ImageIO.read(in).toString();
        } catch (Exception e) {
            throw new AdvertisingFileNotImageException("Переданный файл не является картинкой!");
        }
    }

    private void checkForUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new AdvertisingUrlIncorrectException("Переданый url не валидный");
        }
    }
}
