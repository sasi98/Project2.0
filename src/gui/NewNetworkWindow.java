package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import valueset.Value;
import architecture.NetworkManager;
import dataManager.PatronData;
import dataManager.WriteExcel;


public class NewNetworkWindow {

	//Variables GUI
    private JInternalFrame frame;
    private JButton bCreateNN;
    private JTextField tfIdCompany, tfInicio;
    private JComboBox<String> comboBox_inputType;
    private JSpinner sPatrones, spNumNeuronO, spNumNeurons;
    private JTextPane textPane; 

    //Variables internas
    private String idCompany;
    private int numNeuronES, numNeuronO, numPatrones, inicio;
    private String outFile; //Fichero en el que escribiremos los inputs/outputs 

   

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
    	
        frame = new JInternalFrame();
        frame.setBounds(100, 100, 737, 493);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        final JLabel lblNewLabel = new JLabel("Identificador empresa: ");
        lblNewLabel.setBounds(10, 45, 174, 14);
        frame.getContentPane().add(lblNewLabel);

        final JLabel lblNewLabel_2 = new JLabel("N\u00BA Patrones de aprendizaje: ");
        lblNewLabel_2.setBounds(10, 229, 174, 14);
        frame.getContentPane().add(lblNewLabel_2);

        final JLabel lblNewLabel_3 = new JLabel("N\u00BA de neuronas de entrada/salida:");
        lblNewLabel_3.setBounds(10, 142, 174, 14);
        frame.getContentPane().add(lblNewLabel_3);

        final JLabel lblNewLabel_4 = new JLabel("N\u00BA de neuronas ocultas:");
        lblNewLabel_4.setBounds(10, 167, 174, 14);
        frame.getContentPane().add(lblNewLabel_4);
        
        final JLabel lblNewLabel_7 = new JLabel("Selección de datos: ");
        lblNewLabel_7.setBounds(10, 78, 97, 14);
        frame.getContentPane().add(lblNewLabel_7);

        final JLabel lblNewLabel_8 = new JLabel("Inicio de los datos:");
        lblNewLabel_8.setBounds(295, 78, 136, 14);
        frame.getContentPane().add(lblNewLabel_8);

        bCreateNN = new JButton("Crear Red Neuronal");
        bCreateNN.setBounds(336, 237, 136, 23);
        frame.getContentPane().add(bCreateNN);

        comboBox_inputType = new JComboBox();
        comboBox_inputType.addItem(Value.ComboBox.SOLAPADOS);
        comboBox_inputType.addItem(Value.ComboBox.NOSOLAPADOS);
        comboBox_inputType.addItem(Value.ComboBox.ALEATORIOS);
        comboBox_inputType.addItem(Value.ComboBox.MANUAL);
        comboBox_inputType.setBounds(185, 75, 85, 20);
        frame.getContentPane().add(comboBox_inputType);
        
        tfIdCompany = new JTextField();
        tfIdCompany.setBounds(214, 42, 36, 20);
        frame.getContentPane().add(tfIdCompany);
        tfIdCompany.setColumns(10);
        
        tfInicio = new JTextField();
        tfInicio.setBounds(400, 75, 23, 20);
        frame.getContentPane().add(tfInicio);
        tfInicio.setColumns(10);
        
        sPatrones = new JSpinner();
        sPatrones.setValue(5);
        sPatrones.setBounds(226, 226, 44, 20);
        frame.getContentPane().add(sPatrones);
        
        spNumNeuronO = new JSpinner();
        spNumNeuronO.setBounds(226, 164, 44, 20);
        spNumNeuronO.setValue(0);
        frame.getContentPane().add(spNumNeuronO);
        
        spNumNeurons = new JSpinner();
        spNumNeurons.setBounds(226, 139, 44, 20);
        spNumNeurons.setValue(0);
        frame.getContentPane().add(spNumNeurons);
        
        JCheckBox checkBox = new JCheckBox("Bias");
        checkBox.setBounds(10, 262, 45, 23);
        frame.getContentPane().add(checkBox);
        
