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
import architecture.TrainingParameters;
import dataManager.*;
import outsFiles.*;

import javax.swing.JCheckBox;

import loadFiles.ReadFile;

import javax.swing.border.LineBorder;
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
	private JTextPane							textPane;
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
	private LearningConstant					learningClass;
	
	private String 								pathMatrices, 
												funtionStr,
												directoryName;
	private File 								directory,
												filechoosen;
	private StructureParameters 				currentStructure;
	private TrainingParameters 					trainingParameters;
	
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
		selectMatrixFile = false;
		this.setLayout(null);
		this.setBounds(MainWindow.JPANEL_MEASURES);
		/**Paneles*/
		
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Par\u00E1metros de entrenamiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(30, 47, 434, 433);
		this.add(panel_4);
		panel_4.setLayout(null);
			
		panel_1 = new JPanel();
		panel_1.setBounds(10, 19, 414, 403);
		panel_4.add(panel_1);
		panel_1.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Coeficiente de aprendizaje", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 200, 383, 125);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(10, 16, 363, 96);
		panel_3.add(panel_2);
			
		textPane = new JTextPane();
		textPane.setBounds(606, 68, 336, 398);
		textPane.setEditable (false);
		add(textPane);
		textPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Informaci\u00F3n general", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		
		/**Etiquetas*/
		
		final JLabel lblFuncion = new JLabel("Funci\u00F3n:  ");
		lblFuncion.setBounds(24,52, 77, 14);
		panel_1.add(lblFuncion);
		
		final JLabel lblAadirMomento = new JLabel("Momento Beta: ");
		lblAadirMomento.setBounds(24, 100, 121, 16);
		panel_1.add(lblAadirMomento);
		
		final JLabel lblCotaDeError = new JLabel("Cota de error:");
		lblCotaDeError.setBounds(24, 127, 86, 16);
		panel_1.add(lblCotaDeError);
		
		final JLabel lblMaxIt = new JLabel("N\u00BA Max Iteraciones:");
		lblMaxIt.setBounds(24, 154, 121, 16);
		panel_1.add(lblMaxIt);
		
		final JLabel lblValor = new JLabel("Valor: ");
		lblValor.setBounds(207, 24, 46, 17);
		panel_2.add(lblValor);
		
		final JLabel lblTipologa = new JLabel("Tipolog\u00EDa:");
		lblTipologa.setBounds(10, 24, 68, 17);
		panel_2.add(lblTipologa);
		
		final JLabel lblAcotado = new JLabel("Acotado: ");
		lblAcotado.setBounds(10, 52, 57, 17);
		panel_2.add(lblAcotado);
		
		final JLabel lblLmite = new JLabel("L\u00EDmite: ");
		lblLmite.setBounds(207, 53, 46, 14);
		panel_2.add(lblLmite);
		
		final JLabel lblmatrices = new JLabel("Matrices iniciales: ");
		lblmatrices.setBounds(24, 13, 115, 28);
		panel_1.add(lblmatrices);
		
		/**TextField, comboBox y RadioButton*/
		
		final ButtonGroup groupFuncion = new ButtonGroup(); //Para que solo pueda ser selecionado uno 
		final ButtonGroup groupMatrices = new ButtonGroup();
		rdbtnLineal = new JRadioButton("Lineal");
		rdbtnLineal.setBounds(145, 48, 69, 23);
		panel_1.add(rdbtnLineal);
		rdbtnLineal.setSelected(true);
		groupFuncion.add(rdbtnLineal);
									
		rdbtnTangencial = new JRadioButton("Tangencial");
		rdbtnTangencial.setBounds(247, 48, 100, 23);
		panel_1.add(rdbtnTangencial);
		groupFuncion.add(rdbtnTangencial);
		
		
		rdbtnAleatorias = new JRadioButton("Aleatorias");
		rdbtnAleatorias.setBounds(145, 16, 100, 23);
		panel_1.add(rdbtnAleatorias);
		rdbtnAleatorias.setSelected(true);
		groupMatrices.add(rdbtnAleatorias);
		
	    rdbtnProcedentesDeArchivo = new JRadioButton("Seleccionar de archivo");
	    rdbtnProcedentesDeArchivo.setBounds(247, 16, 161, 23);
		panel_1.add(rdbtnProcedentesDeArchivo);
		groupMatrices.add(rdbtnProcedentesDeArchivo);
							
		tfmomentoB = new JTextField();
		tfmomentoB.setBounds(217, 98, 80, 20);
		panel_1.add(tfmomentoB);
		tfmomentoB.setColumns(10);
						
		tflearningCNT = new JTextField();
		tflearningCNT.setBounds(263, 24, 90, 17);
		panel_2.add(tflearningCNT);
		tflearningCNT.setColumns(10);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(263, 51, 90, 17);
		panel_2.add(textField);
		textField.setColumns(10);
		
		tfcortaError = new JTextField();
		tfcortaError.setBounds(217, 125, 80, 20);
		panel_1.add(tfcortaError);
		tfcortaError.setColumns(10);
		
		tfmaxIt = new JTextField();
		tfmaxIt.setBounds(217, 152, 80, 20);
		panel_1.add(tfmaxIt);
		tfmaxIt.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.addItem(valueset.Value.LearningCNTTipologia.FIJO);
		comboBox.addItem(valueset.Value.LearningCNTTipologia.VARIABLE);
		comboBox.setBounds(81, 24, 90, 17);
		panel_2.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addItem("Cota superior");
		comboBox_1.addItem("Sin cota");
		comboBox_1.setBounds(81, 52, 90, 17);
		panel_2.add(comboBox_1);
						
		/**Botones*/						
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(83, 369, 89, 23);
		panel_1.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(304, 369, 89, 23);
		panel_1.add(btnCancelar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(192, 369, 89, 23);
		panel_1.add(btnGuardar);
																	
																											
//
//			if ( (cbAcotadoLearning.isSelected()) && (ne != null) ){
//				double max = Double.parseDouble(lbMsnlearningCuoteMax.getText());
//				double currentValue = Double.parseDouble(tflearningCNT.getText()); 
//					if (currentValue> max){
//						JOptionPane.showMessageDialog(
//								null,
//								"El coeficiente de aprendizaje es mayor que el m�ximo recomendado en "
//								+ "relaci�n a los datos. Modifica este par�metro",
//								"Coeficiente de aprendizaje", JOptionPane.WARNING_MESSAGE);
//					}
//					
//				}
//			

	}


	private void createEvents() {
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAceptarActionPerformed();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCancelarActionPerformed();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGuardarActionPerformed();
			}
		});
		rdbtnProcedentesDeArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnProcedentesDeArchivoActionPerformed();
			}
		});
