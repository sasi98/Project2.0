package gui;

import graph.Graph;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

import utilities.Matrix;
import utilities.WeightMatrix;
import valueset.Value;
import architecture.NetworkManager;
import dataManager.ReadFile;
import dataManager.TrainingWindowOuts;

import javax.swing.JCheckBox;

public class TrainingWindow {

	public static SwingWorker<Void, Void> worker;

	public static Graph errorGraph;
	private final boolean isStarted = false;

	private Panel panel;
	private JPanel panelGraph;
	private JTextField tfcortaError, tflearningCNT, tfmaxIt;
	private JButton btnIniciarEntrenamiento, // Starts
												// training
			btnCancelarEntrenamiento; // Stops the training (break the process);
										// // Switch button: Restart or paused
										// training (
	private JComboBox comboBox;
	private JRadioButton rdbtnLineal, rdbtnTangencial, rdbtnNo,
			rdbtnAleatorias, rdbtnProcedentesDeArchivo,  rdbtnFijo, rdbtnVariable;
	private JTextArea txtrUtilizarMatricesDe;
	private JLabel lblNewLabel_1, lbMsnlearningCuoteMax;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JCheckBox cbAcotadoLearning;

	private BigDecimal cotaError;
	private double learningCnt, learningCNTCuote;
	private int iterationMax;
	private boolean momentB, acotadoMax, learningCNTVariable;
	private double momentBValue;
	private WeightMatrix matrices;
	private boolean selectMatrixFile; // Flag para ver si hemos cogido las
										// matrices de fichero o no
	private String pathMatrices; //Ruta del archivo de donde obtenemos las matrices iniciales, en el caso
	private Value.Funtion funtion;
	private String funtionStr;
	private File directory; 
	private String directoryName; 
	
	NetworkManager ne;
	private SwingWorker<Integer, Integer> sw;
	private JTextField tfmomentoB;
	private static Logger log = Logger.getLogger(TrainingWindow.class);

	/**
	 * Create the application.
	 */
	public TrainingWindow() {
		initialize();
		createEvents();
	}

