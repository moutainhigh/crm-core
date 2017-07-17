package com.cafe.crm.services.interfaces.email;


import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.worker.Boss;

import java.util.Collection;

public interface EmailService {

    void sendAdvertisingFromImage(String imageUrl, String subject, String urlToLink, Collection<? extends Card> cards);

    void sendAdvertisingFromText(String text, String subject, Collection<? extends Card> cards);

    void sendDispatchStatusNotification(Card card);

    void sendBalanceInfoAfterDeduction(Double newBalance, Double deductionAmount, String email);

    void sendBalanceInfoAfterRefill(Double newBalance, Double refillAmount, String email);

    void sendCloseShiftInfoFromText(Double cashBox, Double cache, Double bankKart, Double payWithCard,
                                    Double allPrice, Collection<? extends Boss> boss, Double shortage);

}
