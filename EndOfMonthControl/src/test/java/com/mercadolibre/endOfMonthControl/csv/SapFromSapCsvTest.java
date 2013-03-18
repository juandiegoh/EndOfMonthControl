package com.mercadolibre.endOfMonthControl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mercadolibre.endOfMonthControl.guice.EndOfMonthControlTestModule;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.utils.TimestampCalculator;

public class SapFromSapCsvTest {

	private SapFromSapCsv csvReader;
	private TimestampCalculator timestampCalculator;
	private static Logger logger = LoggerFactory.getLogger(SapFromSapCsvTest.class);

	@Before
	public void setUp() {
		Injector injector = Guice.createInjector(new EndOfMonthControlTestModule());
		this.csvReader = injector.getInstance(SapFromSapCsv.class);
		this.timestampCalculator = injector.getInstance(TimestampCalculator.class);
	}

	@Test
	public void testReader() {
		long startTime = this.timestampCalculator.getTimeStamp();
		logger.info("Start reading: test-SAP.csv at " + startTime);

		List<Sap> saps = null;

		try {
			saps = this.csvReader.readCsv("test-SAP.csv", null);
		} catch (Exception e) {
			assertTrue(false);
		}

		long endTime = this.timestampCalculator.getTimeStamp();
		logger.info("Finished reading: test-SAP.csv at "
				+ this.timestampCalculator.getStringTimeFromTimeStamp(endTime - startTime));

		assertEquals(62786, saps.size());
	}
}
