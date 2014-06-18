package gui;

import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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

public class LoadNetworkWindow extends JPanel {
	
	private StructureParameters currentStructurePar; //Red actual cargada
	
	
	
	//GUI variables
	private JComboBox				 comboBox;
	private JButton 				 btnAbrir,
									 btnAceptar;
	
	private JLabel 					label_4,
									label_5,
									label_6,
									label_7,
									label_8;

	public LoadNetworkWindow() {
		initialize();
		createEvents();
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setBounds(MainWindow.JPANEL_MEASURES);
		this.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selecci\u00F3n de red", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.setBounds(93, 106, 342, 195);
		this.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panelSeleccion = new JPanel();
		panelSeleccion.setBounds(6, 16, 325, 172);
		panelSeleccion.setLayout(null);
		panel_1.add(panelSeleccion);
		
		final JLabel lblNewLabel = new JLabel("Existente");
		lblNewLabel.setBounds(30, 37, 97, 24);
		panelSeleccion.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(182, 37, 97, 24);
		for (StructureParameters ne: MainWindow.structureCreatedList) {
			comboBox.addItem(ne.getName());
		}
		panelSeleccion.add(comboBox);
		
		JLabel lblDeArchivo = new JLabel("De archivo");
		panelSeleccion.add(lblDeArchivo);
		lblDeArchivo.setBounds(30, 92, 97, 24);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(182, 93, 97, 23);
		panelSeleccion.add(btnAbrir);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(478, 111, 90, 30);
		add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(513, 208, 90, 30);
		add(btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estructura de red", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(662, 106, 204, 168);
		add(panel);
		
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
		
		JLabel lblBias = new JLabel("Bias: ");
		lblBias.setBounds(20, 128, 46, 14);
		panel.add(lblBias);
		
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
		final JFileChooser filechooser = new JFileChooser ("C:\\repositoryGit\\Salidas");
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final java.io.File filechoosen = filechooser.getSelectedFile();
			try {
				StructureParametersLoad reader = new StructureParametersLoad(filechoosen.getAbsolutePath());
				currentStructurePar = reader.loadStructureParameters();
				showStructureInformation();
				 currentStructurePar.print();
				//Mostrar redShow in right window//				
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//La red cargada pasa a ser la que estamos usando en la aplicación
	private void btnAceptarActionPerformed() {
		if (currentStructurePar != null){
			MainWindow.structurePar = currentStructurePar;
			MainWindow.structurePar.print();
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



