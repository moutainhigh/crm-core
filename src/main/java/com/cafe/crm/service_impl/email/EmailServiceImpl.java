package com.cafe.crm.service_impl.email;


import com.cafe.crm.configs.property.AdvertisingProperties;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.service_abstract.advertising.AdvertisingHtmlCreator;
import com.cafe.crm.service_abstract.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Collection;

@Service
public class EmailServiceImpl implements EmailService {

    private final AdvertisingHtmlCreator advertisingHtmlCreator;

    private final AdvertisingProperties properties;

    private final JavaMailSender javaMailSender;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, AdvertisingProperties properties, AdvertisingHtmlCreator advertisingHtmlCreator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.javaMailSender = javaMailSender;
        this.properties = properties;
        this.advertisingHtmlCreator = advertisingHtmlCreator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void sendAdvertisingFromImage(String imageUrl, String subject, String urlToLink, Collection<? extends Client> clients) {
        MimeMessagePreparator[] mimeMessages = new MimeMessagePreparator[clients.size()];
        int i = 0;
        for (Client client : clients) {
            mimeMessages[i++] = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(properties.getMail().getSender());
                messageHelper.setTo(client.getEmail());
                messageHelper.setSubject(subject);
                String email = client.getEmail();
                Long id = client.getId();
                String token = bCryptPasswordEncoder.encode(email);
                String view = properties.getMail().getImageView();
                String html = advertisingHtmlCreator.getFromImage(imageUrl, view, urlToLink, id, token);
                messageHelper.setText(html, true);
            };
        }
        javaMailSender.send(mimeMessages);
    }

    @Override
    public void sendAdvertisingFromText(String text, String subject, Collection<? extends Client> clients) {
        MimeMessagePreparator[] mimeMessages = new MimeMessagePreparator[clients.size()];
        int i = 0;
        for (Client client : clients) {
            mimeMessages[i++] = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(properties.getMail().getSender());
                messageHelper.setTo(client.getEmail());
                messageHelper.setSubject(subject);
                String email = client.getEmail();
                Long id = client.getId();
                String token = bCryptPasswordEncoder.encode(email);
                String view = properties.getMail().getTextView();
                String html = advertisingHtmlCreator.getFromText(text, view, id, token);
                messageHelper.setText(html, true);
            };
        }
        javaMailSender.send(mimeMessages);
    }

    @Override
    public void sendDispatchStatusNotification(Client client) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(properties.getMail().getSender());
            messageHelper.setTo(client.getEmail());
            messageHelper.setSubject(properties.getMail().getDisableSubject());
            String email = client.getEmail();
            Long id = client.getId();
            String token = bCryptPasswordEncoder.encode(email);
            String view = properties.getMail().getDisableView();
            String html = advertisingHtmlCreator.getForDisable(view, id, token);
            messageHelper.setText(html, true);
        };
        javaMailSender.send(message);
    }
}
