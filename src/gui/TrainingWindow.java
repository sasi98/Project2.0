package gui;

import graph.Graph;

import java.awt.Color;
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
import org.apache.log4j.chainsaw.Main;
import org.jfree.util.Log;

import utilities.Matrix;
import utilities.WeightMatrix;
import valueset.Value;
import architecture.Manager;
import architecture.StructureParameters;
import architecture.TrainingParameters;
import architecture.TrainingResults;
import dataManager.*;
import outsFiles.*;

import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import loadFiles.ReadFile;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class TrainingWindow extends JPanel {
	
	public static SwingWorker<Void, Void> 		worker;
	public static Graph 						errorGraph;

	/**GUI Variables*/
	private JPanel 								panelGraph,
												panel_1,
												panel_3,
												panel_2,
												panel_4;
	private JTextPane							textPane;
	
	private JButton 							btnIniciarEntrenamiento,
												btnCancelarEntrenamiento;
	
	private JLabel								lblNombre,
												lblTipo,
												lblFuncion,
												lblLearning,
												lblMomentoB,
												lblDimensiones,
												lblPatrones;
	
	/**Data variables*/
	private SwingWorker<Integer, Integer> 		sw;
	private String 								directoryName;		/**Nombre del directorio donde se guardan los ficheros de salida correspondientes al proceso de entrenamiento*/ 
	private TrainingResults						results;  			/**Clase que devuelve el método training que informa sobre el la finalización del proceso de entrenamiento*/


	private static Logger log = Logger.getLogger(TrainingWindow.class);
	

	/**
	 * Create the application.
	 */
	public TrainingWindow() {
		initialize();
		createEvents();
	}

	public void initialize() {
		
		this.setBounds(MainWindow.JPANEL_MEASURES);
		this.setLayout(null);
			
		/**Paneles*/
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n general", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(703, 95, 258, 305);
		panel_1.setLayout(null);
		add(panel_1);
		panel_1.setLayout(null);

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estructura de red", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(27, 25, 204, 139);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Par\u00E1metros de entrenamiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(27, 184, 204, 110);
		panel_1.add(panel_4);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Salidas de datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(53, 318, 487, 163);
		add(panel_2);
		panel_2.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(67, 281, 749, 180);
		textPane.setEditable (false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 17, 467, 138);
		panel_2.add(scrollPane);
		scrollPane.setViewportView(textPane);
		
		/**Etiquetas */
		JLabel lblNewLabel = new JLabel("Nombre: ");
		lblNewLabel.setBounds(20, 28, 57, 14);
		panel_3.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tipo: ");
		lblNewLabel_1.setBounds(20, 53, 46, 14);
		panel_3.add(lblNewLabel_1);
		
		JLabel  lblNewLabel_2 = new JLabel("Dimensiones: ");
		lblNewLabel_2.setBounds(20, 78, 90, 14);
		panel_3.add(lblNewLabel_2);
		
		JLabel  lblNewLabel_3 = new JLabel("N\u00BA de Patrones:");
		lblNewLabel_3.setBounds(20, 103, 90, 14);
		panel_3.add(lblNewLabel_3);
		
		lblNombre = new JLabel("New label");
		lblNombre.setBounds(122, 28, 72, 14);
		panel_3.add(lblNombre);
		
		lblTipo = new JLabel("New label");
		lblTipo.setBounds(122, 53, 72, 14);
		panel_3.add(lblTipo);
		
		lblDimensiones = new JLabel("New label");
		lblDimensiones.setBounds(122, 78, 72, 14);
		panel_3.add(lblDimensiones);
		
		lblPatrones = new JLabel("New label");
		lblPatrones.setBounds(122, 103, 72, 14);
		panel_3.add(lblPatrones);
		
		
		JLabel lblNewLabel_6 = new JLabel("Funci\u00F3n: ");
		lblNewLabel_6.setBounds(20, 28, 66, 14);
		panel_4.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Momento B:");
		lblNewLabel_7.setBounds(20, 78, 78, 14);
		panel_4.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel((String) null);
		lblNewLabel_8.setBounds(10, 107, 223, 14);
		panel_4.add(lblNewLabel_8);
		
		JLabel lblNewLabel_11 = new JLabel("Aprendizaje: ");
		lblNewLabel_11.setBounds(20, 53, 90, 14);
		panel_4.add(lblNewLabel_11);
		
		lblFuncion = new JLabel("New label");
		lblFuncion.setBounds(120, 28, 74, 14);
		panel_4.add(lblFuncion);
		
		lblLearning = new JLabel("New label");
		lblLearning.setBounds(120, 53, 74, 14);
		panel_4.add(lblLearning);
		
		lblMomentoB = new JLabel("New label");
		lblMomentoB.setBounds(120, 78, 74, 14);
		panel_4.add(lblMomentoB);

		/**Botones*/
		btnIniciarEntrenamiento = new JButton("Iniciar");
		btnIniciarEntrenamiento.setBounds(new Rectangle(718, 439, 110, 30));
		this.add(btnIniciarEntrenamiento);
		
		btnCancelarEntrenamiento = new JButton("Cancelar");
		btnCancelarEntrenamiento.setBounds(new Rectangle(864, 439, 110, 30));
		this.add(btnCancelarEntrenamiento);
		addNewGraph();
		displayGeneralInformation();

	}

	private void createEvents() {
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
	}

	private void btnIniciarEntrenamientoActionPerformed() {

		boolean start = true; 
		MainWindow.cancelTraining = false;
		deleteGraph();
		addNewGraph();
		MainWindow.structurePar.print();

		
		//Creamos el directorio donde guardaremos los archivos procedentes al entrenamiento
		
		directoryName = "C:\\repositoryGit\\Salidas\\Training_"+MainWindow.getCurrentTimeStamp();
		File directory = new File(directoryName);
		try{
			boolean creado = directory.mkdir();
			System.out.print("creado: "+ creado+"\n");
		}
		catch(SecurityException se){
			Log.error("El directorio "+ directoryName + "no ha podido ser creado");
		}

		// Creamos el hilo que llama al training
		sw = new SwingWorker<Integer, Integer>() {
			@Override
			protected Integer doInBackground() throws Exception {
				if (MainWindow.structurePar.getTypeNet().equals(Value.RedType.MONOCAPA)){
					results = Manager.trainingSimplyNetwork (directoryName, MainWindow.structurePar, MainWindow.trainPar);
				}
				else{
					results = Manager.training (directoryName, MainWindow.structurePar, MainWindow.trainPar);
					
				}
				return null;
			}

			@Override
			protected void done() {
				// Display results
				String fileName = new String(directoryName +"\\resultsTraining.txt");
				try {
				JOptionPane.showMessageDialog (null, results.getContentWindowBox(),
							results.getTitleWindowBox(),JOptionPane.PLAIN_MESSAGE);
					String strToAdd = FileUtils.readFileToString(new File(fileName));
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
		String initialMatrixFile = new String(directoryName+ "\\MatricesIniciales.csv");
		WriteFile writerMatrix = new WriteFile(initialMatrixFile);
		writerMatrix.writeMatrices(MainWindow.trainPar.getMatrices());
		writerMatrix.closeFile();
		if (MainWindow.structurePar.getTypeNet().equals(Value.RedType.MONOCAPA)){
			SNTrainingOuts resultados = new SNTrainingOuts(outFile);
			resultados.previousInformation(MainWindow.structurePar.getName(),MainWindow.trainPar.getMatrices().getW(),  MainWindow.trainPar.getLearning().getValue(),  MainWindow.trainPar.getMomentoBvalue(), MainWindow.trainPar.getFuncion());
		}else{
			LNTrainingOuts resultados = new LNTrainingOuts(outFile);
			resultados.previousInformation(MainWindow.structurePar.getName(), MainWindow.trainPar.getMatrices(),  MainWindow.trainPar.getLearning().getValue(),  MainWindow.trainPar.getMomentoBvalue(),  MainWindow.trainPar.getFuncion());
		}
		
		// Display results
		FileReader reader;
		try {
			reader = new FileReader(outFile);
			BufferedReader br = new BufferedReader(reader);
			textPane.read(br, null);
			br.close();
//			br.toString();
//
//			textPane.getText();
//
//			 textPane.requestFocus();
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
		System.out.print("Estado: " + sw.getState());
	}
	
	// reset graph instance and add to the frame
	private void addNewGraph() {
		errorGraph = new Graph();
		panelGraph = errorGraph.createPanel();

		panelGraph.setBounds(53, 32, 607, 257);
		panelGraph.setVisible(true);
		this.add(panelGraph);
	}

	private void deleteGraph() {
		this.remove(panelGraph);
	}


	
	//Muestra la información acerca del entrenamiento (estructura y parámetros) en el panel de la derecha
	void displayGeneralInformation(){
		StructureParameters structPar = MainWindow.structurePar;
		TrainingParameters trainPar = MainWindow.trainPar;
		if (structPar != null){
			lblNombre.setText(structPar.getName());
			lblTipo.setText(structPar.getTypeNet());
			lblPatrones.setText(Integer.toString(structPar.getNumPatterns()));
			if (structPar.getTypeNet().equals(Value.RedType.MONOCAPA))
				lblDimensiones.setText(Integer.toString(structPar.getNumNeuronsE())+ " X "+ Integer.toString(structPar.getNumNeuronsS()));
			else
				lblDimensiones.setText(Integer.toString(structPar.getNumNeuronsE())+ " X "+ Integer.toString(structPar.getNumNeuronsO())+" X "+Integer.toString(structPar.getNumNeuronsS()));
		}
		else{
			lblNombre.setText("");
			lblTipo.setText("");
			lblPatrones.setText("");
			lblDimensiones.setText("");
		}
		if (trainPar != null){
			lblLearning.setText(Double.toString(trainPar.getLearning().getValue()));
			lblMomentoB.setText(Double.toString(trainPar.getMomentoBvalue()));
			lblFuncion.setText(trainPar.getFuncion());
		}
		else{
			lblLearning.setText("");
			lblMomentoB.setText("");
			lblFuncion.setText("");
		}
		
	}
}
