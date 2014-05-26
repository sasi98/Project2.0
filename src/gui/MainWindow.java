/*
 * MainWindow.java
 *
 * Created on __DATE__, __TIME__
 */

package gui;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import architecture.Network;
import architecture.NetworkManager;
import javax.swing.JLabel;


/**
 *
 * @author  __USER__
 */
public class MainWindow extends javax.swing.JFrame {
	
	public static NetworkManager ne;
	
	//Variables GUI
	private JFrame frame;
	private JToolBar toolBar;
	private JDesktopPane desktopPane;
	private JButton btnCrearNueva, btnEntrenar, btnCalcularSalidas;
	
	//Classes with internal frames
	private NewNetworkWindow newNetworkWindow;
	private TrainingWindow trainingWindow;

	
	
	
	/**
	 * @param args the command line arguments
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
		initialize();
		createEvents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
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
		desktopPane.setBounds(10,60,696,406);
		frame.getContentPane().add(desktopPane);
	 
	  }
	
	
	private void createEvents (){
		
		btnCrearNueva.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {  //NewNetworkWindow
				if (trainingWindow != null){
					try {
						trainingWindow.getFrame().setClosed(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if ( (newNetworkWindow == null) ){
					newNetworkWindow = new NewNetworkWindow(); 
					//newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
					desktopPane.add(newNetworkWindow.getFrame(), BorderLayout.WEST);
					
					newNetworkWindow.getFrame().show();
				}
				else{
					if (newNetworkWindow.getFrame().isClosed()){
						newNetworkWindow = new NewNetworkWindow();
						//newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
						desktopPane.add(newNetworkWindow.getFrame(), BorderLayout.WEST);
						newNetworkWindow.getFrame().show();
					}
				}
			}
		});
		
		btnEntrenar.addActionListener(new ActionListener() {
			
			//Close other internals frames before
			public void actionPerformed(ActionEvent e) {
				if (newNetworkWindow != null){
					try {
						newNetworkWindow.getFrame().setClosed(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				//TrainingWindows
				if ( (trainingWindow == null) ){
					trainingWindow = new TrainingWindow();
					//trainingWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
					desktopPane.add(trainingWindow.getFrame(), BorderLayout.WEST);
					trainingWindow.getFrame().show();
				}
				else{
					if (trainingWindow.getFrame().isClosed()){
						trainingWindow = new TrainingWindow();
						//trainingWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
						desktopPane.add(trainingWindow.getFrame(), BorderLayout.WEST);
						trainingWindow.getFrame().show();
					}
				}
			}
		});
		
		btnCalcularSalidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
	}
}

