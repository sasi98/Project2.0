/*
 * MainWindow.java
 *
 * Created on __DATE__, __TIME__
 */

package gui;

import graph.Graph;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
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
import java.awt.Color;

/**
 * 
 * @author __USER__
 */
public class MainWindow extends JFrame {

	// Lista con las actuales instancias de la clase NetworkManager
	public static ArrayList<NetworkManager> neList = new ArrayList<>();
	// public static HashMap<String, Graph> chartMap = new HashMap<String,
	// Graph>();
	public static int numInstances = 0; // N�mero de instancias creadas
	public static boolean cancelTraining = false;
	public static JDesktopPane desktopPane;

	// Variables GUI
	private JFrame frame;
	private JToolBar toolBar;
	// private JDesktopPane desktopPane;
	private JButton btnCrearNueva, btnEntrenar, btnCalcularSalidas;
	private JToggleButton tglbtnTrazas;

	// Classes with internal frames
	private NewNetworkWindow newNetworkWindow;
	private TrainingWindow trainingWindow;
	private CalculateOutputsWindow calculateOutputsWindow;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				final MainWindow window = new MainWindow();
				window.frame.setVisible(true);
			}
		});
	}

	/** Creates new form MainWindow */
	public MainWindow() {
		PropertyConfigurator.configure("files" + File.separator
				+ "log4j.properties");
		initialize();
		createEvents();
	}

	// public static void addGraph(String id) {
	// chartMap.put(id, new Graph(id));
	// }
	//
	// public static Graph getGraph(String id) {
	// return chartMap.get(id);
	// }

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.white);
		btnCrearNueva = new JButton("Nueva");
		btnEntrenar = new JButton("Entrenar");
		btnCalcularSalidas = new JButton("Calcular salidas");

		toolBar = new JToolBar();
		toolBar.setBackground(Color.WHITE);
		toolBar.setForeground(Color.WHITE);
		toolBar.setBounds(6, 6, 215, 21);
		toolBar.add(btnCrearNueva);
		toolBar.add(btnEntrenar);
		toolBar.add(btnCalcularSalidas);
		frame.getContentPane().add(toolBar);

		desktopPane = new JDesktopPane();
		desktopPane.setBounds(6, 39, 988, 633);
		desktopPane.setBackground(Color.WHITE);
		desktopPane.setOpaque(false);
		frame.getContentPane().add(desktopPane);

		tglbtnTrazas = new JToggleButton("Desactivar trazas");
		tglbtnTrazas.setBounds(851, 6, 123, 21);
		frame.getContentPane().add(tglbtnTrazas);

	}

	private void createEvents() {

		btnCrearNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { // NewNetworkWindow
				btnCrearNuevaActionPerformed();
			}
		});

		btnEntrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEntrenarActionPerformed();
			}
		});

		btnCalcularSalidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCalcularSalidasActionPerformed();
			}
		});

		tglbtnTrazas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				tglbtnTrazasActionPerformed(ev);
			}
		});

		// frame.addComponentListener(new ComponentListener() {
		//
		// // get frame windows size
		// int frameWidth = frame.getWidth(), frameHeight = frame.getHeight();
		//
		// @Override
		// public void componentHidden(final ComponentEvent e) {
		// }
		//
		// @Override
		// public void componentMoved(final ComponentEvent e) {
		// }
		//
		// @Override
		// public void componentResized(final ComponentEvent e) {
		// // calculate the delta value
		// final int deltaWidth = frameWidth - desktopPane.getWidth();
		// final int deltaHeight = frameHeight - desktopPane.getHeight();
		// // dynamic reset desktopPane size
		// desktopPane.setBounds(10, 60, frame.getWidth() - deltaWidth,
		// frame.getHeight() - deltaHeight);
		// desktopPane.updateUI();
		// }
		//
		// @Override
		// public void componentShown(final ComponentEvent e) {
		// }
		//
		// });
	}

	private void btnCrearNuevaActionPerformed() {
		if (trainingWindow != null) {
			trainingWindow.getPanel().hide(); // Close other
												// internals frames
												// before
		}
		if ((newNetworkWindow == null)) {
			newNetworkWindow = new NewNetworkWindow();
			// newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000,
			// 700, 450));
			desktopPane.add(newNetworkWindow.getPanel(), BorderLayout.CENTER);

			newNetworkWindow.getPanel().show();
		} else {
			if (!newNetworkWindow.getPanel().isShowing()) {
				newNetworkWindow = new NewNetworkWindow();
				// newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000,
				// 700, 450));
				desktopPane.add(newNetworkWindow.getPanel(), BorderLayout.WEST);
				newNetworkWindow.getPanel().show();
			} else { // El panel no est� cerrado pero he vuelto a hacer click,
						// borro todo pero no creo una instancia nueva
						// clearTextFields(newNetworkWindow.getFrame());

			}
		}

	}

	private void btnEntrenarActionPerformed() {
		if (newNetworkWindow != null) {
			newNetworkWindow.getPanel().hide();
		}
		if (calculateOutputsWindow != null) {
			calculateOutputsWindow.getPanel().hide();
		}
		// TrainingWindows
		if ((trainingWindow == null)) {
			trainingWindow = new TrainingWindow();
			// trainingWindow.getFrame().setBounds(new Rectangle(000, 000, 700,
			// 450));
			desktopPane.add(trainingWindow.getPanel(), BorderLayout.WEST);
			trainingWindow.getPanel().show();
		} else {
			if (!trainingWindow.getPanel().isShowing()) {
				trainingWindow = new TrainingWindow();
				// trainingWindow.getFrame().setBounds(new Rectangle(000, 000,
				// 700, 450));
				desktopPane.add(trainingWindow.getPanel(), BorderLayout.WEST);
				trainingWindow.getPanel().show();
			}
		}

	}

	private void btnCalcularSalidasActionPerformed() {
		if (newNetworkWindow != null) {
			newNetworkWindow.getPanel().hide();
		}
		if (newNetworkWindow != null) {
			newNetworkWindow.getPanel().hide();
		}
		if (trainingWindow != null) {
			trainingWindow.getPanel().hide();
		}
		// Calculate Outputs
		if ((calculateOutputsWindow == null)) {
			calculateOutputsWindow = new CalculateOutputsWindow();
			desktopPane.add(calculateOutputsWindow.getPanel(),
					BorderLayout.WEST);
			calculateOutputsWindow.getPanel().show();
		} else {
			if (!calculateOutputsWindow.getPanel().isShowing()) {
				calculateOutputsWindow = new CalculateOutputsWindow();
				desktopPane.add(calculateOutputsWindow.getPanel(),
						BorderLayout.WEST);
				calculateOutputsWindow.getPanel().show();
			}
		}

	}

	// Switch button: Si las trazas estaban activas, las desactiva (delete all
	// the loggers)
	// Si las trazas estaban desactivadas, las activa SOBREESCRIBIENDO el
	// fichero de traces.log

	public void tglbtnTrazasActionPerformed(ItemEvent ev) {
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			tglbtnTrazas.setText("Activar trazas");
			ArrayList<Logger> loggers = Collections.<Logger> list(LogManager
					.getCurrentLoggers());
			loggers.add(LogManager.getRootLogger());
			for (Logger logger : loggers) {
				logger.setLevel(Level.OFF);
				// System.out.print("Logger: "+logger.getName()+"\n");
			}
		} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
			tglbtnTrazas.setText("Desactivar trazas");
			PropertyConfigurator.configure("files" + File.separator
					+ "log4j.properties"); // Sobreescribimos fichero
			ArrayList<Logger> loggers = Collections.<Logger> list(LogManager
					.getCurrentLoggers());
			loggers.add(LogManager.getRootLogger());
			for (Logger logger : loggers) {
				logger.setLevel(Level.ALL);
				// System.out.print("Logger: "+logger.getName()+"\n");
			}
		}

	}

	public void clearTextFields(Container container) {
		for (Component c : container.getComponents()) {
			if ((c instanceof JTextField) && !(c instanceof JSpinner)) {
				System.out.print("Tipo de componente: " + c.getName() + "\n");
				JTextField f = (JTextField) c;
				f.setText("");
				// if (c instanceof JSpinner){
				// JSpinner f1 = (JSpinner) c;
				// f1.setValue(0);	
				// System.out.print("Entra aqu�");
				// }
			} else if (c instanceof Container)
				clearTextFields((Container) c);
		}
	}

}
