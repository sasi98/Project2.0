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
import valueset.LearningConstant;
import valueset.Value;
import architecture.NetworkManager;
import architecture.StructureParameters;
import dataManager.*;
import outsFiles.*;

import javax.swing.JCheckBox;

import loadFiles.ReadFile;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JList;

import java.awt.Color;

public class SetUpParametersTrainWindow extends JPanel{
		
		
		/**GUI variables*/
	
	private JPanel 								panel_1,
												panel_2,
												panel_3,
												panel_4;
	private JTextField 							tfcortaError, 
												tflearningCNT, 
												tfmaxIt,
												tfmomentoB,
												textField;
	private JRadioButton 						rdbtnLineal, 
												rdbtnTangencial, 
												rdbtnAleatorias,
												rdbtnProcedentesDeArchivo;
	private JTextArea 							txtrUtilizarMatricesDe;
	private JComboBox 							comboBox,
												comboBox_1;
	private JButton								btnAceptar, 
												btnCancelar, 
												btnGuardar;
	
	/**Data variables*/
	
	private BigDecimal 							cotaError;
	private double  							momentBValue;
	private int 								iterationMax;
	private boolean 							momentB,
												acotadoMax,   
												learningCNTVariable,
												selectMatrixFile;  /**Ruta del archivo de donde obtenemos las matrices iniciales, en el caso*/
	private WeightMatrix 						matrices;
	private Matrix	 							W; 
	private LearningConstant					learningClass;
	
	private String 								pathMatrices, 
												funtionStr,
												directoryName;
	private File 								directory,
												filechoosen;
	private StructureParameters 				currentRed;
	
	private static Logger log = Logger.getLogger(SetUpParametersTrainWindow.class);

