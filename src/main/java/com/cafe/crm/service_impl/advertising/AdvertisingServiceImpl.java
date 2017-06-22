package com.cafe.crm.service_impl.advertising;

import com.cafe.crm.exception.advertising.AdvertisingClientIdIncorrectException;
import com.cafe.crm.exception.advertising.AdvertisingImageFileException;
import com.cafe.crm.exception.advertising.AdvertisingTokenNotMatchException;
import com.cafe.crm.exception.advertising.AdvertisingUrlIncorrectException;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.service_abstract.advertising.AdvertisingService;
import com.cafe.crm.service_abstract.advertising.CloudService;
import com.cafe.crm.service_abstract.clientService.ClientService;
import com.cafe.crm.service_abstract.email.EmailService;
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

    private final ClientService clientService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdvertisingServiceImpl(ClientService clientService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService, CloudService cloudService) {
        this.clientService = clientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
        this.cloudService = cloudService;
    }

    @Transactional(readOnly = true)
    @Override
    public void sendAdvertisingFromImageUrl(String imageUrl, String subject, String urlToLink) {
        checkForUrl(imageUrl);
        List<Client> clients = clientService.findByEmailNotNullAndAdvertisingIsTrue();
        emailService.sendAdvertisingFromImage(imageUrl, subject, urlToLink, clients);
    }

    @Transactional(readOnly = true)
    @Override
    public void sendAdvertisingFromImageFile(MultipartFile imageFile, String subject, String urlToLink) {
        checkForImage(imageFile);
        String imageUrl = cloudService.uploadAndGetUrl(imageFile);
        List<Client> clients = clientService.findByEmailNotNullAndAdvertisingIsTrue();
        emailService.sendAdvertisingFromImage(imageUrl, subject, urlToLink, clients);
    }

    @Transactional(readOnly = true)
    @Override
    public void sendAdvertisingFromText(String text, String subject) {
        List<Client> clients = clientService.findByEmailNotNullAndAdvertisingIsTrue();
        emailService.sendAdvertisingFromText(text, subject, clients);
    }

    @Override
    public boolean toggleAdvertisingDispatchStatus(String id, String token) {
        Long clientId = parseClientId(id);
        Client client = clientService.getOne(clientId);
        checkForMatch(client, token);
        boolean isEnable = client.isAdvertising();
        changeAdvertisingStatus(client, isEnable);
        clientService.save(client);
        return !isEnable;
    }

    private void changeAdvertisingStatus(Client client, boolean isEnable) {
        client.setAdvertising(!isEnable);
        if (!isEnable) {
            return;
        }
        emailService.sendDispatchStatusNotification(client);
    }

    private void checkForMatch(Client client, String encodedString) {
        if (client == null || !bCryptPasswordEncoder.matches(client.getEmail(), encodedString)) {
            throw new AdvertisingTokenNotMatchException("Переданная информация не действительна.");
        }
    }

    private Long parseClientId(String id) {
        Long clientId;
        try {
            clientId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new AdvertisingClientIdIncorrectException("Переданная информация не действительна.");
        }
        return clientId;
    }

    private void checkForImage(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            ImageIO.read(in).toString();
        } catch (Exception e) {
            throw new AdvertisingImageFileException("Переданный файл не является картинкой!");
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
