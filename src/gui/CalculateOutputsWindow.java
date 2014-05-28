package gui;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import utilities.WeightMatrix;
import dataManager.ReadFile;
import dataManager.WriteExcel;
import architecture.NetworkManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CalculateOutputsWindow {

	private JInternalFrame frame;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnNewButton, btnCalcularOutputs;
	
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
		
		frame = new JInternalFrame();
        frame.setBounds(100, 100, 737, 493);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblSeleccionaRed = new JLabel("Sistema de datos: ");
        lblSeleccionaRed.setBounds(44, 60, 99, 14);
        frame.getContentPane().add(lblSeleccionaRed);
        
        JLabel lblSeleccionaMatricesDe = new JLabel("Matrices de pesos:\r\n");
        lblSeleccionaMatricesDe.setBounds(44, 113, 154, 14);
        frame.getContentPane().add(lblSeleccionaMatricesDe);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(272, 171, 46, 14);
        frame.getContentPane().add(lblNewLabel);
        
        comboBox = new JComboBox();
    	for (NetworkManager ne: MainWindow.neList){ //Añadimos las instancias creadas al ComBox
    		comboBox.addItem(ne.getName());
    	}
        comboBox.setBounds(229, 57, 121, 20);
        frame.getContentPane().add(comboBox);
        
        btnNewButton = new JButton("Selecciona archivo");
        btnNewButton.setBounds(229, 109, 121, 23);
        frame.getContentPane().add(btnNewButton);
        
        btnCalcularOutputs = new JButton("Calcular Outputs");
        btnCalcularOutputs.setBounds(474, 167, 113, 23);
        frame.getContentPane().add(btnCalcularOutputs);
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
		//Grafico con las deseadas y las obtenidas o la variación delta
		WriteExcel writer = new WriteExcel ("C:\\repositoryGit\\Salidas\\Desired_Obtained_Outputs.csv"); 
		writer.writeOuDesiredOuObtained(salidas, ne.getDesiredOutputs());
		writer.closeFile();
	}

	public JInternalFrame getFrame() {
		return frame;
	}

	public void setFrame(JInternalFrame frame) {
		this.frame = frame;
	}

	
}



