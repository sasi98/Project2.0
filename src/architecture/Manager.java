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

public class Manager {

	public static final int 				CNT = 1000,  		/**Valor CNT por el que se multiplican los valores del excel*/
											PRECISION = 10,     /**Valor que indica el número de dígitos decimales que tienes los datos*/
											MATRIX_MAX = 10,    /**Valor máximo para la creación de matrices de pesos aleatorias*/ 
											MATRIX_MIN = -10;   /**Valor minimo para la creación de matrices de pesos aleatorias*/
	
	public static Matrix 					previousW, 
											previousV;
	
	private StructureParameters 			structurePar;
	private TrainingParameters 				trainPar;
	
	
	private TrainingResults 				results;
	
	private static Logger log = Logger.getLogger(Manager.class);

	
	
	public Manager(StructureParameters structurePar, TrainingParameters trainPar) {
		super();
		this.structurePar= structurePar;
		this.trainPar = trainPar;
	}

	public TrainingResults training (String directoryName){ 
		int contToResetLearn = 0;
		double increment;
		BigDecimal  previousError = null,
					errorfinal = null; //Error obtenido al terminar el entrenamiento
		int state = 0;
//		increment = learningCNT/2;

		/**Sacamos los parámetros de la estructura que usaremos*/
		ArrayList<BigDecimal[]> inputs = structurePar.getInputs();
		ArrayList<BigDecimal[]> desiredOutputs = structurePar.getDesiredOutputs();
		int numNeuronO = structurePar.getNumNeuronsO();
		boolean bias = structurePar.hasBias();
		
		/**Sacamos los parámetros de entrenamiento*/
		String funtion = trainPar.getFuncion(); 
		Matrix W = trainPar.getMatrices().getW();
		Matrix V = trainPar.getMatrices().getV();
		LearningConstant learning = trainPar.getLearning();
		int iterMax = trainPar.getIterMax();
		BigDecimal cuote = trainPar.getCuoteError();
		double momentoValue = 0;
		if (trainPar.isMomentoB()){
			momentoValue = trainPar.getMomentoBvalue();
		}

		previousW = W;			/**Las actuales W y V serían utilizadas en el momento beta de la SIGUIENTE iteración en el for,*/
		previousV = V;			/**las debo guardar antes, para poder ser utilizadas en la it siguiente*/
		boolean end = false;
		int iteration = 0;

		String outFile = new String (directoryName+"\\resultsTraining.txt");
		LNTrainingOuts resultados = new LNTrainingOuts(outFile);
		WriteFile writerErrorProgress = new WriteFile(directoryName+"\\ErrorProgress.csv"); // Outcomes file
		WriteFile writerMatrices = new WriteFile(directoryName+"\\MatricesObtenidas.csv"); // Outcomes file

		while (!end) {
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();    
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronO, inputs.get(i), learning.getValue(), desiredOutputs.get(i), W, V, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronO,inputs.get(i), learning.getValue(), desiredOutputs.get(i), W, V, funtion);
				}
				if (trainPar.isMomentoB()) {
					subNetwork.trainWithMomentB(i, momentoValue);
				} else {
					subNetwork.train(i);
				}

