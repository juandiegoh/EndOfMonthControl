package com.mercadolibre.endOfMonthControl.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.csv.SapResultsCsv;
import com.mercadolibre.endOfMonthControl.exceptions.CsvGenerationException;
import com.mercadolibre.endOfMonthControl.model.Sap;
import com.mercadolibre.endOfMonthControl.model.aux.FocusMinusSapAndDuplicates;
import com.mercadolibre.endOfMonthControl.model.utils.DifferenceCollectionUtils;
import com.mercadolibre.endOfMonthControl.model.utils.TimestampCalculator;

public class CsvOutputGeneratorService {

	private static Logger log = LoggerFactory.getLogger(CsvOutputGeneratorService.class);

	private static final String DUPLICATES_FILE_NAME = "Duplicados.csv";
	private static final String FOCUS_MINUS_SAP_FILE_NAME = "FocusMenosSap.csv";
	private static final String SAP_MINUS_FOCUS_FILE_NAME = "SapMenosFocus.csv";

	@Inject
	private DifferenceCollectionUtils differenceCollectionUtils;

	@Inject
	private SapResultsCsv sapResultsCsv;

	@Inject
	private TimestampCalculator timestampCalculator;

	public void run(List<Sap> sap, List<Sap> focus, String outPutBasePath) throws CsvGenerationException {

		FocusMinusSapAndDuplicates focusMinusSapAndDuplicates = getDifferenceFocusMinusSap(sap, focus);

		// Duplicates
		String duplicatesPath = outPutBasePath + DUPLICATES_FILE_NAME;
		generateDuplicates(duplicatesPath, focusMinusSapAndDuplicates);

		// Focus Minus Payments
		String focusMinusSapPath = outPutBasePath + FOCUS_MINUS_SAP_FILE_NAME;
		generateFocusMinusSap(focusMinusSapAndDuplicates, focusMinusSapPath);

		// Sap Minus Payments
		List<Sap> sapMinusFocus = getDifferenceSapMinusFocus(sap, focus);
		String sapMinusFocusPath = outPutBasePath + SAP_MINUS_FOCUS_FILE_NAME;
		generateSapMinusFocus(sapMinusFocus, sapMinusFocusPath);
	}

	private void generateSapMinusFocus(List<Sap> sapMinusFocus, String sapMinusFocusPath) throws CsvGenerationException {
		Long startTime = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start generatin Sap Minus Focus file=%s at %s", sapMinusFocusPath, startTime));

		try {
			sapResultsCsv.generateCsv(sapMinusFocusPath, sapMinusFocus);
		} catch (IOException e) {
			e.printStackTrace();
			String errorMessage = String.format("There was an error generating the Sap Minus Focus file: %s. %s",
					sapMinusFocusPath, e.getMessage());
			log.error(errorMessage);
			throw new CsvGenerationException(errorMessage);
		}

		log.info(String
				.format("Finished generating Sap Minus Focus file=%s taking %s",
						sapMinusFocusPath,
						this.timestampCalculator.getStringTimeFromTimeStamp(this.timestampCalculator.getTimeStamp()
								- startTime)));
	}

	private List<Sap> getDifferenceSapMinusFocus(List<Sap> sap, List<Sap> focus) {
		Long startTime = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start getting difference Sap Minus Focus at ", startTime));

		List<Sap> sapMinusFocus = this.differenceCollectionUtils.differenceBetweenSapMinusFocus(sap, focus);

		log.info(String.format("Finished getting difference Sap Minus Focus at ", this.timestampCalculator
				.getStringTimeFromTimeStamp(this.timestampCalculator.getTimeStamp() - startTime)));
		return sapMinusFocus;
	}

	private void generateFocusMinusSap(FocusMinusSapAndDuplicates focusMinusSapAndDuplicates, String focusMinusSapPath)
			throws CsvGenerationException {

		Long startTime = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start generating Focus Minus Sap file=%s at %s", focusMinusSapPath, startTime));

		try {
			sapResultsCsv.generateCsv(focusMinusSapPath, focusMinusSapAndDuplicates.getFocusMinusSap());
		} catch (IOException e) {
			e.printStackTrace();
			String errorMessage = String.format("There was an error generating the Focus Minus Sap file: %s. %s",
					focusMinusSapPath, e.getMessage());
			log.error(errorMessage);
			throw new CsvGenerationException(errorMessage);
		}

		log.info(String
				.format("Finished generating Focus Minus Sap file=%s taking %s",
						focusMinusSapPath,
						this.timestampCalculator.getStringTimeFromTimeStamp(this.timestampCalculator.getTimeStamp()
								- startTime)));
	}

	private void generateDuplicates(String duplicatesPath, FocusMinusSapAndDuplicates focusMinusSapAndDuplicates)
			throws CsvGenerationException {

		Long startTime = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start generatin duplicates file=%s at %s", duplicatesPath, startTime));

		try {
			sapResultsCsv.generateCsv(duplicatesPath, focusMinusSapAndDuplicates.getDuplicatedSap());
		} catch (IOException e) {
			e.printStackTrace();
			String errorMessage = String.format("There was an error generating the Duplicate file: %s. %s",
					duplicatesPath, e.getMessage());
			log.error(errorMessage);
			throw new CsvGenerationException(errorMessage);
		}

		log.info(String.format("Finished generating file=%s taking %s", duplicatesPath, this.timestampCalculator
				.getStringTimeFromTimeStamp(this.timestampCalculator.getTimeStamp() - startTime)));
	}

	private FocusMinusSapAndDuplicates getDifferenceFocusMinusSap(List<Sap> sap, List<Sap> focus) {
		Long startTime = this.timestampCalculator.getTimeStamp();
		log.info(String.format("Start getting difference Focus Minus Sap at %s", startTime));

		FocusMinusSapAndDuplicates focusMinusSapAndDuplicates = this.differenceCollectionUtils
				.differenceBetweenFocusMinusSap(focus, sap);

		log.info(String.format("Finished getting difference Focus Minus Sap taking %s", this.timestampCalculator
				.getStringTimeFromTimeStamp(this.timestampCalculator.getTimeStamp() - startTime)));
		return focusMinusSapAndDuplicates;
	}
}
