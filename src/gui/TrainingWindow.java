package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JButton;

public class TrainingWindow {
	
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	JPanel panel, panel_1;
	
	
	
	
	 /**
     * Launch the application
     */
    public static void main(final String[] args) {
	 	try {
	 		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 	} catch (Throwable e) {
	 		e.printStackTrace();
	 	}
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final TrainingWindow window = new TrainingWindow();
                    window.frame.setVisible(true);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TrainingWindow() {
    		initialize();
    }

    public void initialize(){
    	frame = new JFrame();
    	panel = new JPanel();
    	panel_1 = new JPanel();
    	
    	JTextArea txtrUtilizarMatricesDe = new JTextArea();
    	txtrUtilizarMatricesDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
    	txtrUtilizarMatricesDe.setOpaque(false);
    	txtrUtilizarMatricesDe.setText("Utilizar matrices de pesos procedentes de entrenamientos anteriores");
    	
    	JButton btnNewButton = new JButton("Seleccionar archivo: ");
    	
    	JButton btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");
    	GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
    	groupLayout.setHorizontalGroup(
    		groupLayout.createParallelGroup(Alignment.LEADING)
    			.addGroup(groupLayout.createSequentialGroup()
    				.addGap(22)
    				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
    				.addGap(18)
    				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
    				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    					.addGroup(groupLayout.createSequentialGroup()
    						.addGap(18)
    						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    							.addComponent(txtrUtilizarMatricesDe, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
    							.addComponent(btnNewButton))
    						.addContainerGap(134, Short.MAX_VALUE))
    					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
    						.addPreferredGap(ComponentPlacement.RELATED)
    						.addComponent(btnIniciarEntrenamiento)
    						.addGap(93))))
    	);
    	groupLayout.setVerticalGroup(
    		groupLayout.createParallelGroup(Alignment.LEADING)
    			.addGroup(groupLayout.createSequentialGroup()
    				.addGap(20)
    				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
    					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
    					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
    					.addGroup(groupLayout.createSequentialGroup()
    						.addComponent(txtrUtilizarMatricesDe, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
    						.addPreferredGap(ComponentPlacement.RELATED)
    						.addComponent(btnNewButton)
    						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    						.addComponent(btnIniciarEntrenamiento)))
    				.addContainerGap(20, Short.MAX_VALUE))
    	);
    	
    	JRadioButton rdbtnLineal = new JRadioButton("Lineal");
    	
    	JRadioButton rdbtnTangencial = new JRadioButton("Tangencial");
    	
    	JRadioButton rdbtnSi = new JRadioButton("Si");
    	
    	JRadioButton rdbtnNo = new JRadioButton("No");
    	
    	textField = new JTextField();
    	textField.setColumns(10);
    	
    	textField_1 = new JTextField();
    	textField_1.setColumns(10);
    	
    	textField_2 = new JTextField();
    	textField_2.setColumns(10);
    	GroupLayout gl_panel_1 = new GroupLayout(panel_1);
    	gl_panel_1.setHorizontalGroup(
    		gl_panel_1.createParallelGroup(Alignment.LEADING)
    			.addGroup(gl_panel_1.createSequentialGroup()
    				.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
    					.addComponent(rdbtnLineal, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    					.addComponent(rdbtnSi, Alignment.LEADING))
    				.addGap(18)
    				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
    					.addComponent(rdbtnNo)
    					.addComponent(rdbtnTangencial))
    				.addContainerGap(28, Short.MAX_VALUE))
    			.addGroup(gl_panel_1.createSequentialGroup()
    				.addContainerGap()
    				.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
    					.addComponent(textField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
    					.addComponent(textField_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
    					.addComponent(textField_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
    				.addContainerGap(113, Short.MAX_VALUE))
    	);
    	gl_panel_1.setVerticalGroup(
    		gl_panel_1.createParallelGroup(Alignment.LEADING)
    			.addGroup(gl_panel_1.createSequentialGroup()
    				.addContainerGap()
    				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
    					.addComponent(rdbtnLineal)
    					.addComponent(rdbtnTangencial))
    				.addPreferredGap(ComponentPlacement.UNRELATED)
    				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
    					.addComponent(rdbtnSi)
    					.addComponent(rdbtnNo))
    				.addGap(24)
    				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    				.addPreferredGap(ComponentPlacement.RELATED)
    				.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    				.addPreferredGap(ComponentPlacement.RELATED)
    				.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    				.addContainerGap(70, Short.MAX_VALUE))
    	);
    	panel_1.setLayout(gl_panel_1);
    	
    	JLabel lblFuncion = new JLabel("Funci\u00F3n:  ");
    	
    	JLabel lblCotaDeError = new JLabel("Cota de error:");
    	
    	JLabel lblCoeficienteDeAprendizaje = new JLabel("Coeficiente de aprendizaje:");
    	
    	JLabel lblNewLabel = new JLabel("M\u00E1ximo n\u00FAmero de iteraciones:");
    	
    	JLabel lblAadirMomento = new JLabel("A\u00F1adir Momento Beta: ");
    	GroupLayout gl_panel = new GroupLayout(panel);
    	gl_panel.setHorizontalGroup(
    		gl_panel.createParallelGroup(Alignment.LEADING)
    			.addGroup(gl_panel.createSequentialGroup()
    				.addContainerGap()
    				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
    					.addComponent(lblFuncion)
    					.addComponent(lblAadirMomento)
    					.addComponent(lblCoeficienteDeAprendizaje)
    					.addComponent(lblCotaDeError)
    					.addComponent(lblNewLabel))
    				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    	);
    	gl_panel.setVerticalGroup(
    		gl_panel.createParallelGroup(Alignment.LEADING)
    			.addGroup(gl_panel.createSequentialGroup()
    				.addContainerGap()
    				.addComponent(lblFuncion)
    				.addGap(12)
    				.addComponent(lblAadirMomento)
    				.addGap(32)
    				.addComponent(lblCotaDeError)
    				.addPreferredGap(ComponentPlacement.UNRELATED)
    				.addComponent(lblCoeficienteDeAprendizaje)
    				.addPreferredGap(ComponentPlacement.UNRELATED)
    				.addComponent(lblNewLabel)
    				.addContainerGap(75, Short.MAX_VALUE))
    	);
    	panel.setLayout(gl_panel);
    	frame.getContentPane().setLayout(groupLayout);
    }
}