				W = subNetwork.getW(); /**Después del entrenamiento, cogemos las matrices W y V*/ 
				V = subNetwork.getV();
				log.debug("Valores de W y V tras actualización de matriz");
				W.printMatrix();   //Logger prints
				V.printMatrix();
			}
			log.trace("Final del training de todas los patrones. Inicio del cálculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronO, inputs.get(i),
							learning.getValue(), desiredOutputs.get(i), W, V, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronO,inputs.get(i), learning.getValue(), desiredOutputs.get(i), W, V, funtion);
				}
				errorList.add(subNetwork.calculateError());
			}

			/**Calculamos el error medio de la iteración*/
			BigDecimal errorIt = new BigDecimal(0);
			for (BigDecimal error : errorList) {
				errorIt = errorIt.add(error);
			}
			errorIt = errorIt.divide(new BigDecimal(errorList.size()), RoundingMode.HALF_UP);
			errorIt = errorIt.setScale(PRECISION, RoundingMode.HALF_UP);

			// Add current error, matrix and iteration in memory and in results class
			TrainingWindow.errorGraph.put(iteration, errorIt);
			writerErrorProgress.writeError(errorIt, iteration);
			if (iteration == 0){
				previousError = errorIt;
			}
		
			log.debug("Error ponderado en la interación " + iteration + " es " + errorIt);
			errorfinal = errorIt; //Por si decidimos finalizar el entrenamiento

			if (iteration == iterMax) {
				log.debug("LLegamos al límite de las iteraciones. Iteration: " + iteration + " Máximo: " + iterMax);
				state = 0;
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, new WeightMatrix(W, V));
				end = true;
			}
			if (MainWindow.cancelTraining) { // Se cancela el  entrenamiento,
				log.debug("Se cancela el entrenamiento en la iteración" + iteration);
				state = 1;
				resultados.cancelledTraining(iteration, errorIt , new WeightMatrix(W, V));
				end = true; 
			}
			if (errorIt.compareTo(cuote) == -1) { //Error menor que la cota
				resultados.finishedTrainingSuccessfully (iteration, errorIt,cuote, new WeightMatrix(W, V));
				state = 2;
				end = true;
				
			} 				
			//Si nuestro learning es variable, y el contador ha llegado a un número determinado de it, 
			//comprobamos si el error es menor que el último guardado, si es menor, incrementamos el learning consta
			
//			if ((variableLearning)&&(contToResetLearn == LearningConstant.IT_LIMIT_TO_MODIFY)){
//				if (errorIt.compareTo(previousError) == -1){ //el algorimo mejora
//					learningClass.setPreviousValue(learningClass.getValue());
//					learningClass.incValue();
//					previousError = errorIt;
//					contToResetLearn = 0;
//					System.out.print("Incrementamos constant learning: " +learningClass.getValue()+"\n");
//				}else if (errorIt.compareTo(previousError) == 1){ //el algorimo empeora
//					learningClass.setPreviousValue(learningClass.getValue());
//					learningClass.decValue();
//					previousError = errorIt; 
//					contToResetLearn = 0; 
//					System.out.print("Decrementamos el constant learning: " +learningClass.getValue()+"\n");
//				}
//			}
//			else{
//				contToResetLearn++;
//			}
			iteration++;
		} // fin while
		
		
		//Salimos del bucle
		results = new TrainingResults(iteration-1, errorfinal, state, new WeightMatrix(W, V));
		writerMatrices.writeMatrices(new WeightMatrix(W, V));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
		return results;
	}

