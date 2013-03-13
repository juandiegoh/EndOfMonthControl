package com.mercadolibre.endOfMonthControl.model.utils;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.aux.FocusMinusSapAndDuplicates;
import com.mercadolibre.endOfMonthControl.model.aux.FocusMinusSapAndDuplicatesFactory;

public class DifferenceCollectionUtils {

	@Inject
	private FocusMinusSapAndDuplicatesFactory focusMinusSapAndDuplicatesFactory;

	public FocusMinusSapAndDuplicates differenceBetweenFocusMinusSap(List<Sap> focus, List<Sap> sap) {
		List<Sap> focusMinusSap = Lists.newArrayList();

		Set<Long> sapIdsSet = Sets.newHashSet();
		List<Sap> duplicatedSap = Lists.newArrayList();

		Set<Sap> setSaps = Sets.newHashSet(sap);

		for (Sap f1 : focus) {
			if (!setSaps.contains(f1)) {
				focusMinusSap.add(f1);
			}

			if (isRepeatedSapId(f1.getSapId(), sapIdsSet)) {
				duplicatedSap.add(f1);
			}
		}

		return focusMinusSapAndDuplicatesFactory.create(focusMinusSap, duplicatedSap);
	}

	public List<Sap> differenceBetweenSapMinusFocus(List<Sap> sap, List<Sap> focus) {
		List<Sap> result = Lists.newArrayList();

		Set<Sap> focusSets = Sets.newHashSet(focus);
		for (Sap s1 : sap) {
			if (!focusSets.contains(s1)) {
				result.add(s1);
			}
		}
		return result;
	}

	private boolean isRepeatedSapId(Long sapId, Set<Long> sapIdsSet) {
		return !sapIdsSet.add(sapId);
	}
}