//		tflearningCNT.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				tflearningCNTActionPerformed();
//			}
//		});
		
	}
		
	private void btnAceptarActionPerformed() {
		if ((!selectMatrixFile) && (rdbtnAleatorias.isSelected())) { 			//Las matrices no fueron seleccionadas de archivo
																	 			//se generan de forma aleatoria,
			if (currentStructure.getTypeData() == Value.RedType.SIMPLE){
				final Dimension dW = new Dimension(currentStructure.getNumNeuronsE(),currentStructure.getNumNeuronsS());
				Matrix W = Matrix.createRandomMatrix( NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW, NetworkManager.PRECISION);
				matrices = new WeightMatrix(W);
			}
			else{ 
				final Dimension dW = new Dimension(currentStructure.getNumNeuronsO(), currentStructure.getNumNeuronsE());
				final Matrix W = Matrix.createRandomMatrix(
				NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW,
				NetworkManager.PRECISION);
				final Dimension dV = new Dimension(currentStructure.getNumNeuronsS(), currentStructure.getNumNeuronsO());
				final Matrix V = Matrix.createRandomMatrix(NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dV,
				NetworkManager.PRECISION);
				matrices = new WeightMatrix(W, V);
			}
		}
		else{
			ReadFile readMatrices;
			try {
				readMatrices = new ReadFile(filechoosen);
				WeightMatrix aux = readMatrices.readWeightMatrix();
				Matrix Waux = readMatrices.readSingleWeightMatrix();
				if (aux != null) { // Hemos seleccionado matrices del fichero
					selectMatrixFile = true; // o quiere decir q tengas las dimensiones apropiadas
					matrices = aux;
					pathMatrices = filechoosen.getName();
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (rdbtnLineal.isSelected())
			funtionStr = Value.Funtion.LINEAL;
		else if (rdbtnTangencial.isSelected())
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
		double learningCoute = 0;
		boolean acotado = false;
		if ((stCnsLearning == null) || (stCnsLearning.equals(""))) {
			tflearningCNT.setText("0.00001");
			 learningValue = 0.00001;
		} 
		else 
			learningValue = Double.parseDouble(stCnsLearning);
		
		String tipologia = comboBox.getSelectedItem().toString(); //Fijo o Variable
		if (comboBox_1.getSelectedItem().toString() == "Cota superior"){
			acotado = true;
			Matrix R = Matrix.createMatrixFromArrayOfVectors(currentStructure.getInputs()); //Patrones de entrada -->  lo convertimos a matriz
			learningCoute = calculateCotaLearning (R);
			textField.setText(Double.toString(learningCoute));
			acotado = true;
		}
		else
			textField.setText("Sin cuota");
		
		learningClass = new LearningConstant(learningValue, tipologia, acotado, learningCoute);
		
		trainingParameters = new TrainingParameters(funtionStr, iterationMax, cotaError, learningClass, matrices, momentBValue, momentB);
	}

	//Dada la matriz de correlaci�n de datos, devuelve 1/maximo de la diagona R � R Transp
	private double calculateCotaLearning (Matrix R) {
		//valor m�ximo del coeficiente
		Matrix RTransp = Matrix.transponer(R);
		Matrix mCorrelacion = Matrix.product(R, RTransp); //R es mi matriz de datos, y R � R Transpuesta es mi matriz de correlaci�n de datos
		double cota = 1 / mCorrelacion.getMaxDiagonal().doubleValue();
		//1 dividido entre el m�ximo de nuestra diagonal ser� el m�ximo
		return cota;
	}


	private void rdbtnProcedentesDeArchivoActionPerformed() {
		final JFileChooser filechooser = new JFileChooser ("C:\\repositoryGit\\Salidas");
			final int returnValue = filechooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				final File filechoosen = filechooser.getSelectedFile();
				try {
					final ReadFile readMatrices = new ReadFile(filechoosen);
					WeightMatrix aux = readMatrices.readWeightMatrix();
					Matrix Waux = readMatrices.readSingleWeightMatrix();
					if (aux != null) { // Hemos seleccionado matrices del fichero
						selectMatrixFile = true; // (no quiere decir q tengas las
													// dimensiones apropiadas
						matrices = aux;
						//lblNewLabel_1.setText(filechoosen.getName());
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

//		private void tflearningCNTActionPerformed() {
//			if (cbAcotadoLearning.isSelected()){
//				double max = Double.parseDouble(lbMsnlearningCuoteMax.getText());
//				double currentValue = Double.parseDouble(tflearningCNT.getText()); 
//					if (currentValue> max){
//						JOptionPane.showMessageDialog(
//								null,
//								"El coeficiente de aprendizaje es mayor que el m�ximo recomendado en "
//								+ "relaci�n a los datos",
//								"Coeficiente de aprendizaje", JOptionPane.WARNING_MESSAGE);
//					}
//					
//				}
//			System.out.print("Entra en el tf learnig");
//				
//			}
		

		private void btnCancelarActionPerformed() {
			// TODO Auto-generated method stub
			
		}

		private void btnGuardarActionPerformed() {
			if (trainingParameters != null){
				final JFileChooser fileChooser = new JFileChooser ("C:\\repositoryGit\\Salidas");
				fileChooser.setDialogTitle("Guardar par�metros de entrenamiento"); 
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {		
					File file = fileChooser.getSelectedFile();
					if (!file.toString().endsWith(".csv")){   //A�adimos la extensi�n, en caso de que no se la hallamos puesta
						file = new File (file + ".csv");
					}
//					StructureParametersOuts classFile = new StructureParametersOuts(file.toString());
//					classFile.saveStructureParameters(currentNet);
					JOptionPane.showMessageDialog (null,"Los par�metros de entrenamiento han sido guardados",
							"Archivo guardado", JOptionPane.PLAIN_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog (null,"Error, no hay par�metros de entrenamiento establecidos",
						"Par�metros no establecidos", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public static String getCurrentTimeStamp() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		    Date now = new Date();
		    String strDate = dateFormat.format(now);
		    return strDate;
		}
}
