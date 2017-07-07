package com.cafe.crm.services.interfaces.email;


import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.worker.Boss;

import java.util.Collection;

public interface EmailService {

	void sendAdvertisingFromImage(String imageUrl, String subject, String urlToLink, Collection<? extends Card> cards);

	void sendAdvertisingFromText(String text, String subject, Collection<? extends Card> cards);

	void sendDispatchStatusNotification(Card card);

	void sendBalanceInfoAfterDebiting(Double newBalance, Double debited, String email);

	void sendCloseShiftInfoFromText(Double salaryShift, Double profitShift, Long cache, Long payWithCard, Collection<? extends Boss> boss);

}