package gui;

import java.awt.Panel;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;

import valueset.Value;
import architecture.NetworkManager;
import dataManager.NewNetworkWindowOuts;
import dataManager.PatronData;

public class NewNetworkWindow {

	// Variables GUI
	private Panel panel;
	private JButton bCreateNN;
	private JTextField tfIdCompany, tfInicio;
	private JComboBox<String> comboBox_inputType;
	private JSpinner sPatrones, spNumNeuronO, spNumNeurons;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JCheckBox checkBox;

	// Variables internas
	private String idCompany;
	private int numNeuronES, numNeuronO, numPatrones, inicio;
	private String directoryName;
	private String outFile; // Fichero en el que escribiremos los inputs/outputs
	private boolean bias;
	private static Logger log = Logger.getLogger(NewNetworkWindow.class);

	/**
	 * Create the application.
	 */
	public NewNetworkWindow() {
		initialize();
		createEvents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		panel = new Panel();

		panel.setBounds(6, 0, 980, 633);

		panel.setLayout(null);

		final JLabel lblNewLabel = new JLabel("Identificador empresa: ");
		lblNewLabel.setBounds(40, 86, 145, 16);
		panel.add(lblNewLabel);

		final JLabel lblNewLabel_2 = new JLabel(
				"N\u00BA Patrones de aprendizaje: ");
		lblNewLabel_2.setBounds(399, 168, 177, 16);
		panel.add(lblNewLabel_2);

		final JLabel lblNewLabel_3 = new JLabel(
				"N\u00BA de neuronas de entrada/salida:");
		lblNewLabel_3.setBounds(398, 86, 215, 16);
		panel.add(lblNewLabel_3);

		final JLabel lblNewLabel_4 = new JLabel("N\u00BA de neuronas ocultas:");
		lblNewLabel_4.setBounds(400, 125, 151, 16);
		panel.add(lblNewLabel_4);

		final JLabel lblNewLabel_7 = new JLabel("Selecciï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½n de datos: ");
		lblNewLabel_7.setBounds(40, 125, 156, 16);
		panel.add(lblNewLabel_7);

		final JLabel lblNewLabel_8 = new JLabel("Inicio de los datos:");
		lblNewLabel_8.setBounds(40, 168, 120, 16);
		panel.add(lblNewLabel_8);

		bCreateNN = new JButton("Crear Red Neuronal");
		bCreateNN.setBounds(40, 241, 165, 29);
		panel.add(bCreateNN);

		comboBox_inputType = new JComboBox();
		comboBox_inputType.setBounds(205, 120, 110, 22);
		comboBox_inputType.addItem(Value.ComboBox.SOLAPADOS);
		comboBox_inputType.addItem(Value.ComboBox.NOSOLAPADOS);
		comboBox_inputType.addItem(Value.ComboBox.ALEATORIOS);
		comboBox_inputType.addItem(Value.ComboBox.MANUAL);
		panel.add(comboBox_inputType);

		tfIdCompany = new JTextField();
		tfIdCompany.setBounds(263, 83, 52, 22);
		panel.add(tfIdCompany);
		tfIdCompany.setColumns(10);

		tfInicio = new JTextField();
		tfInicio.setBounds(263, 162, 52, 22);
		panel.add(tfInicio);
		tfInicio.setColumns(10);

		sPatrones = new JSpinner();
		sPatrones.setBounds(623, 165, 37, 22);
		sPatrones.setValue(5);
		panel.add(sPatrones);

		spNumNeuronO = new JSpinner();
		spNumNeuronO.setBounds(623, 122, 37, 22);
		spNumNeuronO.setValue(0);
		panel.add(spNumNeuronO);

		spNumNeurons = new JSpinner();
		spNumNeurons.setBounds(623, 83, 37, 22);
		spNumNeurons.setValue(0);
		panel.add(spNumNeurons);

		checkBox = new JCheckBox("Bias");
		checkBox.setBounds(400, 212, 57, 23);
		panel.add(checkBox);
		// textPane.setEditable(false);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 282, 642, 184);
		panel.add(scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		
		//Creación del directorio:
		directoryName = "C:\\repositoryGit\\Salidas\\NewNetworkOuts";
		File directory = new File(directoryName);
		try{
			boolean creado = directory.mkdir();
			System.out.print("creado: "+ creado+"\n");
		}
		catch(SecurityException se){
			log.error("El directorio "+ directoryName + "no ha podido ser creado");
		}
		System.out.print (directoryName);
		

	}

	private void createEvents() {
		bCreateNN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				bCreateNNActionPerformed();
			}
		});
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	private void bCreateNNActionPerformed() {

		// ID debe estar entre 000 y 510
		idCompany = tfIdCompany.getText();

		if ((idCompany == null) || (idCompany.equals(""))) {
			JOptionPane
					.showMessageDialog(
							null,
							"Error, el campo identificador de empresa está vacio",
							"Campo requerido", JOptionPane.ERROR_MESSAGE);
		} else {
			int auxInt = Integer.parseInt(idCompany);
			if ((auxInt < 1) || (auxInt > 510) || (idCompany.length() != 3)) {
				JOptionPane.showMessageDialog(null,
						"Error, el identificador debe de tener formato 000"
								+ " y valores entre 0 y 510.",
						"Identificador no válido",
						JOptionPane.ERROR_MESSAGE);
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
					ArrayList<ArrayList<Integer>> randomLists = manejador
							.generateRandomLists(numPatrones, numNeuronES); // It
																			// generates
																			// the
																			// list
																			// that
																			// contains
																			// randoms
																			// values
																			// equivalent
																			// to
																			// a
																			// positions
																			// in
																			// the
																			// company
																			// table
					inputs = manejador.createRandomArrayRm(NetworkManager.CNT,
							randomLists);
					desiredOutputs = manejador.createRandomArrayRi(
							NetworkManager.CNT, randomLists);
				}

				// Manuales
				else {
					// open a new JFrame
				}

				// Create the Network, give it an identificator (we name the
				// Network)
				MainWindow.numInstances++;
				String name = idCompany + "_"
						+ this.comboBox_inputType.getSelectedItem()
						+ MainWindow.numInstances;
				NetworkManager aux = new NetworkManager(name, numPatrones,
						numNeuronES, numNeuronO, inputs, desiredOutputs, bias);
				MainWindow.neList.add(aux);

				System.out.print("Current number of instances: "
						+ MainWindow.neList.size());

				// Testing collecting data
				outFile = new String(directoryName+"\\"+TrainingWindow.getCurrentTimeStamp()+".txt");
				NewNetworkWindowOuts resultados = new NewNetworkWindowOuts(
						outFile);
				resultados.createNewNetworkingOut(idCompany, numNeuronES,
						numNeuronO, numPatrones, bias, this.comboBox_inputType
								.getSelectedItem().toString(), name, inputs,
						desiredOutputs);
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