package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import valueset.Value;
import architecture.NetworkManager;
import dataManager.PatronData;
import dataManager.WriteExcel;

public class NewNetworkWindowq {

    private JFrame frame;
    private JTextField tfIdCompany;
    private JTextField tfCotaError;
    private JTextField tfNumNeuronES;
    private JTextField tfNumNeuronO;
    private JTextField tfCnsLearning;
    private JTextField tfIteractionMax;
    private JTextField tfInicio;
    private JCheckBox cbBias;
    private JComboBox<String> comboBox_inputType;
    private JSpinner sPatrones;

    private String idCompany;
    private BigDecimal cotaError;
    private double learningCnt;
    private int numNeuronES, numNeuronO, numPatrones, iteractionMax, inicio;
    private boolean bias;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final NewNetworkWindowq window = new NewNetworkWindowq();
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
    public NewNetworkWindowq() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 520, 391);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        final JLabel lblNewLabel = new JLabel("Identificador empresa: ");
        lblNewLabel.setBounds(10, 32, 174, 14);
        frame.getContentPane().add(lblNewLabel);

        final JLabel lblNewLabel_1 = new JLabel("Cota de error: ");
        lblNewLabel_1.setBounds(10, 64, 174, 14);
        frame.getContentPane().add(lblNewLabel_1);

        final JLabel lblNewLabel_2 = new JLabel("N\u00BA Patrones de aprendizaje: ");
        lblNewLabel_2.setBounds(10, 105, 174, 14);
        frame.getContentPane().add(lblNewLabel_2);

        final JLabel lblNewLabel_3 = new JLabel("N\u00BA de neuronas de entrada/salida:");
        lblNewLabel_3.setBounds(10, 141, 174, 14);
        frame.getContentPane().add(lblNewLabel_3);

        final JLabel lblNewLabel_4 = new JLabel("N\u00BA de neuronas ocultas");
        lblNewLabel_4.setBounds(10, 166, 174, 14);
        frame.getContentPane().add(lblNewLabel_4);

        final JLabel lblNewLabel_5 = new JLabel("Factor de aprendizaje:");
        lblNewLabel_5.setBounds(10, 202, 174, 14);
        frame.getContentPane().add(lblNewLabel_5);

        final JLabel lblNewLabel_6 = new JLabel("N\u00BA M\u00E1ximo de iteracciones: ");
        lblNewLabel_6.setBounds(10, 232, 174, 14);
        frame.getContentPane().add(lblNewLabel_6);

        tfIdCompany = new JTextField();
        tfIdCompany.setBounds(194, 29, 56, 20);
        frame.getContentPane().add(tfIdCompany);
        tfIdCompany.setColumns(10);

        tfCotaError = new JTextField();
        tfCotaError.setBounds(194, 61, 86, 20);
        frame.getContentPane().add(tfCotaError);
        tfCotaError.setColumns(10);

        tfNumNeuronES = new JTextField();
        tfNumNeuronES.setBounds(194, 138, 86, 20);
        frame.getContentPane().add(tfNumNeuronES);
        tfNumNeuronES.setColumns(10);

        tfNumNeuronO = new JTextField();
        tfNumNeuronO.setBounds(194, 163, 86, 20);
        frame.getContentPane().add(tfNumNeuronO);
        tfNumNeuronO.setColumns(10);

        tfCnsLearning = new JTextField();
        tfCnsLearning.setBounds(194, 199, 86, 20);
        frame.getContentPane().add(tfCnsLearning);
        tfCnsLearning.setColumns(10);

        tfIteractionMax = new JTextField();
        tfIteractionMax.setBounds(194, 229, 86, 20);
        frame.getContentPane().add(tfIteractionMax);
        tfIteractionMax.setColumns(10);

        final JButton bCreateNN = new JButton("Crear Red");
        bCreateNN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                bCreateNNActionPerformed();
            }
        });
        bCreateNN.setBounds(408, 317, 89, 23);
        frame.getContentPane().add(bCreateNN);

        comboBox_inputType = new JComboBox();
        comboBox_inputType.addItem(Value.ComboBox.SOLAPADOS);
        comboBox_inputType.addItem(Value.ComboBox.NOSOLAPADOS);
        comboBox_inputType.addItem(Value.ComboBox.ALEATORIOS);
        comboBox_inputType.addItem(Value.ComboBox.MANUAL);
        comboBox_inputType.setBounds(412, 29, 85, 20);
        frame.getContentPane().add(comboBox_inputType);

        final JLabel lblNewLabel_7 = new JLabel("Selecci\u00F3n de datos: ");
        lblNewLabel_7.setBounds(305, 32, 97, 14);
        frame.getContentPane().add(lblNewLabel_7);

        tfInicio = new JTextField();
        tfInicio.setBounds(411, 61, 86, 20);
        frame.getContentPane().add(tfInicio);
        tfInicio.setColumns(10);

        final JLabel lblNewLabel_8 = new JLabel("Inicio de los datos:");
        lblNewLabel_8.setBounds(305, 64, 136, 14);
        frame.getContentPane().add(lblNewLabel_8);

        sPatrones = new JSpinner();
        //sPatrones.setValue(10);
        sPatrones.setBounds(194, 102, 66, 20);
        frame.getContentPane().add(sPatrones);

        final JLabel lblNewLabel_9 = new JLabel("Funci\u00F3n: ");
        lblNewLabel_9.setBounds(10, 281, 46, 14);
        frame.getContentPane().add(lblNewLabel_9);

        final JRadioButton rbLineal = new JRadioButton("Lineal");
        rbLineal.setBounds(78, 277, 109, 23);
        frame.getContentPane().add(rbLineal);

        final JRadioButton rbTangencial = new JRadioButton("Tangencial");
        rbTangencial.setBounds(212, 277, 109, 23);
        frame.getContentPane().add(rbTangencial);

        cbBias = new JCheckBox("Bias");
        cbBias.setBounds(10, 317, 97, 23);
        frame.getContentPane().add(cbBias);

    }

    private void bCreateNNActionPerformed() {

        System.out.print(tfIdCompany.getText());
        idCompany = tfIdCompany.getText();
        System.out.print("ide" + idCompany);

        //si id company no est?entre 0 o 500, y adem?s tiene q tener el formato "000"

        if ((idCompany == null) || (idCompany.equals(""))) {
            // JOptionPane.showMessageDialog(this, "Error, el campo identificador de empresa est?vacio", "Campo requerido", JOptionPane.ERROR_MESSAGE);
        } else {

            final int auxInt = Integer.parseInt(idCompany);

            if (auxInt < 1) {
                System.out.print("Menor que ");//
            }

            if (auxInt > 510) {
                System.out.print("Mayor que ");//
            }

            //                  if ( (idCompany.compareTo("1") == -1) || (idCompany.compareTo("510") < 0)){
            //                          System.out.print("Se sale del rango");//Tb pilla si metes letras
            //                  }
            //

            //Si no incluimos los dem?s campos, estos se a?adiran por defecto
            final String stCotaError = tfCotaError.getText();
            if ((stCotaError == null) || (stCotaError.equals(""))) {
                tfCotaError.setText("0.001");
                cotaError = new BigDecimal(0.001);
                cotaError.setScale(NetworkManager.PRECISION, RoundingMode.HALF_UP);

            } else {
                cotaError = new BigDecimal(stCotaError);
                cotaError.setScale(NetworkManager.PRECISION);
            }
            final String stneuronSize = tfNumNeuronES.getText();
            if ((stneuronSize == null) || (stneuronSize.equals(""))) {
                tfNumNeuronES.setText("10");
                numNeuronES = 10; //independientemente del bias

            } else {
                numNeuronES = Integer.parseInt(stneuronSize);
            }

            String stnumNeuronO = tfNumNeuronO.getText();
            if ((stnumNeuronO == null) || (stnumNeuronO.equals(""))) {
                numNeuronO = numNeuronES - (numNeuronES / 2);
                stnumNeuronO = String.valueOf(numNeuronO);
                tfNumNeuronO.setText(stnumNeuronO);

            } else {
                numNeuronO = Integer.parseInt(stnumNeuronO);
            }

            final String stCnsLearning = tfCnsLearning.getText();
            if ((stCnsLearning == null) || (stCnsLearning.equals(""))) {
                tfCnsLearning.setText("0.001");
                learningCnt = 0.001;

            } else {
                learningCnt = Double.parseDouble(stCnsLearning);
            }

            final String stItMax = tfIteractionMax.getText();
            if ((stItMax == null) || (stItMax.equals(""))) {
                tfIteractionMax.setText("1000");
                iteractionMax = 1000;

            } else {
                iteractionMax = Integer.parseInt(stItMax);
            }

            final String strInicio = tfInicio.getText();
            if ((strInicio == null) || (strInicio.equals(""))) {
                tfInicio.setText("1");
                inicio = 1;

            } else {
                inicio = Integer.parseInt(strInicio);
            }
            numPatrones = (int) sPatrones.getValue();

            bias = cbBias.isSelected();

            //                  System.out.print("Creando NN con id empresa: " + idCompany
            //                                  + " cota de error " + cotaError + " N: "
            //                                  + numNeuronES + " factor de aprendizaje " + learningCnt
            //                                  + " bias: " + bias + "numero de patrones " + numPatrones
            //                                  + "m?ximo n?mero de iteraciones: " + iteractionMax
            //                                  + "Inicio de los patrones: " + inicio);

        }

        //Creamos la red con todos los datos

        final PatronData manejador = new PatronData(idCompany);
        ArrayList<BigDecimal[]> inputs = new ArrayList<BigDecimal[]>();
        ArrayList<BigDecimal[]> desiredOutputs = new ArrayList<BigDecimal[]>();
        

        //Solapados
        if (this.comboBox_inputType.getSelectedItem().equals(Value.ComboBox.SOLAPADOS)) {
            inputs = manejador.createSolapadoArrayRm(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
            desiredOutputs = manejador.createSolapadoArrayRi(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
        }

        //No solapados
        else if (this.comboBox_inputType.getSelectedItem().equals(Value.ComboBox.NOSOLAPADOS)) {
            inputs = manejador.createNoSolapadoArrayRm(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
            desiredOutputs = manejador.createNoSolapadoArrayRi(numPatrones, inicio, NetworkManager.CNT, numNeuronES);
        }

        //Aleatorios
        else if (this.comboBox_inputType.getSelectedItem().equals(Value.ComboBox.ALEATORIOS)) {
        	ArrayList<ArrayList<Integer>> randomLists = manejador.generateRandomLists(numPatrones, numNeuronES); //It generates the list that contains randoms values equivalent to a positions in the company table
        	inputs = manejador.createRandomArrayRm(NetworkManager.CNT, randomLists);
        	desiredOutputs = manejador.createRandomArrayRi(NetworkManager.CNT, randomLists);
        }

        //Manuales
        else {
            //open a new JFrame
        }

        //Testing collecting data
        String fileName = new String ("C:\\repositoryGit\\Salidas\\ChosenPatrons.csv");
        WriteExcel patrones = new WriteExcel(fileName);
        patrones.writeInputsOutputs(inputs, desiredOutputs);
        patrones.closeFile();
        
        
        //Create and train the Network
//        final NetworkManager ne = new NetworkManager(numPatrones, numNeuronES, numNeuronO, iteractionMax, cotaError, 
//        		learningCnt, inputs, desiredOutputs, bias);
//        ne.training();


    }
}
