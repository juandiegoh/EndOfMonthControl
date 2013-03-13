package com.mercadolibre.endOfMonthControl.model.aux;

import java.util.List;

import com.mercadolibre.endOfMonthControl.model.Sap;

public class FocusMinusSapAndDuplicates {

	private List<Sap> focusMinusSap;
	private List<Sap> duplicatedSap;
	
	public FocusMinusSapAndDuplicates(List<Sap> focusMinusSap, List<Sap> duplicatedSap) {
		this.focusMinusSap = focusMinusSap;
		this.duplicatedSap = duplicatedSap;
	}

	public List<Sap> getFocusMinusSap() {
		return focusMinusSap;
	}

	public List<Sap> getDuplicatedSap() {
		return duplicatedSap;
	}
}