//	// pre: this debe de ser una red vï¿½lida, sus atributos no pueden ser nulos
//	// Calcula los outputs resultantes con las entradas de la red (inputs)
//	// returns: Array con los vectores los cuales se corresponden con los
//	// valores de la neuronas de salida de un patrï¿½n
//	public ArrayList<BigDecimal[]> calculateOutputs(WeightMatrix matrices) {
//		log.info("Calculate Outputs");
//		if ((matrices.getW().getRow() == numNeuronsO)
//				&& (matrices.getW().getColumn() == numNeuronsE)
//				&& (matrices.getV().getRow() == numNeuronsS)
//				&& (matrices.getV().getColumn() == numNeuronsO)) {
//			ArrayList<BigDecimal[]> outputs = new ArrayList<>();
//			for (int i = 0; i < inputs.size(); i++) {
//				Network subNetwork = new Network();
//				// We are not using learning constant, we ignore his value
//				if (bias) {
//					subNetwork.setUpPatronWithBias(numNeuronsO, inputs.get(i),
//							0.00001, desiredOutputs.get(i), matrices.getW(),
//							matrices.getV(), "Lineal");
//				} else {
//					subNetwork.setUpPatronWithoutBias(numNeuronsO,
//							inputs.get(i), 0.00001, desiredOutputs.get(i),
//							matrices.getW(), matrices.getV(), "Lineal");
//				}
//				subNetwork.feedForward(); // PropagaciÃ³n hacia delante, se
//											// calculan las salidas
//				OutputNeuron[] auxNeurons = subNetwork.getOutputLayer();
//				BigDecimal[] aux = new BigDecimal[auxNeurons.length];
//				for (int j = 0; j < auxNeurons.length; j++) { // Obtenemos el
//																// vector de
//																// neuronas de
//																// salida, y
//																// pasamos sus
//																// valores a un
//																// vector
//					aux[j] = auxNeurons[j].getOutValue();
//				}
//				outputs.add(aux);
//			}
//			return outputs;
//		} else {
//			log.error("La estructura de la red no coincide con las de las matrices seleccionadas."
//					+ " No es posible calcular las salidas");
//			return null;
//		}
//
//	}
//	
//	
//	
//	
//	
//	
	
	
	public TrainingResults trainingSimplyNetwork (String directoryName) {
		
		int contToResetLearn = 0, 
				state = 0;
		double increment;
		BigDecimal 	previousError = null,
					errorfinal = null;
//		increment = learningCNT/2;
		

		/**Sacamos los parámetros de la estructura que usaremos*/
		ArrayList<BigDecimal[]> inputs = structurePar.getInputs();
		ArrayList<BigDecimal[]> desiredOutputs = structurePar.getDesiredOutputs();
		boolean bias = structurePar.hasBias();
		
		/**Sacamos los parámetros de entrenamiento*/
		String funtion = trainPar.getFuncion();
		Matrix W = trainPar.getMatrices().getW();
		LearningConstant learning = trainPar.getLearning();
		int iterMax = trainPar.getIterMax();
		BigDecimal cuote = trainPar.getCuoteError();
		double momentoValue = 0;
		if (trainPar.isMomentoB()){
			momentoValue = trainPar.getMomentoBvalue();
		}
		previousW = W;			/**La actual W seria utilizada en el momento beta de la SIGUIENTE iteración en el for,*/
								/**la debo guardar antes, para poder ser utilizada en la it siguiente*/
		boolean end = false;
		int iteration = 0;

		String outFile = new String (directoryName+"\\resultsTraining.txt");
		SNTrainingOuts resultados = new SNTrainingOuts(outFile);
		WriteFile writerErrorProgress = new WriteFile(directoryName+"\\ErrorProgress.csv"); // Outcomes file
		WriteFile writerMatrices = new WriteFile(directoryName+"\\MatricesObtenidas.csv"); // Outcomes file

		while (!end) {
			for (int i = 0; i < inputs.size(); i++) {
				SimplyNetwork subNetwork = new SimplyNetwork();
				if (bias) {
					subNetwork.setUpPatronWithBias(inputs.get(i),
							learning.getValue(), desiredOutputs.get(i), W, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(inputs.get(i), learning.getValue(), desiredOutputs.get(i),
							W, funtion);
				}
//				if (momentoBool) {
//					subNetwork.trainWithMomentB(i, momentoB);
//				} else {
					subNetwork.train(i);
				//}
				W = subNetwork.getW();
				log.debug("Valores de W tras actualización de matriz");
				W.printMatrix(); 	//Logger prints
			}
			log.trace("Final del training de todas los patrones. Inicio del cálculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				SimplyNetwork subNetwork = new SimplyNetwork();
				if (bias) {
					subNetwork.setUpPatronWithBias(inputs.get(i),
							learning.getValue(), desiredOutputs.get(i), W, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(inputs.get(i), learning.getValue(), desiredOutputs.get(i), W, funtion);
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
				log.debug("LLegamos al límite de las iteraciones. Iteration: " + iteration + " Máximo: " + iterMax);
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, W);
				state = 0;
				end = true;
			}
			if (MainWindow.cancelTraining) {
				resultados.cancelledTraining(iteration, errorIt , W);
				state = 1;
				end = true; 
			}
			if (errorIt.compareTo(cuote) == -1) {
				resultados.finishedTrainingSuccessfully (iteration, errorIt,cuote, W);
				state = 2;
				end = true;
				
			}
			iteration++;
		} // fin while
		

		//writerMatrices.writeMatrices(new WeightMatrix(W, V));
		results = new TrainingResults(iteration-1, errorfinal, state, new WeightMatrix(W, null));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
		return results;
	}
	
	
	
	
	
	

	

}
