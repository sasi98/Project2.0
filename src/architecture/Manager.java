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
import valueset.Value;
import outsFiles.*;

public class Manager {

	public static final int 				CNT = 100,  		/**Valor CNT por el que se multiplican los valores del excel*/
											PRECISION = 10,     /**Valor que indica el n�mero de d�gitos decimales que tienes los datos*/
											MATRIX_MAX = 1,    /**Valor m�ximo para la creaci�n de matrices de pesos aleatorias*/ 
											MATRIX_MIN = -1;   /**Valor minimo para la creaci�n de matrices de pesos aleatorias*/
	
	public static Matrix 					previousW, 
											previousV;
	
	private static Logger log = Logger.getLogger(Manager.class);

	public Manager (){
		
	}

	public static TrainingResults training (String directoryName, StructureParameters structurePar, TrainingParameters trainPar){ 
		int contToResetLearn = 0;
		double increment;
		BigDecimal  previousError = null,
					errorfinal = null; //Error obtenido al terminar el entrenamiento
		int state = 0;
//		increment = learningCNT/2;

		/**Sacamos los par�metros de la estructura que usaremos*/
		ArrayList<BigDecimal[]> inputs = structurePar.getInputs();
		ArrayList<BigDecimal[]> desiredOutputs = structurePar.getDesiredOutputs();
		int numNeuronO = structurePar.getNumNeuronsO();
		boolean bias = structurePar.hasBias();
		
		/**Sacamos los par�metros de entrenamiento*/
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

		previousW = W;			/**Las actuales W y V ser�an utilizadas en el momento beta de la SIGUIENTE iteraci�n en el for,*/
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
					subNetwork.setUpPatronWithBias(numNeuronO, inputs.get(i),desiredOutputs.get(i), W, V, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronO,inputs.get(i),desiredOutputs.get(i), W, V, funtion);
				}
				if (trainPar.isMomentoB()) {
					subNetwork.trainWithMomentB(i, momentoValue, learning.getValue());
				} else {
					subNetwork.train(i, learning.getValue());
				}

