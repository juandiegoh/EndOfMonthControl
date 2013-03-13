package com.mercadolibre.endOfMonthControl.model.utils;

import java.util.Date;

public class TimestampCalculator {

	public Long getTimeStamp() {
		return new Date().getTime();
	}
	
	public String getStringTimeFromTimeStamp(Long timeStamp) {
		Long totalSeconds = timeStamp/1000;
		Long hour = totalSeconds/3600;
		Long minutes = (totalSeconds-(hour*3600))/60;
		Long seconds = totalSeconds-(minutes*60)-(hour*3600);
		
		String sHour = String.valueOf(hour).length() >= 2 ? String.valueOf(hour) : "0" + String.valueOf(hour);
		String sMinutes = String.valueOf(minutes).length() >= 2 ? String.valueOf(minutes) : "0" + String.valueOf(minutes);
		String sSeconds = String.valueOf(seconds).length() >= 2 ? String.valueOf(seconds) : "0" + String.valueOf(seconds);
		
		return sHour + ":" + sMinutes + ":" + sSeconds;
	}

	public Long getTimeStampFrom(Long startTime) {
		return this.getTimeStamp() - startTime;
	}
}
