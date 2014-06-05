package architecture;

import gui.MainWindow;
import gui.TrainingWindow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;


//import dataManager.WriteOutcomes;
import utilities.Matrix;
import utilities.WeightMatrix;
import dataManager.TrainingWindowOuts;
import dataManager.WriteExcel;

public class NetworkManager {

	// Constantes que representan los valores mï¿½ximos y minimos con los que se
	// crean las matrices aletorias
	public static final int MATRIX_MAX = 10, MATRIX_MIN = -10; // CNT por la que
																// multiplican
																// los valores
																// del excel
	public static Matrix previousW, previousV;

	public static final int PRECISION = 10;

	public static final int CNT = 1000;

	private int numPatrones, numNeuronsE, numNeuronsS, // Debido a la tipologÃ­a
														// de nuestra red Rm
														// ligado a Ri el nÃºmero
														// de neuronas de
														// entradas
														// siempre serÃ¡ el mismo
														// que el nÂº de neuronas
														// de salida,
														// exceptuando a redes
														// con bias
										numNeuronsO;
	/** numNeuronsO y numNeuronsE incluyen el tamaÃ±o del bias en el caso */

	ArrayList<BigDecimal[]> inputs, desiredOutputs;
	private boolean bias;
	private String name; // Lo usaremos para distingirla una vez creada.
	private static Logger log = Logger.getLogger(NetworkManager.class);

	public NetworkManager() {

	}

	// pre: inputs and desiredOutputs deben ser arrays vÃ¡lidos (creados por la
	// clase PatronData, en la interfaz)
	// sizeNetwork: tamaÃ±o de la red, representa el nÃºmero de neuronas de
	// entrada o salida que tendrÃ¡ la red, sin incluir
	// neurona bias en el caso
	// numNeuronsO: NÃºmero de neuronas en la capa oculta, de nuevo sin incluir
	// la neurona bias en el caso
	public NetworkManager(String name, int numPatrones, int sizeNetwork,
			int numNeuronsO, ArrayList<BigDecimal[]> inputs,
			ArrayList<BigDecimal[]> desiredOutputs, boolean bias) {

		super();
		this.name = name;
		this.numPatrones = numPatrones;
		this.numNeuronsS = sizeNetwork;
		if (bias) {
			this.numNeuronsE = sizeNetwork + 1;
			this.numNeuronsO = numNeuronsO + 1;
		} else {
			this.numNeuronsE = sizeNetwork;
			this.numNeuronsO = numNeuronsO;
		}
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;
		this.bias = bias;

		log.debug("Creando NetworkManager: " + this.name + " NÂº de patrones "
				+ this.numPatrones + " NÂº de neuronas de entrada"
				+ this.numNeuronsE + "NÂº de neuronas de salida \n"
				+ this.numNeuronsS + " NÂº de neuronas ocultas: "
				+ this.numNeuronsO + "Bias: " + bias);

	}

	// GETTERS

	public int getNumPatrones() {
		return numPatrones;
	}

	public String getName() {
		return name;
	}

	public int getNumNeuronsO() {
		return numNeuronsO;
	}

	public ArrayList<BigDecimal[]> getInputs() {
		return inputs;
	}

	public ArrayList<BigDecimal[]> getDesiredOutputs() {
		return desiredOutputs;
	}

	public int getNumNeuronsE() {
		return numNeuronsE;
	}

	public int getNumNeuronsS() {
		return numNeuronsS;
	}

	// SETTERS

	public void setNumPatrones(int numPatrones) {
		this.numPatrones = numPatrones;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumNeuronsO(int numNeuronsO) {
		this.numNeuronsO = numNeuronsO;
	}

	public void setInputs(ArrayList<BigDecimal[]> inputs) {
		this.inputs = inputs;
	}

	public void setDesiredOutputs(ArrayList<BigDecimal[]> desiredOutputs) {
		this.desiredOutputs = desiredOutputs;
	}

	public void setNumNeuronsE(int numNeuronsE) {
		this.numNeuronsE = numNeuronsE;
	}

	public void setNumNeuronsS(int numNeuronsS) {
		this.numNeuronsS = numNeuronsS;
	}


	public void training (int iterMax, BigDecimal cuote, double learningCNT,
			WeightMatrix matrices, boolean momentoB) {
		Matrix W = matrices.getW();
		Matrix V = matrices.getV();
		previousW = matrices.getW();
		previousV = matrices.getV();
		boolean end = false;
		int iteration = 0;
		
		TrainingParameters results = new TrainingParameters();
		WriteExcel writerErrorProgress = new WriteExcel(
				"C:\\repositoryGit\\Salidas\\ErrorProgress.csv"); // Outcomes file
		WriteExcel writerMatrices = new WriteExcel(
				"C:\\repositoryGit\\Salidas\\MatricesObtenidas.csv"); // Outcomes file

		while (!end) {
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				// Las actuales W y V serían utilizadas en el momento beta de la SIGUIENTE iteración en el for,
				// las debo guardar antes, para poder ser utilizadas en la it siguiente
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronsO, inputs.get(i),
							learningCNT, desiredOutputs.get(i), W, V);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronsO,
							inputs.get(i), learningCNT, desiredOutputs.get(i),
							W, V); // Establecemos la red con el patrï¿½n i
				}
				if (momentoB) {
					subNetwork.trainWithMomentB(i);
				} else {
					subNetwork.train(i);
				}