	public void initialize() {
		pathMatrices = "";

		panel = new Panel();
		panel.setBounds(6, 0, 980, 633);
		panel.setLayout(null);

		txtrUtilizarMatricesDe = new JTextArea();
		txtrUtilizarMatricesDe.setLineWrap(true);
		txtrUtilizarMatricesDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrUtilizarMatricesDe.setOpaque(false);
		txtrUtilizarMatricesDe.setText("Matrices iniciales: ");

		txtrUtilizarMatricesDe.setBounds(new Rectangle(43, 222, 142, 28));

		panel.add(txtrUtilizarMatricesDe);

		btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");

		btnIniciarEntrenamiento.setBounds(new Rectangle(679, 401, 158, 27));

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
		

		for (final NetworkManager ne : MainWindow.neList) { // Aï¿½ï¿½ï¿½adimos las
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

//		rdbtnNo = new JRadioButton("No");
//		rdbtnNo.setSelected(true);
//		momentB = false;
//		rdbtnNo.setBounds(new Rectangle(295, 95, 50, 23));
//		panel.add(rdbtnNo);
//
//		final ButtonGroup groupSiNo = new ButtonGroup();
//		groupSiNo.add(rdbtnSi);
//		groupSiNo.add(rdbtnNo);

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
		tflearningCNT.setBounds(new Rectangle(411, 126, 80, 20));
		panel.add(tflearningCNT);

		tfmaxIt = new JTextField();
		tfmaxIt.setColumns(10);
		tfmaxIt.setBounds(new Rectangle(223, 186, 80, 20));
		panel.add(tfmaxIt);
		
		tfmomentoB = new JTextField();
		tfmomentoB.setBounds(223, 96, 80, 20);
		panel.add(tfmomentoB);
		tfmomentoB.setColumns(10);

		btnCancelarEntrenamiento = new JButton("Cancelar entrenamiento");
		btnCancelarEntrenamiento.setBounds(679, 440, 158, 27);
		panel.add(btnCancelarEntrenamiento);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 251, 470, 216);
		panel.add(scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(294, 250, 165, 14);
		panel.add(lblNewLabel_1);
		
		rdbtnFijo = new JRadioButton("Fijo");
		rdbtnFijo.setBounds(219, 125, 48, 23);
		rdbtnFijo.setSelected(true);
		panel.add(rdbtnFijo);
		
		rdbtnVariable = new JRadioButton("Variable");
		rdbtnVariable.setBounds(283, 125, 69, 23);
		panel.add(rdbtnVariable);
		
		ButtonGroup groupLearningCNT = new ButtonGroup();
		groupLearningCNT.add(rdbtnFijo);
		groupLearningCNT.add(rdbtnVariable);
		
		JLabel lblValor = new JLabel("Valor : ");
		lblValor.setBounds(374, 129, 46, 14);
		panel.add(lblValor);
		
		cbAcotadoLearning = new JCheckBox("Acotado superior");
		cbAcotadoLearning.setBounds(374, 155, 115, 23);
		panel.add(cbAcotadoLearning);
		
		lbMsnlearningCuoteMax = new JLabel("");
		lbMsnlearningCuoteMax.setBounds(374, 189, 136, 14);
		panel.add(lbMsnlearningCuoteMax);

		if ( (cbAcotadoLearning.isSelected()) && (ne != null) ){
			double max = Double.parseDouble(lbMsnlearningCuoteMax.getText());
			double currentValue = Double.parseDouble(tflearningCNT.getText()); 
				if (currentValue> max){
					JOptionPane.showMessageDialog(
							null,
							"El coeficiente de aprendizaje es mayor que el máximo recomendado en "
							+ "relación a los datos. Modifica este parámetro",
							"Coeficiente de aprendizaje", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		
		
		addNewGraph();

	}

	private void createEvents() {

		rdbtnProcedentesDeArchivo.addActionListener(new ActionListener() {
			@Override
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
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed ();
			}
		});
		
		cbAcotadoLearning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cbAcotadoLearningActionPerformed();
			}
		});

		tflearningCNT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tflearningCNTActionPerformed();
			}
		});
		
	}

	private void rdbtnProcedentesDeArchivoActionPerformed() {
		final JFileChooser filechooser = new JFileChooser(
				"C:\\repositoryGit\\Salidas");
		final int returnValue = filechooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			final File filechoosen = filechooser.getSelectedFile();
			try {
				final ReadFile readWM = new ReadFile(filechoosen);
				WeightMatrix aux = readWM.readWeightMatrix();
				if (aux != null) { // Hemos seleccionado matrices del fichero
					selectMatrixFile = true; // (no quiere decir q tengas las
												// dimensiones apropiadas
					matrices = aux;
					lblNewLabel_1.setText(filechoosen.getName());
					pathMatrices = filechoosen.getName();
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

		boolean start = true; 
		MainWindow.cancelTraining = false;
		selectMatrixFile = false;
		deleteGraph();
		addNewGraph();
		if ( (cbAcotadoLearning.isSelected()) && (ne != null) ){
			double max = Double.parseDouble(lbMsnlearningCuoteMax.getText());
			double currentValue = Double.parseDouble(tflearningCNT.getText()); 
				if (currentValue> max){
					JOptionPane.showMessageDialog(
							null,
							"El coeficiente de aprendizaje es mayor que el máximo recomendado en "
							+ "relación a los datos. Modifica este parámetro",
							"Coeficiente de aprendizaje", JOptionPane.WARNING_MESSAGE);
					start = false; 
				}
				
			}
		
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

		if (cbAcotadoLearning.isSelected()){
			acotadoMax = true;
		}
		
		
		final String stItMax = tfmaxIt.getText();
		if ((stItMax == null) || (stItMax.equals(""))) {
			tfmaxIt.setText("1000");
			iterationMax = 1000;

		} else {
			iterationMax = Integer.parseInt(stItMax);
		}
		
		final String stMomentoB = tfmomentoB.getText();
		if ((stMomentoB == null) || (stMomentoB.equals(""))) {
			momentB = false; //No utilizamos momento

		} else {
			momentBValue = Double.parseDouble(stMomentoB);
			momentB = true;
		}
		
		if (rdbtnLineal.isSelected()){
			System.out.print("Funcion lineal");
			funtionStr = "Lineal";
		}
		else if (rdbtnTangencial.isSelected()){
			System.out.print("Funcion tangencial");
			funtionStr = "Tangencial";
		}
		
		if (rdbtnFijo.isSelected()){
			learningCNTVariable = false; 
		}else if (rdbtnVariable.isSelected()){
			learningCNTVariable = true; 
		}
if (start){
		NetworkManager aux2 = null;
		for (final NetworkManager aux : MainWindow.neList) {
			if (aux.getName().equals(comboBox.getSelectedItem())) {
				aux2 = aux; // Controlar que se halla elegido alguna, de lo
							// contrario tendremos null exceptions
			}
		}
		ne = aux2;

		if ((!selectMatrixFile) && (rdbtnAleatorias.isSelected())) { // Las
																		// matrices
																		// no
																		// fueron
																		// seleccionadas
																		// de
																		// archivo,
																		// se
																		// generan
																		// de
																		// forma
																		// aletoria
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

		}
		
		//Creamos el directorio donde guardaremos los archivos procedentes al entrenamiento
		
		directoryName = "C:\\repositoryGit\\Salidas\\Training_"+TrainingWindow.getCurrentTimeStamp();
		directory = new File(directoryName);
		try{
			boolean creado = directory.mkdir();
			System.out.print("creado: "+ creado+"\n");
		}
		catch(SecurityException se){
			Log.error("El directorio "+ directoryName + "no ha podido ser creado");
		}
		System.out.print (directoryName);

		// Creamos el hilo que llama al training
		sw = new SwingWorker<Integer, Integer>() {
			@Override
			protected Integer doInBackground() throws Exception {
				ne.training(iterationMax, cotaError, learningCnt, matrices,
						momentB, momentBValue,funtionStr, directoryName, learningCNTVariable);
				return null;
			}

			@Override
			protected void done() {
				// Display results
				String fileName = new String(directoryName +"\\resultsTraining.txt");
				try {
					String strToAdd = FileUtils.readFileToString(new File(
							fileName));
					System.out.println(strToAdd);
					textPane.setText(textPane.getText() + strToAdd);
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.done();
			}

		};

		sw.execute();

		if (sw.isCancelled()) {
			System.out.print("ha terminado");

		}
		
		// Testing collecting data, guardamos la información previa obtenida dentro de la carpeta actual 
		
		String outFile = new String(directoryName +"\\previousInformationTraining.txt");
		TrainingWindowOuts resultados = new TrainingWindowOuts(outFile);
		resultados.previousInformation(ne.getName(), matrices, learningCnt, momentBValue, funtionStr, pathMatrices);
		
		

		// Display results
		// outFile = new String(
		// "C:\\repositoryGit\\Salidas\\ChosenPatrons.csv");
		// WriteExcel patrones = new WriteExcel(outFile);
		// patrones.writeInputsOutputs(inputs, desiredOutputs);
		// patrones.closeFile();
		//

		// Display results
		FileReader reader;
		try {
			reader = new FileReader(outFile);
			BufferedReader br = new BufferedReader(reader);
			textPane.read(br, null);
			br.close();
			// br.toString()

			// textPane.getText();

			// textPane.requestFocus();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	}

	private void btnCancelarEntrenamientoActionPerformed() {

		MainWindow.cancelTraining = true;
		System.out.print("Estado: " + sw.getState());
	}
	
	
	private void cbAcotadoLearningActionPerformed() {
		if ((cbAcotadoLearning.isSelected()) && (ne != null)){ //Si lo seleccionamos, Incluiremos un mensaje acerca del 
											//valor máximo del coeficiente
			Matrix R = Matrix.createMatrixFromArrayOfVectors(ne.getInputs()); //Patrones de entrada -->  lo convertimos a matriz
			Matrix RTransp = Matrix.transponer(R);
			Matrix mCorrelacion = Matrix.product(R, RTransp); //R es mi matriz de datos, y R · R Transpuesta es mi matriz de correlación de datos
			learningCNTCuote = 1 / mCorrelacion.getMaxDiagonal().doubleValue();
			//1 dividido entre el máximo de nuestra diagonal será el máximo
			
			
			
			lbMsnlearningCuoteMax.setText(Double.toString(learningCNTCuote));
		}
		else{
			learningCNTCuote = 0; 
			lbMsnlearningCuoteMax.setText("");		
		}
	}
	
	private void comboBoxActionPerformed() {
		NetworkManager aux2 = null;
		for (final NetworkManager aux : MainWindow.neList) {
			if (aux.getName().equals(comboBox.getSelectedItem())) {
				aux2 = aux; // Controlar que se halla elegido alguna, de lo
							// contrario tendremos null exceptions
			}
		}
		ne = aux2;
		
		//como depende de los datos de entrada, si cambiamos la red, tenemos que tendremos 
		//que calcular de nuevo la cuota superior del learning en el caso de q tengamos esa opción seleccionada
	
			if (cbAcotadoLearning.isSelected()){ //Si lo seleccionamos, Incluiremos un mensaje acerca del 
				//valor máximo del coeficiente
				Matrix R = Matrix.createMatrixFromArrayOfVectors(ne.getInputs()); //Patrones de entrada -->  lo convertimos a matriz
				Matrix RTransp = Matrix.transponer(R);
				Matrix mCorrelacion = Matrix.product(R, RTransp); //R es mi matriz de datos, y R · R Transpuesta es mi matriz de correlación de datos
				learningCNTCuote = 1 / mCorrelacion.getMaxDiagonal().doubleValue();
				//1 dividido entre el máximo de nuestra diagonal será el máximo
				lbMsnlearningCuoteMax.setText(Double.toString(learningCNTCuote));
				}
			else{
				learningCNTCuote = 0; 
				lbMsnlearningCuoteMax.setText("");		
			}
			
	}

	private void tflearningCNTActionPerformed() {
		if (cbAcotadoLearning.isSelected()){
			double max = Double.parseDouble(lbMsnlearningCuoteMax.getText());
			double currentValue = Double.parseDouble(tflearningCNT.getText()); 
				if (currentValue> max){
					JOptionPane.showMessageDialog(
							null,
							"El coeficiente de aprendizaje es mayor que el máximo recomendado en "
							+ "relación a los datos",
							"Coeficiente de aprendizaje", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		System.out.print("Entra en el tf learnig");
			
		}
	
		
	
	
	public Panel getPanel() {
		return panel;

	}

	// reset graph instance and add to the frame
	private void addNewGraph() {
		panel.getComponents();
		errorGraph = new Graph();
		panelGraph = errorGraph.createPanel();

		panelGraph.setBounds(460, 15, 510, 229);
		panelGraph.setVisible(true);
		panel.add(panelGraph);
		// panel.repaint();

	}

	private void deleteGraph() {
		panel.remove(panelGraph);

	}

	public void setPanel(final Panel panel) {
		this.panel = panel;
	}
	
	public static String getCurrentTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	    Date now = new Date();
	    String strDate = dateFormat.format(now);
	    return strDate;
	}
}
