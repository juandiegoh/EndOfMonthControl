package com.mercadolibre.endOfMonthControl.gui;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import com.mercadolibre.endOfMonthControl.exceptions.CsvGenerationException;
import com.mercadolibre.endOfMonthControl.exceptions.CsvReaderException;
import com.mercadolibre.endOfMonthControl.exceptions.MissingColumnException;
import com.mercadolibre.endOfMonthControl.service.EndOfMonthControlService;

public class MainWindowController {

	@Inject
	private MainWindow mainWindow;

	@Inject
	private EndOfMonthControlService endOfMonthControlService;

	private static Logger logger = LoggerFactory.getLogger(MainWindowController.class);

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void initWindow() {
		mainWindow.getMainFrame().setVisible(true);
	}

	public void processInputFromUi(String sapFilePath, String focusFilePath, String targetDirectoryPath,
			String targetName) {
		String ERROR_MESSAGE_DIALOG = "Por favor complete todos los campos antes de procesar";
		String FILE_NOT_EXISTS_MESSAGE_DIALOG = "Los siguientes archivos no existen: "
				+ System.getProperty("line.separator");

		if (sapFilePath.isEmpty() || focusFilePath.isEmpty() || targetDirectoryPath.isEmpty() || targetName.isEmpty()) {
			mainWindow.showErrorMessage(ERROR_MESSAGE_DIALOG);
		} else {
			if (fileExists(sapFilePath) && fileExists(focusFilePath) && fileExists(targetDirectoryPath)) {
				logger.info("Validaciones todas OK");
				try {
					Boolean result = this.runService(sapFilePath, focusFilePath, targetDirectoryPath, targetName);
					if (result) {
						logger.info("Proceso finalizado correctamente");
						mainWindow.showOKMessage("Todos los archivos se crearon correctamente. Proceso finalizado.");
						mainWindow.clearFields();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Error inesperado: " + e.getCause());
					mainWindow.showErrorMessage("Sucedi— un error inesperado. Se suspende la ejecuci—n.");
				}
			} else {
				StringBuffer buffer = new StringBuffer();
				if (!fileExists(sapFilePath)) {
					buffer.append(sapFilePath);
				}
				if (!fileExists(focusFilePath)) {
					if (buffer.length() > 0) {
						buffer.append(System.getProperty("line.separator"));
					}
					buffer.append(focusFilePath);
				}
				if (!fileExists(targetDirectoryPath)) {
					if (buffer.length() > 0) {
						buffer.append(System.getProperty("line.separator"));
					}
					buffer.append(targetDirectoryPath);
				}
				mainWindow.showErrorMessage(FILE_NOT_EXISTS_MESSAGE_DIALOG + buffer.toString());
			}
		}

	}

	private boolean fileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	private Boolean runService(final String sapFilePath, final String focusFilePath, final String targetDirectoryPath,
			final String targetName) throws InterruptedException, ExecutionException {

		SwingWorker<Boolean, Object> worker = new SwingWorker<Boolean, Object>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				logger.info("Start running background task.");

				String outPutBasePath = "";
				if (targetDirectoryPath.matches(File.separator + "$")) {
					outPutBasePath = targetDirectoryPath + targetName;
				} else {
					outPutBasePath = targetDirectoryPath + File.separator + targetName;
				}

				try {
					endOfMonthControlService.run(sapFilePath, focusFilePath, outPutBasePath);
				} catch (CsvReaderException e) {
					e.printStackTrace();
					logger.error(String.format("There was an error Reading the CVS:", e.getCause()));
					mainWindow
							.showErrorMessage("Hubo un error leyendo los archivos .csv\nRevise los archivos de entrada. La ejecuci—n se suspende.");
					return false;
				} catch (CsvGenerationException e) {
					e.printStackTrace();
					logger.error(String.format("There was an error Generating the CVS:", e.getCause()));
					mainWindow
							.showErrorMessage("Hubo un error generando los archivos de salida\n. La ejecuci—n se suspende.");
					return false;
				} catch (MissingColumnException e) {
					mainWindow.showErrorMessage(e.getMessage());
					return false;
				}

				return true;
			}

			@Override
			protected void done() {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						logger.info("Finished running background task.");
						mainWindow.hideProgressBar();
					}
				});
			}

			@Override
			protected void process(List<Object> chunks) {
				logger.info("Update progress bar progress.");
				mainWindow.changeProgressBarLabel(endOfMonthControlService.getProgress());
			}
		};

		worker.execute();

		logger.info("Start running progress bar.");
		mainWindow.showProgressBar();

		return worker.get();

	}
}
