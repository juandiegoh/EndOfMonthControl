package com.mercadolibre.endOfMonthControl.csv;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.factory.SapFactory;

public class SapFromFocusCsv extends CsvExporter<Sap> {

	private static final String FOCUS_PAYMENT_ID = "Focus Payment ID";
	private static final String FOCUS_SAP_DATE = "Focus SAP Date";
	private static final String FOCUS_TOTAL_PAID_AMOUNT = "Focus Total Paid Amount";
	private static final String FOCUS_SAP_ID = "Focus SAP ID";
	private static final String FOCUS_SITE = "Focus Site";
	@Inject
	private SapFactory sapFactory;

	@Override
	protected String[] getHeaders() {
		return new String[] { "0Focus Site", "1Focus Operation ID", "2Focus Source ID", "3Focus Installment",
				"4Focus Payer ID", "5Focus Payment ID", "6Focus Authorization Code", "7Focus Number TC",
				"8Focus SAP Status", "9Focus SAP ID", "10Focus Move ID", "11Focus SAP Date", "12Focus NSU Number",
				"13Focus TID Number", "14Fc Payment Method Site", "15Fc Payment Method Payment ID",
				"16Fc Payment Method Payment Type", "17Fc Payment Method DESC", "18Indicadores",
				"19Focus Total Paid Amount" };
	}

	@Override
	protected List<String> getColumnsToRead() {
		return Arrays.asList(FOCUS_SITE, FOCUS_SAP_ID, FOCUS_TOTAL_PAID_AMOUNT, FOCUS_SAP_DATE, FOCUS_PAYMENT_ID);
	}

	@Override
	protected String[] transformElementToRow(Sap sap) {
		return null;
	}

	@Override
	protected Sap transformRowToElement(String[] row) throws ParseException {
		return this.sapFactory.createSapFromFocus(row[this.headerColumns.get(FOCUS_SITE)],
				row[this.headerColumns.get(FOCUS_SAP_ID)], row[this.headerColumns.get(FOCUS_TOTAL_PAID_AMOUNT)],
				row[this.headerColumns.get(FOCUS_SAP_DATE)], row[this.headerColumns.get(FOCUS_PAYMENT_ID)]);
	}

	@Override
	public Boolean actuallyHasToBeRead(Sap t, Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected Boolean readWithCondition() {
		return false;
	}

}
