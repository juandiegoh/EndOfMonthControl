package com.mercadolibre.endOfMonthControl.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainWindow {

	private JFrame mainFrame;
	private JTextField inputSapTxtField;
	private JTextField inputFocusTxtField;
	private JTextField inputTargetTxtField;
	private JTextField inputTargetNameTxtField;

	private static final String MAIN_FRAME_TITLE = "CONTROL MASTER";
	private static final String TITLE_LABEL = "Herramienta de cruce de archivos entre SAP y Focus (Payments)";
	private static final String INPUT_SAP_LABEL = "Seleccione el archivo de SAP:";
	private static final String SELECT_BUTTON_TEXT = "Seleccionar";
	private static final String INPUT_FOCUS_LABEL = "Seleccione el archivo de Focus:";
	private static final String INPUT_TARGET_LABEL = "Seleccione la carpeta destino:";
	private static final String PROCESS_BUTTON_TEXT = "Procesar";
	private static final String FOOTER_LABEL_TEXT = "Ayuda";
	private static final String INPUT_TARGET_NAME_LABEL = "Ingrese el nombre destino:";
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
		getMainFrame().setBounds(100, 100, 667, 217);
		getMainFrame().setTitle(MAIN_FRAME_TITLE);
		getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel titlePanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) titlePanel.getLayout();
		flowLayout_1.setVgap(10);
		getMainFrame().getContentPane().add(titlePanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel(TITLE_LABEL);
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		titlePanel.add(titleLabel);

		JPanel mainPanel = new JPanel();
		getMainFrame().getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] {220, 320, 100, 0};
		gbl_mainPanel.rowHeights = new int[]{35, 35, 35, 35, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
						
								final JLabel inputSapLabel = new JLabel(INPUT_SAP_LABEL);
								GridBagConstraints gbc_inputSapLabel = new GridBagConstraints();
								gbc_inputSapLabel.fill = GridBagConstraints.BOTH;
								gbc_inputSapLabel.insets = new Insets(0, 0, 5, 5);
								gbc_inputSapLabel.gridx = 0;
								gbc_inputSapLabel.gridy = 0;
								mainPanel.add(inputSapLabel, gbc_inputSapLabel);
								inputSapLabel.setHorizontalAlignment(SwingConstants.CENTER);
				
						inputSapTxtField = new JTextField();
						GridBagConstraints gbc_inputSapTxtField = new GridBagConstraints();
						gbc_inputSapTxtField.fill = GridBagConstraints.BOTH;
						gbc_inputSapTxtField.insets = new Insets(0, 0, 5, 5);
						gbc_inputSapTxtField.gridx = 1;
						gbc_inputSapTxtField.gridy = 0;
						mainPanel.add(inputSapTxtField, gbc_inputSapTxtField);
						inputSapTxtField.setEditable(false);
						inputSapTxtField.setColumns(21);
		
				JButton selectSapButton = new JButton(SELECT_BUTTON_TEXT);
				GridBagConstraints gbc_selectSapButton = new GridBagConstraints();
				gbc_selectSapButton.fill = GridBagConstraints.BOTH;
				gbc_selectSapButton.insets = new Insets(0, 0, 5, 0);
				gbc_selectSapButton.gridx = 2;
				gbc_selectSapButton.gridy = 0;
				mainPanel.add(selectSapButton, gbc_selectSapButton);
				selectSapButton.addMouseListener(new SelectSapMouseAdapter());
						
								final JLabel inputFocusLabel = new JLabel(INPUT_FOCUS_LABEL);
								inputFocusLabel.setHorizontalAlignment(SwingConstants.CENTER);
								GridBagConstraints gbc_inputFocusLabel = new GridBagConstraints();
								gbc_inputFocusLabel.fill = GridBagConstraints.BOTH;
								gbc_inputFocusLabel.insets = new Insets(0, 0, 5, 5);
								gbc_inputFocusLabel.gridx = 0;
								gbc_inputFocusLabel.gridy = 1;
								mainPanel.add(inputFocusLabel, gbc_inputFocusLabel);
				
						inputFocusTxtField = new JTextField();
						GridBagConstraints gbc_inputFocusTxtField = new GridBagConstraints();
						gbc_inputFocusTxtField.fill = GridBagConstraints.BOTH;
						gbc_inputFocusTxtField.insets = new Insets(0, 0, 5, 5);
						gbc_inputFocusTxtField.gridx = 1;
						gbc_inputFocusTxtField.gridy = 1;
						mainPanel.add(inputFocusTxtField, gbc_inputFocusTxtField);
						inputFocusTxtField.setEditable(false);
						inputFocusTxtField.setColumns(20);
						
								JButton selectFocusButton = new JButton(SELECT_BUTTON_TEXT);
								GridBagConstraints gbc_selectFocusButton = new GridBagConstraints();
								gbc_selectFocusButton.fill = GridBagConstraints.BOTH;
								gbc_selectFocusButton.insets = new Insets(0, 0, 5, 0);
								gbc_selectFocusButton.gridx = 2;
								gbc_selectFocusButton.gridy = 1;
								mainPanel.add(selectFocusButton, gbc_selectFocusButton);
								selectFocusButton.addMouseListener(new SelectFocusMouseAdapter());
						
								final JLabel inputTargetLabel = new JLabel(INPUT_TARGET_LABEL);
								inputTargetLabel.setHorizontalAlignment(SwingConstants.CENTER);
								GridBagConstraints gbc_inputTargetLabel = new GridBagConstraints();
								gbc_inputTargetLabel.fill = GridBagConstraints.BOTH;
								gbc_inputTargetLabel.insets = new Insets(0, 0, 5, 5);
								gbc_inputTargetLabel.gridx = 0;
								gbc_inputTargetLabel.gridy = 2;
								mainPanel.add(inputTargetLabel, gbc_inputTargetLabel);
				
						inputTargetTxtField = new JTextField();
						GridBagConstraints gbc_inputTargetTxtField = new GridBagConstraints();
						gbc_inputTargetTxtField.fill = GridBagConstraints.BOTH;
						gbc_inputTargetTxtField.insets = new Insets(0, 0, 5, 5);
						gbc_inputTargetTxtField.gridx = 1;
						gbc_inputTargetTxtField.gridy = 2;
						mainPanel.add(inputTargetTxtField, gbc_inputTargetTxtField);
						inputTargetTxtField.setEditable(false);
						inputTargetTxtField.setColumns(21);
		
				JButton targetButton = new JButton(SELECT_BUTTON_TEXT);
				GridBagConstraints gbc_targetButton = new GridBagConstraints();
				gbc_targetButton.fill = GridBagConstraints.BOTH;
				gbc_targetButton.insets = new Insets(0, 0, 5, 0);
				gbc_targetButton.gridx = 2;
				gbc_targetButton.gridy = 2;
				mainPanel.add(targetButton, gbc_targetButton);
				targetButton.addMouseListener(new SelectTargetMouseAdapter());
		
				JLabel inputTargetNameLabel = new JLabel(INPUT_TARGET_NAME_LABEL);
				inputTargetNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_inputTargetNameLabel = new GridBagConstraints();
				gbc_inputTargetNameLabel.fill = GridBagConstraints.BOTH;
				gbc_inputTargetNameLabel.insets = new Insets(0, 0, 0, 5);
				gbc_inputTargetNameLabel.gridx = 0;
				gbc_inputTargetNameLabel.gridy = 3;
				mainPanel.add(inputTargetNameLabel, gbc_inputTargetNameLabel);
				
						inputTargetNameTxtField = new JTextField();
						GridBagConstraints gbc_inputTargetNameTxtField = new GridBagConstraints();
						gbc_inputTargetNameTxtField.fill = GridBagConstraints.BOTH;
						gbc_inputTargetNameTxtField.insets = new Insets(0, 0, 0, 5);
						gbc_inputTargetNameTxtField.gridx = 1;
						gbc_inputTargetNameTxtField.gridy = 3;
						mainPanel.add(inputTargetNameTxtField, gbc_inputTargetNameTxtField);
						inputTargetNameTxtField.setToolTipText(TOOLTIP_TARGET_NAME);
						inputTargetNameTxtField.setColumns(17);
		
				JButton processButton = new JButton(PROCESS_BUTTON_TEXT);
				GridBagConstraints gbc_processButton = new GridBagConstraints();
				gbc_processButton.fill = GridBagConstraints.BOTH;
				gbc_processButton.gridx = 2;
				gbc_processButton.gridy = 3;
				mainPanel.add(processButton, gbc_processButton);
				processButton.addMouseListener(new ProcessButtonMouseAdapter());

		JPanel footerPanel = new JPanel();
		//getMainFrame().getContentPane().add(footerPanel, BorderLayout.SOUTH);

		JLabel footerLabel = new JLabel(FOOTER_LABEL_TEXT);
		footerLabel.setForeground(Color.BLUE);
		footerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		footerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JDialog dialog = new JDialog(); 
				dialog.setTitle("Ayuda");
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		footerPanel.add(footerLabel);
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		BorderLayout borderLayout = (BorderLayout) mainFrame.getContentPane().getLayout();
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
		progressBarDialog.getContentPane().add(content, BorderLayout.CENTER);
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
