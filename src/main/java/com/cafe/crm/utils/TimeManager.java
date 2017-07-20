package com.cafe.crm.utils;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.time.*;
import java.util.List;

@Component
public class TimeManager {

	@Value("#{'${time.server}'.split(',')}")
	private List<String> servers;

	private LocalDateTime date;

	private NTPUDPClient timeClient = new NTPUDPClient();

	private InetAddress inetAddress;

	private TimeInfo timeInfo;

	private boolean isServerDateTime;

	public TimeManager() {
	}

	public boolean getIsServerDateTime() {
		return isServerDateTime;
	}

	public LocalDate getDate() {
		return getDateTime().toLocalDate();
	}

	public LocalDateTime getDateTime() {
		timeClient.setDefaultTimeout(500);
		for (String server : servers) {
			try {
				inetAddress = InetAddress.getByName(server);
				timeInfo = timeClient.getTime(inetAddress);
				long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
				date = Instant.ofEpochMilli(returnTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
				isServerDateTime = true;
				return date;
			} catch (IOException ignored) {
			}
		}
		isServerDateTime = false;
		date = LocalDateTime.now();
		return date;
	}

	public LocalTime getTime() {
		return getDateTime().toLocalTime();
	}

}
