package com.mercadolibre.endOfMonthControl.csv;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.factory.SapFactory;

public class SapFromSapCsv extends CsvExporter<Sap> {

	private static final String DATE = "Doc. date";
	private static final String IMPORT = "Importe ML";
	private static final String SAP_ID = "N documento";
	private static final String SITE = "Soc.";
	@Inject
	private SapFactory sapFactory;

	@Override
	protected String[] getHeaders() {
		return new String[] { SITE, SAP_ID, "Cl.Doc", DATE, "Pstng date", "Ejer", "Div.", "D/H", IMPORT, "Moneda",
				"Ctro Coste Libro mayo Deudor", "Doc.comp.  Clv.ref.1", "Fecha entr", "C—dT",
				"Usuario Acreedor Referencia", "Pos" };
	}

	@Override
	protected List<String> getColumnsToRead() {
		return Arrays.asList(SITE, SAP_ID, IMPORT, DATE);
	}

	@Override
	protected String[] transformElementToRow(Sap sap) {
		return null;
	}

	@Override
	protected Sap transformRowToElement(String[] row) throws ParseException {
		return this.getSapFactory().createSapFromSap(row[this.headerColumns.get(SITE)],
				row[this.headerColumns.get(SAP_ID)], row[this.headerColumns.get(IMPORT)],
				row[this.headerColumns.get(DATE)]);
	}

	@Override
	public Boolean actuallyHasToBeRead(Sap t, Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected Boolean readWithCondition() {
		return false;
	}

	public SapFactory getSapFactory() {
		return sapFactory;
	}

	public void setSapFactory(SapFactory sapFactory) {
		this.sapFactory = sapFactory;
	}

}
