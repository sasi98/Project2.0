package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import loadFiles.ReadFile;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import outsFiles.WriteFile;
import utilities.Matrix;
import utilities.WeightMatrix;
import valueset.Value;
import architecture.Manager;
import architecture.StructureParameters;


public class CalculateOutputsWindow extends JPanel{
	
	/**GUI Variables*/
	private JPanel 			panel;
	private JLabel 			label_4,
							label_5,
							label_6,
							label_7,
							label_8;
	private JTextField 		textField;
	private String 			strMatrices, 
							directoryName;
	private JButton 		btnNewButton,
							btnAceptar;
					
	
	/**Data variables */

	private WeightMatrix 	matrices;
	private boolean 		selectMatrixFile;
	private File 			lastVisitedDirectory;
	
	
	private static Logger log = Logger.getLogger(CalculateOutputsWindow.class);
	
	
	/**
	 * Create the application.
	 */
	public CalculateOutputsWindow(){
		initialize();
		createEvents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		

		lastVisitedDirectory = new File("C:\\repositoryGit\\Salidas");
		selectMatrixFile = false;
		this.setBounds(MainWindow.JPANEL_MEASURES);
		this.setLayout(null);
		
		/**Paneles*/
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estructura de red", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(70, 95, 204, 166);
		add(panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Matriz de pesos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(304, 120, 398, 108);
		add(panel_2);
		panel_2.setLayout(null);
		
		/**Etiquetas*/
		JLabel label = new JLabel("Nombre: ");
		label.setBounds(20, 28, 57, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Tipo: ");
		label_1.setBounds(20, 53, 46, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Dimensiones: ");
		label_2.setBounds(20, 78, 90, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("N\u00BA de Patrones:");
		label_3.setBounds(20, 103, 90, 14);
		panel.add(label_3);

		JLabel lblBias = new JLabel("Bias:");
		lblBias.setBounds(20, 128, 46, 14);
		panel.add(lblBias);
		
		label_4 = new JLabel("New label");
		label_4.setBounds(122, 28, 72, 14);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setBounds(122, 53, 72, 14);
		panel.add(label_5);
		
		label_6 = new JLabel("New label");
		label_6.setBounds(122, 78, 72, 14);
		panel.add(label_6);
		
		label_7 = new JLabel("New label");
		label_7.setBounds(122, 103, 72, 14);
		panel.add(label_7);
		
		label_8 = new JLabel("New label");
		label_8.setBounds(122, 128, 46, 14);
		panel.add(label_8);
		
		
		
		/**Botones*/
		btnNewButton = new JButton("Seleccionar\r\n");
		btnNewButton.setBounds(25, 42, 89, 23);
		panel_2.add(btnNewButton);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(529, 433, 89, 23);
		add(btnAceptar);
		
		textField = new JTextField();
		textField.setBounds(182, 43, 206, 20);
		panel_2.add(textField);
		textField.setColumns(10);
		showStructureInformation();
		
	}
	
	private void createEvents() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewButtonActionPerformed();
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAceptarActionPerformed();
			}
		});
	}
	
	
	private void btnNewButtonActionPerformed() {
		final JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(lastVisitedDirectory);
		final int returnValue = filechooser.showOpenDialog(null);
		StructureParameters currentStructurePar = MainWindow.structurePar;
		if (currentStructurePar == null){
			JOptionPane.showMessageDialog (null,"No se han cargado o creados los datos de entrada", 
					"Estructura no encontrada", JOptionPane.ERROR_MESSAGE);
		}
		else{
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				final File filechoosen = filechooser.getSelectedFile();
				ReadFile readMatrices;
				try {
					readMatrices = new ReadFile(filechoosen);
					WeightMatrix aux = null; 
					if (currentStructurePar.getTypeNet().equals(Value.RedType.MULTICAPA))
						aux = readMatrices.readWeightMatrix();
					else if (currentStructurePar.getTypeNet().equals(Value.RedType.MONOCAPA)){
						Matrix Waux = readMatrices.readSingleWeightMatrix();
						aux = new WeightMatrix(Waux, null);
					}
					if  (aux != null) { // Hemos seleccionado matrices del fichero
						selectMatrixFile = true; // o quiere decir q tengas las dimensiones apropiadas
						matrices = aux;
					}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				textField.setText(filechooser.getName());
				lastVisitedDirectory = filechooser.getCurrentDirectory();
			}
		}
	}
	
