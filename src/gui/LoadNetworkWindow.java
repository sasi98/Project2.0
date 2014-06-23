package gui;

import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JEditorPane;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JButton;

import architecture.Manager;
import architecture.StructureParameters;
import architecture.TrainingParameters;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import javax.swing.JTextPane;

import outsFiles.StructureParametersOuts;
import valueset.Value;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

import jxl.read.biff.File;
import loadFiles.StructureParametersLoad;
import javax.swing.JTextField;

public class LoadNetworkWindow extends JFrame {
	
	private StructureParameters currentStructurePar; //Red actual cargada
	private JButton 				btnAbrir,
									btnAceptar;
	
	private JLabel 					label_4,
									label_5,
									label_6,
									label_7,
									label_8;
	
	
	private java.io.File 			lastVisitedDirectory;
	private JTextField textField;
	

	public LoadNetworkWindow() {
		setTitle("Cargar estructura de red");
		initialize();
		createEvents();
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		lastVisitedDirectory = new java.io.File("C:\\repositoryGit\\Salidas");
		
		this.setBounds(MainWindow.LOAD_JFRAME_MEASURES);
		this.getContentPane().setBounds(MainWindow.LOAD_JFRAME_MEASURES);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 156, 331, 168);
		getContentPane().add(panel);
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estructura de red", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
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
		
		label_4 = new JLabel("");
		label_4.setBounds(155, 28, 166, 14);
		panel.add(label_4);
		
		label_5 = new JLabel("");
		label_5.setBounds(155, 53, 166, 14);
		panel.add(label_5);
		
		label_6 = new JLabel("");
		label_6.setBounds(155, 78, 166, 14);
		panel.add(label_6);
		
		label_7 = new JLabel("");
		label_7.setBounds(155, 103, 166, 14);
		panel.add(label_7);
		
		label_8 = new JLabel("");
		label_8.setBounds(155, 128, 166, 14);
		panel.add(label_8);
		
		JLabel lblBias = new JLabel("Bias: ");
		lblBias.setBounds(20, 128, 46, 14);
		panel.add(lblBias);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(236, 373, 90, 30);
		getContentPane().add(btnCancelar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(46, 373, 90, 30);
		getContentPane().add(btnAceptar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selecci\u00F3n de fichero", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 25, 331, 95);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(139, 41, 183, 20);
		panel_2.add(textField);
		textField.setColumns(10);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(38, 40, 67, 23);
		panel_2.add(btnAbrir);
		
	}
	
	private void createEvents() {
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAbrirActionPerformed();
			}
		});
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  btnAceptarActionPerformed();
			}
		});
	}
	
	private void btnAbrirActionPerformed() {
		final JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(lastVisitedDirectory);
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final java.io.File filechoosen = filechooser.getSelectedFile();
			try {
				StructureParametersLoad reader = new StructureParametersLoad(filechoosen.getAbsolutePath());
				currentStructurePar = reader.loadStructureParameters();
				showStructureInformation();
				textField.setText(filechoosen.getName());
			
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastVisitedDirectory = filechooser.getCurrentDirectory();
		}
	}
	
	//La red cargada pasa a ser la que estamos usando en la aplicación
	private void btnAceptarActionPerformed() {
		if (currentStructurePar != null){
			MainWindow.structurePar = currentStructurePar;
			MainWindow.updateInformationsPanels();
			JOptionPane.showMessageDialog (null,"La estructura de red "+ currentStructurePar.getName()+ " ha sido seleccionada. ",
					"Red seleccionada", JOptionPane.PLAIN_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog (null,"No se ha cargado ninguna estructura de red",
					"Red no seleccionada", JOptionPane.ERROR_MESSAGE);	
		}
		
	}
	
	private void showStructureInformation() {
		if (currentStructurePar != null){
			label_4.setText(currentStructurePar.getName());
			label_5.setText(currentStructurePar.getTypeNet());
			label_7.setText(Integer.toString(currentStructurePar.getNumPatterns()));
			if (currentStructurePar.getTypeNet().equals(Value.RedType.MONOCAPA))
				label_6.setText(Integer.toString(currentStructurePar.getNumNeuronsE())+ " X "+ Integer.toString(currentStructurePar.getNumNeuronsS()));
			else
				label_6.setText(Integer.toString(currentStructurePar.getNumNeuronsE())+ " X "+ Integer.toString(currentStructurePar.getNumNeuronsO())+" X "+Integer.toString(currentStructurePar.getNumNeuronsS()));
			if (currentStructurePar.hasBias()){
				label_8.setText("Sí");
			}
			else{
				label_8.setText("No");
				
			}
		}
		else{
			label_4.setText("");
			label_5.setText("");
			label_7.setText("");
			label_6.setText("");
		}
		  
	}
}



