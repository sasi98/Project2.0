package gui;

import graph.Graph;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import utilities.Matrix;
import utilities.WeightMatrix;
import dataManager.ReadFile;
import architecture.Network;
import architecture.NetworkManager;

import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JScrollPane;

public class TrainingWindow {

	public static SwingWorker<Void, Void> worker;
	public static Graph errorGraph;
	private boolean isStarted = false;

	private Panel panel;
	private JPanel panelGraph;
	private JTextField tfcortaError, tflearningCNT, tfmaxIt;
	private JButton btnSelecMatrices, btnIniciarEntrenamiento, // Starts
																// training
			btnCancelarEntrenamiento, // Stops the training (break the process)
			btnPausarReanundarEntrenamiento; // Switch button: Restart or paused
												// training (
	private JComboBox comboBox;
	private JRadioButton rdbtnLineal, rdbtnTangencial, rdbtnSi, rdbtnNo;
	private JTextArea txtrUtilizarMatricesDe;
	private JLabel lblNewLabel;
	private JTextPane textPane;

	private BigDecimal cotaError;
	private double learningCnt;
	private int iterationMax;
	private boolean momentB;
	private WeightMatrix matrices;
	private JScrollPane scrollPane;

	/**
	 * Create the application.
	 */
	public TrainingWindow() {
		initialize();
		createEvents();
	}

	public void initialize() {

		panel = new Panel();
		panel.setBounds(6, 0, 980, 633);
		panel.setLayout(null);

		txtrUtilizarMatricesDe = new JTextArea();
		txtrUtilizarMatricesDe.setLineWrap(true);
		txtrUtilizarMatricesDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrUtilizarMatricesDe.setOpaque(false);
		txtrUtilizarMatricesDe
				.setText("Utilizar matrices de pesos procedentes deentrenamientos anteriores:");
		txtrUtilizarMatricesDe.setBounds(new Rectangle(43, 334, 203, 28));

		panel.add(txtrUtilizarMatricesDe);

		btnSelecMatrices = new JButton("Seleccionar archivo");
		btnSelecMatrices.setBounds(new Rectangle(283, 352, 165, 29));
		panel.add(btnSelecMatrices);

		btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");

		btnIniciarEntrenamiento.setBounds(new Rectangle(725, 419, 158, 27));
		panel.add(btnIniciarEntrenamiento);

		final JLabel lblSeleccionaRed = new JLabel("Selecciona Red: ");
		lblSeleccionaRed.setBounds(50, 82, 101, 16);
		panel.add(lblSeleccionaRed);

		final JLabel lblFuncion = new JLabel("Funci\u00F3n:  ");
		lblFuncion.setBounds(50, 122, 62, 16);
		panel.add(lblFuncion);

		final JLabel lblAadirMomento = new JLabel("A\u00F1adir Momento Beta: ");
		lblAadirMomento.setBounds(50, 169, 142, 16);
		panel.add(lblAadirMomento);

		final JLabel lblCotaDeError = new JLabel("Cota de error:");
		lblCotaDeError.setBounds(50, 255, 86, 16);
		panel.add(lblCotaDeError);

		final JLabel lblCoeficienteDeAprendizaje = new JLabel(
				"Coeficiente de aprendizaje:");
		lblCoeficienteDeAprendizaje.setBounds(50, 212, 170, 16);
		panel.add(lblCoeficienteDeAprendizaje);

		final JLabel lblMaxIt = new JLabel(
				"M\u00E1ximo n\u00FAmero de iteraciones:");
		lblMaxIt.setBounds(50, 295, 149, 16);
		panel.add(lblMaxIt);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(440, 53, 0, 0);
		panel.add(lblNewLabel);
		
		

		comboBox = new JComboBox();

		for (final NetworkManager ne : MainWindow.neList) { // A�adimos las
															// instancias
															// creadas al ComBox
			comboBox.addItem(ne.getName());
		}
		comboBox.setBounds(new Rectangle(230, 77, 129, 27));
		panel.add(comboBox);

		// initilaize diagram when combobox loading
		// String selectedItem = (String) comboBox.getSelectedItem();
		// if (selectedItem != null) {
		// Graph diagram = MainWindow.getGraph(selectedItem);
		// JPanel panel = diagram.createPanel();
		// panel.setBounds(45, 288, 620, 300);
		// panel.setVisible(true);
		// frame.getContentPane().add(panel);
		// }

		rdbtnLineal = new JRadioButton("Lineal");
		rdbtnLineal.setSelected(true);
		rdbtnLineal.setBounds(new Rectangle(230, 119, 69, 23));
		panel.add(rdbtnLineal);

		rdbtnTangencial = new JRadioButton("Tangencial");
		rdbtnTangencial.setBounds(new Rectangle(301, 119, 100, 23));
		panel.add(rdbtnTangencial);

		final ButtonGroup groupFuncion = new ButtonGroup(); // To get just one
															// selected at the
															// time
		groupFuncion.add(rdbtnTangencial);
		groupFuncion.add(rdbtnLineal);

		rdbtnSi = new JRadioButton("Si");
		rdbtnSi.setBounds(new Rectangle(230, 166, 43, 23));
		panel.add(rdbtnSi);

		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setSelected(true);
		momentB = false;
		rdbtnNo.setBounds(new Rectangle(302, 166, 50, 23));
		panel.add(rdbtnNo);

		final ButtonGroup groupSiNo = new ButtonGroup();
		groupSiNo.add(rdbtnSi);
		groupSiNo.add(rdbtnNo);

		tfcortaError = new JTextField();
		tfcortaError.setColumns(10);
		tfcortaError.setBounds(new Rectangle(230, 253, 80, 20));
		panel.add(tfcortaError);

		tflearningCNT = new JTextField();
		tflearningCNT.setColumns(10);
		tflearningCNT.setBounds(new Rectangle(230, 210, 80, 20));
		panel.add(tflearningCNT);

		tfmaxIt = new JTextField();
		tfmaxIt.setColumns(10);
		tfmaxIt.setBounds(new Rectangle(230, 293, 80, 20));
		panel.add(tfmaxIt);

		btnCancelarEntrenamiento = new JButton("Cancelar entrenamiento");
		btnCancelarEntrenamiento.setBounds(725, 541, 158, 27);
		panel.add(btnCancelarEntrenamiento);

		btnPausarReanundarEntrenamiento = new JButton("Pausar entrenamiento");
		btnPausarReanundarEntrenamiento.setBounds(725, 477, 158, 27);
		panel.add(btnPausarReanundarEntrenamiento);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(43, 392, 470, 216);
		panel.add(scrollPane);

		addNewGraph();

	}

