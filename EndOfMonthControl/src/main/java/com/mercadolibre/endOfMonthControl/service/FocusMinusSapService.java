package com.mercadolibre.endOfMonthControl.service;

import java.util.List;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.aux.FocusMinusSapAndDuplicates;
import com.mercadolibre.endOfMonthControl.model.utils.DifferenceCollectionUtils;

public class FocusMinusSapService {

	@Inject
	private DifferenceCollectionUtils differenceCollectionUtils;

	public void run(List<Sap> sap, List<Sap> focus, String outPutBasePath) {

		FocusMinusSapAndDuplicates focusMinusSapAndDuplicates = this.differenceCollectionUtils
				.differenceBetweenFocusMinusSap(focus, sap);
	}
}
