package com.mercadolibre.endOfMonthControl.model.utils;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mercadolibre.endOfMonthControl.csv.SapFromFocusCsv;
import com.mercadolibre.endOfMonthControl.csv.SapFromSapCsv;
import com.mercadolibre.endOfMonthControl.guice.EndOfMonthControlTestModule;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.aux.FocusMinusSapAndDuplicates;

public class DifferenceCollectionUtilsTest {

	private static final int ROUND_SCALE = 2;
	private static final int ROUND_MODE = BigDecimal.ROUND_DOWN;
	private DifferenceCollectionUtils util;
	private DateTimeUtils dateUtils;
	private SapFromSapCsv sapFromSap;
	private SapFromFocusCsv sapFromFocus;
	private TimestampCalculator timestampCalculator;

	private static Logger logger = LoggerFactory.getLogger(DifferenceCollectionUtilsTest.class);

	@Before
	public void setUp() {
		Injector injector = Guice.createInjector(new EndOfMonthControlTestModule());
		this.sapFromSap = injector.getInstance(SapFromSapCsv.class);
		this.sapFromFocus = injector.getInstance(SapFromFocusCsv.class);
		this.timestampCalculator = injector.getInstance(TimestampCalculator.class);
		this.util = injector.getInstance(DifferenceCollectionUtils.class);
		this.dateUtils = injector.getInstance(DateTimeUtils.class);
	}

	@Test
	public void ifItContainsEveryElementItMustReturnAnEmptyList() {
		LocalDate date = this.dateUtils.getDateTimeFromDDMMYYYY("10/10/2013");

		Sap f1 = new Sap("MLA", 1L, new BigDecimal(100.00D).setScale(ROUND_SCALE, ROUND_MODE), date, 1L);
		Sap f2 = new Sap("MLA", 1L, new BigDecimal(100.00D).setScale(ROUND_SCALE, ROUND_MODE), date, 1L);
		List<Sap> focus = Lists.newArrayList();
		focus.add(f1);
		focus.add(f2);

		Sap s1 = new Sap("MLA", 1L, new BigDecimal(100D).setScale(ROUND_SCALE, ROUND_MODE), date, 1L);
		List<Sap> sap = Lists.newArrayList();
		sap.add(s1);

		FocusMinusSapAndDuplicates focusMinusSapAndDuplicates = util.differenceBetweenFocusMinusSap(focus, sap);

		assertTrue(focusMinusSapAndDuplicates.getFocusMinusSap().isEmpty());
		assertTrue(focusMinusSapAndDuplicates.getDuplicatedSap().size() == 1);
	}

	@Test
	public void ifItContainsOneElementItMustReturnTheListWithTheExtraOne() {
		LocalDate date = this.dateUtils.getDateTimeFromDDMMYYYY("10/10/2013");

		Sap f1 = new Sap("MLA", 1L, new BigDecimal(100D).setScale(ROUND_SCALE, ROUND_MODE), date, 1L);
		List<Sap> focus = Lists.newArrayList();
		focus.add(f1);

		Sap s1 = new Sap("MLA", 1L, new BigDecimal(100D).setScale(ROUND_SCALE, ROUND_MODE), date, 1L);
		Sap s2 = new Sap("MLA", 2L, new BigDecimal(100D).setScale(ROUND_SCALE, ROUND_MODE), date, 1L);

		List<Sap> sap = Lists.newArrayList();
		sap.add(s1);
		sap.add(s2);

		List<Sap> sapMinusFocus = util.differenceBetweenSapMinusFocus(sap, focus);

		assertTrue(sapMinusFocus.size() == 1);
		assertTrue(sapMinusFocus.contains(s2));
	}

