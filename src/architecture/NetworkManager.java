package architecture;

import gui.MainWindow;
import gui.TrainingWindow;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import utilities.Matrix;
import utilities.WeightMatrix;
import valueset.LearningConstant;
import outsFiles.*;

public class NetworkManager {

	// Constantes que representan los valores m�ximos y minimos con los que se
	// crean las matrices aletorias
	public static final int MATRIX_MAX = 10, MATRIX_MIN = -10; // CNT por la que
																// multiplican
																// los valores
																// del excel
	public static Matrix previousW, previousV;

	public static final int PRECISION = 10;

	public static final int CNT = 1000;

	private int numPatrones, numNeuronsE, numNeuronsS, // Debido a la tipología
														// de nuestra red Rm
														// ligado a Ri el número
														// de neuronas de
														// entradas
														// siempre será el mismo
														// que el nº de neuronas
														// de salida,
														// exceptuando a redes
														// con bias
	
										numNeuronsO;// if 0, nuestra red es simple
	
	/** numNeuronsO y numNeuronsE incluyen el tamaño del bias en el caso */

	ArrayList<BigDecimal[]> inputs, desiredOutputs;
	private boolean bias;
	private String name; // Lo usaremos para distingirla una vez creada.
	private static Logger log = Logger.getLogger(NetworkManager.class);

	public NetworkManager() {

	}

	// pre: inputs and desiredOutputs deben ser arrays válidos (creados por la
	// clase PatronData, en la interfaz)
	// sizeNetwork: tamaño de la red, representa el número de neuronas de
	// entrada o salida que tendrá la red, sin incluir
	// neurona bias en el caso
	// numNeuronsO: Número de neuronas en la capa oculta, de nuevo sin incluir
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

		log.debug("Creando NetworkManager: " + this.name + " Nº de patrones "
				+ this.numPatrones + " Nº de neuronas de entrada"
				+ this.numNeuronsE + "Nº de neuronas de salida \n"
				+ this.numNeuronsS + " Nº de neuronas ocultas: "
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
			WeightMatrix matrices, boolean momentoBool, double momentoB, String funtion, String directoryName, boolean variableLearning) {
		int contToResetLearn = 0;
		double increment;
		BigDecimal previousError = null;
		increment = learningCNT/2;
		LearningConstant  learningClass = new LearningConstant(learningCNT, increment); 
			
		Matrix W = matrices.getW();
		Matrix V = matrices.getV();
		previousW = matrices.getW();
		previousV = matrices.getV();
		boolean end = false;
		int iteration = 0;
		
		TrainingParameters results = new TrainingParameters();

		String outFile = new String (directoryName+"\\resultsTraining.txt");
		LNTrainingOuts resultados = new LNTrainingOuts(outFile);
		WriteExcel writerErrorProgress = new WriteExcel(directoryName+"\\ErrorProgress.csv"); // Outcomes file
		WriteExcel writerMatrices = new WriteExcel(directoryName+"\\MatricesObtenidas.csv"); // Outcomes file

		while (!end) {
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				// Las actuales W y V ser�an utilizadas en el momento beta de la SIGUIENTE iteraci�n en el for,
				// las debo guardar antes, para poder ser utilizadas en la it siguiente
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronsO, inputs.get(i),
							learningClass.getValue(), desiredOutputs.get(i), W, V, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronsO,
							inputs.get(i), learningClass.getValue(), desiredOutputs.get(i),
							W, V, funtion); // Establecemos la red con el patr�n i
				}
				if (momentoBool) {
					subNetwork.trainWithMomentB(i, momentoB);
				} else {
					subNetwork.train(i);
				}

