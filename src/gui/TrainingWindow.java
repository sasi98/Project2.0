package gui;

import graph.Graph;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import utilities.Matrix;
import utilities.WeightMatrix;
import architecture.NetworkManager;
import dataManager.ReadFile;
import dataManager.TrainingWindowOuts;

public class TrainingWindow {

	public static SwingWorker<Void, Void> worker;
	
	public static Graph errorGraph;
	private boolean isStarted = false;

	private Panel panel;
	private JPanel panelGraph;
	private JTextField tfcortaError, tflearningCNT, tfmaxIt;
	private JButton btnIniciarEntrenamiento, // Starts
																// training
			btnCancelarEntrenamiento, // Stops the training (break the process)
			btnPausarReanundarEntrenamiento; // Switch button: Restart or paused
												// training (
	private JComboBox comboBox;
	private JRadioButton rdbtnLineal, rdbtnTangencial, rdbtnSi, rdbtnNo, rdbtnAleatorias, rdbtnProcedentesDeArchivo;
	private JTextArea txtrUtilizarMatricesDe;
	private JLabel lblNewLabel_1;
	private JTextPane textPane;
	private JScrollPane scrollPane;

	private BigDecimal cotaError;
	private double learningCnt;
	private int iterationMax;
	private boolean momentB;
	private WeightMatrix matrices;
	private boolean selectMatrixFile; //Flag para ver si hemos cogido las matrices de fichero o no
	NetworkManager ne;
	private SwingWorker<Integer, Integer> sw;

	

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
				.setText("Matrices iniciales: ");

		txtrUtilizarMatricesDe.setBounds(new Rectangle(43, 222, 142, 28));

		panel.add(txtrUtilizarMatricesDe);

		btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");

		btnIniciarEntrenamiento.setBounds(new Rectangle(725, 419, 158, 27));

		panel.add(btnIniciarEntrenamiento);

		final JLabel lblSeleccionaRed = new JLabel("Selecciona Red: ");
		lblSeleccionaRed.setBounds(43, 36, 101, 16);
		panel.add(lblSeleccionaRed);

		final JLabel lblFuncion = new JLabel("Funci\u00F3n:  ");
		lblFuncion.setBounds(43, 67, 62, 16);
		panel.add(lblFuncion);

		final JLabel lblAadirMomento = new JLabel("A\u00F1adir Momento Beta: ");
		lblAadirMomento.setBounds(43, 98, 142, 16);
		panel.add(lblAadirMomento);

		final JLabel lblCotaDeError = new JLabel("Cota de error:");
		lblCotaDeError.setBounds(43, 158, 86, 16);
		panel.add(lblCotaDeError);

		final JLabel lblCoeficienteDeAprendizaje = new JLabel(
				"Coeficiente de aprendizaje:");
		lblCoeficienteDeAprendizaje.setBounds(43, 128, 170, 16);
		panel.add(lblCoeficienteDeAprendizaje);

		final JLabel lblMaxIt = new JLabel(
				"M\u00E1ximo n\u00FAmero de iteraciones:");
		lblMaxIt.setBounds(43, 188, 149, 16);
		panel.add(lblMaxIt);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		for (final NetworkManager ne : MainWindow.neList) { // A�adimos las
															// instancias
															// creadas al ComBox
			comboBox.addItem(ne.getName());
		}
		comboBox.setBounds(new Rectangle(223, 32, 129, 27));
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
		rdbtnLineal.setBounds(new Rectangle(223, 64, 69, 23));
		panel.add(rdbtnLineal);

		rdbtnTangencial = new JRadioButton("Tangencial");
		rdbtnTangencial.setBounds(new Rectangle(294, 64, 100, 23));
		panel.add(rdbtnTangencial);

		final ButtonGroup groupFuncion = new ButtonGroup(); // To get just one
															// selected at the
															// time
		groupFuncion.add(rdbtnTangencial);
		groupFuncion.add(rdbtnLineal);

		rdbtnSi = new JRadioButton("Si");
		rdbtnSi.setBounds(new Rectangle(223, 95, 43, 23));
		panel.add(rdbtnSi);

		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setSelected(true);
		momentB = false;
		rdbtnNo.setBounds(new Rectangle(295, 95, 50, 23));
		panel.add(rdbtnNo);

		final ButtonGroup groupSiNo = new ButtonGroup();
		groupSiNo.add(rdbtnSi);
		groupSiNo.add(rdbtnNo);
		
		rdbtnAleatorias = new JRadioButton("Aleatorias");
		rdbtnAleatorias.setBounds(223, 220, 80, 23);
		rdbtnAleatorias.setSelected(true);
		panel.add(rdbtnAleatorias);
		
		rdbtnProcedentesDeArchivo = new JRadioButton("Procedentes de archivo");
		rdbtnProcedentesDeArchivo.setBounds(310, 220, 149, 23);
		panel.add(rdbtnProcedentesDeArchivo);
		
		final ButtonGroup groupMatrices = new ButtonGroup();
		groupMatrices.add(rdbtnAleatorias);
		groupMatrices.add(rdbtnProcedentesDeArchivo);
		

		tfcortaError = new JTextField();
		tfcortaError.setColumns(10);
		tfcortaError.setBounds(new Rectangle(223, 156, 80, 20));
		panel.add(tfcortaError);