	@Test
	public void focusMinusSapGiantTest() throws IOException, ParseException {
		List<Sap> sap = this.sapFromSap.readCsv("test-SAP.csv", null);
		List<Sap> focus = this.sapFromFocus.readCsv("test-FOCUS.csv", null);

		long startTime = this.timestampCalculator.getTimeStamp();
		logger.info("Start removing -> Focus - Sap: test-FOCUS.csv at " + startTime);
		FocusMinusSapAndDuplicates focusMinusSapAndDuplicates = this.util.differenceBetweenFocusMinusSap(focus, sap);
		long endTime = this.timestampCalculator.getTimeStamp();
		logger.info("Finished removing -> Focus - Sap: test-FOCUS.csv at "
				+ this.timestampCalculator.getStringTimeFromTimeStamp(endTime - startTime));

		List<Sap> differenceBetweenFocusMinusSap = focusMinusSapAndDuplicates.getFocusMinusSap();
		List<Sap> duplicatedSap = focusMinusSapAndDuplicates.getDuplicatedSap();

		long startTime2 = this.timestampCalculator.getTimeStamp();
		logger.info("Start removing -> Sap - Focus: test-SAP.csv at" + startTime);
		List<Sap> differenceBetweenSapMinusFocus = this.util.differenceBetweenSapMinusFocus(sap, focus);
		long endTime2 = this.timestampCalculator.getTimeStamp();
		logger.info("Finished removing -> Sap - Focus: test-SAP.csv at "
				+ this.timestampCalculator.getStringTimeFromTimeStamp(endTime2 - startTime2));

		logger.info("Focus - Sap = " + differenceBetweenFocusMinusSap.size());
		logger.info("Sap - Focus = " + differenceBetweenSapMinusFocus.size());

		BigDecimal sapMinusFocusTotalAmount = BigDecimal.ZERO;
		for (Sap s : differenceBetweenSapMinusFocus) {
			sapMinusFocusTotalAmount = sapMinusFocusTotalAmount.add(s.getAmount());
		}

		logger.info("Sap - Focus Total Amount = " + sapMinusFocusTotalAmount);

		BigDecimal duplicatedSapTotalAmount = BigDecimal.ZERO;
		for (Sap s : duplicatedSap) {
			duplicatedSapTotalAmount = duplicatedSapTotalAmount.add(s.getAmount());
		}

		logger.info("Duplicated Saps Total Amount = " + duplicatedSapTotalAmount);

		assertTrue(differenceBetweenFocusMinusSap.size() == 0);
		assertTrue(differenceBetweenSapMinusFocus.size() == 499);
		assertTrue(duplicatedSap.size() == 6);

	}

	@Test
	public void focusMinusSapSmallTest() throws IOException, ParseException {
		List<Sap> sap = this.sapFromSap.readCsv("test2-SAP.csv", null);
		List<Sap> focus = this.sapFromFocus.readCsv("test2-FOCUS.csv", null);

		long startTime = this.timestampCalculator.getTimeStamp();
		logger.info("Start removing -> Focus - Sap: test-FOCUS.csv at " + startTime);
		FocusMinusSapAndDuplicates focusMinusSapAndDuplicates = this.util.differenceBetweenFocusMinusSap(focus, sap);
		List<Sap> differenceBetweenFocusMinusSap = focusMinusSapAndDuplicates.getFocusMinusSap();
		long endTime = this.timestampCalculator.getTimeStamp();
		logger.info("Finished removing -> Focus - Sap: test-FOCUS.csv at "
				+ this.timestampCalculator.getStringTimeFromTimeStamp(endTime - startTime));

		long startTime2 = this.timestampCalculator.getTimeStamp();
		logger.info("Start removing -> Sap - Focus: test-SAP.csv at" + startTime);
		List<Sap> differenceBetweenSapMinusFocus = this.util.differenceBetweenSapMinusFocus(sap, focus);
		long endTime2 = this.timestampCalculator.getTimeStamp();
		logger.info("Finished removing -> Sap - Focus: test-SAP.csv at "
				+ this.timestampCalculator.getStringTimeFromTimeStamp(endTime2 - startTime2));

		assertTrue(differenceBetweenFocusMinusSap.isEmpty());
		assertTrue(differenceBetweenSapMinusFocus.isEmpty());

	}
}
