package com.mercadolibre.endOfMonthControl.model.utils;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DateTimeUtilsTest {

	private DateTimeUtils dateTimeUtils;

	@Before
	public void setUp() {
		this.dateTimeUtils = new DateTimeUtils();
	}
	
	@Test
	public void stringToDateTime() {
		LocalDate time = this.dateTimeUtils.getDateTimeFromDDMMYYYY("14/10/2013");
		assertEquals(14, time.getDayOfMonth());
		assertEquals(10, time.getMonthOfYear());
		assertEquals(2013, time.getYear());
		
		LocalDate time2 = this.dateTimeUtils.getDateTimeFromDDMMYYYY("1/8/2013");
		assertEquals(1, time2.getDayOfMonth());
		assertEquals(8, time2.getMonthOfYear());
		assertEquals(2013, time2.getYear());
		
		LocalDate time3 = this.dateTimeUtils.getDateTimeFromDDMMYYYYHHMM("1/8/2013 17:46");
		assertEquals(1, time3.getDayOfMonth());
		assertEquals(8, time3.getMonthOfYear());
		assertEquals(2013, time3.getYear());
	}
	
	@Test
	public void dateTimeToString() {
		LocalDate time = this.dateTimeUtils.getDateTimeFromDDMMYYYY("14/10/2013");
		assertEquals("14/10/2013", this.dateTimeUtils.getStringFromDateTime(time));
		
		LocalDate time2 = this.dateTimeUtils.getDateTimeFromDDMMYYYY("1/8/2013");
		assertEquals("01/08/2013", this.dateTimeUtils.getStringFromDateTime(time2));
		
		LocalDate time3 = this.dateTimeUtils.getDateTimeFromDDMMYYYY("01/08/2013");
		assertEquals("01/08/2013", this.dateTimeUtils.getStringFromDateTime(time3));
		
		LocalDate time4 = this.dateTimeUtils.getDateTimeFromDDMMYYYYHHMM("1/8/2013 17:46");
		assertEquals("01/08/2013", this.dateTimeUtils.getStringFromDateTime(time4));
	}
}
