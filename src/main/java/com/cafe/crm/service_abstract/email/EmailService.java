package com.cafe.crm.service_abstract.email;


import com.cafe.crm.models.client.Client;

import java.util.Collection;

public interface EmailService {
    void sendAdvertisingFromImage(String imageUrl, String subject, String urlToLink, Collection<? extends Client> clients);
    void sendAdvertisingFromText(String text, String subject, Collection<? extends Client> clients);
    void sendDispatchStatusNotification(Client client);
    void sendBalanceInfoAfterDebiting(Long newBalance, Long debited, String email);

}
