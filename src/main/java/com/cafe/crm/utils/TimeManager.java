package com.cafe.crm.utils;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.time.*;
import java.util.List;

// TODO: 09.07.2017 TimeManager Подает время с пк
@Component
public class TimeManager {

	@Value("#{'${time.server}'.split(',')}")
	private List<String> servers;

	private LocalDateTime date;

	private NTPUDPClient timeClient = new NTPUDPClient();

	private InetAddress inetAddress;

	private TimeInfo timeInfo;

	public TimeManager() {
	}

	public LocalDate getDate() {
		return getDateTime0().toLocalDate();
	}

	public LocalDateTime getDateTime() {
		return getDateTime0();
	}

	public LocalTime getTime() {
		return getDateTime0().toLocalTime();
	}

	// TODO: 06.07.2017 Придумать название
	private LocalDateTime getDateTime0() {
		timeClient.setDefaultTimeout(500);
		for (String server : servers) {
			try {
				inetAddress = InetAddress.getByName(server);
				timeInfo = timeClient.getTime(inetAddress);
				long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
				date = Instant.ofEpochMilli(returnTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
				return date;
			} catch (IOException ignored) {
			}
		}
		date = LocalDateTime.now();
		return date;
	}
}
