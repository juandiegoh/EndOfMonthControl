package com.mercadolibre.endOfMonthControl.csv.factory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilesFactory {

	public FileWriter createFileWriter(String path) throws IOException {
		return new FileWriter(path);
	}
	
	public FileReader createFileReader(String path) throws IOException {
		return new FileReader(path);
	}
	
	public File createFile(String path) {
		return new File(path);
	}
}
