package gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Panel;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import utilities.WeightMatrix;
import dataManager.CalculateOutputsWindowOuts;
import dataManager.ReadFile;
import dataManager.WriteExcel;
import architecture.NetworkManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CalculateOutputsWindow {

	private Panel panel;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnNewButton, btnCalcularOutputs;
	private JTextPane textPane;
	private	JScrollPane scrollPane;
	
	private WeightMatrix matrices;
	

	/**
	 * Create the frame.
	 */
	public CalculateOutputsWindow() {
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
        
        JLabel lblSeleccionaRed = new JLabel("Sistema de datos: ");
        lblSeleccionaRed.setBounds(44, 60, 99, 14);
        panel.add(lblSeleccionaRed);
        
        JLabel lblSeleccionaMatricesDe = new JLabel("Matrices de pesos:\r\n");
        lblSeleccionaMatricesDe.setBounds(44, 113, 154, 14);
        panel.add(lblSeleccionaMatricesDe);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(272, 171, 46, 14);
        panel.add(lblNewLabel);
        
        comboBox = new JComboBox();
    	for (NetworkManager ne: MainWindow.neList){ //A�adimos las instancias creadas al ComBox
    		comboBox.addItem(ne.getName());
    	}
        comboBox.setBounds(229, 57, 121, 20);
        panel.add(comboBox);
        
        btnNewButton = new JButton("Selecciona archivo");
        btnNewButton.setBounds(229, 109, 121, 23);
        panel.add(btnNewButton);
        
        btnCalcularOutputs = new JButton("Calcular Outputs");
        btnCalcularOutputs.setBounds(474, 167, 113, 23);
        panel.add(btnCalcularOutputs);
        
        
        JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(43, 392, 470, 216);
		panel.add(scrollPane);

	}

	private void createEvents() {
		 btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		 btnNewButtonActionPerformed();
	        	}
	        });
		 
		 btnCalcularOutputs.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		btnCalcularOutputsActionPerformed();
	        	}
	        });
		
	}

	private void btnNewButtonActionPerformed() {
		JFileChooser filechooser = new JFileChooser("C:\\repositoryGit\\Salidas");
		int returnValue = filechooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File filechoosen= filechooser.getSelectedFile();
			try {
				ReadFile readWM = new ReadFile(filechoosen);
				matrices = readWM.readWeightMatrix();
				lblNewLabel.setText(filechoosen.getName());
	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		} else 
			System.out.println("Open command cancelled by user.");
		
		
		
	}
	
	private void btnCalcularOutputsActionPerformed() {
		//matrices can't be null!
		NetworkManager ne = null;
		for (NetworkManager aux: MainWindow.neList){
			if (aux.getName().equals(comboBox.getSelectedItem())){
				ne = aux;
			}
		}
		//manejar excepciones
		ArrayList<BigDecimal[]> salidas = ne.calculateOutputs(matrices); //Salidas obtenidas
		//Escribir salidas en fichero y en pantalla
		//Grafico con las deseadas y las obtenidas o la variaci�n delta
		WriteExcel writer = new WriteExcel ("C:\\repositoryGit\\Salidas\\Desired_Obtained_Outputs.csv"); 
		writer.writeOuDesiredOuObtained(salidas, ne.getDesiredOutputs());
		writer.closeFile();
		
		
		// Testing collecting data
		String outFile = new String( "C:\\repositoryGit\\Salidas\\previousInformationCalculateOutputs.txt");
		CalculateOutputsWindowOuts resultados = new CalculateOutputsWindowOuts(outFile);
		resultados.results (ne.getName(), matrices, ne.getInputs(), ne.getDesiredOutputs(), salidas);
		// Display results
		FileReader reader;
		try {
			reader = new FileReader(outFile);
			BufferedReader br = new BufferedReader(reader);
			textPane.read(br, null);
			br.close();
			//br.toString()
			
		//	textPane.getText();
			
			//textPane.requestFocus();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}
}