	private void createEvents() {
		btnIniciarEntrenamiento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				btnIniciarEntrenamientoActionPerformed();
			}
		});

		btnSelecMatrices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				btnNewButtonActionPerformed();
			}
		});

		btnCancelarEntrenamiento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				btnCancelarEntrenamientoActionPerformed();
			}
		});

		btnPausarReanundarEntrenamiento.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				btnPausarReanundarEntrenamientoActionPerformed(ev);
			}

		});

	}

	private void btnIniciarEntrenamientoActionPerformed() {

		addNewGraph();

		final String stCotaError = tfcortaError.getText();
		if ((stCotaError == null) || (stCotaError.equals(""))) {
			tfcortaError.setText("0.001");
			cotaError = new BigDecimal(0.001);
			cotaError.setScale(NetworkManager.PRECISION, RoundingMode.HALF_UP);

		} else {
			cotaError = new BigDecimal(stCotaError);
			cotaError.setScale(NetworkManager.PRECISION);
		}

		final String stCnsLearning = tflearningCNT.getText();
		if ((stCnsLearning == null) || (stCnsLearning.equals(""))) {
			tflearningCNT.setText("0.00001");
			learningCnt = 0.00001;

		} else {
			learningCnt = Double.parseDouble(stCnsLearning);
		}

		final String stItMax = tfmaxIt.getText();
		if ((stItMax == null) || (stItMax.equals(""))) {
			tfmaxIt.setText("1000");
			iterationMax = 1000;

		} else {
			iterationMax = Integer.parseInt(stItMax);
		}
		if (rdbtnSi.isSelected()) { // Entrenamiento con momento Beta a�adido en
									// la actualizaci�n de matriz
			momentB = true;
		}

		NetworkManager aux2 = null;
		for (final NetworkManager aux : MainWindow.neList) {
			if (aux.getName().equals(comboBox.getSelectedItem())) {
				aux2 = aux; // Controlar que se halla elegido alguna, de lo
							// contrario tendremos null exceptions
			}
		}
		final NetworkManager ne = aux2;

		if (matrices == null) {
			// No fueron seleccionadas de archivo, deben de ser creadas
			// aletorias
			// Las creo y se las paso al trainnig junto con el resto de
			// par�metros
			final Dimension dW = new Dimension(ne.getNumNeuronsO(),
					ne.getNumNeuronsE());
			final Matrix W = Matrix.createRandomMatrix(
					NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW,
					NetworkManager.PRECISION);
			final Dimension dV = new Dimension(ne.getNumNeuronsS(),
					ne.getNumNeuronsO());
			final Matrix V = Matrix.createRandomMatrix(
					NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dV,
					NetworkManager.PRECISION);
			matrices = new WeightMatrix(W, V);

			worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {

					ne.training(iterationMax, cotaError, learningCnt, matrices,
							momentB);
					return null;
				}
			};

			if (!isStarted) {
				worker.execute();
				isStarted = false;
			}

			// worker.execute();

		} else {
			// se las paso al trainnig directamente junto con el resto de
			// par�metros
			final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					ne.training(iterationMax, cotaError, learningCnt, matrices,
							momentB);
					return null;
				}
			};

			worker.execute();

		}

	}

	private void btnNewButtonActionPerformed() {
		final JFileChooser filechooser = new JFileChooser(
				"C:\\repositoryGit\\Salidas");
		final int returnValue = filechooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			final File filechoosen = filechooser.getSelectedFile();
			try {
				final ReadFile readWM = new ReadFile(filechoosen);
				matrices = readWM.readWeightMatrix();
				lblNewLabel.setText(filechoosen.getName());

			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Open command cancelled by user.");
		}

	}

	private void btnCancelarEntrenamientoActionPerformed() {
		MainWindow.cancelTraining = true;
	}

	private void btnPausarReanundarEntrenamientoActionPerformed(ItemEvent ev) {
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			btnPausarReanundarEntrenamiento.setText("Reanudar entrenamiento");
			// we restart training where we left it

		} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
			btnPausarReanundarEntrenamiento.setText("Pausar entrenamiento");
			// we pause the training

		}

	}

	public Panel getPanel() {
		return panel;

	}

	// reset graph instance and add to the frame
	private void addNewGraph() {
		errorGraph = new Graph();
		panelGraph = errorGraph.createPanel();
		panelGraph.setBounds (550, 133, 399, 229);
		panelGraph.setVisible(true);
		panel.add(panelGraph);

	}

	public void setPanel(final Panel panel) {
		this.panel = panel;
	}
}
