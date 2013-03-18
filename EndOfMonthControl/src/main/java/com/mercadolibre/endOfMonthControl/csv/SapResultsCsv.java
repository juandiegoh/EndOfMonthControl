package com.mercadolibre.endOfMonthControl.csv;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.factory.SapFactory;
import com.mercadolibre.endOfMonthControl.model.utils.DateTimeUtils;

public class SapResultsCsv extends CsvExporter<Sap> {

	@Inject
	private DateTimeUtils dateTimeUtils;

	@Inject
	private SapFactory sapFactory;

	@Override
	protected String[] getHeaders() {
		return new String[] { "SAP_ID", "SITE", "AMOUNT", "DATE", "PAYMENT_ID" };
	}

	@Override
	protected String[] transformElementToRow(Sap sap) {
		Long paymentId = sap.getPaymentId();
		String sPaymentId = paymentId == null ? "" : paymentId.toString();
		return new String[] { sap.getSapId().toString(), sap.getSite(), sap.getAmount().toString(),
				this.dateTimeUtils.getStringFromDateTime(sap.getDate()), sPaymentId };
	}

	@Override
	protected Sap transformRowToElement(String[] row) throws ParseException {
		return sapFactory.createSapFromFocus(row[1], row[0], row[2], row[3], row[4]);
	}

	@Override
	public Boolean actuallyHasToBeRead(Sap t, Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected Boolean readWithCondition() {
		return false;
	}

	@Override
	protected List<String> getColumnsToRead() {
		return Arrays.asList("SAP_ID", "SITE", "AMOUNT", "DATE", "PAYMENT_ID");
	}

}
