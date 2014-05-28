package architecture;

import gui.MainWindow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;


//import dataManager.WriteOutcomes;
import utilities.Matrix;
import utilities.WeightMatrix;
import dataManager.WriteExcel;

public class NetworkManager {

	//Constantes que representan los valores máximos y minimos con los que se crean las matrices aletorias
	public static final int 		MATRIX_MAX = 10,
									MATRIX_MIN = -10;  //CNT por la que multiplican los valores del excel



	public static final int PRECISION = 10;



	public static final int CNT = 1000;
	
	
	
	private int 					numPatrones,
									numNeuronsES,  //Debido a la tipología de nuestra red Rm ligado a Ri el nº de neuronas de entradas
												  // siempre será el mismo que el nº de neuronas de salida.
									numNeuronsO;
	
	ArrayList<BigDecimal[]> 		inputs, 
									desiredOutputs;
	
	private boolean 				bias;
	private String 					name; //Lo usaremos para distingirla una vez creada.
	
	
	private static Logger log = Logger.getLogger(NetworkManager.class);
	
	public NetworkManager () {
		
	}
	
	//pre: inputs and desiredOutputs deben ser arrays válidos (creados por la clase PatronData, en la interfaz)
	public NetworkManager(String name, int numPatrones, int numNeuronsES, int numNeuronsO,ArrayList<BigDecimal[]> inputs,
				 ArrayList<BigDecimal[]> desiredOutputs, boolean bias) {
			
		super();
		log.debug ("Creando NetworkManager: "+ name +" Nº de Patrones: " + numPatrones + " Nº de neuronas de entrada "
			+ numNeuronsES + " Nº de neuronas de salida \n"+ numNeuronsES +" Nº de neuronas ocultas: "
		    + numNeuronsO + "Bias: "+ bias);
		this.name = name;
		this.numPatrones = numPatrones;
		this.numNeuronsES = numNeuronsES;
		this.numNeuronsO = numNeuronsO;
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;
		this.bias = bias;
	}
	
	
	//GETTERS
	
		
	public int getNumPatrones() {
		return numPatrones;
	}

	public String getName() {
		return name;
	}

