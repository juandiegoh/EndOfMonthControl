package com.mercadolibre.endOfMonthControl.model.factory;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.mercadolibre.endOfMonthControl.model.Sap;

public class SapFactory {

	public Sap createSap(Long sapId, BigDecimal amount, DateTime date) {
		return new Sap(sapId, amount, date);
	}
}
