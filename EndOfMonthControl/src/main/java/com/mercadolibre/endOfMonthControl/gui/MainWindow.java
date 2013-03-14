package com.mercadolibre.endOfMonthControl.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.inject.Inject;

public class MainWindow {

	private JFrame mainFrame;
	private JTextField inputSapTxtField;
	private JTextField inputFocusTxtField;
	private JTextField inputTargetTxtField;
	private JTextField inputTargetNameTxtField;

	private static final String MAIN_FRAME_TITLE = "CONTROL MASTER";
	private static final String TITLE_LABEL = "CONTROL MASTER 2.0";
	private static final String INPUT_SAP_LABEL = "Seleccione el archivo de SAP:";
	private static final String SELECT_BUTTON_TEXT = "Seleccionar";
	private static final String INPUT_FOCUS_LABEL = "Seleccione el archivo de Focus:";
	private static final String INPUT_TARGET_LABEL = "Seleccione la carpeta destino:";
	private static final String PROCESS_BUTTON_TEXT = "Procesar";
	private static final String FOOTER_LABEL_TEXT = "Made By JD inc.";
	private static final String INPUT_TARGET_NAME_LABEL = "Ingrese el nombre del archivo destino:";
	private static final String TOOLTIP_TARGET_NAME = "Ingrese el prefijo de los nombres de los archivos de salida";

	@Inject
	private MainWindowController controller;
	private JProgressBar progressBar;
	private JDialog progressBarDialog;
	private JLabel progressBarLabel;

	public MainWindowController getController() {
		return controller;
	}

	public void setController(MainWindowController controller) {
		this.controller = controller;
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		mainFrame.setLocationRelativeTo(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		setMainFrame(new JFrame());
		getMainFrame().setResizable(false);
		getMainFrame().setBounds(100, 100, 600, 297);
		getMainFrame().setTitle(MAIN_FRAME_TITLE);
		getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel titlePanel = new JPanel();
		getMainFrame().getContentPane().add(titlePanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel(TITLE_LABEL);
		titlePanel.add(titleLabel);

		JPanel mainPanel = new JPanel();
		FlowLayout fl_mainPanel = (FlowLayout) mainPanel.getLayout();
		fl_mainPanel.setAlignment(FlowLayout.LEFT);
		getMainFrame().getContentPane().add(mainPanel, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		mainPanel.add(panel);

		final JLabel inputSapLabel = new JLabel(INPUT_SAP_LABEL);
		panel.add(inputSapLabel);
		inputSapLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JButton selectSapButton = new JButton(SELECT_BUTTON_TEXT);
		selectSapButton.addMouseListener(new SelectSapMouseAdapter());

		inputSapTxtField = new JTextField();
		inputSapTxtField.setEditable(false);
		panel.add(inputSapTxtField);
		inputSapTxtField.setColumns(21);
		panel.add(selectSapButton);

		JPanel focusPanel = new JPanel();
		mainPanel.add(focusPanel);

		final JLabel inputFocusLabel = new JLabel(INPUT_FOCUS_LABEL);
		focusPanel.add(inputFocusLabel);

		inputFocusTxtField = new JTextField();
		inputFocusTxtField.setEditable(false);
		inputFocusTxtField.setColumns(20);
		focusPanel.add(inputFocusTxtField);

		JButton selectFocusButton = new JButton(SELECT_BUTTON_TEXT);
		selectFocusButton.addMouseListener(new SelectFocusMouseAdapter());
		focusPanel.add(selectFocusButton);

		JPanel targetPanel = new JPanel();
		mainPanel.add(targetPanel);

		final JLabel inputTargetLabel = new JLabel(INPUT_TARGET_LABEL);
		targetPanel.add(inputTargetLabel);

		inputTargetTxtField = new JTextField();
		inputTargetTxtField.setEditable(false);
		inputTargetTxtField.setColumns(21);
		targetPanel.add(inputTargetTxtField);

		JButton targetButton = new JButton(SELECT_BUTTON_TEXT);
		targetButton.addMouseListener(new SelectTargetMouseAdapter());
		targetPanel.add(targetButton);

		JPanel targetNamePanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) targetNamePanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		mainPanel.add(targetNamePanel);

		JLabel inputTargetNameLabel = new JLabel(INPUT_TARGET_NAME_LABEL);
		targetNamePanel.add(inputTargetNameLabel);

		inputTargetNameTxtField = new JTextField();
		inputTargetNameTxtField.setToolTipText(TOOLTIP_TARGET_NAME);
		inputTargetNameTxtField.setColumns(17);
		targetNamePanel.add(inputTargetNameTxtField);

		JPanel processPanel = new JPanel();
		mainPanel.add(processPanel);

		JButton processButton = new JButton(PROCESS_BUTTON_TEXT);
		processButton.addMouseListener(new ProcessButtonMouseAdapter());
		processPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 245, 5));
		processButton.setHorizontalAlignment(SwingConstants.RIGHT);
		processPanel.add(processButton);

		JPanel footerPanel = new JPanel();
		getMainFrame().getContentPane().add(footerPanel, BorderLayout.SOUTH);

		JLabel footerLabel = new JLabel(FOOTER_LABEL_TEXT);
		footerPanel.add(footerLabel);
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void showErrorMessage(String message) {
		final String ERROR_TITLE_DIALOG = "Error";
		JOptionPane.showMessageDialog(getMainFrame(), message, ERROR_TITLE_DIALOG, JOptionPane.ERROR_MESSAGE);
	}

	public void showOKMessage(String message) {
		final String OK_TITLE_DIALOG = "Proceso finalizado";
		JOptionPane.showMessageDialog(getMainFrame(), message, OK_TITLE_DIALOG, JOptionPane.INFORMATION_MESSAGE);
	}

	private class ProcessButtonMouseAdapter implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			controller.processInputFromUi(inputSapTxtField.getText(), inputFocusTxtField.getText(),
					inputTargetTxtField.getText(), inputTargetNameTxtField.getText());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}

