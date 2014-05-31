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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import architecture.Network;
import architecture.NetworkManager;

import javax.swing.JLabel;
import javax.swing.JToggleButton;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author __USER__
 */
public class MainWindow extends JFrame {

	// Lista con las actuales instancias de la clase NetworkManager
	public static ArrayList<NetworkManager> neList = new ArrayList<>();
	public static Graph chart=new Graph();
	public static int numInstances = 0; // N�mero de instancias creadas
	public static boolean cancelTraining = false;

	// Variables GUI
	private JFrame frame;
	private JToolBar toolBar;
	private JDesktopPane desktopPane;
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
		//PropertyConfigurator.configure("files"+File.separator+"log4j.properties");
		PropertyConfigurator.configure("files\\log4j.properties");
		initialize();
		createEvents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 732, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnCrearNueva = new JButton("Nueva");
		btnEntrenar = new JButton("Entrenar");
		btnCalcularSalidas = new JButton("Calcular salidas");

		toolBar = new JToolBar();
		toolBar.setBounds(new Rectangle(13, 15, 9, 8));
		toolBar.add(btnCrearNueva);
		toolBar.add(btnEntrenar);
		toolBar.add(btnCalcularSalidas);
		toolBar.setBounds(10, 11, 484, 38);
		frame.getContentPane().add(toolBar);

		desktopPane = new JDesktopPane();
		desktopPane.setOpaque(false);
		desktopPane.setBounds(10, 60, 696, 406);
		frame.getContentPane().add(desktopPane);
		frame.addComponentListener(new ComponentListener() {

			// get frame windows size
			int frameWidth = frame.getWidth();
			int frameHeight = frame.getHeight();

			@Override
			public void componentHidden(final ComponentEvent e) {

			}

			@Override
			public void componentMoved(final ComponentEvent e) {

			}

			@Override
			public void componentResized(final ComponentEvent e) {
				// calculate the delta value
				final int deltaWidth = frameWidth - desktopPane.getWidth();
				final int deltaHeight = frameHeight - desktopPane.getHeight();

				// dynamic reset desktopPane size
				desktopPane.setBounds(10, 60, frame.getWidth() - deltaWidth,
						frame.getHeight() - deltaHeight);
				desktopPane.updateUI();
			}

			@Override
			public void componentShown(final ComponentEvent e) {

			}
		});

		tglbtnTrazas = new JToggleButton("Desactivar trazas"); // By default
																// they are able
																// by
																// log4j.properties
		tglbtnTrazas.setBounds(583, 30, 123, 21);
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
	}

	private void btnCrearNuevaActionPerformed() {
		if (trainingWindow != null) {
			try {
				trainingWindow.getFrame().setClosed(true); // Close other
															// internals frames
															// before
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if ((newNetworkWindow == null)) {
			newNetworkWindow = new NewNetworkWindow();
			// newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000,
			// 700, 450));
			desktopPane.add(newNetworkWindow.getFrame(), BorderLayout.WEST);

			newNetworkWindow.getFrame().show();
		} else {
			if (newNetworkWindow.getFrame().isClosed()) {
				newNetworkWindow = new NewNetworkWindow();
				// newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000,
				// 700, 450));
				desktopPane.add(newNetworkWindow.getFrame(), BorderLayout.WEST);
				newNetworkWindow.getFrame().show();
			} else { // El panel no est� cerrado pero he vuelto a hacer click,
						// borro todo pero no creo una instancia nueva
						// clearTextFields(newNetworkWindow.getFrame());

			}
		}

	}

	private void btnEntrenarActionPerformed() {
		if (newNetworkWindow != null) {
			try {
				newNetworkWindow.getFrame().setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (calculateOutputsWindow != null) {
			try {
				calculateOutputsWindow.getFrame().setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// TrainingWindows
		if ((trainingWindow == null)) {
			trainingWindow = new TrainingWindow();
			// trainingWindow.getFrame().setBounds(new Rectangle(000, 000, 700,
			// 450));
			desktopPane.add(trainingWindow.getFrame(), BorderLayout.WEST);
			trainingWindow.getFrame().show();
		} else {
			if (trainingWindow.getFrame().isClosed()) {
				trainingWindow = new TrainingWindow();
				// trainingWindow.getFrame().setBounds(new Rectangle(000, 000,
				// 700, 450));
				desktopPane.add(trainingWindow.getFrame(), BorderLayout.WEST);
				trainingWindow.getFrame().show();
			}
		}

	}

	private void btnCalcularSalidasActionPerformed() {
		if (newNetworkWindow != null) {
			try {
				newNetworkWindow.getFrame().setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (newNetworkWindow != null) {
			try {
				newNetworkWindow.getFrame().setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (trainingWindow != null) {
			try {
				trainingWindow.getFrame().setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// Calculate Outputs
		if ((calculateOutputsWindow == null)) {
			calculateOutputsWindow = new CalculateOutputsWindow();
			desktopPane.add(calculateOutputsWindow.getFrame(),
					BorderLayout.WEST);
			calculateOutputsWindow.getFrame().show();
		} else {
			if (calculateOutputsWindow.getFrame().isClosed()) {
				calculateOutputsWindow = new CalculateOutputsWindow();
				desktopPane.add(calculateOutputsWindow.getFrame(),
						BorderLayout.WEST);
				calculateOutputsWindow.getFrame().show();
			}
		}

	}

	public void tglbtnTrazasActionPerformed(ItemEvent ev) {
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			tglbtnTrazas.setText("Activar trazas");
			ArrayList<Logger> loggers = Collections.<Logger> list(LogManager.getCurrentLoggers());
			loggers.add(LogManager.getRootLogger());
			for (Logger logger : loggers) {
				logger.setLevel(Level.OFF);
			}
		} else if (ev.getStateChange() == ItemEvent.DESELECTED)
			tglbtnTrazas.setText("Desactivar trazas");
			PropertyConfigurator.configure("files\\log4j.properties");
		
//			ArrayList<Logger> loggers = Collections.<Logger> list(LogManager.getCurrentLoggers());
//			loggers.add(LogManager.getRootLogger());
//			for (Logger logger : loggers) {
//				logger.setLevel(Level.ALL); //se deberían activar de n
//			}
		
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
