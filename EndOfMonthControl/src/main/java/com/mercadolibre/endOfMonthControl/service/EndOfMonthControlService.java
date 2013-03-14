package com.mercadolibre.endOfMonthControl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.exceptions.CsvGenerationException;
import com.mercadolibre.endOfMonthControl.exceptions.CsvReaderException;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.utils.TimestampCalculator;

public class EndOfMonthControlService {

	private static Logger log = LoggerFactory.getLogger(EndOfMonthControlService.class);

	@Inject
	private CsvOutputGeneratorService csvOutputGeneratorService;

	@Inject
	private GenerateSapCollectionsFromCsvService generateSapCollectionsFromCsvService;

	@Inject
	private TimestampCalculator timestampCalculator;

	public void run(String sapFromSapPath, String sapFromFocusPath, String baseOutputPath) throws CsvReaderException,
			CsvGenerationException {

		Long startSapFromSap = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start reading %s at %s.", sapFromSapPath, startSapFromSap));
		List<Sap> sapFromSap = this.generateSapCollectionsFromCsvService.readSapFromSapCsv(sapFromSapPath);
		Long endSapFromSap = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Finished reading %s at %s.", sapFromSapPath,
				this.timestampCalculator.getStringTimeFromTimeStamp(endSapFromSap - startSapFromSap)));

		Long startSapFromFocus = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start reading %s at %s.", sapFromFocusPath, startSapFromFocus));
		List<Sap> sapFromFocus = this.generateSapCollectionsFromCsvService.readSapFromFocusCsv(sapFromFocusPath);
		Long endSapFromFocus = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Finished reading %s at %s.", sapFromFocusPath,
				this.timestampCalculator.getStringTimeFromTimeStamp(endSapFromFocus - startSapFromFocus)));

		log.info("Start generating outputs.");
		this.csvOutputGeneratorService.run(sapFromSap, sapFromFocus, baseOutputPath);
		log.info("Finished generating outputs.");
	}
}
