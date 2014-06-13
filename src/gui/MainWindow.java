
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import architecture.NetworkManager;
import architecture.StructureParameters;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * @author ESTHER_GC
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Lista con las actuales estructuras de red creadas
	public static ArrayList<StructureParameters> structureCreatedList = new ArrayList<>();
	public static int numInstances = 0; 
	public static boolean cancelTraining = false;
	public static JDesktopPane desktopPane;
	public static ArrayList<JPanel> createdWindows = new ArrayList<>();

	// Variables GUI
	private JFrame frame;
	private JMenuBar menuBar;
	private JToggleButton tglbtnTrazas;
	private JMenuItem mntmCrearRedSimple,
					  mntmCrearRedCon,
					  mntmCargarRedExistente,
					  mntmEstablecerParametros,
					  mntmEntrenarRed;

	// JPanel classes
	private NewSimplyNetworkWindow newSimplyNet;
	private NewHiddenNetworkWindow newHiddenNet;
	private LoadNetworkWindow loadNetwork;
	private SetUpParametersTrainWindow sepUpParameters;
	private TrainingWindow trainingWindow;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final MainWindow window = new MainWindow();
				window.frame.setVisible(true);
			}
		});
	}

	/** Creates new form MainWindow */
	public MainWindow() {
		PropertyConfigurator.configure("files" + File.separator+ "log4j.properties");
		initialize();
		createEvents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.white);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 21, 984, 491);
		desktopPane.setBackground(Color.WHITE);
		//desktopPane.setOpaque(false);
		frame.getContentPane().add(desktopPane);
	
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 984, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnArquitecturaRed = new JMenu("Arquitectura de red\r\n");
		menuBar.add(mnArquitecturaRed);
		
		mntmCrearRedSimple = new JMenuItem("Crear red simple");
		mnArquitecturaRed.add(mntmCrearRedSimple);
		
		mntmCrearRedCon = new JMenuItem("Crear red con capa oculta");
		mnArquitecturaRed.add(mntmCrearRedCon);
		
		mntmCargarRedExistente = new JMenuItem("Cargar red existente");
		mnArquitecturaRed.add(mntmCargarRedExistente);
		
		
		JMenu mnEntrenamiento = new JMenu("Entrenamiento");
		menuBar.add(mnEntrenamiento);
		
		mntmEstablecerParametros = new JMenuItem("Establecer par\u00E1metros");
		mnEntrenamiento.add(mntmEstablecerParametros);
		
		mntmEntrenarRed = new JMenuItem("Entrenar red");
		
		mnEntrenamiento.add(mntmEntrenarRed);
		
		JMenu mnResultados = new JMenu("Resultados");
		menuBar.add(mnResultados);
		
		JMenu mnAcercaDe = new JMenu("Acerca de ");
		menuBar.add(mnAcercaDe);
								
												
		tglbtnTrazas = new JToggleButton("Desactivar trazas");
		tglbtnTrazas.setBounds(855, 29, 123, 21);
		desktopPane.add(tglbtnTrazas);

	}

	private void createEvents() {
		
		mntmCrearRedSimple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmCrearRedSimpleActionPerformed();
			}
		});
		mntmCrearRedCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmCrearRedConActionPerformed();
			}
		});
		mntmCargarRedExistente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmCargarRedExistenteActionPerformed();
			}
		});
		mntmEstablecerParametros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmEstablecerParametrosActionPerformed();
			}

		});
		mntmEntrenarRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmEntrenarRedActionPerformed();
			}
			
		});
		tglbtnTrazas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				tglbtnTrazasActionPerformed(ev);
			}
		});

	}
	
	private void mntmCrearRedSimpleActionPerformed(){
		hideWindows();
		if (newSimplyNet == null) {
			newSimplyNet = new NewSimplyNetworkWindow();
			newSimplyNet.setBounds(0, 0, 984, 491);
			desktopPane.add(newSimplyNet, BorderLayout.CENTER);
			newSimplyNet.show();
			createdWindows.add(newSimplyNet);
		}
		else{
			newSimplyNet.show();
		}
	}
	
	private void mntmCrearRedConActionPerformed() {
		hideWindows();
		if (newHiddenNet == null) {
			newHiddenNet = new NewHiddenNetworkWindow();
			newHiddenNet.setBounds(new Rectangle(0, 0, 984, 491));
			desktopPane.add(newHiddenNet, BorderLayout.CENTER);
			newHiddenNet.show();
			createdWindows.add(newHiddenNet);
		}
		else{
			newHiddenNet.show();
		}
	}
	
	private void mntmCargarRedExistenteActionPerformed() {
		hideWindows();
		if (loadNetwork == null) {
			loadNetwork = new LoadNetworkWindow();
			loadNetwork.setBounds(new Rectangle(0, 0, 984, 491));
			desktopPane.add(loadNetwork, BorderLayout.CENTER);
			loadNetwork.show();
			createdWindows.add(loadNetwork);
		}
		else{
			loadNetwork.show();
		}
	}
	
	private void mntmEstablecerParametrosActionPerformed() {
		hideWindows();
		if (sepUpParameters == null) {
			sepUpParameters = new SetUpParametersTrainWindow();
			sepUpParameters.setBounds(new Rectangle(0, 0, 984, 491));
			desktopPane.add(sepUpParameters, BorderLayout.CENTER);
			sepUpParameters.show();
			createdWindows.add(sepUpParameters);
		}
		else{
			sepUpParameters.show();
		}
	}
	
	private void mntmEntrenarRedActionPerformed() {
		hideWindows();
		if (trainingWindow == null) {
			trainingWindow = new TrainingWindow();
			trainingWindow.setBounds(new Rectangle(0, 0, 984, 491));
			desktopPane.add(trainingWindow, BorderLayout.CENTER);
			trainingWindow.show();
			createdWindows.add(trainingWindow);
		}
		else{
			trainingWindow.show();
		}
	}
	
	private void hideWindows(){
		for (JPanel panel: createdWindows){
			panel.hide();
		}	
	}


	// Switch button: Si las trazas estaban activas, las desactiva (delete all
	// the loggers)
	// Si las trazas estaban desactivadas, las activa SOBREESCRIBIENDO el
	// fichero de traces.log

	public void tglbtnTrazasActionPerformed(ItemEvent ev) {
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			tglbtnTrazas.setText("Activar trazas");
			ArrayList<Logger> loggers = Collections.<Logger> list(LogManager.getCurrentLoggers());
			loggers.add(LogManager.getRootLogger());
			for (Logger logger : loggers) {
				logger.setLevel(Level.OFF);
			}
		} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
			tglbtnTrazas.setText("Desactivar trazas");
			PropertyConfigurator.configure("files" + File.separator + "log4j.properties"); // Sobreescribimos fichero
			ArrayList<Logger> loggers = Collections.<Logger> list(LogManager.getCurrentLoggers());
			loggers.add(LogManager.getRootLogger());
			for (Logger logger : loggers) {
				logger.setLevel(Level.ALL);
			}
		}

	}

	public static void clearTextFields (Container container) {
		for (Component c : container.getComponents()) {
			if ((c instanceof JTextField) && !(c instanceof JSpinner)) {
				System.out.print("Tipo de componente: " + c.getName() + "\n");
				JTextField f = (JTextField) c;
				f.setText("");
				// if (c instanceof JSpinner){
				// JSpinner f1 = (JSpinner) c;
				// f1.setValue(0);
				// System.out.print("Entra aqu���");
				// }
			} else if (c instanceof Container) {
				clearTextFields((Container) c);
			}
		}
	}
}
