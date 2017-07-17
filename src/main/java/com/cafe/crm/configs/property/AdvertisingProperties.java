package com.cafe.crm.configs.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("advertising")
public class AdvertisingProperties {

    private Mail mail;
    private Cloud cloud;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public static class Mail {
        private String sender;
        private String imageView;
        private String textView;
        private String disableView;
        private String disableSubject;

        public String getSender() {
            return sender;
        }

        public void setSender(String name) {
            this.sender = name;
        }

        public String getImageView() {
            return imageView;
        }

        public void setImageView(String imageView) {
            this.imageView = imageView;
        }

        public String getTextView() {
            return textView;
        }

        public void setTextView(String textView) {
            this.textView = textView;
        }

        public String getDisableView() {
            return disableView;
        }

        public void setDisableView(String disableView) {
            this.disableView = disableView;
        }

        public String getDisableSubject() {
            return disableSubject;
        }

        public void setDisableSubject(String disableSubject) {
            this.disableSubject = disableSubject;
        }

    }

    public static class Cloud {
        private String name;
        private String key;
        private String secret;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }


}