	private class SelectSapMouseAdapter implements MouseListener {

		private static final String FILE_CHOOSER_SAP_TITLE = "Seleccione el archivo de SAP";

		@Override
		public void mouseClicked(MouseEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle(FILE_CHOOSER_SAP_TITLE);
			int returnVal = fileChooser.showOpenDialog(e.getComponent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				inputSapTxtField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}

	private class SelectFocusMouseAdapter implements MouseListener {

		private static final String FILE_CHOOSER_FOCUS_TITLE = "Seleccione el archivo de Focus";

		@Override
		public void mouseClicked(MouseEvent arg0) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle(FILE_CHOOSER_FOCUS_TITLE);
			int returnVal = fileChooser.showOpenDialog(arg0.getComponent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				inputFocusTxtField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

	}

	private class SelectTargetMouseAdapter implements MouseListener {

		private static final String FILE_CHOOSER_TARGET_TITLE = "Seleccione la carpeta destino";

		@Override
		public void mouseClicked(MouseEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle(FILE_CHOOSER_TARGET_TITLE);
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			int returnVal = fileChooser.showOpenDialog(e.getComponent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				inputTargetTxtField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

	}

	public void showProgressBar() {

		final int width = 200;
		final int height = 40;
		this.progressBar = new JProgressBar();
		this.progressBar.setIndeterminate(true);
		this.progressBar.setPreferredSize(new Dimension(width, height));

		final JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.setLayout(new BorderLayout());
		this.progressBarLabel = new JLabel("Generando archivos de salida...");
		content.add(progressBarLabel, BorderLayout.NORTH);
		content.add(progressBar, BorderLayout.CENTER);

		progressBarDialog = new JDialog(this.mainFrame, true);
		progressBarDialog.add(content, BorderLayout.CENTER);
		progressBarDialog.pack();
		progressBarDialog.setLocationRelativeTo(null);

		progressBarDialog.setVisible(true);
		progressBarDialog.dispose();
	}

	public void hideProgressBar() {
		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
	}

	public void changeProgressBarLabel(String message) {
		this.progressBarLabel.setText(message);
	}

	public void clearFields() {
		this.inputFocusTxtField.setText("");
		this.inputSapTxtField.setText("");
		this.inputTargetNameTxtField.setText("");
		this.inputTargetTxtField.setText("");
	}

}
