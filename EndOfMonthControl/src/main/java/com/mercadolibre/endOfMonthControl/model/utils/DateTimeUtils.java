package com.mercadolibre.endOfMonthControl.model.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtils {

	public LocalDate getDateTimeFromDDMMYYYY(String sDate) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		return formatter.parseDateTime(sDate).toLocalDate();
	}
	
	public LocalDate getDateTimeFromDDMMYYYYHHMM(String sDate) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		return formatter.parseDateTime(sDate).toLocalDate();
	}
	
	public String getStringFromDateTime(LocalDate time) {
		return time.toString("dd/MM/yyyy");
	}
}
