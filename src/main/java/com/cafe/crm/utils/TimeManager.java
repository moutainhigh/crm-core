package com.cafe.crm.utils;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TimeManager {

	@Value("${time.server.address}")
	private String TIME_SERVER;

	private LocalDateTime date;

	public TimeManager() {
	}

	@PostConstruct
	public void genericTime() {
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress;
		TimeInfo timeInfo;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
			timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getReturnTime();
			date = Instant.ofEpochMilli(returnTime).atZone(ZoneId.systemDefault()).toLocalDateTime();

		} catch (IOException e) {
			date = LocalDateTime.now();
		}
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
