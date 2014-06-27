
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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

import utilities.Matrix;
import utilities.WeightMatrix;
import valueset.Value;

import com.petersoft.advancedswing.onoffbutton.OnOffButton;

import architecture.Manager;
import architecture.StructureParameters;
import architecture.TrainingParameters;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * @author ESTHER_GC
 */
public class MainWindow extends JFrame {

	
	public static final Rectangle JPANEL_MEASURES = new Rectangle(0, 0, 984, 491);
	public static final Rectangle LOAD_JFRAME_MEASURES = new Rectangle(240,170, 370, 470);
	public static final Rectangle SETUP_JFRAME_MEASURES = new Rectangle(240,170, 430, 460);
	public static final Rectangle DESKTOP_PANEL_MEASURES = new Rectangle(0, 21, 984, 491);
	public static final Dimension BUTTON_SIZE = new Dimension(110, 30);
	
	
	// Lista con las actuales estructuras de red creadas
	public static ArrayList<StructureParameters> structureCreatedList = new ArrayList<>();
	public static int numInstances = 0; 
	public static boolean cancelTraining = false;
	public static JDesktopPane desktopPane;
	public static ArrayList<JPanel> createdWindows = new ArrayList<>();
	public static StructureParameters structurePar; /**Estructura utilizada a lo largo de toda la GUI de la red*/
	public static TrainingParameters trainPar;    /**Parámetros utilizados en el entrenamiento de la red*/
	
	

	// Variables GUI
	private JFrame frame;
	private JMenuBar menuBar;
	private JToggleButton tglbtnTrazas;
	private JMenuItem mntmCrearRedSimple,
					  mntmCrearRedCon,
					  mntmCargarRedExistente,
					  mntmEstablecerParametros,
					  mntmEntrenarRed, 
					  mntmCalcularSalidas;

	// JPanel/JFrames classes
	public static NewSimplyNetworkWindow newSimplyNet;
	public static NewHiddenNetworkWindow newHiddenNet;
	public static LoadNetworkWindow loadNetwork;
	public static SetUpParametersTrainWindow setUpParameters;
	public static TrainingWindow trainingWindow;
	public static CalculateOutputsWindow calculateOutputs;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
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
		frame.setTitle("BackPropagation - Neuronal Network");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.white);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(DESKTOP_PANEL_MEASURES);
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
		
		mntmCalcularSalidas = new JMenuItem("Calcular salidas");
		mnResultados.add(mntmCalcularSalidas);
		
		JMenu mnAcercaDe = new JMenu("Acerca de ");
		menuBar.add(mnAcercaDe);
								
												
		tglbtnTrazas = new JToggleButton("Desactivar trazas");
		tglbtnTrazas.setBounds(875, 11, 99, 21);
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
		mntmCalcularSalidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmCalcularSalidasActionPerformed();
			}
		});

	}
	
	private void mntmCrearRedSimpleActionPerformed(){
		hideWindows();
		if (newSimplyNet == null) {
			newSimplyNet = new NewSimplyNetworkWindow();
			newSimplyNet.setBounds(JPANEL_MEASURES);
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
			newHiddenNet.setBounds(JPANEL_MEASURES);
			desktopPane.add(newHiddenNet, BorderLayout.CENTER);
			newHiddenNet.show();
			createdWindows.add(newHiddenNet);
		}
		else{
			newHiddenNet.show();
		}
	}
	
	private void mntmCargarRedExistenteActionPerformed() {
		if (loadNetwork == null) {
			loadNetwork = new LoadNetworkWindow();
			loadNetwork.setVisible(true);
		}
		else{
			loadNetwork.setVisible(true);
		}
	}
	
	private void mntmEstablecerParametrosActionPerformed() {
		if (setUpParameters == null) {
			setUpParameters = new SetUpParametersTrainWindow();
			setUpParameters.setVisible(true);
		}
		else{
			setUpParameters.setVisible(true);
		}
	}
	
	private void mntmEntrenarRedActionPerformed() {
		hideWindows();
		if (trainingWindow == null) {
			trainingWindow = new TrainingWindow();
			trainingWindow.setBounds(JPANEL_MEASURES);
			desktopPane.add(trainingWindow, BorderLayout.CENTER);
			trainingWindow.show();
			createdWindows.add(trainingWindow);
		}
		else{
			trainingWindow.show();
		}
	}
	
	private void mntmCalcularSalidasActionPerformed() {
		hideWindows();
		if (calculateOutputs == null) {
			calculateOutputs = new CalculateOutputsWindow();
			calculateOutputs.setBounds(JPANEL_MEASURES);
			desktopPane.add(calculateOutputs, BorderLayout.CENTER);
			calculateOutputs.show();
			createdWindows.add(calculateOutputs);
		}
		else{
			calculateOutputs.show();
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
				// System.out.print("Entra aquï¿½ï¿½ï¿½");
				// }
			} else if (c instanceof Container) {
				clearTextFields((Container) c);
			}
		}
	}
	
	public static String getCurrentTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	    Date now = new Date();
	    String strDate = dateFormat.format(now);
	    return strDate;
	}
	
	public static boolean MatrixSuitStructure (StructureParameters structurePar, WeightMatrix matrices){
		boolean suit = false;
		Matrix W = matrices.getW();
		if (matrices.getW() != null){
			int WCol = W.getColumn(), 
				WRow = W.getRow(),
				nE = structurePar.getNumNeuronsE(),
				nS = structurePar.getNumNeuronsS();
			if (structurePar.getTypeNet().equals(Value.RedType.MONOCAPA) && (matrices.getV() == null)){
				if ( (nS == WRow) && (nE == WCol) )
					suit = true;
			}else if (structurePar.getTypeNet().equals(Value.RedType.MULTICAPA) && (matrices.getV() != null)){
				Matrix V = matrices.getV();
				int VCol = V.getColumn(), 
					VRow = V.getRow(),
					nO = structurePar.getNumNeuronsO();
				if ((nO == WRow) && (nE == WCol) && (nS == VRow) && (nO == VCol))
					suit = true;
			}
		}
		return suit;
	}
	
	public static void updateInformationsPanels(){
		if (trainingWindow != null){
			trainingWindow.displayGeneralInformation();
		}
		if (calculateOutputs != null){
			calculateOutputs.showStructureInformation();
		}
		if (setUpParameters != null){
			setUpParameters.updateLerningCuoteValue();
		}
	}
	
}
