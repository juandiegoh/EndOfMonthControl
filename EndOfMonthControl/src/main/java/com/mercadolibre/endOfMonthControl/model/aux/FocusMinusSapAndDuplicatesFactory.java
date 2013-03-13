package com.mercadolibre.endOfMonthControl.model.aux;

import java.util.List;

import com.mercadolibre.endOfMonthControl.model.Sap;

public class FocusMinusSapAndDuplicatesFactory {

	public FocusMinusSapAndDuplicates create(List<Sap> focusMinusSap, List<Sap> duplicatedSapIds) {
		return new FocusMinusSapAndDuplicates(focusMinusSap, duplicatedSapIds);
	}
}
