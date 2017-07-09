package com.cafe.crm.services.impl.email;


import com.cafe.crm.configs.property.AdvertisingProperties;
import com.cafe.crm.configs.property.BalanceInfoProperties;
import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.services.interfaces.email.EmailService;
import com.cafe.crm.services.interfaces.email.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmailServiceImpl implements EmailService {

	private final HtmlService htmlService;

	private final AdvertisingProperties properties;

	private final JavaMailSender javaMailSender;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final BalanceInfoProperties balanceInfoProperties;

	@Value("${closeShiftEmailShortage.view}")
	private String closeShiftView;

	@Value("${closeShiftEmailShortage.text}")
	private String closeShiftText;

	@Value("${closeShiftEmailShortage.subject}")
	private String closeShiftSubject;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender, AdvertisingProperties properties, HtmlService htmlService, BCryptPasswordEncoder bCryptPasswordEncoder, BalanceInfoProperties balanceInfoProperties) {
		this.javaMailSender = javaMailSender;
		this.properties = properties;
		this.htmlService = htmlService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.balanceInfoProperties = balanceInfoProperties;
	}

	@Override
	public void sendAdvertisingFromImage(String imageUrl, String subject, String urlToLink, Collection<? extends Card> cards) {
		MimeMessagePreparator[] mimeMessages = new MimeMessagePreparator[cards.size()];
		int messageNum = 0;
		for (Card card : cards) {
			String email = card.getEmail();
			if (email == null) {
				continue;
			}
			mimeMessages[messageNum++] = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom(properties.getMail().getSender());
				messageHelper.setTo(email);
				messageHelper.setSubject(subject);
				Long id = card.getId();
				String token = bCryptPasswordEncoder.encode(email);
				String view = properties.getMail().getImageView();
				String html = htmlService.getAdvertisingFromImage(imageUrl, view, urlToLink, id, token);
				messageHelper.setText(html, true);
			};
		}
		if (messageNum == 0) {
			return;
		}
		javaMailSender.send(mimeMessages);
	}

	@Override
	public void sendAdvertisingFromText(String text, String subject, Collection<? extends Card> cards) {
		MimeMessagePreparator[] mimeMessages = new MimeMessagePreparator[cards.size()];
		int messageNum = 0;
		for (Card card : cards) {
			String email = card.getEmail();
			if (email == null) {
				continue;
			}
			mimeMessages[messageNum++] = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom(properties.getMail().getSender());
				messageHelper.setTo(email);
				messageHelper.setSubject(subject);
				Long id = card.getId();
				String token = bCryptPasswordEncoder.encode(email);
				String view = properties.getMail().getTextView();
				String html = htmlService.getAdvertisingFromText(text, view, id, token);
				messageHelper.setText(html, true);
			};
		}
		if (messageNum == 0) {
			return;
		}
		javaMailSender.send(mimeMessages);
	}

	@Override
	public void sendDispatchStatusNotification(Card card) {
		if (card == null) {
			return;
		}
		String email = card.getEmail();
		if (email == null) {
			return;
		}
		MimeMessagePreparator message = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(properties.getMail().getSender());
			messageHelper.setTo(email);
			messageHelper.setSubject(properties.getMail().getDisableSubject());
			Long id = card.getId();
			String token = bCryptPasswordEncoder.encode(email);
			String view = properties.getMail().getDisableView();
			String html = htmlService.getAdvertisingForDisable(view, id, token);
			messageHelper.setText(html, true);
		};
		javaMailSender.send(message);
	}

	// TODO: 25.06.2017 Сделать возможность добалять чек в письмо
	@Override
	public void sendBalanceInfoAfterDeduction(Double newBalance, Double deductionAmount, String email) {
		if (email == null) {
			return;
		}
		MimeMessagePreparator message = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(properties.getMail().getSender());
			messageHelper.setTo(email);
			messageHelper.setSubject(balanceInfoProperties.getDeduction().getSubject());
			String html = htmlService.getBalanceInfoAfterDeduction(newBalance, deductionAmount, balanceInfoProperties.getDeduction().getView());
			messageHelper.setText(html, true);
		};
		javaMailSender.send(message);
	}

	@Override
	public void sendBalanceInfoAfterRefill(Double newBalance, Double refillAmount, String email) {
		if (email == null) {
			return;
		}
		MimeMessagePreparator message = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(properties.getMail().getSender());
			messageHelper.setTo(email);
			messageHelper.setSubject(balanceInfoProperties.getRefill().getSubject());
			String html = htmlService.getBalanceInfoAfterRefill(newBalance, refillAmount, balanceInfoProperties.getRefill().getView());
			messageHelper.setText(html, true);
		};
		javaMailSender.send(message);
	}

	@Override
	public void sendCloseShiftInfoFromText(Double salaryShift, Double profitShift, Long cache, Long payWithCard, Collection<? extends Boss> boss) {
		MimeMessagePreparator[] mimeMessages = new MimeMessagePreparator[boss.size()];
		int messageNum = 0;
		for (Boss bosses : boss) {
			String email = bosses.getEmail();
			if (email == null) {
				continue;
			}
			mimeMessages[messageNum++] = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom(properties.getMail().getSender());
				messageHelper.setTo(email);
				messageHelper.setSubject(closeShiftSubject);
				String html = htmlService.getCloseShiftFromText(closeShiftText, salaryShift, profitShift, cache, payWithCard,
						closeShiftView);
				messageHelper.setText(html, true);
			};
		}
		if (messageNum == 0) {
			return;
		}
		javaMailSender.send(mimeMessages);
	}
}
