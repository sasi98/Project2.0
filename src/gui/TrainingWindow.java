package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import utilities.Matrix;
import utilities.WeightMatrix;
import architecture.NetworkManager;
import dataManager.ReadFile;

public class TrainingWindow {

    private JInternalFrame frame;
    private JTextField tfcortaError, tflearningCNT, tfmaxIt;
    private JButton btnSelecMatrices, btnIniciarEntrenamiento, btnCancelarEntrenamiento;
    private JComboBox comboBox;
    private JRadioButton rdbtnLineal, rdbtnTangencial, rdbtnSi, rdbtnNo;
    private JTextArea txtrUtilizarMatricesDe;
    private JLabel lblNewLabel;

    private BigDecimal cotaError;
    private double learningCnt;
    private int iterationMax;
    private WeightMatrix matrices;

    /**
     * Create the application.
     */
    public TrainingWindow() {
        initialize();
        createEvents();
    }

    public void initialize() {

        frame = new JInternalFrame();
        frame.setBounds(100, 100, 732, 517);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        txtrUtilizarMatricesDe = new JTextArea();
        txtrUtilizarMatricesDe.setLineWrap(true);
        txtrUtilizarMatricesDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtrUtilizarMatricesDe.setOpaque(false);
        txtrUtilizarMatricesDe.setText("Utilizar matrices de pesos procedentes deentrenamientos anteriores:");
        txtrUtilizarMatricesDe.setBounds(new Rectangle(483, 103, 203, 34));

        frame.getContentPane().add(txtrUtilizarMatricesDe);

        btnSelecMatrices = new JButton("Seleccionar archivo");
        btnSelecMatrices.setBounds(new Rectangle(528, 153, 141, 34));
        frame.getContentPane().add(btnSelecMatrices);

        btnIniciarEntrenamiento = new JButton("Iniciar entrenamiento");
        btnIniciarEntrenamiento.setBounds(new Rectangle(368, 379, 144, 34));
        frame.getContentPane().add(btnIniciarEntrenamiento);

        final JLabel lblSeleccionaRed = new JLabel("Selecciona Red: ");
        lblSeleccionaRed.setBounds(47, 74, 79, 14);
        frame.getContentPane().add(lblSeleccionaRed);

        final JLabel lblFuncion = new JLabel("Funci\u00F3n:  ");
        lblFuncion.setBounds(47, 118, 47, 14);
        frame.getContentPane().add(lblFuncion);

        final JLabel lblAadirMomento = new JLabel("A\u00F1adir Momento Beta: ");
        lblAadirMomento.setBounds(47, 148, 110, 14);
        frame.getContentPane().add(lblAadirMomento);

        final JLabel lblCotaDeError = new JLabel("Cota de error:");
        lblCotaDeError.setBounds(47, 194, 69, 14);
        frame.getContentPane().add(lblCotaDeError);

        final JLabel lblCoeficienteDeAprendizaje = new JLabel("Coeficiente de aprendizaje:");
        lblCoeficienteDeAprendizaje.setBounds(47, 222, 132, 14);
        frame.getContentPane().add(lblCoeficienteDeAprendizaje);

        final JLabel lblMaxIt = new JLabel("M\u00E1ximo n\u00FAmero de iteraciones:");
        lblMaxIt.setBounds(48, 251, 149, 14);
        frame.getContentPane().add(lblMaxIt);

        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(537, 212, 132, 34);
        frame.getContentPane().add(lblNewLabel);

        comboBox = new JComboBox();
        for (final NetworkManager ne : MainWindow.neList) { //Añadimos las instancias creadas al ComBox
            comboBox.addItem(ne.getName());
        }
        comboBox.setBounds(new Rectangle(261, 71, 125, 20));
        frame.getContentPane().add(comboBox);

        rdbtnLineal = new JRadioButton("Lineal");
        rdbtnLineal.setBounds(new Rectangle(258, 114, 53, 23));
        frame.getContentPane().add(rdbtnLineal);

        rdbtnTangencial = new JRadioButton("Tangencial");
        rdbtnTangencial.setBounds(new Rectangle(333, 114, 77, 23));
        frame.getContentPane().add(rdbtnTangencial);

        final ButtonGroup groupFuncion = new ButtonGroup(); //To get just one selected at the time
        groupFuncion.add(rdbtnTangencial);
        groupFuncion.add(rdbtnLineal);

        rdbtnSi = new JRadioButton("Si");
        rdbtnSi.setBounds(new Rectangle(258, 144, 33, 23));
        frame.getContentPane().add(rdbtnSi);

        rdbtnNo = new JRadioButton("No");
        rdbtnNo.setBounds(new Rectangle(333, 144, 39, 23));
        frame.getContentPane().add(rdbtnNo);

        final ButtonGroup groupSiNo = new ButtonGroup();
        groupSiNo.add(rdbtnSi);
        groupSiNo.add(rdbtnNo);

        tfcortaError = new JTextField();
        tfcortaError.setColumns(10);
        tfcortaError.setBounds(new Rectangle(261, 191, 86, 20));
        frame.getContentPane().add(tfcortaError);

        tflearningCNT = new JTextField();
        tflearningCNT.setColumns(10);
        tflearningCNT.setBounds(new Rectangle(261, 219, 86, 20));
        frame.getContentPane().add(tflearningCNT);

        tfmaxIt = new JTextField();
        tfmaxIt.setColumns(10);
        tfmaxIt.setBounds(new Rectangle(261, 247, 86, 20));
        frame.getContentPane().add(tfmaxIt);

        btnCancelarEntrenamiento = new JButton("Cancelar entrenamiento");
        btnCancelarEntrenamiento.setBounds(547, 379, 141, 34);
        frame.getContentPane().add(btnCancelarEntrenamiento);

    }

