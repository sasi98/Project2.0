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

import javax.swing.*;

import org.apache.log4j.Logger;

import outsFiles.NewNetworkOuts;
import architecture.NetworkManager;
import dataManager.PatronData;
import valueset.Value;

public class NewSimplyNetworkWindow extends JPanel {

	/**GUI Variables*/
	private JButton 			bCreateNN, btnCancelar;
	private JTextField 			tfIdCompany, 
								tfInicio;
	private JComboBox<String> 	comboBox_inputType;
	private JSpinner 			sPatrones,
					 			spNumNeurons;
	private JTextPane 			textPane;
	private JScrollPane 		scrollPane;
	private JCheckBox 			checkBox;

	/**Data variables*/
	private String 				idCompany,
								directoryName,
								outFile; 			//Nombre del fichero en el que escribimos los inputs/outputs
	private int 				numNeuronES,
								numPatrones, 
								inicio;
	private boolean 			bias;
	
	private static Logger log = Logger.getLogger(NewNetworkWindow.class);

	/**
	 * Create the application.
	 */
	public NewSimplyNetworkWindow() {
		initialize();
		createEvents();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setBounds(6, 0, 980, 633);
		this.setLayout(null);

		final JLabel lblNewLabel = new JLabel("Identificador empresa: ");
		lblNewLabel.setBounds(31, 86, 145, 16);
		this.add(lblNewLabel);

		final JLabel lblNewLabel_2 = new JLabel("Patrones de aprendizaje: ");
		lblNewLabel_2.setBounds(386, 51, 177, 16);
		this.add(lblNewLabel_2);

		final JLabel lblNewLabel_3 = new JLabel("Neuronas de entrada/salida:");
		lblNewLabel_3.setBounds(386, 86, 215, 16);
		this.add(lblNewLabel_3);

		final JLabel lblNewLabel_7 = new JLabel("Selecci�n de datos: ");
		lblNewLabel_7.setBounds(31, 51, 156, 16);
		this.add(lblNewLabel_7);

		final JLabel lblNewLabel_8 = new JLabel("Inicio de los datos:");
		lblNewLabel_8.setBounds(31, 123, 120, 16);
		this.add(lblNewLabel_8);

		bCreateNN = new JButton("Crear red");
		bCreateNN.setBounds(734, 330, 110, 29);
		this.add(bCreateNN);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(860, 330, 110, 29);
		add(btnCancelar);

		comboBox_inputType = new JComboBox();
		comboBox_inputType.setBounds(220, 48, 110, 22);
		comboBox_inputType.addItem(Value.ComboBox.SOLAPADOS);
		comboBox_inputType.addItem(Value.ComboBox.NOSOLAPADOS);
		comboBox_inputType.addItem(Value.ComboBox.ALEATORIOS);
		comboBox_inputType.addItem(Value.ComboBox.MANUAL);
		this.add(comboBox_inputType);

		tfIdCompany = new JTextField();
		tfIdCompany.setBounds(278, 83, 52, 22);
		this.add(tfIdCompany);
		tfIdCompany.setColumns(10);

		tfInicio = new JTextField();
		tfInicio.setBounds(278, 120, 52, 22);
		this.add(tfInicio);
		tfInicio.setColumns(10);

		sPatrones = new JSpinner();
		sPatrones.setBounds(623, 48, 37, 22);
		sPatrones.setValue(5);
		this.add(sPatrones);

		spNumNeurons = new JSpinner();
		spNumNeurons.setBounds(623, 83, 37, 22);
		spNumNeurons.setValue(0);
		this.add(spNumNeurons);

		checkBox = new JCheckBox("Bias");
		checkBox.setBounds(381, 120, 57, 23);
		this.add(checkBox);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 282, 642, 184);
		this.add(scrollPane);

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
				
		//Creaci�n del directorio:
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
		bCreateNN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bCreateNNActionPerformed();
				
			}
		});		
	}


	private void bCreateNNActionPerformed() {

		// ID debe estar entre 000 y 510
		idCompany = tfIdCompany.getText();

		if ((idCompany == null) || (idCompany.equals(""))) {
			JOptionPane.showMessageDialog (null,"Error, el campo identificador de empresa est� vacio",
							"Campo requerido", JOptionPane.ERROR_MESSAGE);
		} else {
			int auxInt = Integer.parseInt(idCompany);
			if ((auxInt < 1) || (auxInt > 510) || (idCompany.length() != 3)) {
				JOptionPane.showMessageDialog (null,"Error, el identificador debe de tener formato 000" + " y valores entre 0 y 510.",
						"Identificador no v�lido", JOptionPane.ERROR_MESSAGE);
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
							inicio, NetworkManager.CNT, numNeuronES);
					desiredOutputs = manejador.createSolapadoArrayRi(
							numPatrones, inicio, NetworkManager.CNT,
							numNeuronES);
				}

				// No solapados
				else if (this.comboBox_inputType.getSelectedItem().equals(
						Value.ComboBox.NOSOLAPADOS)) {
					inputs = manejador.createNoSolapadoArrayRm(numPatrones,
							inicio, NetworkManager.CNT, numNeuronES);
					desiredOutputs = manejador.createNoSolapadoArrayRi(
							numPatrones, inicio, NetworkManager.CNT,
							numNeuronES);
				}

				// Aleatorios
				else if (this.comboBox_inputType.getSelectedItem().equals(
						Value.ComboBox.ALEATORIOS)) {
					ArrayList<ArrayList<Integer>> randomLists = manejador.generateRandomLists(numPatrones, numNeuronES); // It generates the list that contains randoms values equivalent to a positions in the company table				
					inputs = manejador.createRandomArrayRm(NetworkManager.CNT, randomLists);
					desiredOutputs = manejador.createRandomArrayRi(NetworkManager.CNT, randomLists);
				}

				// Manuales
				else {
					// open a new JFrame
				}

				// Create the Network, give it an id
				MainWindow.numInstances++;
				String name = idCompany + "_"+ this.comboBox_inputType.getSelectedItem() + MainWindow.numInstances;
				NetworkManager aux = new NetworkManager(name, numPatrones, numNeuronES, 0, inputs, desiredOutputs, bias);
				MainWindow.neList.add(aux);

				
				// Testing collecting data
				outFile = new String(directoryName+"\\"+TrainingWindow.getCurrentTimeStamp()+".txt");
				NewNetworkOuts resultados = new NewNetworkOuts(
						outFile);
				resultados.createNewNetworkingOut(idCompany, numNeuronES, 0, numPatrones, bias, this.comboBox_inputType .getSelectedItem().toString(), name, inputs, desiredOutputs);
				
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

			}// end else2
		}// end else1
	}
}