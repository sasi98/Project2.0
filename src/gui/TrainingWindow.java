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
												panel, 
												panel_1,
												panel_3,
												panel_2,
												panel_4;
	private JTextPane							textPane;
	
	private JButton 							btnIniciarEntrenamiento,
												btnCancelarEntrenamiento;
	
	/**Data variables*/
	private Manager 					     	manager;
	private SwingWorker<Integer, Integer> 		sw;
	private String 								directoryName;		/**Nombre del directorio donde se guardan los ficheros de salida correspondientes al proceso de entrenamiento*/ 
	private TrainingResults						results;  			/**Clase que devuelve el método training que informa sobre el la finalización del proceso de entrenamiento*/


	private static Logger log = Logger.getLogger(TrainingWindow.class);
	
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;

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
		addNewGraph();
			
		/**Paneles*/
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n general", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(33, 47, 482, 139);
		panel_1.setLayout(null);
		add(panel_1);
		panel_1.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(6, 16, 466, 249);
		panel.setLayout(null);
		panel_1.add(panel);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estructura de red", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(27, 11, 292, 187);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		lblNewLabel = new JLabel("Nombre: ");
		lblNewLabel.setBounds(27, 31, 46, 14);
		panel_3.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Tipo: ");
		lblNewLabel_1.setBounds(27, 67, 46, 14);
		panel_3.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Neuronas de entrada: \r\n");
		lblNewLabel_2.setBounds(27, 105, 102, 14);
		panel_3.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(27, 147, 46, 14);
		panel_3.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(129, 31, 46, 14);
		panel_3.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(129, 67, 46, 14);
		panel_3.add(lblNewLabel_5);
		
		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Par\u00E1metros de entrenamiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(27, 203, 292, 52);
		panel.add(panel_4);

		/**Botones*/
		btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");
		btnIniciarEntrenamiento.setBounds(new Rectangle(679, 401, 158, 27));
		this.add(btnIniciarEntrenamiento);
		
		btnCancelarEntrenamiento = new JButton("Cancelar");
		btnCancelarEntrenamiento.setBounds(new Rectangle(679, 451, 158, 27));
		this.add(btnCancelarEntrenamiento);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Salidas de datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(45, 268, 487, 210);
		add(panel_2);
		panel_2.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(67, 281, 749, 182);
		textPane.setEditable (false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 17, 469, 182);
		panel_2.add(scrollPane);
		scrollPane.setViewportView(textPane);
		
		

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
		manager = new Manager(MainWindow.structurePar, MainWindow.trainPar);

		
		//Creamos el directorio donde guardaremos los archivos procedentes al entrenamiento
		
		directoryName = "C:\\repositoryGit\\Salidas\\Training_"+TrainingWindow.getCurrentTimeStamp();
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
				if (MainWindow.structurePar.getTypeNet() == Value.RedType.SIMPLE){
					results = manager.trainingSimplyNetwork (directoryName);
				}
				else{
					results = manager.training(directoryName);
					
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
		
		if (MainWindow.structurePar.getTypeNet() == Value.RedType.SIMPLE){
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

		panelGraph.setBounds(460, 15, 510, 229);
		panelGraph.setVisible(true);
		this.add(panelGraph);
	}

	private void deleteGraph() {
		this.remove(panelGraph);
	}

	public static String getCurrentTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	    Date now = new Date();
	    String strDate = dateFormat.format(now);
	    return strDate;
	}
}