				W = subNetwork.getW(); /**Despu�s del entrenamiento, cogemos las matrices W y V*/ 
				V = subNetwork.getV();
				log.debug("Valores de W y V tras actualizaci�n de matriz");
				W.printMatrix();   //Logger prints
				V.printMatrix();
			}
			log.trace("Final del training de todas los patrones. Inicio del c�lculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				if (bias) {
					subNetwork.setUpPatronWithBias(numNeuronO, inputs.get(i), desiredOutputs.get(i), W, V, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(numNeuronO,inputs.get(i), desiredOutputs.get(i), W, V, funtion);
				}
				errorList.add(subNetwork.calculateError());
			}

			/**Calculamos el error medio de la iteraci�n*/
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
		
			log.debug("Error ponderado en la interaci�n " + iteration + " es " + errorIt);
			errorfinal = errorIt; //Por si decidimos finalizar el entrenamiento

			if (iteration == iterMax) {
				log.debug("LLegamos al l�mite de las iteraciones. Iteration: " + iteration + " M�ximo: " + iterMax);
				state = 0;
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, new WeightMatrix(W, V));
				end = true;
			}
			if (MainWindow.cancelTraining) { // Se cancela el  entrenamiento,
				log.debug("Se cancela el entrenamiento en la iteraci�n" + iteration);
				state = 1;
				resultados.cancelledTraining(iteration, errorIt , new WeightMatrix(W, V));
				end = true; 
			}
			if (errorIt.compareTo(cuote) == -1) { //Error menor que la cota
				resultados.finishedTrainingSuccessfully (iteration, errorIt,cuote, new WeightMatrix(W, V));
				state = 2;
				end = true;
				
			} 				
			//Si nuestro learning es variable, y el contador ha llegado a un n�mero determinado de it, 
			//comprobamos si el error es menor que el �ltimo guardado, si es menor, incrementamos el learning consta
			
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
		TrainingResults results = new TrainingResults(iteration-1, errorfinal, state, new WeightMatrix(W, V));
		writerMatrices.writeMatrices(new WeightMatrix(W, V));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
		return results;
	}
	
	public static TrainingResults trainingSimplyNetwork (String directoryName, StructureParameters structurePar, TrainingParameters trainPar) {
		
		int contToResetLearn = 0, 
				state = 0;
		double increment;
		BigDecimal 	previousError = null,
					errorfinal = null;
//		increment = learningCNT/2;
		

		/**Sacamos los par�metros de la estructura que usaremos*/
		ArrayList<BigDecimal[]> inputs = structurePar.getInputs();
		ArrayList<BigDecimal[]> desiredOutputs = structurePar.getDesiredOutputs();
		boolean bias = structurePar.hasBias();
		
		/**Sacamos los par�metros de entrenamiento*/
		String funtion = trainPar.getFuncion();
		Matrix W = trainPar.getMatrices().getW();
		LearningConstant learning = trainPar.getLearning();
		int iterMax = trainPar.getIterMax();
		BigDecimal cuote = trainPar.getCuoteError();
		double momentoValue = 0;
		if (trainPar.isMomentoB()){
			momentoValue = trainPar.getMomentoBvalue();
		}
		previousW = W;			/**La actual W seria utilizada en el momento beta de la SIGUIENTE iteraci�n en el for,*/
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
					subNetwork.setUpPatronWithBias(inputs.get(i), desiredOutputs.get(i), W, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(inputs.get(i), desiredOutputs.get(i),	W, funtion);
				}
				if (trainPar.isMomentoB()) {
					subNetwork.trainWithMomentB(i, momentoValue, learning.getValue());
				} else {
					subNetwork.train(i, learning.getValue());
				}
				W = subNetwork.getW();
				log.debug("Valores de W tras actualizaci�n de matriz");
				W.printMatrix(); 	//Logger prints
			}
			log.trace("Final del training de todas los patrones. Inicio del c�lculo del error");
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i < inputs.size(); i++) {
				SimplyNetwork subNetwork = new SimplyNetwork();
				if (bias) {
					subNetwork.setUpPatronWithBias(inputs.get(i), desiredOutputs.get(i), W, funtion);
				} else {
					subNetwork.setUpPatronWithoutBias(inputs.get(i), desiredOutputs.get(i), W, funtion);
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
			
			errorfinal = errorIt; //Por si decidimos finalizar el entrenamiento
			if (iteration == iterMax) {
				log.debug("LLegamos al l�mite de las iteraciones. Iteration: " + iteration + " M�ximo: " + iterMax);
				resultados.finishedTrainingByMaxIt(iteration, errorIt, cuote, W);
				state = 0;
				end = true;
			}
			if (MainWindow.cancelTraining) {
				resultados.cancelledTraining(iteration, errorIt , W);
				System.out.print("Entramiento cancelado, red simple");
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
		
		writerMatrices.writeMatrices(new WeightMatrix(W, null));
		writerMatrices.closeFile();
		writerErrorProgress.closeFile();
		TrainingResults results = new TrainingResults(iteration-1, errorfinal, state, new WeightMatrix(W, null));
	
		return results;
	}

	
// pre: this debe de ser una red v�lida, sus atributos no pueden ser nulos. Las dimensiones de las matrices del fichero y la estructura 
	//de la red debe coincidir
//	// Calcula los outputs resultantes con las entradas de la red (inputs)
//	// returns: Array con los vectores los cuales se corresponden con los
//	// valores de la neuronas de salida de un patr�n
	public static ArrayList<BigDecimal[]> calculateOutputs (StructureParameters structurePar, WeightMatrix matrices, String function) {
		log.info("Calculate Outputs");
		
		ArrayList<BigDecimal[]> outputs = new ArrayList<>();
		/**Sacamos los par�metros de la estructura que usaremos*/
		ArrayList<BigDecimal[]> inputs = structurePar.getInputs();
		ArrayList<BigDecimal[]> desiredOutputs = structurePar.getDesiredOutputs();
		int numNeuronO = structurePar.getNumNeuronsO();
		boolean bias = structurePar.hasBias();
		
		
		if (structurePar.getTypeNet().equals(Value.RedType.MONOCAPA)){
			for (int i = 0; i < inputs.size(); i++) {
				SimplyNetwork subNetwork = new SimplyNetwork();
				if (bias) 
					subNetwork.setUpPatronWithBias (inputs.get(i), desiredOutputs.get(i), matrices.getW(), function);
				else 
					subNetwork.setUpPatronWithoutBias (inputs.get(i), desiredOutputs.get(i), matrices.getW(), function);
				
				subNetwork.feedForward(); 	/**Propagaci�n hacia delante, se calculan las salidas*/
				OutputNeuron[] auxNeurons = subNetwork.getOutputLayer();
				BigDecimal[] aux = new BigDecimal[auxNeurons.length];
				for (int j = 0; j < auxNeurons.length; j++) { /** Obtenemos el vector de neuronas de salida y creamos un vector con los valores de cada neurona otenida*/
					aux[j] = auxNeurons[j].getOutValue();
				}
				outputs.add(aux);
			}
			
		}else if (structurePar.getTypeNet().equals(Value.RedType.MULTICAPA)){
			for (int i = 0; i < inputs.size(); i++) {
				Network subNetwork = new Network();
				if (bias) 
					subNetwork.setUpPatronWithBias(numNeuronO, inputs.get(i), desiredOutputs.get(i), matrices.getW(), matrices.getV(), function);
				else 
					subNetwork.setUpPatronWithoutBias(numNeuronO,inputs.get(i), desiredOutputs.get(i), matrices.getW(), matrices.getV(), function);
				
				subNetwork.feedForward(); 	/**Propagaci�n hacia delante, se calculan las salidas*/
				OutputNeuron[] auxNeurons = subNetwork.getOutputLayer();
				BigDecimal[] aux = new BigDecimal[auxNeurons.length];
				for (int j = 0; j < auxNeurons.length; j++) { /** Obtenemos el vector de neuronas de salida y creamos un vector con los valores de cada neurona otenida*/
					aux[j] = auxNeurons[j].getOutValue();
				}
				outputs.add(aux);
			}
		}
		return outputs;
	}
	
	
	
	
	
	
	
	
	
	
	

	

}
