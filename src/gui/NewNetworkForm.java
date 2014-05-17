/*
 * NewNetworkForm.java
 *
 * Created on __DATE__, __TIME__
 */

package gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jxl.read.biff.BiffException;
import dataManager.Company;
import architecture.Network;
import architecture.Neuron;
import dataManager.ReadExcel;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

/**
 *
 * @author  __USER__
 */
public class NewNetworkForm extends javax.swing.JFrame {

	private String idCompany;
	private BigDecimal cotaError;
	private double learningCnt;
	private int numNeuronES, numNeuronO, numPatrones, iteractionMax, inicio;
	private boolean bias;

	/** Creates new form NewNetworkForm */
	public NewNetworkForm() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		
		tfIdCompany = new javax.swing.JTextField();
		tfCotaError = new javax.swing.JTextField();
		tfNumNeuronES = new javax.swing.JTextField();
		tfCnsLearning = new javax.swing.JTextField();
		tfIteractionMax = new javax.swing.JTextField();
		tfInicio = new javax.swing.JTextField();
		//tfNumNeuronO = new JTextField();
		
		sPatrones = new javax.swing.JSpinner();
		sPatrones.setValue(10);
		rbLineal = new javax.swing.JRadioButton();
		rbTangencial = new javax.swing.JRadioButton();
		cbBias = new javax.swing.JCheckBox();
		bCreateNN = new javax.swing.JButton();
		

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("Identificador empresa: ");

		jLabel2.setText("Factor de aprendizaje:");

		jLabel3.setText("N\u00BA de neuronas de entrada/salida:");

		jLabel4.setText("N\u00ba Patrones de aprendizaje: ");

		jLabel5.setText("Cota de error: ");

		jLabel6.setText("Funci\u00f3n: ");

		jLabel7.setText("N\u00ba M\u00e1ximo de iteracciones: ");
		
		jLabel8.setText("Inicio de los datos:");
		
		JLabel jlabel9 = new JLabel("N\u00BA de neuronas ocultas");


