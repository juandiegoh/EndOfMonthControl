package com.mercadolibre.endOfMonthControl.csv.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FilesFactory {

	public FileWriter createFileWriter(String path) throws IOException {
		return new FileWriter(path);
	}

	public Reader createFileReader(String path) throws IOException {
		// return new FileReader(path);
		return new InputStreamReader(new FileInputStream(path), "Windows-1252");
	}

	public File createFile(String path) {
		return new File(path);
	}
}
