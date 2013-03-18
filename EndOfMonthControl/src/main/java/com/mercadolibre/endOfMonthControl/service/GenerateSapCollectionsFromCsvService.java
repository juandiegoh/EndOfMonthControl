package com.mercadolibre.endOfMonthControl.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.csv.CsvExporter;
import com.mercadolibre.endOfMonthControl.csv.SapFromFocusCsv;
import com.mercadolibre.endOfMonthControl.csv.SapFromSapCsv;
import com.mercadolibre.endOfMonthControl.exceptions.CsvReaderException;
import com.mercadolibre.endOfMonthControl.exceptions.MissingColumnException;
import com.mercadolibre.endOfMonthControl.model.Sap;

public class GenerateSapCollectionsFromCsvService {

	private static Logger log = LoggerFactory.getLogger(GenerateSapCollectionsFromCsvService.class);

	@Inject
	private SapFromSapCsv sapFromSapCsv;

	@Inject
	private SapFromFocusCsv sapFromFocusCsv;

	public List<Sap> readSapFromSapCsv(String path) throws CsvReaderException, MissingColumnException {
		return this.readCsv(sapFromSapCsv, path);
	}

	public List<Sap> readSapFromFocusCsv(String path) throws CsvReaderException, MissingColumnException {
		return this.readCsv(sapFromFocusCsv, path);
	}

	private List<Sap> readCsv(CsvExporter<Sap> reader, String path) throws CsvReaderException, MissingColumnException {
		try {
			return reader.readCsv(path, null);
		} catch (IOException e) {
			e.printStackTrace();
			String message = String.format("There was an error reading %s. %s", path, e.getCause());
			log.error(message);
			throw new CsvReaderException(message);
		} catch (ParseException e) {
			String message = String.format("There was an error parsing %s. %s", path, e.getCause());
			log.error(message);
			throw new CsvReaderException(message);
		} catch (MissingColumnException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
	}
}
