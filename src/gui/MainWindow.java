/*
 * MainWindow.java
 *
 * Created on __DATE__, __TIME__
 */

package gui;

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
import java.util.ArrayList;
import java.util.Collections;

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

/**
 * 
 * @author __USER__
 */
public class MainWindow extends JFrame {

    //Lista con las actuales instancias de la clase NetworkManager
    public static ArrayList<NetworkManager> neList = new ArrayList<>();
    public static int numInstances = 0; //Número de instancias creadas
    public static boolean cancelTraining = false;

    //Variables GUI
    private JFrame frame;
    private JToolBar toolBar;
    private JDesktopPane desktopPane;
    private JButton btnCrearNueva, btnEntrenar, btnCalcularSalidas;
    private JToggleButton tglbtnTrazas;

    //Classes with internal frames
    private NewNetworkWindow newNetworkWindow;
    private TrainingWindow trainingWindow;
    private CalculateOutputsWindow calculateOutputsWindow;

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(final String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Throwable e) {
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

        tglbtnTrazas = new JToggleButton("Activar trazas");
        tglbtnTrazas.setBounds(583, 30, 123, 21);
        frame.getContentPane().add(tglbtnTrazas);
        frame.addComponentListener(new ComponentListener() {

            //get frame windows size
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
                //calculate the delta value
                final int deltaWidth = frameWidth - desktopPane.getWidth();
                final int deltaHeight = frameHeight - desktopPane.getHeight();

                //dynamic reset desktopPane size
                desktopPane.setBounds(10, 60, frame.getWidth() - deltaWidth, frame.getHeight() - deltaHeight);
                desktopPane.updateUI();
            }

            @Override
            public void componentShown(final ComponentEvent e) {

            }
        });

    }

    private void createEvents() {

        btnCrearNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) { //NewNetworkWindow
                btnCrearNuevaActionPerformed();
            }
        });

        btnEntrenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnEntrenarActionPerformed();
            }
        });

        btnCalcularSalidas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnCalcularSalidasActionPerformed();
            }
        });

        tglbtnTrazas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent ev) {
                tglbtnTrazasActionPerformed(ev);
            }
        });
    }

    private void btnCrearNuevaActionPerformed() {
        if (trainingWindow != null) {
            try {
                trainingWindow.getFrame().setClosed(true); //Close other internals frames before
            } catch (final PropertyVetoException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if ((newNetworkWindow == null)) {
            newNetworkWindow = new NewNetworkWindow();
            //newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
            desktopPane.add(newNetworkWindow.getFrame(), BorderLayout.WEST);

            newNetworkWindow.getFrame().show();
        } else {
            if (newNetworkWindow.getFrame().isClosed()) {
                newNetworkWindow = new NewNetworkWindow();
                //newNetworkWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
                desktopPane.add(newNetworkWindow.getFrame(), BorderLayout.WEST);
                newNetworkWindow.getFrame().show();
            } else { //El panel no estcerrado pero he vuelto a hacer click, borro todo pero no creo una instancia nueva
                //clearTextFields(newNetworkWindow.getFrame());

            }
        }

    }

    private void btnEntrenarActionPerformed() {
        if (newNetworkWindow != null) {
            try {
                newNetworkWindow.getFrame().setClosed(true);
            } catch (final PropertyVetoException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (calculateOutputsWindow != null) {
            try {
                calculateOutputsWindow.getFrame().setClosed(true);
            } catch (final PropertyVetoException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //TrainingWindows
        if ((trainingWindow == null)) {
            trainingWindow = new TrainingWindow();
            //trainingWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
            desktopPane.add(trainingWindow.getFrame(), BorderLayout.WEST);
            trainingWindow.getFrame().show();
        } else {
            if (trainingWindow.getFrame().isClosed()) {
                trainingWindow = new TrainingWindow();
                //trainingWindow.getFrame().setBounds(new Rectangle(000, 000, 700, 450));
                desktopPane.add(trainingWindow.getFrame(), BorderLayout.WEST);
                trainingWindow.getFrame().show();
            }
        }

    }

    private void btnCalcularSalidasActionPerformed() {
        if (newNetworkWindow != null) {
            try {
                newNetworkWindow.getFrame().setClosed(true);
            } catch (final PropertyVetoException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (newNetworkWindow != null) {
            try {
                newNetworkWindow.getFrame().setClosed(true);
            } catch (final PropertyVetoException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (trainingWindow != null) {
            try {
                trainingWindow.getFrame().setClosed(true);
            } catch (final PropertyVetoException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //Calculate Outputs
        if ((calculateOutputsWindow == null)) {
            calculateOutputsWindow = new CalculateOutputsWindow();
            desktopPane.add(calculateOutputsWindow.getFrame(), BorderLayout.WEST);
            calculateOutputsWindow.getFrame().show();
        } else {
            if (calculateOutputsWindow.getFrame().isClosed()) {
                calculateOutputsWindow = new CalculateOutputsWindow();
                desktopPane.add(calculateOutputsWindow.getFrame(), BorderLayout.WEST);
                calculateOutputsWindow.getFrame().show();
            }
        }

    }

    public void tglbtnTrazasActionPerformed(final ItemEvent ev) {
        if (ev.getStateChange() == ItemEvent.SELECTED) {
            tglbtnTrazas.setText("Desactivar trazas");
        } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
            tglbtnTrazas.setText("Activar trazas");
            final ArrayList<Logger> loggers = Collections.<Logger> list(LogManager.getCurrentLoggers());
            loggers.add(LogManager.getRootLogger());
            for (final Logger logger : loggers) {
                logger.setLevel(Level.OFF);
            }
        }
    }

    public void clearTextFields(final Container container) {
        for (final Component c : container.getComponents()) {
            if ((c instanceof JTextField) && !(c instanceof JSpinner)) {
                System.out.print("Tipo de componente: " + c.getName() + "\n");
                final JTextField f = (JTextField) c;
                f.setText("");
                //				if (c instanceof JSpinner){
                //					JSpinner f1 = (JSpinner) c;
                //				    f1.setValue(0);
                //				    System.out.print("Entra aqu);
                //				}
            } else if (c instanceof Container) {
                clearTextFields((Container) c);
            }
        }
    }

}
