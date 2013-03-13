package com.mercadolibre.endOfMonthControl.csv;

import java.text.ParseException;
import java.util.Map;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.factory.SapFactory;

public class SapFromFocusCsv extends CsvExporter<Sap> {

	@Inject private SapFactory sapFactory;
	
	@Override
	protected String[] getHeaders() {
		return new String[]{ 
			"0Focus Site",
			"1Focus Operation ID",
			"2Focus Source ID",
			"3Focus Installment",
			"4Focus Payer ID",
			"5Focus Payment ID",
			"6Focus Authorization Code",
			"7Focus Number TC",
			"8Focus SAP Status",
			"9Focus SAP ID",
			"10Focus Move ID",
			"11Focus SAP Date",
			"12Focus NSU Number",
			"13Focus TID Number",
			"14Fc Payment Method Site",
			"15Fc Payment Method Payment ID",
			"16Fc Payment Method Payment Type",
			"17Fc Payment Method DESC",
			"18Indicadores",
			"19Focus Total Paid Amount"
		};
	}

	@Override
	protected String[] transformElementToRow(Sap movement) {
		return null;
	}

	@Override
	protected Sap transformRowToElement(String[] row) throws ParseException {
		return this.sapFactory.createSapFromFocus(row[0], row[9], row[19], row[11], row[5]);
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
