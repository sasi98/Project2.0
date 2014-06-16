package outsFiles;

import java.math.BigDecimal;

import utilities.Matrix;
import utilities.WeightMatrix;


//Clase que representa las salidas de pantalla de la ventana de entrenamiento de las redes creadas
//con capa oculta LN--> LayerHiddenNetwork

public class LNTrainingOuts extends WriteFile {

	public LNTrainingOuts(String fileName) {
		super(fileName);
	}
	
	public void previousInformation(String name, WeightMatrix matrices, double learningCnt, double momentB, String funcion){
		wr.append("Comenzando entrenamiento de la Red: "+name+"\n");
		wr.append("Coeficiente de aprendizaje: "+ learningCnt +"\n");
		if (momentB != 0){
			wr.append("Momento Beta: "+ momentB+"\n");
		}
		else{
			wr.append("No se ha utilizado momento B \n");
		}
		wr.append("Función de activación: "+funcion+"\n");
		wr.append("Matrices iniciales");
//		if (pathArchivo != "")
//			wr.append(" procedentes del archivo: "+ pathArchivo+"\n");
//		else
//			wr.append(" generadas de forma aletoria \n");
		writeMatrices(matrices);
		wr.append("\n");
		closeFile();
	}
	
	
	public void cancelledTraining (int it, BigDecimal error, WeightMatrix matrices){
		wr.append("El entrenamiento ha sido cancelado en la iteración: " + it+"\n");
		wr.append("Error resultante = " +error+"\n");
		wr.append("Matrices de pesos obtenidas: \n ");
		writeMatrices(matrices);
		wr.append("\n");
		closeFile();
	}
	
	public void finishedTrainingByMaxIt (int it, BigDecimal error, BigDecimal cota, WeightMatrix matrices){
		wr.append("El entrenamiento ha alcanzado el máximo número de iteraciónes permitidas "+it+"\n");
		wr.append("Error resultante = "+error+" Cota = "+cota+"\n");
		wr.append("Matrices de pesos obtenidas: \n");
		writeMatrices(matrices);
		wr.append("\n");
		closeFile();
	}
	
	public void finishedTrainingSuccessfully (int it, BigDecimal error, BigDecimal cota, WeightMatrix matrices){
		wr.append("El error ha alcanzado la cota en la iteración "+it+"\n");
		wr.append("Error = "+error+" Cota = "+cota+"\n");
		wr.append("Matrices de pesos obtenidas: \n");
		writeMatrices(matrices);
		closeFile();
	}

}
