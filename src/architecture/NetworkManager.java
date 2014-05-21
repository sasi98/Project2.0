package architecture;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dataManager.WriteExcel;
import dataManager.WriteOutcomes;
import utilities.Matrix;

public class NetworkManager {
	
	
	public double getLearningCNT() {
		return learningCNT;
	}

	public void setLearningCNT(double learningCNT) {
		this.learningCNT = learningCNT;
	}



	//Constantes que representan los valores máximos y minimos con los que se crean las matrices aletorias
	private static final int 		MATRIX_MAX = 10,
									MATRIX_MIN = -10;  //CNT por la que multiplican los valores del excel



	public static final int PRECISION = 10;



	public static final int CNT = 1000;
	
	
	
	private int 					numPatrones,
									numNeuronsES,  //Debido a la tipología de nuestra red Rm ligado a Ri el nº de neuronas de entradas
												  // siempre será el mismo que el nº de neuronas de salida.
									numNeuronsO,
									iterMax;
	
	private BigDecimal 				cuote;
	private double 					learningCNT;
	
	ArrayList<BigDecimal[]> 		inputs, 
									desiredOutputs;
	
	
	private static Logger log = Logger.getLogger(NetworkManager.class);
	
	public NetworkManager () {
		
	}
	
	//pre: inputs and desiredOutputs deben ser arrays válidos (creados por la clase PatronData, en la interfaz)
		public NetworkManager(int numPatrones, int numNeuronsES, int numNeuronsO, int iterMax,
				BigDecimal cuote, double learningCNT ,ArrayList<BigDecimal[]> inputs,
				 ArrayList<BigDecimal[]> desiredOutputs) {
			
			super();
			log.debug ("Creando NetworkManager. Nº de Patrones: " + numPatrones + " Nº de neuronas de entrada "
					+ numNeuronsES + " Nº de neuronas de salida \n"+ numNeuronsES +" Nº de neuronas ocultas: "
				    + numNeuronsO + " Cota de error: " + cuote + "coeficiente de aprendizaje: "+ learningCNT + 
				    "\n Número máximo de iteracciones permitidas: " + iterMax);
			
			this.numPatrones = numPatrones;
			this.numNeuronsES = numNeuronsES;
			this.numNeuronsO = numNeuronsO;
			this.iterMax = iterMax;
			this.learningCNT = learningCNT;
			this.cuote = cuote;
			this.inputs = inputs;
			this.desiredOutputs = desiredOutputs;
		}
	

	//GETTERS
		
	public int getNumPatrones() {
		return numPatrones;
	}

	public int getNumNeuronsES() {
		return numNeuronsES;
	}
	
	public int getNumNeuronsO() {
		return numNeuronsO;
	}

	public int getIterMax() {
		return iterMax;
	}
	
	public BigDecimal getCuote() {
		return cuote;
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

	public void setNumNeuronsES(int numNeuronsES) {
		this.numNeuronsES = numNeuronsES;
	}

	public void setNumNeuronsO(int numNeuronsO) {
		this.numNeuronsO = numNeuronsO;
	}

	public void setIterMax(int iterMax) {
		this.iterMax = iterMax;
	}

	public void setCuote(BigDecimal cuote) {
		this.cuote = cuote;
	}

	public void setInputs(ArrayList<BigDecimal[]> inputs) {
		this.inputs = inputs;
	}

	public void setDesiredOutputs(ArrayList<BigDecimal[]> desiredOutputs) {
		this.desiredOutputs = desiredOutputs;
	}
	
	
	
	public void training ()
	{
		//Creamos las matrices W y V de forma aleatoria. 
		Dimension dW = new Dimension(this.numNeuronsO, this.numNeuronsES);  
		Matrix W = Matrix.createRandomMatrix(MATRIX_MIN, MATRIX_MAX, dW, PRECISION);
		Dimension dV = new Dimension(this.numNeuronsES, this.numNeuronsO);
		Matrix V = Matrix.createRandomMatrix(MATRIX_MIN, MATRIX_MAX, dV, PRECISION);
		boolean end = false;
		int iteration = 0;
		
		WriteOutcomes writer = new WriteOutcomes("C:\\repositoryGit\\Salidas\\training.txt", this); //Outcomes file
		WriteExcel writerByIteration = new WriteExcel ("C:\\repositoryGit\\Salidas\\resultsByIteration.csv"); //Outcomes file
		writer.writeBasicInformation();
		writer.closeFile();
		
		while (!end){
			
			String fileName = new String ("C:\\repositoryGit\\Salidas\\resultsByPatronIteration");
			String strIteration = String.valueOf(iteration);
			fileName = fileName + strIteration + ".csv";
			WriteExcel writerByPatron = new WriteExcel (fileName);
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i),learningCNT, desiredOutputs.get(i), W, V); //Establecemos la red con el patrón i
				subNetwork.train(writerByPatron); 															 //La entrenamos
				W = subNetwork.getW ();																				 //after training, we get the matrix W and V
				V = subNetwork.getV ();
				log.debug("Valores de W y V tras actualización de matriz");
				W.printMatrix();
				V.printMatrix();
			}
			writer.closeFile();

			//Comprobado con trazas hasta aquí, everything is working fine
			log.trace("Final del training de todas los patrones. Inicio del cálculo del error");
			
			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i),learningCNT, desiredOutputs.get(i), W, V);
				errorList.add (subNetwork.calculateError()); 
			}
		
			//Calculamos el error medio de la iteración 
			BigDecimal errorIt = new BigDecimal(0);
			for (BigDecimal error: errorList)
			{
				errorIt = errorIt.add(error);
			}
			errorIt = errorIt.divide(new BigDecimal(errorList.size()), RoundingMode.HALF_UP);
			
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
			writerByIteration.writeOneIterationInf(iteration, errorIt, W, V);
			iteration++;
		
		} //fin while
		writerByIteration.closeFile();
		
		writer.closeFile();
		
	}
	
	
	
	
	
	
	
	
	

}
