package com.cafe.crm.services.interfaces.email;


import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.worker.Boss;

import java.util.Collection;

public interface EmailService {

	void sendAdvertisingFromImage(String imageUrl, String subject, String urlToLink, Collection<? extends Client> clients);

	void sendAdvertisingFromText(String text, String subject, Collection<? extends Client> clients);

	void sendDispatchStatusNotification(Client client);

	void sendBalanceInfoAfterDebiting(Double newBalance, Double debited, String email);

	void sendCloseShiftInfoFromText(Double salaryShift, Double profitShift, Long cache, Long payWithCard, Collection<? extends Boss> boss);

}
