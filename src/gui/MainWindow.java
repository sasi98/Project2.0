/*
 * MainWindow.java
 *
 * Created on __DATE__, __TIME__
 */

package gui;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;


/**
 *
 * @author  __USER__
 */
public class MainWindow extends javax.swing.JFrame {
	
	//Variables GUI
	private JFrame frame;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JButton btnCrearNueva, btnEntrenar, btnCalcularSalidas;
	
	private NewNetworkWindow newNetworkWindow;
	
	
	
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
	
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBounds(10,62,696,406);
		frame.getContentPane().add(scrollPane);
	 
	  }
	
	
	private void createEvents (){
		
		btnCrearNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {  //NewNetworkWindow
				if ( (newNetworkWindow == null) ){
					newNetworkWindow = new NewNetworkWindow(); 
					newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000, 520, 391));
					scrollPane.add(newNetworkWindow.getFrame());
					newNetworkWindow.getFrame().show();
				}
				else{
					if (newNetworkWindow.getFrame().isClosed()){
						newNetworkWindow = new NewNetworkWindow();
						scrollPane.add(newNetworkWindow.getFrame());
						newNetworkWindow.getFrame().show();
					}
				}
			}
		});
		
		btnEntrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TrainingWindows
				
			}
		});
		
		btnCalcularSalidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
	}
}

