package architecture;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dataManager.WriteOutcomes;
import utilities.Matrix;

public class NetworkManager {
	
	
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
	
	private BigDecimal 				cuote,
									learningCNT;
	
	ArrayList<BigDecimal[]> 		inputs, 
									desiredOutputs;
	
	
	private static Logger log = Logger.getLogger(NetworkManager.class);
	
	public NetworkManager () {
		
	}
	
	//pre: inputs and desiredOutputs deben ser arrays válidos (creados por la clase PatronData, en la interfaz)
		public NetworkManager(int numPatrones, int numNeuronsES, int numNeuronsO, int iterMax,
				BigDecimal cuote, BigDecimal learningCNT,
				ArrayList<BigDecimal[]> inputs, ArrayList<BigDecimal[]> desiredOutputs) {
			
			
			super();
			log.debug ("Creando NetworkManager. Nº de Patrones: " + numPatrones + " Nº de neuronas de entrada "
					+ numNeuronsES + " Nº de neuronas de salida "+ numNeuronsES +" Nº de neuronas ocultas: "
				    + numNeuronsO + " Cota de error: " + cuote );
			
			this.numPatrones = numPatrones;
			this.numNeuronsES = numNeuronsES;
			this.numNeuronsO = numNeuronsO;
			this.iterMax = iterMax;
			this.cuote = cuote;
			this.learningCNT = learningCNT;
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
	
	public BigDecimal getLearningCNT() {
		return learningCNT;
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

	public void setLearningCNT(BigDecimal learningCNT) {
		this.learningCNT = learningCNT;
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
		Matrix W = Matrix.createRandomMatrix(MATRIX_MIN, MATRIX_MIN, dW, PRECISION);
		Dimension dV = new Dimension(this.numNeuronsES, this.numNeuronsO);
		Matrix V = Matrix.createRandomMatrix(MATRIX_MIN, MATRIX_MAX, dV, PRECISION);
		boolean end = false;
		int iteration = 0;
		
		WriteOutcomes writer = new WriteOutcomes("C:\\repositoryGit\\Salidas\\training.txt", this); //Outcomes file
		
		writer.writeBasicInformation();
		writer.closeFile();
		
		while (!end){
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i), desiredOutputs.get(i), W, V); //Establecemos la red con el patrón i
				subNetwork.train(); 															 //La entrenamos
				W = subNetwork.getW ();																				 //after training, we get the matrix W and V
				V = subNetwork.getV ();
			}

			ArrayList<BigDecimal> errorList = new ArrayList<BigDecimal>();
			for (int i = 0; i<inputs.size(); i++){
				Network subNetwork = new Network();
				subNetwork.setUpPatron(numNeuronsO, inputs.get(i), desiredOutputs.get(i), W, V);
				errorList.add (subNetwork.calculateError()); 
			}
		
			//Calculamos el error medio de la iteración 
			BigDecimal errorIt = new BigDecimal(0);
			for (BigDecimal error: errorList)
			{
				errorIt = errorIt.add(error);
			}
			errorIt = errorIt.divide(new BigDecimal(errorList.size()), RoundingMode.HALF_UP);
		
		
			if ( (errorIt.compareTo(cuote) == 1) || (iteration == iterMax) ) // El error se pasa de la cota, o nº de iter = máximo
				end = true;
		
			iteration++;
		
		} //fin while
		
		writer.closeFile();
		
	}
	
	
	
	
	
	
	
	
	

}
