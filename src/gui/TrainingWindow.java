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
import architecture.StructureParameters;
import architecture.TrainingParameters;
import dataManager.*;
import outsFiles.*;

import javax.swing.JCheckBox;

import loadFiles.ReadFile;

public class TrainingWindow extends JPanel {
	
	public static SwingWorker<Void, Void> 		worker;
	public static Graph 						errorGraph;

	/**GUI Variables*/
	private JPanel 								panelGraph;
	private JButton 							btnIniciarEntrenamiento,
												btnCancelarEntrenamiento;
	
	/**Data variables*/
	private StructureParameters					structurePar;
	private TrainingParameters					trainingPar; 
	private NetworkManager 						manager;
	private SwingWorker<Integer, Integer> 		sw;
	private String 								directoryName; 
	private File								directory;


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
		addNewGraph();

		btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");
		btnIniciarEntrenamiento.setBounds(new Rectangle(679, 401, 158, 27));
		this.add(btnIniciarEntrenamiento);
		
		btnCancelarEntrenamiento = new JButton("Cancelar");
		btnCancelarEntrenamiento.setBounds(new Rectangle(679, 451, 158, 27));
		this.add(btnCancelarEntrenamiento);

		

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
				if (structurePar.getTypeNet() == Value.RedType.SIMPLE){
					System.out.print("Red simple sin capas");
					manager.trainingSimplyNetwork (directoryName);
				}
				else{
					System.out.print("Red simple con capa oculta");
					manager.training(directoryName);
				}
				return null;
			}

			@Override
			protected void done() {
				// Display results
				String fileName = new String(directoryName +"\\resultsTraining.txt");
				try {
					String strToAdd = FileUtils.readFileToString(new File(fileName));
					System.out.println(strToAdd);
					//textPane.setText(textPane.getText() + strToAdd);
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
		
		if (structurePar.getTypeNet() == Value.RedType.SIMPLE){
			SNTrainingOuts resultados = new SNTrainingOuts(outFile);
			//resultados.previousInformation(ne.getName(), W, learningCNTCuote, momentBValue, funtionStr, pathMatrices);
		}else{
			LNTrainingOuts resultados = new LNTrainingOuts(outFile);
			//resultados.previousInformation(ne.getName(), matrices, learningCnt, momentBValue, funtionStr, pathMatrices);
		}
		
		// Display results
		FileReader reader;
		try {
			reader = new FileReader(outFile);
			BufferedReader br = new BufferedReader(reader);
		//	textPane.read(br, null);
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
