package outsFiles;

import java.math.BigDecimal;

import utilities.Matrix;


//Clase que representa las salidas de pantalla de la ventana de entrenamiento 
//de las redes creadas sin capa oculta 

public class SNTrainingOuts extends WriteExcel {

	public SNTrainingOuts(String fileName) {
		super(fileName);
	}
	
	
	public void previousInformation(String name, Matrix W, double learningCnt, double momentB, String funcion, String pathArchivo){
		wr.append("Comenzando entrenamiento de la Red: "+name+"\n");
		wr.append("Coeficiente de aprendizaje: "+ learningCnt +"\n");
		if (momentB != 0){
			wr.append("Momento Beta: "+ momentB+"\n");
		}
		else{
			wr.append("No se ha utilizado momento B \n");
		}
		wr.append("Función de activación: "+funcion+"\n");
		wr.append("Matriz inicial ");
		if (pathArchivo != "")
			wr.append(" procedente del archivo: "+ pathArchivo+"\n");
		else
			wr.append(" generada de forma aletoria \n");
		writeMatriz(W);
		wr.append("\n");
		closeFile();
	}
	
	public void cancelledTraining (int it, BigDecimal error, Matrix W){
		wr.append("El entrenamiento ha sido cancelado en la iteración: " + it+"\n");
		wr.append("Error resultante en la iteración = " +error+"\n");
		wr.append("Matriz de peso obtenida: \n ");
		writeMatriz(W);
		wr.append("\n");
		closeFile();
	}
	
	public void finishedTrainingByMaxIt (int it, BigDecimal error, BigDecimal cota, Matrix W){
		wr.append("El entrenamiento ha alcanzado el máximo número de iteraciónes permitidas "+it+"\n");
		wr.append("Error = "+error+" Cota = "+cota+"\n");
		wr.append("Matriz de peso obtenida: \n ");
		writeMatriz(W);
		closeFile();
	}
	
	public void finishedTrainingSuccessfully (int it, BigDecimal error, BigDecimal cota, Matrix W){
		wr.append("El error ha alcanzado la cota  en la iteración "+it+"\n");
		wr.append("Error = "+error+" Cota = "+cota+"\n");
		wr.append("Matriz de peso obtenida: \n ");
		writeMatriz(W);
		closeFile();
	}

}