	public int getNumNeuronsES() {
		return numNeuronsES;
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

	//SETTERS

	public void setNumPatrones(int numPatrones) {
		this.numPatrones = numPatrones;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setNumNeuronsES(int numNeuronsES) {
		this.numNeuronsES = numNeuronsES;
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
	
	
	public void training (int iterMax, BigDecimal cuote, double learningCNT, WeightMatrix matrices)
	{
		Matrix W = matrices.getW();
		Matrix V = matrices.getV();
		boolean end = false;
		int iteration = 0;
		
//		WriteOutcomes writer = new WriteOutcomes("C:\\repositoryGit\\Salidas\\training.txt", this); //Outcomes file
//		writer.closeFile();
		//WriteExcel writerByIteration = new WriteExcel ("C:\\repositoryGit\\Salidas\\resultsByIteration.csv"); //Outcomes file
		WriteExcel writerErrorProgress = new WriteExcel ("C:\\repositoryGit\\Salidas\\ErrorProgress.csv"); //Outcomes file
		WriteExcel writerMatrices = new WriteExcel ("C:\\repositoryGit\\Salidas\\MatricesObtenidas.csv"); //Outcomes file
		//writer.writeBasicInformation();
	
		
		while (!end){
			
//			String fileName = new String ("C:\\repositoryGit\\Salidas\\resultsByPatronIteration");
//			String strIteration = String.valueOf(iteration);
//			fileName = fileName + strIteration + ".csv";
//			WriteExcel writerByPatron = new WriteExcel (fileName);
			//WriteExcel writerByPatron = new WriteExcel ("empty");
			if(MainWindow.cancelTraining){ //Se cancela el entrenamiento, rompemos el bucle y cerramos ficheros
				writerMatrices.writeMatrices(new WeightMatrix(W, V));
				writerMatrices.closeFile();
				writerErrorProgress.closeFile();
				break;
			} 
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i),learningCNT, desiredOutputs.get(i), W, V, bias); //Establecemos la red con el patrón i
				subNetwork.train(i); 															 //La entrenamos
				W = subNetwork.getW ();																				 //after training, we get the matrix W and V
				V = subNetwork.getV ();
				log.debug("Valores de W y V tras actualización de matriz");
				W.printMatrix(); //logger prints
				V.printMatrix();
			}
			//writerByPatron.closeFile();

			//Comprobado con trazas hasta aquí, everything is working fine
			log.trace("Final del training de todas los patrones. Inicio del cálculo del error");
			
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i),learningCNT, desiredOutputs.get(i), W, V, bias);
				errorList.add (subNetwork.calculateError()); 
			}
		
			//Calculamos el error medio de la iteración 
			BigDecimal errorIt = new BigDecimal(0);
			for (BigDecimal error: errorList)
			{
				errorIt = errorIt.add(error);
			}
			errorIt = errorIt.divide(new BigDecimal(errorList.size()), RoundingMode.HALF_UP);
			errorIt = errorIt.setScale(PRECISION, RoundingMode.HALF_UP);
			
			writerErrorProgress.writeError (errorIt, iteration);
			log.debug("Error ponderado en la interacción "+ iteration + " es " + errorIt);
		
			if (iteration == iterMax){
				log.debug("LLegamos al límite de las iteraciones. Iteration: "+ iteration + " Máximo: "+ iterMax);
				end = true;
			}
			if (errorIt.compareTo(cuote)==1){
				log.debug("Error se pasa de la cota.");
			}
			else{
				end = true; 
				
			}
			//if ( (errorIt.compareTo(cuote) == 1) || (iteration == iterMax) ) // El error se pasa de la cota, o nº de iter = máximo
			//	end = true;
				
			//Escribir matrices W y V y error obtenido
			errorIt.setScale(PRECISION, RoundingMode.HALF_UP);
			//writerByIteration.writeOneIterationInf(iteration, errorIt, W, V);
			iteration++;
		
		} //fin while
		
		//Escribimos las matrices obtenidas
		writerMatrices.writeMatrices(new WeightMatrix(W, V));
		writerMatrices.closeFile();
		
		//writerByIteration.closeFile();
		writerErrorProgress.closeFile();
		
		//writer.closeFile();
		
	}
	
	//pre: this debe de ser una red válida, sus atributos no pueden ser nulos
	//Calcula los outputs resultantes con las entradas de la red (inputs)
	//returns: Array con los vectores los cuales se corresponden con los valores de la neuronas de salida de un patrón
	public ArrayList<BigDecimal[]> calculateOutputs (WeightMatrix matrices){
		log.info("Calculate Outputs");
		if ( (matrices.getW().getColumn() == numNeuronsES) && (matrices.getW().getColumn() == numNeuronsES) && 
				(matrices.getW().getRow() == numNeuronsO) && (matrices.getV().getColumn() == numNeuronsO) ) {
			ArrayList<BigDecimal[]> outputs = new ArrayList<>();
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				//We are not using learning constant, we ignore his value
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i),0.00001, desiredOutputs.get(i),
						matrices.getW(), matrices.getV(), bias);
				subNetwork.feedForward(); //Propagación hacia delante, se calculan las salidas
				OutputNeuron[] auxNeurons = subNetwork.getOutputLayer();
				BigDecimal[] aux = new BigDecimal[auxNeurons.length];  
				for (int j = 0; j< auxNeurons.length; j++){ //Obtenemos el vector de neuronas de salida, y pasamos sus valores a un vector
						aux[j] = auxNeurons[j].getOutValue();
				}
				outputs.add(aux);
			}
			return outputs;
		}
		else{
			log.error("La estructura de la red no coincide con las de las matrices seleccionadas."
					+ " No es posible calcular las salidas");
			return null;
		}
	
		
	}
	
	
	
	
	
	
	
	
	
	

}