		tflearningCNT = new JTextField();
		tflearningCNT.setColumns(10);
		tflearningCNT.setBounds(new Rectangle(223, 126, 80, 20));
		panel.add(tflearningCNT);

		tfmaxIt = new JTextField();
		tfmaxIt.setColumns(10);
		tfmaxIt.setBounds(new Rectangle(223, 186, 80, 20));
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
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(294, 250, 165, 14);
		panel.add(lblNewLabel_1);
		
		
		
		
		//addNewGraph();
		

	}

	private void createEvents() {
		
		rdbtnProcedentesDeArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnProcedentesDeArchivoActionPerformed();
			}
		});
		
		btnIniciarEntrenamiento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				btnIniciarEntrenamientoActionPerformed();
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
	
	private void rdbtnProcedentesDeArchivoActionPerformed() {
		final JFileChooser filechooser = new JFileChooser("C:\\repositoryGit\\Salidas");
		final int returnValue = filechooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			final File filechoosen = filechooser.getSelectedFile();
			try {
				final ReadFile readWM = new ReadFile(filechoosen);
				WeightMatrix aux = readWM.readWeightMatrix();
				if (aux!= null){				//Hemos seleccionado matrices del fichero				
					selectMatrixFile = true;	//(no quiere decir q tengas las dimensiones apropiadas
					matrices = aux;
					lblNewLabel_1.setText(filechoosen.getName());
				}						
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Open command cancelled by user.");
		}
	}
	

	private void btnIniciarEntrenamientoActionPerformed() {

		MainWindow.cancelTraining = false;
		selectMatrixFile = false; 
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
		ne = aux2;
		
		
		if ( (!selectMatrixFile) && (rdbtnAleatorias.isSelected())) { //Las matrices no fueron seleccionadas de archivo, se generan de forma aletoria
			final Dimension dW = new Dimension(ne.getNumNeuronsO(),	ne.getNumNeuronsE());
			final Matrix W = Matrix.createRandomMatrix (NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW,
					NetworkManager.PRECISION);
			final Dimension dV = new Dimension(ne.getNumNeuronsS(),	ne.getNumNeuronsO());
			final Matrix V = Matrix.createRandomMatrix (NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dV,
					NetworkManager.PRECISION);
			matrices = new WeightMatrix(W, V);
			
		}

		//Creamos el hilo que llama al training
		sw = new SwingWorker<Integer, Integer>() {
			@Override
			protected Integer doInBackground() throws Exception {
			ne.training(iterationMax, cotaError, learningCnt, matrices, momentB) ;
				return null;
			}
//			@Override
//			protected void done() {
//				// Display results
//				String fileName = new String("C:\\repositoryGit\\Salidas\\resultsTraining.txt");
//					try {
//						String strToAdd = FileUtils.readFileToString(new File(fileName));
//						System.out.println(strToAdd);
//						textPane.setText(textPane.getText()+ strToAdd);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//				super.done();
//			}
			
		};
		
		sw.execute();
		
		if (sw.isCancelled()){
			System.out.print("ha terminado");			
			
		}

			//if (!isStarted) {
				
				//sw.execute();
				//isStarted = false;
			//}


		// Testing collecting data
		String outFile = new String(
				"C:\\repositoryGit\\Salidas\\previousInformationTraining.txt");
		TrainingWindowOuts resultados = new TrainingWindowOuts(outFile);
		resultados.previousInformation(ne.getName(), matrices);
		
		//Display results
//		outFile = new String(
//				"C:\\repositoryGit\\Salidas\\ChosenPatrons.csv");
//		WriteExcel patrones = new WriteExcel(outFile);
//		patrones.writeInputsOutputs(inputs, desiredOutputs);
//		patrones.closeFile();
//

		// Display results
		FileReader reader;
		try {
			reader = new FileReader(outFile);
			BufferedReader br = new BufferedReader(reader);
			textPane.read(br, null);
			br.close();
			//br.toString()
			
		//	textPane.getText();
			
			//textPane.requestFocus();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void btnCancelarEntrenamientoActionPerformed() {
		
		MainWindow.cancelTraining = true;
		System.out.print("Estado: "+ sw.getState());
//		// Display results
		sw.cancel(true); //Cancelamos el hilo
		
		String fileName = new String("C:\\repositoryGit\\Salidas\\resultsTraining.txt");
		try {
			String strToAdd = FileUtils.readFileToString(new File(fileName));
			System.out.println(strToAdd);
			textPane.setText(textPane.getText()+ strToAdd);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void btnPausarReanundarEntrenamientoActionPerformed(ItemEvent ev) {
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			btnPausarReanundarEntrenamiento.setText("Reanudar entrenamiento");
			//We pause the training
			MainWindow.cancelTraining = true;
			sw.cancel(true);

		} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
			btnPausarReanundarEntrenamiento.setText("Pausar entrenamiento");
			sw.execute();

		}

	}

	public Panel getPanel() {
		return panel;

	}

	// reset graph instance and add to the frame
	private void addNewGraph() {
		panel.getComponents();
		errorGraph = new Graph();
		panelGraph = errorGraph.createPanel();

		panelGraph.setBounds(550, 133, 399, 229);
		panelGraph.setVisible(true);
		panel.add(panelGraph);
		//panel.repaint();

	}

	public void setPanel(final Panel panel) {
		this.panel = panel;
	}
}