	/**
	 * Create the application.
	 */
	public SetUpParametersTrainWindow(){
		initialize();
		createEvents();
	}
	
	
	public void initialize() {
		
		pathMatrices = "";
		this.setBounds(6, 0, 980, 633);
		this.setLayout(null);
		
		/**Paneles*/
		
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Par\u00E1metros de entrenamiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(79, 60, 413, 450);
		this.add(panel_4);
		panel_4.setLayout(null);
			
		panel_1 = new JPanel();
		panel_1.setBounds(6, 16, 397, 427);
		panel_4.add(panel_1);
		panel_1.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Coeficiente de aprendizaje", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(24, 242, 252, 174);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(10, 16, 232, 147);
		panel_3.add(panel_2);
			
		/**Etiquetas*/
		
		final JLabel lblFuncion = new JLabel("Funci\u00F3n:  ");
		lblFuncion.setBounds(24,72, 77, 14);
		panel_1.add(lblFuncion);
		
		final JLabel lblAadirMomento = new JLabel("Momento Beta: ");
		lblAadirMomento.setBounds(24, 132, 77, 16);
		panel_1.add(lblAadirMomento);
		
		final JLabel lblCotaDeError = new JLabel("Cota de error:");
		lblCotaDeError.setBounds(24, 159, 86, 16);
		panel_1.add(lblCotaDeError);
		
		final JLabel lblMaxIt = new JLabel("M\u00E1ximo N\u00BA Iteraciones:");
		lblMaxIt.setBounds(24, 186, 121, 16);
		panel_1.add(lblMaxIt);
		
		final JLabel lblValor = new JLabel("Valor: ");
		lblValor.setBounds(10, 80, 46, 17);
		panel_2.add(lblValor);
		
		final JLabel lblTipologa = new JLabel("Tipolog\u00EDa:");
		lblTipologa.setBounds(10, 24, 46, 17);
		panel_2.add(lblTipologa);
		
		final JLabel lblAcotado = new JLabel("Acotado: ");
		lblAcotado.setBounds(10, 52, 57, 17);
		panel_2.add(lblAcotado);
		
		final JLabel lblLmite = new JLabel("L\u00EDmite: ");
		lblLmite.setBounds(10, 108, 46, 14);
		panel_2.add(lblLmite);
		
		final JLabel lblmatrices = new JLabel("Matrices iniciales: ");
		lblmatrices.setBounds(24, 33, 86, 28);
		panel_1.add(lblmatrices);
		
		/**TextField, comboBox y RadioButton*/
		
		final ButtonGroup groupFuncion = new ButtonGroup(); //Para que solo pueda ser selecionado uno 
		final ButtonGroup groupMatrices = new ButtonGroup();
		rdbtnLineal = new JRadioButton("Lineal");
		rdbtnLineal.setBounds(139, 68, 69, 23);
		panel_1.add(rdbtnLineal);
		rdbtnLineal.setSelected(true);
		groupFuncion.add(rdbtnLineal);
									
		rdbtnTangencial = new JRadioButton("Tangencial");
		rdbtnTangencial.setBounds(221, 68, 100, 23);
		panel_1.add(rdbtnTangencial);
		groupFuncion.add(rdbtnTangencial);
		
		
		rdbtnAleatorias = new JRadioButton("Aleatorias");
		rdbtnAleatorias.setBounds(139, 31, 80, 23);
		panel_1.add(rdbtnAleatorias);
		rdbtnAleatorias.setSelected(true);
		groupMatrices.add(rdbtnAleatorias);
		
	    rdbtnProcedentesDeArchivo = new JRadioButton("Procedentes de archivo");
	    rdbtnProcedentesDeArchivo.setBounds(219, 31, 149, 23);
		panel_1.add(rdbtnProcedentesDeArchivo);
		groupMatrices.add(rdbtnProcedentesDeArchivo);
							
		tfmomentoB = new JTextField();
		tfmomentoB.setBounds(217, 130, 80, 20);
		panel_1.add(tfmomentoB);
		tfmomentoB.setColumns(10);
						
		tflearningCNT = new JTextField();
		tflearningCNT.setBounds(132, 79, 90, 17);
		panel_2.add(tflearningCNT);
		tflearningCNT.setColumns(10);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(132, 107, 90, 17);
		panel_2.add(textField);
		textField.setColumns(10);
		
		tfcortaError = new JTextField();
		tfcortaError.setBounds(217, 157, 80, 20);
		panel_1.add(tfcortaError);
		tfcortaError.setColumns(10);
		
		tfmaxIt = new JTextField();
		tfmaxIt.setBounds(217, 184, 80, 20);
		panel_1.add(tfmaxIt);
		tfmaxIt.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.addItem(valueset.Value.LearningCNTTipologia.FIJO);
		comboBox.addItem(valueset.Value.LearningCNTTipologia.VARIABLE);
		comboBox.setBounds(132, 24, 90, 17);
		panel_2.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addItem("Cota superior");
		comboBox.addItem("Sin cota");
		comboBox_1.setBounds(132, 52, 90, 17);
		panel_2.add(comboBox_1);
						
		/**Botones*/						

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(298, 311, 89, 23);
		panel_1.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(298, 345, 89, 23);
		panel_1.add(btnCancelar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(298, 379, 89, 23);
		panel_1.add(btnGuardar);
														
																											
//
//			if ( (cbAcotadoLearning.isSelected()) && (ne != null) ){
//				double max = Double.parseDouble(lbMsnlearningCuoteMax.getText());
//				double currentValue = Double.parseDouble(tflearningCNT.getText()); 
//					if (currentValue> max){
//						JOptionPane.showMessageDialog(
//								null,
//								"El coeficiente de aprendizaje es mayor que el máximo recomendado en "
//								+ "relación a los datos. Modifica este parámetro",
//								"Coeficiente de aprendizaje", JOptionPane.WARNING_MESSAGE);
//					}
//					
//				}
//			

	}


	private void createEvents() {
		rdbtnProcedentesDeArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnProcedentesDeArchivoActionPerformed();
			}
		});
		tflearningCNT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tflearningCNTActionPerformed();
			}
		});
	}
		
	private void btnIniciarEntrenamientoActionPerformed() {
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
			
		if (rdbtnLineal.isSelected())
			funtionStr = Value.Funtion.LINEAL;
		else if (rdbtnTangencial.isSelected()){
			funtionStr = Value.Funtion.TANGENCIAL;
			
		final String stCotaError = tfcortaError.getText();
		if ((stCotaError == null) || (stCotaError.equals(""))) {
			tfcortaError.setText("0.001");
			cotaError = new BigDecimal(0.001);
			cotaError.setScale(NetworkManager.PRECISION, RoundingMode.HALF_UP);
		} else {
			cotaError = new BigDecimal(stCotaError);
			cotaError.setScale(NetworkManager.PRECISION);
		}
		
		final String stItMax = tfmaxIt.getText();
		if ((stItMax == null) || (stItMax.equals(""))) {
			tfmaxIt.setText("1000");
			iterationMax = 1000;
		} else 
			iterationMax = Integer.parseInt(stItMax);
		
		final String stMomentoB = tfmomentoB.getText();
		if ((stMomentoB == null) || (stMomentoB.equals(""))) {
			momentB = false; 	/*No utilizamos momento*/

		} else {
			momentBValue = Double.parseDouble(stMomentoB);
			momentB = true;
		}
		final String stCnsLearning = tflearningCNT.getText();
		double learningValue;
		double learningCoute;
		boolean acotado = false;
		if ((stCnsLearning == null) || (stCnsLearning.equals(""))) {
			tflearningCNT.setText("0.00001");
			 learningValue = 0.00001;
			} else {
			learningValue = Double.parseDouble(stCnsLearning);
		}
		comboBox.getSelectedItem().toString(); //Fijo o Variable
		if (comboBox_1.getSelectedItem().toString() == "Cota superior"){
			acotado = true;
			Matrix R = Matrix.createMatrixFromArrayOfVectors(currentRed.getInputs()); //Patrones de entrada -->  lo convertimos a matriz
			learningCoute = calculateCotaLearning (R);
			textField.setText(Double.toString(learningCoute));
			acotado = true;
		}
		else
			textField.setText("Sin cuota");
		learningClass = new LearningConstant(learningValue, increment, tipologia)
		
		
		
		}
			
			
//	if (start){
//			NetworkManager aux2 = null;
//			for (final NetworkManager aux : MainWindow.neList) {
//				if (aux.getName().equals(comboBox.getSelectedItem())) {
//					aux2 = aux; // Controlar que se halla elegido alguna, de lo
//								// contrario tendremos null exceptions
//				}
//			}
//			ne = aux2;
//
//			if ((!selectMatrixFile) && (rdbtnAleatorias.isSelected())) { // Las
//																			// matrices
//																			// no
//																			// fueron
//																			// seleccionadas
//																			// de
//																			// archivo,
//																			// se
//																			// generan
//																			// de
//																			// forma
//																			// aletoria
//				
//				if (ne.getNumNeuronsO() == 0){
//					final Dimension dW = new Dimension(ne.getNumNeuronsE(),
//							ne.getNumNeuronsS());
//					W = Matrix.createRandomMatrix(
//							NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW,
//							NetworkManager.PRECISION);
//					//matrices = new WeightMatrix(W);
//					 
//				}
//				else{
//					final Dimension dW = new Dimension(ne.getNumNeuronsO(),
//						ne.getNumNeuronsE());
//					final Matrix W = Matrix.createRandomMatrix(
//						NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW,
//						NetworkManager.PRECISION);
//					final Dimension dV = new Dimension(ne.getNumNeuronsS(),
//						ne.getNumNeuronsO());
//					final Matrix V = Matrix.createRandomMatrix(
//						NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dV,
//						NetworkManager.PRECISION);
//					matrices = new WeightMatrix(W, V);
//				}
//
//			}
//			else{
//				ReadFile readMatrices;
//				try {
//					readMatrices = new ReadFile(filechoosen);
//					WeightMatrix aux = readMatrices.readWeightMatrix();
//					Matrix Waux = readMatrices.readSingleWeightMatrix();
//					if (aux != null) { // Hemos seleccionado matrices del fichero
//						selectMatrixFile = true; // (no quiere decir q tengas las
//													// dimensiones apropiadas
//						matrices = aux;
//						lblNewLabel_1.setText(filechoosen.getName());
//						pathMatrices = filechoosen.getName();
//					}
//					}catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			//Creamos el directorio donde guardaremos los archivos procedentes al entrenamiento
//			
//			directoryName = "C:\\repositoryGit\\Salidas\\Training_"+TrainingWindow.getCurrentTimeStamp();
//			directory = new File(directoryName);
//			try{
//				boolean creado = directory.mkdir();
//				System.out.print("creado: "+ creado+"\n");
//			}
//			catch(SecurityException se){
//				Log.error("El directorio "+ directoryName + "no ha podido ser creado");
//			}
//			System.out.print (directoryName);
//
//			// Creamos el hilo que llama al training
//			sw = new SwingWorker<Integer, Integer>() {
//				@Override
//				protected Integer doInBackground() throws Exception {
//					
//					if (ne.getNumNeuronsO()==0){
//						System.out.print("Red simple sin capas");
//						ne.trainingSimplyNetwork(iterationMax, cotaError, learningCnt, matrices.getW(), momentB, momentBValue, funtionStr, directoryName);
//						
//					}
//					else{
//						System.out.print("Red simple con capa oculta");
//					ne.training(iterationMax, cotaError, learningCnt, matrices,
//							momentB, momentBValue,funtionStr, directoryName, learningCNTVariable);
//					}
//					return null;
//				}
//
//				@Override
//				protected void done() {
//					// Display results
//					String fileName = new String(directoryName +"\\resultsTraining.txt");
//					try {
//						String strToAdd = FileUtils.readFileToString(new File(
//								fileName));
//						System.out.println(strToAdd);
//						textPane.setText(textPane.getText() + strToAdd);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					super.done();
//				}
//
//			};
//
//			sw.execute();
//
//			if (sw.isCancelled()) {
//				System.out.print("ha terminado");
//
//			}
//			
//			// Testing collecting data, guardamos la información previa obtenida dentro de la carpeta actual 
//			
//			String outFile = new String(directoryName +"\\previousInformationTraining.txt");
//			
//			if (ne.getNumNeuronsO() == 0){
//				SNTrainingOuts resultados = new SNTrainingOuts(outFile);
//				resultados.previousInformation(ne.getName(), W, learningCNTCuote, momentBValue, funtionStr, pathMatrices);
//			
//				
//			}else{
//				LNTrainingOuts resultados = new LNTrainingOuts(outFile);
//				resultados.previousInformation(ne.getName(), matrices, learningCnt, momentBValue, funtionStr, pathMatrices);
//			}
//			
//			
//			
//
//			// Display results
//			// outFile = new String(
//			// "C:\\repositoryGit\\Salidas\\ChosenPatrons.csv");
//			// WriteExcel patrones = new WriteExcel(outFile);
//			// patrones.writeInputsOutputs(inputs, desiredOutputs);
//			// patrones.closeFile();
//			//
//
//			// Display results
//			FileReader reader;
//			try {
//				reader = new FileReader(outFile);
//				BufferedReader br = new BufferedReader(reader);
//				textPane.read(br, null);
//				br.close();
//				// br.toString()
//
//				// textPane.getText();
//
//				// textPane.requestFocus();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	}
//
//		}

		

	//Dada la matriz de correlación de datos, devuelve 1/maximo de la diagona R · R Transp
	private double calculateCotaLearning (Matrix R) {
		//valor máximo del coeficiente
		Matrix RTransp = Matrix.transponer(R);
		Matrix mCorrelacion = Matrix.product(R, RTransp); //R es mi matriz de datos, y R · R Transpuesta es mi matriz de correlación de datos
		double cota = 1 / mCorrelacion.getMaxDiagonal().doubleValue();
		//1 dividido entre el máximo de nuestra diagonal será el máximo
		return cota;
	}


		private void rdbtnProcedentesDeArchivoActionPerformed() {
			final JFileChooser filechooser = new JFileChooser(
					"C:\\repositoryGit\\Salidas");
			final int returnValue = filechooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				final File filechoosen = filechooser.getSelectedFile();
//				try {
//					final ReadFile readMatrices = new ReadFile(filechoosen);
//					WeightMatrix aux = readMatrices.readWeightMatrix();
//					Matrix Waux = readMatrices.readSingleWeightMatrix();
//					if (aux != null) { // Hemos seleccionado matrices del fichero
//						selectMatrixFile = true; // (no quiere decir q tengas las
//													// dimensiones apropiadas
//						matrices = aux;
//						lblNewLabel_1.setText(filechoosen.getName());
//						pathMatrices = filechoosen.getName();
//					}
					
					
//				} catch (final FileNotFoundException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

			} else {
				System.out.println("Open command cancelled by user.");
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
		
			
	
		
		public static String getCurrentTimeStamp() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		    Date now = new Date();
		    String strDate = dateFormat.format(now);
		    return strDate;
		}
}
