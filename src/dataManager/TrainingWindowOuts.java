package dataManager;

import java.math.BigDecimal;

import utilities.Matrix;
import utilities.WeightMatrix;


//Clase que representa las salidas de pantalla de la ventana de entrenamiento. 

public class TrainingWindowOuts extends WriteExcel {

	public TrainingWindowOuts(String fileName) {
		super(fileName);
	}
	
	public void previousInformation(String name, WeightMatrix matrices){
		wr.append("Comenzando entrenamiento de la Red: "+name);
		wr.append("Matrices iniciales: \n");
		writeMatrices(matrices);
		closeFile();
	}

	
	public void cancelledTraining (int it, BigDecimal error, WeightMatrix matrices){
		wr.append("El entrenamiento ha sido cancelado en la iteración: " + it+"\n");
		wr.append("Error resultante en la iteración = " +error+"\n");
		wr.append("Matrices de pesos obtenidas: \n ");
		writeMatrices(matrices);
		closeFile();
	}
	
	public void finishedTrainingByMaxIt (int it, BigDecimal error, BigDecimal cota, WeightMatrix matrices){
		wr.append("El entrenamiento ha alcanzado el máximo número de iteraciónes permitidas "+it+"\n");
		wr.append("Error = "+error+" Cota = "+cota+"\n");
		wr.append("Matrices de pesos obtenidas: \n");
		writeMatrices(matrices);
		closeFile();
	}
	
	public void finishedTrainingSuccessfully (int it, BigDecimal error, BigDecimal cota, WeightMatrix matrices){
		wr.append("El error ha alcanzado la cota  en la iteración "+it+"\n");
		wr.append("Error = "+error+" Cota = "+cota+"\n");
		wr.append("Matrices de pesos obtenidas: \n");
		writeMatrices(matrices);
		closeFile();
	}

}
