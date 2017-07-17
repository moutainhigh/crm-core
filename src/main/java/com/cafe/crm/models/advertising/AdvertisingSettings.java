package com.cafe.crm.models.advertising;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "advertising_settings")
public class AdvertisingSettings {

    @Id
    @GeneratedValue
    private Long id;

    private String nameSettings;

    @Column(unique = true)
    private String email;

    private String password;

    private String smtpProvider;

    public AdvertisingSettings() {
    }

    public AdvertisingSettings(String nameSettings, String email, String password, String smtpProvider) {
        this.nameSettings = nameSettings;
        this.email = email;
        this.password = password;
        this.smtpProvider = smtpProvider;
    }

    public Long getId() {
        return id;
    }

    public String getNameSettings() {
        return nameSettings;
    }

    public void setNameSettings(String nameSettings) {
        this.nameSettings = nameSettings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpProvider() {
        return smtpProvider;
    }

    public void setSmtpProvider(String smtpProvider) {
        this.smtpProvider = smtpProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertisingSettings settings = (AdvertisingSettings) o;
        return Objects.equals(id, settings.id) &&
                Objects.equals(nameSettings, settings.nameSettings) &&
                Objects.equals(email, settings.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameSettings, email);
    }
}