    private void createEvents() {
        btnIniciarEntrenamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                btnIniciarEntrenamientoActionPerformed();
            }
        });

        btnSelecMatrices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                btnNewButtonActionPerformed();
            }
        });

        btnCancelarEntrenamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                btnCancelarEntrenamientoActionPerformed();
            }
        });

    }

    private void btnIniciarEntrenamientoActionPerformed() {

        final String stCotaError = tfcortaError.getText();
        if ((stCotaError == null) || (stCotaError.equals(""))) {
            tfcortaError.setText("0.001");
            cotaError = new BigDecimal(0.001);
            cotaError.setScale(NetworkManager.PRECISION, RoundingMode.HALF_UP);

        } else {
            cotaError = new BigDecimal(stCotaError);
            cotaError.setScale(NetworkManager.PRECISION);
        }

        final String stCnsLearning = tflearningCNT.getText();
        if ((stCnsLearning == null) || (stCnsLearning.equals(""))) {
            tflearningCNT.setText("0.00001");
            learningCnt = 0.00001;

        } else {
            learningCnt = Double.parseDouble(stCnsLearning);
        }

        final String stItMax = tfmaxIt.getText();
        if ((stItMax == null) || (stItMax.equals(""))) {
            tfmaxIt.setText("1000");
            iterationMax = 1000;

        } else {
            iterationMax = Integer.parseInt(stItMax);
        }

        NetworkManager aux2 = null;
        for (final NetworkManager aux : MainWindow.neList) {
            if (aux.getName().equals(comboBox.getSelectedItem())) {
                aux2 = aux; //Controlar que se halla elegido alguna, de lo contrario tendremos null exceptions
            }
        }
        final NetworkManager ne = aux2;

        if (matrices == null) {
            //No fueron seleccionadas de archivo, deben de ser creadas aletorias
            //Las creo y se las paso al trainnig junto con el resto de parámetros
            final Dimension dW = new Dimension(ne.getNumNeuronsO(), ne.getNumNeuronsES());
            final Matrix W = Matrix.createRandomMatrix(NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dW, NetworkManager.PRECISION);
            final Dimension dV = new Dimension(ne.getNumNeuronsES(), ne.getNumNeuronsO());
            final Matrix V = Matrix.createRandomMatrix(NetworkManager.MATRIX_MIN, NetworkManager.MATRIX_MAX, dV, NetworkManager.PRECISION);
            final WeightMatrix matrices = new WeightMatrix(W, V);

            final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ne.training(iterationMax, cotaError, learningCnt, matrices);
                    return null;
                }
            };

            worker.execute();

        } else {
            //se las paso al trainnig directamente junto con el resto de parámetros

            final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ne.training(iterationMax, cotaError, learningCnt, matrices);
                    return null;
                }
            };

            worker.execute();
        }

    }

    private void btnNewButtonActionPerformed() {
        final JFileChooser filechooser = new JFileChooser("C:\\repositoryGit\\Salidas");
        final int returnValue = filechooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            final File filechoosen = filechooser.getSelectedFile();
            try {
                final ReadFile readWM = new ReadFile(filechoosen);
                matrices = readWM.readWeightMatrix();
                lblNewLabel.setText(filechoosen.getName());

            } catch (final FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            System.out.println("Open command cancelled by user.");
        }

    }

    private void btnCancelarEntrenamientoActionPerformed() {
        MainWindow.cancelTraining = true;
    }

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setFrame(final JInternalFrame frame) {
        this.frame = frame;
    }
}
