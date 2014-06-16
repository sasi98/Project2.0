package gui;

import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

import jxl.read.biff.File;
import loadFiles.StructureParametersLoad;

public class LoadNetworkWindow extends JPanel {
	
	private StructureParameters redShowed;
	
	
	
	//GUI variables
	private JComboBox				 comboBox;
	private JButton 				 btnAbrir,
									 btnAceptar;
	private JTextPane				 textPane;

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
		btnAceptar.setBounds(124, 412, 90, 30);
		add(btnAceptar);
		
		textPane = new JTextPane();
		textPane.setBounds(652, 50, 290, 367);
		textPane.setEditable (false);
		add(textPane);
		textPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Informaci\u00F3n general", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(307, 412, 90, 30);
		add(btnCancelar);
		
	}
	
	private void createEvents() {
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAbrirActionPerformed();
			}
		});
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   showGeneralInformation();
			}
		});
	}
	
	private void btnAbrirActionPerformed() {
		final JFileChooser filechooser = new JFileChooser ("C:\\repositoryGit\\Salidas");
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final java.io.File filechoosen = filechooser.getSelectedFile();
			try {
				StructureParametersLoad reader = new StructureParametersLoad(filechoosen.getAbsolutePath());
				redShowed = reader.loadStructureParameters();
				MainWindow.structurePar = redShowed;
				//Mostrar redShow in right window//				
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void showGeneralInformation() {

		  redShowed = new StructureParameters("testData", "testData", "testData",
		    123, 123, 123, new ArrayList<BigDecimal[]>(),
		    new ArrayList<BigDecimal[]>(), true);
		  textPane.setText(this.redShowed.toString());
		 }
	
	
	
	
}
