package com.mercadolibre.endOfMonthControl.gui;

import java.io.File;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.google.inject.Inject;

public class MainWindowController {
	@Inject
	private MainWindow mainWindow;
	
	private static Logger logger = (Logger) LoggerFactory.getLogger(MainWindowController.class);

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	} 
	
	public void initWindow(){
		mainWindow.getMainFrame().setVisible(true);
	}
	
	public void processInputFromUi(String sapFilePath, String focusFilePath, String targetDirectoryPath, String targetName){
		String ERROR_MESSAGE_DIALOG = "Por favor complete todos los campos antes de procesar";
		String FILE_NOT_EXISTS_MESSAGE_DIALOG = "Los siguientes archivos no existen: " + System.getProperty("line.separator");
		
		if(sapFilePath.isEmpty() || focusFilePath.isEmpty() || targetDirectoryPath.isEmpty() || targetName.isEmpty()){
			mainWindow.showErrorMessage(ERROR_MESSAGE_DIALOG);
		}else{
			if(fileExists(sapFilePath) && fileExists(focusFilePath) && fileExists(targetDirectoryPath)){ 
				logger.info("Todo OK");
			}else{
				StringBuffer buffer = new StringBuffer();
				if(!fileExists(sapFilePath)){
					buffer.append(sapFilePath);
				}
				if(!fileExists(focusFilePath)){
					if(buffer.length()>0){
						buffer.append(System.getProperty("line.separator"));
					}
					buffer.append(focusFilePath);
				}
				if(!fileExists(targetDirectoryPath)){
					if(buffer.length()>0){
						buffer.append(System.getProperty("line.separator"));
					}
					buffer.append(targetDirectoryPath);
				}
				mainWindow.showErrorMessage(FILE_NOT_EXISTS_MESSAGE_DIALOG + buffer.toString());
			}
		}
	}
	
	private boolean fileExists(String filePath){
		File file = new File(filePath);
		return file.exists(); 
	}
}