				W = subNetwork.getW(); // after training, we get the matrix W and V
				V = subNetwork.getV();
				log.debug("Valores de W y V tras actualizaciï¿½n de matriz");
				W.printMatrix(); // logger prints
				V.printMatrix();
			}
			// Comprobado con trazas hasta aquí everything is working fine
			log.trace("Final del training de todas los patrones. Inicio del cálculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronsO, inputs.get(i),
							learningCNT, desiredOutputs.get(i), W, V);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronsO,
							inputs.get(i), learningCNT, desiredOutputs.get(i),
							W, V);
				}

				errorList.add(subNetwork.calculateError());
			}

			// Calculamos el error medio de la iteración
			BigDecimal errorIt = new BigDecimal(0);
			for (BigDecimal error : errorList) {
				errorIt = errorIt.add(error);
			}
			errorIt = errorIt.divide(new BigDecimal(errorList.size()),
					RoundingMode.HALF_UP);
			errorIt = errorIt.setScale(PRECISION, RoundingMode.HALF_UP);

			// Add current error, matrix and iteration in memory and in results class
			TrainingWindow.errorGraph.put(iteration, errorIt);
			writerErrorProgress.writeError(errorIt, iteration);
			
			log.debug("Error ponderado en la interación " + iteration + " es " + errorIt);

			if (iteration == iterMax) {
				log.debug("LLegamos al límite de las iteraciones. Iteration: "
						+ iteration + " Máximo: " + iterMax);
				results.setStatus(0);
				String outFile = new String ("C:\\repositoryGit\\Salidas\\resultsTraining.txt"); 
				TrainingWindowOuts resultados = new TrainingWindowOuts(outFile);
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, new WeightMatrix(W, V));
				end = true;
			}
			if (errorIt.compareTo(cuote) == -1) { //Error menor que la cota
				results.setStatus(1);
				log.debug("El error ha alcanzado la cota.");
				String outFile = new String ("C:\\repositoryGit\\Salidas\\resultsTraining.txt");
				TrainingWindowOuts resultados = new TrainingWindowOuts(outFile);
				resultados.finishedTrainingSuccessfully (iteration, errorIt,cuote, new WeightMatrix(W, V));
				end = true;
				
			} 				
			//if (TrainingWindow.sw.isCancelled()){
			if (MainWindow.cancelTraining) { // Se cancela el  entrenamiento,
				String outFile = new String ("C:\\repositoryGit\\Salidas\\resultsTraining.txt"); //
				TrainingWindowOuts resultados = new TrainingWindowOuts(outFile);
				resultados.cancelledTraining(iteration, errorIt , new WeightMatrix(W, V));
				end = true; 
			}
			
			iteration++;
		} // fin while
		
	//Salimos del bucle
		writerMatrices.writeMatrices(new WeightMatrix(W, V));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
	}

	// pre: this debe de ser una red vï¿½lida, sus atributos no pueden ser nulos
	// Calcula los outputs resultantes con las entradas de la red (inputs)
	// returns: Array con los vectores los cuales se corresponden con los
	// valores de la neuronas de salida de un patrï¿½n
	public ArrayList<BigDecimal[]> calculateOutputs(WeightMatrix matrices) {
		log.info("Calculate Outputs");
		if ((matrices.getW().getRow() == numNeuronsO)
				&& (matrices.getW().getColumn() == numNeuronsE)
				&& (matrices.getV().getRow() == numNeuronsS)
				&& (matrices.getV().getColumn() == numNeuronsO)) {
			ArrayList<BigDecimal[]> outputs = new ArrayList<>();
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				// We are not using learning constant, we ignore his value
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronsO, inputs.get(i),
							0.00001, desiredOutputs.get(i), matrices.getW(),
							matrices.getV());
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronsO,
							inputs.get(i), 0.00001, desiredOutputs.get(i),
							matrices.getW(), matrices.getV());
				}
				subNetwork.feedForward(); // PropagaciÃ³n hacia delante, se
											// calculan las salidas
				OutputNeuron[] auxNeurons = subNetwork.getOutputLayer();
				BigDecimal[] aux = new BigDecimal[auxNeurons.length];
				for (int j = 0; j < auxNeurons.length; j++) { // Obtenemos el
																// vector de
																// neuronas de
																// salida, y
																// pasamos sus
																// valores a un
																// vector
					aux[j] = auxNeurons[j].getOutValue();
				}
				outputs.add(aux);
			}
			return outputs;
		} else {
			log.error("La estructura de la red no coincide con las de las matrices seleccionadas."
					+ " No es posible calcular las salidas");
			return null;
		}

	}

	

}