				W = subNetwork.getW(); // after training, we get the matrix W and V
				V = subNetwork.getV();
				log.debug("Valores de W y V tras actualizaci�n de matriz");
				W.printMatrix(); // logger prints
				V.printMatrix();
			}
			// Comprobado con trazas hasta aqu� everything is working fine
			log.trace("Final del training de todas los patrones. Inicio del c�lculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronsO, inputs.get(i),
							learningCNT, desiredOutputs.get(i), W, V, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronsO,
							inputs.get(i), learningCNT, desiredOutputs.get(i),
							W, V, funtion);
				}

				errorList.add(subNetwork.calculateError());
			}

			// Calculamos el error medio de la iteraci�n
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
			if (iteration == 0){
				previousError = errorIt;
			}
		
			log.debug("Error ponderado en la interaci�n " + iteration + " es " + errorIt);

			if (iteration == iterMax) {
				log.debug("LLegamos al l�mite de las iteraciones. Iteration: "
						+ iteration + " M�ximo: " + iterMax);
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, new WeightMatrix(W, V));
				end = true;
			}
			
			if (MainWindow.cancelTraining) { // Se cancela el  entrenamiento,
				resultados.cancelledTraining(iteration, errorIt , new WeightMatrix(W, V));
				end = true; 
			}
			
			if (errorIt.compareTo(cuote) == -1) { //Error menor que la cota
				resultados.finishedTrainingSuccessfully (iteration, errorIt,cuote, new WeightMatrix(W, V));
				end = true;
				
			} 				
			//if (TrainingWindow.sw.isCancelled()){
			
			//Si nuestro learning es variable, y el contador ha llegado a un n�mero determinado de it, 
			//comprobamos si el error es menor que el �ltimo guardado, si es menor, incrementamos el learning consta,
			
			if ((variableLearning)&&(contToResetLearn == LearningConstant.IT_LIMIT_TO_MODIFY)){
				if (errorIt.compareTo(previousError) == -1){ //el algorimo mejora
					learningClass.setPreviousValue(learningClass.getValue());
					learningClass.incValue();
					previousError = errorIt;
					contToResetLearn = 0;
					System.out.print("Incrementamos constant learning: " +learningClass.getValue()+"\n");
				}else if (errorIt.compareTo(previousError) == 1){ //el algorimo empeora
					learningClass.setPreviousValue(learningClass.getValue());
					learningClass.decValue();
					previousError = errorIt; 
					contToResetLearn = 0; 
					System.out.print("Decrementamos el constant learning: " +learningClass.getValue()+"\n");
				}
			}
			else{
				contToResetLearn++;
			}
		
			
			iteration++;
		} // fin while
		
	//Salimos del bucle
		writerMatrices.writeMatrices(new WeightMatrix(W, V));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
	}

	// pre: this debe de ser una red v�lida, sus atributos no pueden ser nulos
	// Calcula los outputs resultantes con las entradas de la red (inputs)
	// returns: Array con los vectores los cuales se corresponden con los
	// valores de la neuronas de salida de un patr�n
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
							matrices.getV(), "Lineal");
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronsO,
							inputs.get(i), 0.00001, desiredOutputs.get(i),
							matrices.getW(), matrices.getV(), "Lineal");
				}
				subNetwork.feedForward(); // Propagación hacia delante, se
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
	
	
	
	
	
	
	
	
	public void trainingSimplyNetwork (int iterMax, BigDecimal cuote, double learningCNT,
			Matrix W, boolean momentoBool, double momentoB, String funtion, String directoryName) {
		
		previousW = W;
		
		boolean end = false;
		int iteration = 0;
		
		TrainingParameters results = new TrainingParameters();

		String outFile = new String (directoryName+"\\resultsTraining.txt");
		SNTrainingOuts resultados = new SNTrainingOuts(outFile);
		WriteExcel writerErrorProgress = new WriteExcel(directoryName+"\\ErrorProgress.csv"); // Outcomes file
		WriteExcel writerMatrices = new WriteExcel(directoryName+"\\MatricesObtenidas.csv"); // Outcomes file

		while (!end) {
			for (int i = 0; i < inputs.size(); i++) {
				SimplyNetwork subNetwork = new SimplyNetwork();
				// Las actuales W y V ser�an utilizadas en el momento beta de la SIGUIENTE iteraci�n en el for,
				// las debo guardar antes, para poder ser utilizadas en la it siguiente
				if (bias) {
					subNetwork.setUpPatronWithBias(inputs.get(i),
							learningCNT, desiredOutputs.get(i), W, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(inputs.get(i), learningCNT, desiredOutputs.get(i),
							W, funtion); // Establecemos la red con el patr�n i
				}
//				if (momentoBool) {
//					subNetwork.trainWithMomentB(i, momentoB);
//				} else {
					subNetwork.train(i);
				//}
				W = subNetwork.getW(); // after training, we get the matrix W and V
				log.debug("Valores de W tras actualizaci�n de matriz");
				W.printMatrix(); // logger prints
			}
			// Comprobado con trazas hasta aqu� everything is working fine
			log.trace("Final del training de todas los patrones. Inicio del c�lculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				SimplyNetwork subNetwork = new SimplyNetwork();
				if (bias) {
					subNetwork.setUpPatronWithBias(inputs.get(i),
							learningCNT, desiredOutputs.get(i), W, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(inputs.get(i), learningCNT, desiredOutputs.get(i),
							W, funtion);
				}

				errorList.add(subNetwork.calculateError());
			}

			// Calculamos el error medio de la iteraci�n
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
			
			log.debug("Error ponderado en la interaci�n " + iteration + " es " + errorIt);

			if (iteration == iterMax) {
				log.debug("LLegamos al l�mite de las iteraciones. Iteration: "
						+ iteration + " M�ximo: " + iterMax);
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, W);
				end = true;
			}
			
			if (MainWindow.cancelTraining) { // Se cancela el  entrenamiento,
				resultados.cancelledTraining(iteration, errorIt , W);
				end = true; 
			}
			
			if (errorIt.compareTo(cuote) == -1) { //Error menor que la cota
				resultados.finishedTrainingSuccessfully (iteration, errorIt,cuote, W);
				end = true;
				
			} 				
			//if (TrainingWindow.sw.isCancelled()){
			
			
			iteration++;
		} // fin while
		
	//Salimos del bucle
		//writerMatrices.writeMatrices(new WeightMatrix(W, V));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
	}
	
	
	
	
	
	

	

}
