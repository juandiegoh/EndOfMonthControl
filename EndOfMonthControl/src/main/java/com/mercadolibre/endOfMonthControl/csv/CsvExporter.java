package com.mercadolibre.endOfMonthControl.csv;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.csv.factory.FilesFactory;


public abstract class CsvExporter<T> {

	protected char SEPARATOR = ',';
	@Inject private FilesFactory filesFactory;

	public void generateCsv(String fileName, List<T> ts) throws IOException {

		CSVWriter writer = new CSVWriter(this.getFilesFactory().createFileWriter(fileName), SEPARATOR);
		
		// WRITE THE HEADER
		writer.writeNext(this.getHeaders());
		
		for (T t : ts) {
			String[] entries = this.transformElementToRow(t);
			writer.writeNext(entries);
		}
		writer.close();
	}

	public List<T> readCsv(String csvPath, Map<String, Object> parameters) throws IOException, ParseException {
		List<T> resultList = new ArrayList<T>();

		CSVReader reader = new CSVReader(this.getFilesFactory().createFileReader(csvPath), SEPARATOR);

		// READE THE HEADER
		reader.readNext();

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			T t = this.transformRowToElement(nextLine);
			if (this.readWithCondition()) {
				if (this.actuallyHasToBeRead(t, parameters)) {
					resultList.add(t);
				}
			} else {
				resultList.add(t);
			}
		}
		reader.close();
		return resultList;
	}

	protected abstract String[] getHeaders();

	protected abstract String[] transformElementToRow(T sap);

	protected abstract T transformRowToElement(String[] row) throws ParseException;

	public abstract Boolean actuallyHasToBeRead(T t, Map<String, Object> parameters);

	protected abstract Boolean readWithCondition();

	public FilesFactory getFilesFactory() {
		return filesFactory;
	}

	public void setFilesFactory(FilesFactory filesFactory) {
		this.filesFactory = filesFactory;
	}

}