	private void btnAceptarActionPerformed() {

		StructureParameters currentStructurePar = MainWindow.structurePar;
		if (!MainWindow.MatrixSuitStructure(currentStructurePar, matrices)){			
			String dimensiones = " ";
			if (matrices.getW()!= null)
				dimensiones =  "W ["+matrices.getW().getRow() + " X "+ matrices.getW().getColumn() +"]";
			if (matrices.getV()!= null)
				dimensiones = dimensiones + "V ["+matrices.getV().getRow() + " X "+ matrices.getV().getColumn() +"]";
			
			JOptionPane.showMessageDialog (null,"Las dimensiones de las matrices elegidas"+ dimensiones+" no corresponden con la estructura de la red\n ", 
					"Dimensiones no adecuadas", JOptionPane.ERROR_MESSAGE);
		}
		else{
			/**Get outputs*/
			ArrayList<BigDecimal[]> outputs = Manager.calculateOutputs(currentStructurePar, matrices, Value.Funtion.LINEAL);
			
			/**Creamos el directorio donde guardaremos los archivos generados en esta clase*/
			directoryName = "C:\\repositoryGit\\Salidas\\Results_"+MainWindow.getCurrentTimeStamp();
			File directory = new File(directoryName);
			try{
				boolean creado = directory.mkdir();
				System.out.print("creado: "+ creado+"\n");
			}
			catch(SecurityException se){
				Log.error("El directorio "+ directoryName + "no ha podido ser creado");
			}
			String outFile = new String(directoryName+"\\inputs_outpObtained_outpDesired "+".csv");
			WriteFile writer = new WriteFile(outFile);
			writer.writeInputsOuDesiredOuObtained(currentStructurePar.getInputs(), outputs, currentStructurePar.getDesiredOutputs());
			writer.closeFile();
			String matricesFile = new String(directoryName+"\\matrices "+".csv");
			writer = new WriteFile(matricesFile);
			if (matrices.getV()!= null){
				writer.writeMatrices(matrices);
			}
			else{
				writer.writeMatriz(matrices.getW());
			}
			writer.closeFile();
			int input = JOptionPane.showOptionDialog(null, "Las salidas han sido generadas. ¿Desea abrir el archivo generado?", "Salidas generadas", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if (input == JOptionPane.YES_OPTION){
				//Abrir documento
				
			}
			
			}
	}
	
	public void showStructureInformation() {
		StructureParameters currentStructurePar = MainWindow.structurePar;
		if (currentStructurePar != null){
			label_4.setText(currentStructurePar.getName());
			label_5.setText(currentStructurePar.getTypeNet());
			label_7.setText(Integer.toString(currentStructurePar.getNumPatterns()));
			if (currentStructurePar.getTypeNet().equals(Value.RedType.MONOCAPA))
				label_6.setText(Integer.toString(currentStructurePar.getNumNeuronsE())+ " X "+ Integer.toString(currentStructurePar.getNumNeuronsS()));
			else
				label_6.setText(Integer.toString(currentStructurePar.getNumNeuronsE())+ " X "+ Integer.toString(currentStructurePar.getNumNeuronsO())+" X "+Integer.toString(currentStructurePar.getNumNeuronsS()));
			if (currentStructurePar.hasBias())
				label_8.setText("Sí");
			else
				label_8.setText("No");
		}
		else{
			label_4.setText("");
			label_5.setText("");
			label_7.setText("");
			label_6.setText("");
			label_8.setText("");
		}
		  
	}
}