        textPane = new JTextPane();
        //textPane.setEditable(false);
        textPane.setBounds(10, 292, 701, 161);
        frame.getContentPane().add(textPane);
    }

    
    public JInternalFrame getFrame() {
		return frame;
	}

	public void setFrame(JInternalFrame frame) {
		this.frame = frame;
	}

	
	private void bCreateNNActionPerformed() {
    	
    	//ID debe estar entre 000 y 510
        idCompany = tfIdCompany.getText();
    
        if ( (idCompany == null) || (idCompany.equals("")) )
        	JOptionPane.showMessageDialog(null, "Error, el campo identificador de empresa está vacio", 
        			"Campo requerido", JOptionPane.ERROR_MESSAGE);
        else {
        	int auxInt = Integer.parseInt(idCompany);
        	if ( (auxInt < 1) || (auxInt > 510) || (idCompany.length() != 3)) 
        		JOptionPane.showMessageDialog(null, "Error, el identificador debe de tener formato 000"
        				+ " y valores entre 0 y 510.", "Identificador no válido", JOptionPane.ERROR_MESSAGE);
        	else{
	        	numPatrones = (int) sPatrones.getValue();
	        	if (numPatrones == 0){
	        		sPatrones.setValue(10);
	        		numPatrones = 10;
	        	}
	        	numNeuronES = (int) spNumNeurons.getValue();
	        	if (numNeuronES == 0){
	        		spNumNeurons.setValue(6);
	        		numNeuronES = 6;
	        	}
	        	numNeuronO = (int) spNumNeuronO.getValue();
	        	if (numNeuronO == 0){
	        		numNeuronO = numNeuronES - (numNeuronES / 2);
	        		spNumNeuronO.setValue(numNeuronO);
	        	}
	        	String strInicio = tfInicio.getText();
	            if ((strInicio == null) || (strInicio.equals(""))) {
	                tfInicio.setText("1");
	                inicio = 1;
	            } 
	            else 
	                inicio = Integer.parseInt(strInicio);
	
		        /** Creamos la red con todos los datos **/
		        final PatronData manejador = new PatronData(idCompany);
		        ArrayList<BigDecimal[]> inputs = new ArrayList<BigDecimal[]>();
		        ArrayList<BigDecimal[]> desiredOutputs = new ArrayList<BigDecimal[]>();
		       
		        //Solapados
		        if (this.comboBox_inputType.getSelectedItem().equals(Value.ComboBox.SOLAPADOS)) {
		            inputs = manejador.createSolapadoArrayRm(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
		            desiredOutputs = manejador.createSolapadoArrayRi(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
		        }
		
		        //No solapados
		        else if (this.comboBox_inputType.getSelectedItem().equals(Value.ComboBox.NOSOLAPADOS)) {
		            inputs = manejador.createNoSolapadoArrayRm(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
		            desiredOutputs = manejador.createNoSolapadoArrayRi(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
		        }
		
		        //Aleatorios
		        else if (this.comboBox_inputType.getSelectedItem().equals(Value.ComboBox.ALEATORIOS)) {
		        	ArrayList<ArrayList<Integer>> randomLists = manejador.generateRandomLists(numPatrones, numNeuronES); //It generates the list that contains randoms values equivalent to a positions in the company table
		        	inputs = manejador.createRandomArrayRm(NetworkManager.CNT, randomLists);
		        	desiredOutputs = manejador.createRandomArrayRi(NetworkManager.CNT, randomLists);
		        }
		
		        //Manuales
		        else {
		            //open a new JFrame
		        }
		
		        //Testing collecting data
		        outFile = new String ("C:\\repositoryGit\\Salidas\\ChosenPatrons.csv");
		        WriteExcel patrones = new WriteExcel(outFile);
		        patrones.writeInputsOutputs(inputs, desiredOutputs);
		        patrones.closeFile();
		        
		        //Display results
		        FileReader reader;
				try {
					reader = new FileReader(outFile);
					 BufferedReader br = new BufferedReader(reader);
				        textPane.read( br, null );
				        br.close();
				       // textPane.requestFocus();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		        
	        
        
        //Create and train the Network
//        final NetworkManager ne = new NetworkManager(numPatrones, numNeuronES, numNeuronO, iteractionMax, cotaError, 
//        		learningCnt, inputs, desiredOutputs, bias);
//        ne.training();

	  
        	}//end else2
        }//end else1
    }



	private void createEvents() {
		bCreateNN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                bCreateNNActionPerformed();
            }
        });
	
	}
}