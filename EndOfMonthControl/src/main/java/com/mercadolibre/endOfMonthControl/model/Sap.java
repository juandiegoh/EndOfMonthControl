package com.mercadolibre.endOfMonthControl.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

public class Sap {

	private Long sapId;
	private BigDecimal amount;
	private LocalDate date;
	private String site;
	private Long paymentId;

	public Sap(String site, Long sapId, BigDecimal amount, LocalDate date, Long paymentId) {
		this.site = site;
		this.sapId = sapId;
		this.amount = amount;
		this.date = date;
		this.paymentId = paymentId;
	}

	public String getSite() {
		return site;
	}

	public Long getSapId() {
		return sapId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Sap) {
			Sap s = (Sap) o;
			return this.getSapId().equals(s.getSapId()) && this.getAmount().equals(s.getAmount())
					&& this.getDate().equals(s.getDate());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3, 7).append(this.sapId).append(this.amount).append(this.date).toHashCode();
	}

	@Override
	public String toString() {
		return sapId.toString() + " " + amount.toString() + " " + date.toString();
	}

}
