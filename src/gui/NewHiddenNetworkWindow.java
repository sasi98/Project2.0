package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import outsFiles.StructureParametersOuts;
import valueset.Value;
import architecture.Manager;
import architecture.StructureParameters;
import dataManager.PatronData;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Rectangle;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class NewHiddenNetworkWindow extends JPanel {

	/**GUI Variables*/
	private JButton 			bCreateNN, 
								btnCancelar,
								btnGuardar;
	private JTextField 			tfIdCompany, 
								tfInicio;
	private JComboBox<String> 	comboBox_inputType;
	private JSpinner 			sPatrones,
					 			spNumNeurons,
					 			spNumNeuronO;
	private JTextPane 			textPane;
	private JCheckBox 			checkBox;
	private JScrollPane         scrollPane;
	private JPanel              panel,
							    panel_1,
							    panel_2;

	/**Data variables*/
	private String 				idCompany,
								directoryName,
								outFile; 			/**Nombre del fichero en el que escribimos los inputs/outputs*/
	private int 				numNeuronES,
								numPatrones,
								numNeuronO,
								inicio;
	private boolean 			bias;
	//private StructureParameters	currentNet;       /**Representa la actual que hemos creado en esta ventana*/

	private static Logger log = Logger.getLogger(NewHiddenNetworkWindow.class);
	
	/**
	 * Create the application.
	 */
	public NewHiddenNetworkWindow() {
		initialize();
		createEvents();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setBounds(MainWindow.JPANEL_MEASURES);
		this.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selecci\u00F3n de datos y estructura de la red", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBounds(61, 56, 755, 193);
		add(panel_1);
		panel_1.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 22, 735, 159);
		panel_1.add(panel);
		panel.setLayout(null);

		final JLabel lblNewLabel_7 = new JLabel("Tipo de datos: ");
		lblNewLabel_7.setBounds(31, 22, 156, 16);
		panel.add(lblNewLabel_7);

		final JLabel lblNewLabel_8 = new JLabel("Inicio de los datos:");
		lblNewLabel_8.setBounds(31, 96, 120, 16);
		panel.add(lblNewLabel_8);
		
		comboBox_inputType = new JComboBox();
		comboBox_inputType.setBounds(215, 19, 115, 22);
		comboBox_inputType.addItem(Value.ComboBox.SOLAPADOS);
		comboBox_inputType.addItem(Value.ComboBox.NOSOLAPADOS);
		comboBox_inputType.addItem(Value.ComboBox.ALEATORIOS);
		panel.add(comboBox_inputType);
		
		final JLabel lblNewLabel_2 = new JLabel("Patrones de aprendizaje: ");
		lblNewLabel_2.setBounds(383, 22, 177, 16);
		panel.add(lblNewLabel_2);
				
		final JLabel lblNewLabel = new JLabel("Identificador empresa: ");
		lblNewLabel.setBounds(31, 57, 145, 16);
		panel.add(lblNewLabel);
		
		final JLabel lblNewLabel_4 = new JLabel("Neuronas ocultas:");
		lblNewLabel_4.setBounds(383, 96, 151, 16);
		panel.add(lblNewLabel_4);
			
		tfIdCompany = new JTextField();
		tfIdCompany.setBounds(278, 54, 52, 22);
		panel.add(tfIdCompany);
		tfIdCompany.setColumns(10);
		
		tfInicio = new JTextField();
		tfInicio.setBounds(278, 93, 52, 22);
		panel.add(tfInicio);
		tfInicio.setColumns(10);
				
		sPatrones = new JSpinner();
		sPatrones.setBounds(623, 19, 37, 22);
		sPatrones.setValue(5);
		panel.add(sPatrones);
										
		spNumNeurons = new JSpinner();
		spNumNeurons.setBounds(623, 54, 37, 22);
		spNumNeurons.setValue(0);
		panel.add(spNumNeurons);
												
		spNumNeuronO = new JSpinner();
		spNumNeuronO.setBounds(623, 93, 37, 22);
	    spNumNeuronO.setValue(0);
		panel.add(spNumNeuronO);
												
		checkBox = new JCheckBox("Bias");
		checkBox.setBounds(383, 129, 52, 23);
		panel.add(checkBox);
														
		final JLabel lblNewLabel_3 = new JLabel("Neuronas de entrada/salida:");
		lblNewLabel_3.setBounds(new Rectangle(383, 57, 215, 16));
																
		panel.add(lblNewLabel_3);

		bCreateNN = new JButton("Crear red");
		bCreateNN.setBounds(591, 425, MainWindow.BUTTON_SIZE.width, MainWindow.BUTTON_SIZE.height);
		this.add(bCreateNN);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(864, 425, MainWindow.BUTTON_SIZE.width, MainWindow.BUTTON_SIZE.height);
		add(btnCancelar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(726, 425, MainWindow.BUTTON_SIZE.width, MainWindow.BUTTON_SIZE.height);
		add(btnGuardar);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Salidas de datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(61, 270, 487, 210);
		add(panel_2);
		panel_2.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(67, 281, 749, 182);
		textPane.setEditable (false);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 17, 469, 182);
		panel_2.add(scrollPane);
		scrollPane.setViewportView(textPane);
	
		//Creación del directorio:
		directoryName = "C:\\repositoryGit\\Salidas\\NewNetworkOuts";
		File directory = new File(directoryName);
		try{
			directory.mkdir();
		}
		catch(SecurityException se){
			log.error("El directorio "+ directoryName + "no ha podido ser creado");
		}
	}

	
	private void createEvents() {
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGuardarActionPerformed();
			}
		});
		bCreateNN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bCreateNNActionPerformed();	
			}
		});	
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelarActionPerformed();
			}
		});
	}


	//Guardamos la red en un fichero. 
	private void btnGuardarActionPerformed() {
		if (MainWindow.structurePar != null){
			final JFileChooser fileChooser = new JFileChooser ("C:\\repositoryGit\\Salidas");
			fileChooser.setDialogTitle("Guardar parámetros de estructura de la red"); 
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {		
				File file = fileChooser.getSelectedFile();
				if (!file.toString().endsWith(".csv")){   //Añadimos la extensión, en caso de que no se la hallamos puesta
					file = new File (file + ".csv");
				}
				StructureParametersOuts classFile = new StructureParametersOuts(file.toString());
				classFile.saveStructureParameters(MainWindow.structurePar);
				JOptionPane.showMessageDialog (null,"Los parámetros de la red han sido guardados",
						"Archivo guardado", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog (null,"Error, no se ha creado ninguna red",
					"Red no creada", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void bCreateNNActionPerformed() {

		idCompany = tfIdCompany.getText();

		if ((idCompany == null) || (idCompany.equals(""))) {
			JOptionPane.showMessageDialog (null,"Error, el campo identificador de empresa está vacio",
							"Campo requerido", JOptionPane.ERROR_MESSAGE);
		} else {
			int auxInt = Integer.parseInt(idCompany);
			if ((auxInt < 1) || (auxInt > 510) || (idCompany.length() != 3)) {
				JOptionPane.showMessageDialog (null,"Error, el identificador debe de tener formato 000" + " y valores entre 0 y 510.",
						"Identificador no válido", JOptionPane.ERROR_MESSAGE);
			} else {
				numPatrones = (int) sPatrones.getValue();
				if (numPatrones == 0) {
					sPatrones.setValue(10);
					numPatrones = 10;
				}
				numNeuronES = (int) spNumNeurons.getValue();
				if (numNeuronES == 0) {
					spNumNeurons.setValue(6);
					numNeuronES = 6;
				}
				numNeuronO = (int) spNumNeuronO.getValue();
				if (numNeuronO == 0) {
					numNeuronO = numNeuronES - (numNeuronES / 2);
					spNumNeuronO.setValue(numNeuronO);
				}
				String strInicio = tfInicio.getText();
				if ((strInicio == null) || (strInicio.equals(""))) {
					tfInicio.setText("1");
					inicio = 1;
				} else {
					inicio = Integer.parseInt(strInicio);
				}

				bias = checkBox.isSelected();

				/** Creamos la red con todos los datos **/
				final PatronData manejador = new PatronData(idCompany);
				ArrayList<BigDecimal[]> inputs = new ArrayList<BigDecimal[]>();
				ArrayList<BigDecimal[]> desiredOutputs = new ArrayList<BigDecimal[]>();

				// Solapados
				if (this.comboBox_inputType.getSelectedItem().equals(
						Value.ComboBox.SOLAPADOS)) {
					inputs = manejador.createSolapadoArrayRm(numPatrones,
							inicio, Manager.CNT, numNeuronES);
					desiredOutputs = manejador.createSolapadoArrayRi(
							numPatrones, inicio, Manager.CNT,
							numNeuronES);
				}

				// No solapados
				else if (this.comboBox_inputType.getSelectedItem().equals(
						Value.ComboBox.NOSOLAPADOS)) {
					inputs = manejador.createNoSolapadoArrayRm(numPatrones,inicio, Manager.CNT, numNeuronES);
					desiredOutputs = manejador.createNoSolapadoArrayRi(numPatrones, inicio, Manager.CNT, numNeuronES);
				}

				// Aleatorios
				else if (this.comboBox_inputType.getSelectedItem().equals(
						Value.ComboBox.ALEATORIOS)) {
					ArrayList<ArrayList<Integer>> randomLists = manejador.generateRandomLists(numPatrones, numNeuronES); // It generates the list that contains randoms values equivalent to a positions in the company table				
					inputs = manejador.createRandomArrayRm(Manager.CNT, randomLists);
					desiredOutputs = manejador.createRandomArrayRi(Manager.CNT, randomLists);
				}

				// Create the Network, give it an id
				MainWindow.numInstances++;
				String name = idCompany + "_"+ this.comboBox_inputType.getSelectedItem() + MainWindow.numInstances;
				MainWindow.structurePar = new StructureParameters (name, Value.RedType.MULTICAPA, this.comboBox_inputType.getSelectedItem().toString(), numPatrones, numNeuronES, numNeuronO, inputs, desiredOutputs, bias);
				
				

				
				// Testing collecting data
				outFile = new String(directoryName+"\\"+MainWindow.getCurrentTimeStamp()+".txt");
				StructureParametersOuts resultados = new StructureParametersOuts(outFile);
				resultados.consoleOut(MainWindow.structurePar);
				// Display results
				FileReader reader;
				try {
					reader = new FileReader(outFile);

					BufferedReader br = new BufferedReader(reader);
					textPane.read(br, null);
					br.close();

					// textPane.requestFocus();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog (null,"La estructura y datos de la red con capa oculta han sido creados",
						"Estructura de red establecida", JOptionPane.PLAIN_MESSAGE);
			}// end else2
		}// end else1
	}
	

	private void btnCancelarActionPerformed() {
		MainWindow.clearTextFields(this);
	}
}

