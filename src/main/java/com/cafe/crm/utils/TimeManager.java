package com.cafe.crm.utils;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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

	public LocalDateTime getDate() {
		timeClient.setDefaultTimeout(500);
			for (String server : servers) {
				try {
					inetAddress = InetAddress.getByName(server);
					timeInfo = timeClient.getTime(inetAddress);
					long returnTime = timeInfo.getReturnTime();
					date = Instant.ofEpochMilli(returnTime).atZone(ZoneId.systemDefault()).toLocalDateTime();

					return date;
				} catch (IOException e) {
					continue;
				}
			}
		date = LocalDateTime.now();
		return date;
	}
}
