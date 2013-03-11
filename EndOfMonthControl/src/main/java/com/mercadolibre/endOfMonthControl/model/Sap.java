package com.mercadolibre.endOfMonthControl.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class Sap {

	private Long sapId;
	private BigDecimal amount;
	private DateTime date;
	
	public Sap(Long sapId, BigDecimal amount, DateTime date) {
		this.sapId = sapId;
		this.amount = amount;
		this.date = date;
	}

	public Long getSapId() {
		return sapId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public DateTime getDate() {
		return date;
	}
	
	
}