		tfIdCompany.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfIdCompanyActionPerformed(evt);
			}
		});

		tfCotaError.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfCotaErrorActionPerformed(evt);
			}
		});

		tfNumNeuronES.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfNumNeuronESActionPerformed(evt);
			}
		});

		tfCnsLearning.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfCnsLearningActionPerformed(evt);
			}
		});

		tfIteractionMax.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfIteractionMaxActionPerformed(evt);
			}
		});

		tfInicio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfInicioActionPerformed(evt);
			}
		});

		rbLineal.setText("Lineal");

		rbTangencial.setText("Tangencial");
		

		cbBias.setText("Bias");
		cbBias.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbBiasActionPerformed(evt);
			}
		});

		bCreateNN.setText("Crear Red");
		bCreateNN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bCreateNNActionPerformed(evt);
			}
		});

		
		
		
		JLabel lblNDeNeuronas = new JLabel("N\u00BA de neuronas ocultas");
		
		tfNumNeuronO = new JTextField();
		tfNumNeuronO.setColumns(10);
		
				
		

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(150)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(jLabel2)
							.addGap(141)
							.addComponent(tfCnsLearning, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
							.addGap(350))
						.addGroup(layout.createSequentialGroup()
							.addComponent(jLabel7)
							.addContainerGap())
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
									.addComponent(jLabel5)
									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(cbBias)
										.addComponent(jLabel6)))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addGroup(layout.createSequentialGroup()
												.addGap(46)
												.addComponent(rbLineal)
												.addGap(26)
												.addComponent(rbTangencial))
											.addGroup(layout.createSequentialGroup()
												.addGap(115)
												.addComponent(tfIteractionMax, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
										.addComponent(bCreateNN)
										.addGap(27))
									.addGroup(layout.createSequentialGroup()
										.addGap(29)
										.addComponent(tfCotaError, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())))
							.addGroup(layout.createSequentialGroup()
								.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addGap(30)
								.addComponent(tfIdCompany, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(522, Short.MAX_VALUE))
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
										.addComponent(jLabel4)
										.addGap(27)
										.addComponent(sPatrones, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
									.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(jLabel3)
											.addComponent(lblNDeNeuronas))
										.addGap(35)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(tfNumNeuronO, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(tfNumNeuronES, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
								.addGap(81)
								.addComponent(jLabel8)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(tfInicio, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addGap(72)))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(29)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(tfIdCompany, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(18)
							.addComponent(jLabel5))
						.addGroup(layout.createSequentialGroup()
							.addGap(7)
							.addComponent(tfCotaError, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel4)
						.addComponent(sPatrones, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel8)
						.addComponent(tfInicio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel3)
						.addComponent(tfNumNeuronES, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfNumNeuronO, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNDeNeuronas))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(2)
							.addComponent(jLabel2))
						.addGroup(layout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfCnsLearning, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(bCreateNN)
							.addGap(98))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel7)
								.addComponent(tfIteractionMax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(40)
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel6)
								.addComponent(rbLineal)
								.addComponent(rbTangencial))
							.addGap(18)
							.addComponent(cbBias)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void tfInicioActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void tfIteractionMaxActionPerformed(java.awt.event.ActionEvent evt) {
		String strIteractionMax = tfIteractionMax.getText();
		iteractionMax = Integer.parseInt(strIteractionMax);
	}

	private void tfIdCompanyActionPerformed(ActionEvent evt) {
		idCompany = tfIdCompany.getText();
		System.out.print("escribiendo iden");
	}

	private void tfCotaErrorActionPerformed(ActionEvent evt) {
		String stCotaError = tfCotaError.getText();
		cotaError = new BigDecimal(stCotaError); //Manejar errores 
	}

	private void tfNumNeuronESActionPerformed(ActionEvent evt) {
		// TODO add your handling code here:
		String stneuronSize = tfNumNeuronES.getText();
		numNeuronES = Integer.parseInt(stneuronSize);
	}

	private void tfCnsLearningActionPerformed(ActionEvent evt) {
		String stCnsLearning = tfCnsLearning.getText();
		learningCnt = Double.parseDouble(stCnsLearning);
	}

	private void cbBiasActionPerformed(ActionEvent evt) {
		bias = cbBias.isSelected();
	}

	private void bCreateNNActionPerformed(java.awt.event.ActionEvent evt) {

		System.out.print(tfIdCompany.getText());
		idCompany = tfIdCompany.getText();
		System.out.print("ide" + idCompany);

		//si id company no est� entre 0 o 500, y adem�s tiene q tener el formato "000"

		if ((idCompany == null) || (idCompany.equals(""))) {
			JOptionPane.showMessageDialog(this,
					"Error, el campo identificador de empresa est� vacio",
					"Campo requerido", JOptionPane.ERROR_MESSAGE);
		} else {

			int auxInt = Integer.parseInt(idCompany);

			if (auxInt < 1) {
				System.out.print("Menor que ");//
			}

			if (auxInt > 510) {
				System.out.print("Mayor que ");//
			}

			//			if ( (idCompany.compareTo("1") == -1) || (idCompany.compareTo("510") < 0)){
			//				System.out.print("Se sale del rango");//Tb pilla si metes letras 
			//			}
			//	

			//Si no incluimos los dem�s campos, estos se a�adiran por defecto
			String stCotaError = tfCotaError.getText();
			if ((stCotaError == null) || (stCotaError.equals(""))) {
				tfCotaError.setText("0.001");
				cotaError = new BigDecimal(0.001);

			} else {
				cotaError = new BigDecimal(stCotaError);
			}
			String stneuronSize = tfNumNeuronES.getText();
			if ((stneuronSize == null) || (stneuronSize.equals(""))) {
				tfNumNeuronES.setText("10");
				numNeuronES = 10; //independientemente del bias 

			} else {
				numNeuronES = Integer.parseInt(stneuronSize);
			}

			String stCnsLearning = tfCnsLearning.getText();
			if ((stCnsLearning == null) || (stCnsLearning.equals(""))) {
				tfCnsLearning.setText("0.001");
				learningCnt = 0.001;

			} else {
				learningCnt = Double.parseDouble(stCnsLearning);
			}

			String stItMax = tfIteractionMax.getText();
			if ((stItMax == null) || (stItMax.equals(""))) {
				tfIteractionMax.setText("1000");
				iteractionMax = 1000;

			} else {
				iteractionMax = Integer.parseInt(stItMax);
			}

			String strInicio = tfInicio.getText();
			if ((strInicio == null) || (strInicio.equals(""))) {
				tfInicio.setText("1");
				inicio = 1;

			} else {
				inicio = Integer.parseInt(strInicio);
			}
			numPatrones = (int) sPatrones.getValue();

			bias = cbBias.isSelected();

			System.out.print("Creando NN con id empresa: " + idCompany
					+ " cota de error " + cotaError + " tama�o de neurona: "
					+ numNeuronES + " factor de aprendizaje " + learningCnt
					+ " bias: " + bias + "numero de patrones " + numPatrones
					+ "m�ximo n�mero de iteraciones: " + iteractionMax
					+ "Inicio de los patrones: " + inicio);

		}

		//Creamos la red con todos los datos

		ReadExcel excel;
//		try {
//			excel = new ReadExcel();
//			Company p = excel.readCompanyById(idCompany);
//			Network n = Network.getInstance(p);
//			n.setUpNetwork(cotaError, numPatrones, numNeuronES, learningCnt,
//					bias, iteractionMax, inicio);
//			n.training();
//		} catch (BiffException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


	}

	//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NewNetworkForm().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton bCreateNN;
	private javax.swing.JCheckBox cbBias;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JRadioButton rbLineal;
	private javax.swing.JRadioButton rbTangencial;
	private javax.swing.JSpinner sPatrones;
	private javax.swing.JTextField tfCnsLearning;
	private javax.swing.JTextField tfCotaError;
	private javax.swing.JTextField tfIdCompany;
	private javax.swing.JTextField tfInicio;
	private javax.swing.JTextField tfIteractionMax;
	private javax.swing.JTextField tfNumNeuronES;
	private JTextField tfNumNeuronO;
}