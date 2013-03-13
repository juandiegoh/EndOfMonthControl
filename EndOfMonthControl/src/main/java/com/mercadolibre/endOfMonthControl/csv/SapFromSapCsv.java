package com.mercadolibre.endOfMonthControl.csv;

import java.text.ParseException;
import java.util.Map;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.factory.SapFactory;

public class SapFromSapCsv extends CsvExporter<Sap> {
	
	@Inject private SapFactory sapFactory;

	@Override
	protected String[] getHeaders() {
		return new String[] { "Soc.", "N¼ documen", "Cl.Doc", "Doc. date",
				"Pstng date", "Ejer", "Div.", "D/H", "Importe ML", "Moneda",
				"Ctro Coste Libro mayo Deudor", "Doc.comp.  Clv.ref.1",
				"Fecha entr", "C—dT", "Usuario Acreedor Referencia",
				"Pos" };
	}

	@Override
	protected String[] transformElementToRow(Sap movement) {
		return null;
	}

	@Override
	protected Sap transformRowToElement(String[] row) throws ParseException {
		return this.getSapFactory().createSapFromSap(row[0], row[1], row[7], row[8], row[3]);
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
