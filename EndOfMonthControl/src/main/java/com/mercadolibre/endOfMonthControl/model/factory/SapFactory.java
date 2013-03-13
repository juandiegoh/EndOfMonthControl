package com.mercadolibre.endOfMonthControl.model.factory;

import java.math.BigDecimal;
import java.text.ParseException;

import org.joda.time.LocalDate;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.utils.DateTimeUtils;

public class SapFactory {

	private static final int ROUND_SCALE = 2;
	private static final String S_NEGATIVE = "H";
	private static final String S_POSITIVE = "S";
	private static final String NEGATIVE = "-1";
	private static final String POSITIVE = "1";
	private static final int ROUND_MODE = BigDecimal.ROUND_DOWN;
	@Inject private DateTimeUtils dateTimeUtils;

	public Sap createSapFromFocus(String site, String sSapId, String sAmount, String sDate, String sPaymentId) {
		
		Long sapId = Long.valueOf(sSapId);
		
		BigDecimal amount = new BigDecimal(sAmount.replaceAll(",", "")).setScale(ROUND_SCALE, ROUND_MODE);
		
		LocalDate date = this.dateTimeUtils.getDateTimeFromDDMMYYYYHHMM(sDate);
		
		Long paymentId = Long.valueOf(sPaymentId);
		
		return new Sap(site, sapId, amount, date, paymentId);
	}
	
	public Sap createSapFromSap(String site, String sSapId, String sSign, String sAmount, String sDate)
			throws ParseException {
		Long sapId = Long.valueOf(sSapId);

		String sMultiplier = "";
		if (S_POSITIVE.equals(sSign)) {
			sMultiplier = POSITIVE;
		} else if (S_NEGATIVE.equals(sSign)) {
			sMultiplier = NEGATIVE;
		} else {
			throw new ParseException(String.format("The sign is different than an H or an S, it is '%s' for sap_id=%s",
					sSign, sSapId), 0);
		}

		BigDecimal amountWithOutSign = new BigDecimal(sAmount.replaceAll(",", "")).setScale(ROUND_SCALE, ROUND_MODE);
		BigDecimal multiplier = new BigDecimal(sMultiplier).setScale(ROUND_SCALE, ROUND_MODE);
		BigDecimal amount = amountWithOutSign.multiply(multiplier).setScale(ROUND_SCALE, ROUND_MODE);
		
		LocalDate date = this.getDateTimeUtils().getDateTimeFromDDMMYYYY(sDate);
		
		return new Sap(site, sapId, amount, date, null);
	}

	public DateTimeUtils getDateTimeUtils() {
		return dateTimeUtils;
	}

	public void setDateTimeUtils(DateTimeUtils dateTimeUtils) {
		this.dateTimeUtils = dateTimeUtils;
	}
}
