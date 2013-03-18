package com.mercadolibre.endOfMonthControl.model.factory;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.utils.DateTimeUtils;

public class SapFactory {

	private static final int ROUND_SCALE = 2;
	private static final int ROUND_MODE = BigDecimal.ROUND_DOWN;
	@Inject
	private DateTimeUtils dateTimeUtils;

	public Sap createSapFromFocus(String site, String sSapId, String sAmount, String sDate, String sPaymentId) {

		Long sapId = Long.valueOf(sSapId);

		BigDecimal amount = new BigDecimal(sAmount.trim().replaceAll(",", "")).setScale(ROUND_SCALE, ROUND_MODE);

		LocalDate date = this.dateTimeUtils.getDateTimeFromDDMMYYYYHHMM(sDate);

		Long paymentId = StringUtils.isBlank(sPaymentId) ? null : Long.valueOf(sPaymentId);

		return new Sap(site, sapId, amount, date, paymentId);
	}

	public Sap createSapFromSap(String site, String sSapId, String sAmount, String sDate) throws ParseException {
		Long sapId = Long.valueOf(sSapId);

		BigDecimal amount = new BigDecimal(sAmount.replaceAll(",", "")).setScale(ROUND_SCALE, ROUND_MODE);

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
