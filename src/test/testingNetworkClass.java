package test;

import java.awt.Dimension;
import java.io.File;
import java.math.BigDecimal;

import org.apache.log4j.PropertyConfigurator;

import architecture.Connection;
import architecture.Network;
import architecture.NetworkManager;
import architecture.Neuron;
import utilities.Matrix;

public class testingNetworkClass {
	
	public testingNetworkClass() {
		PropertyConfigurator.configure("files"+File.separator+"log4j.properties");
	}
	
	

	public void testingCreateNetworkWithBias(){
			
		Network ne = new Network();
		
		System.out.print("Creamos matrices W y V \n");
		BigDecimal [][] auxW = new BigDecimal[3][4];
		auxW[0][0] = new BigDecimal(1); 
		auxW[0][1] = new BigDecimal(2);
		auxW[0][2] = new BigDecimal(4);
		auxW[0][3] = new BigDecimal(7);
		
		auxW[1][0] = new BigDecimal(7); 
		auxW[1][1] = new BigDecimal(3);
		auxW[1][2] = new BigDecimal(1);
		auxW[1][3] = new BigDecimal(5);
		
		auxW[2][0] = new BigDecimal(9); 
		auxW[2][1] = new BigDecimal(3);
		auxW[2][2] = new BigDecimal(8);
		auxW[2][3] = new BigDecimal(5);
			
		Matrix W = new Matrix(auxW);
		W.printMatrix();

		BigDecimal [][] auxV = new BigDecimal[3][3];
		auxV[0][0] = new BigDecimal(5); 
		auxV[0][1] = new BigDecimal(8);
		auxV[0][2] = new BigDecimal(2);
		
		auxV[1][0] = new BigDecimal(1);
		auxV[1][1] = new BigDecimal(4);
		auxV[1][2] = new BigDecimal(9);
		
		auxV[2][0] = new BigDecimal(2);
		auxV[2][1] = new BigDecimal(3);
		auxV[2][2] = new BigDecimal(6);
		
		Matrix V = new Matrix(auxV);
		
		V.printMatrix();
		int numNOcultas = 3;
		double learningCNT = 1;
		BigDecimal[] inputLayer = new BigDecimal[3];
		BigDecimal[] desiredOutputsLayer = new BigDecimal[3];
		System.out.print ("Establecemos los valores de las neuronas inputs y las salidas deseadas \n");
		
		inputLayer[0] = new BigDecimal(3);
		inputLayer[1] = new BigDecimal(7);
		inputLayer[2] = new BigDecimal(6);
		
		desiredOutputsLayer[0] = new BigDecimal(300);
		desiredOutputsLayer[1] = new BigDecimal(700);
		desiredOutputsLayer[2] = new BigDecimal(600);
		
		ne.setUpPatronWithBias (numNOcultas, inputLayer, learningCNT, desiredOutputsLayer, W, V);
			
		System.out.print("Mostrando inputs creados (it must contains bias):\n");
		for (int i = 0; i< ne.getInputLayer().length; i++){
			System.out.print(ne.getInputLayer()[i].getOutValue()+ " ");
		}
		
		System.out.print("Feed Forward \n");
		
		ne.feedForward();
		
		System.out.print("Mostrando capa oculta.\n");
		for (int i = 0; i< ne.getHiddenLayer().length; i++){
			System.out.print(ne.getHiddenLayer()[i].getOutValue()+ " ");
		}
		System.out.print("Mostrando capa de salida.\n");
		for (int i = 0; i< ne.getOutputLayer().length; i++){
			System.out.print(ne.getOutputLayer()[i].getOutValue()+ " ");
		}
		
		ne.train(0);
		ne.getW(); //Actualizadas
		ne.getV();
		Network ne2 = new Network(); 
		ne2.setUpPatronWithBias(numNOcultas, inputLayer, learningCNT, desiredOutputsLayer, ne.getW(), ne.getV());
		

	}
	
	
	
	
	
	
	public void testCreateRandomMatrix (){
		int row = 2, column = 3;
		Dimension d = new Dimension(row, column); //ancho = nºfilas;  largo = nºcolumnas
		Matrix randomW = Matrix.createRandomMatrix(-10, 10, d, NetworkManager.PRECISION);
		randomW.printMatrix();
	}
	
	
	public void testSetUpNetworkWithoutBias (){
		
		Network ne = new Network();
		
		System.out.print("Creamos matrices W y V \n");
		BigDecimal [][] auxW = new BigDecimal[2][3];
		auxW[0][0] = new BigDecimal(1); 
		auxW[0][1] = new BigDecimal(2);
		auxW[0][2] = new BigDecimal(4);
		
		auxW[1][0] = new BigDecimal(7); 
		auxW[1][1] = new BigDecimal(3);
		auxW[1][2] = new BigDecimal(1);
		
		Matrix W = new Matrix(auxW);
		
		W.printMatrix();

		BigDecimal [][] auxV = new BigDecimal[3][2];
		auxV[0][0] = new BigDecimal(5); 
		auxV[0][1] = new BigDecimal(8);
		
		auxV[1][0] = new BigDecimal(1);
		auxV[1][1] = new BigDecimal(4);
		
		auxV[2][0] = new BigDecimal(2);
		auxV[2][1] = new BigDecimal(3);
		
		Matrix V = new Matrix(auxV);
		
		V.printMatrix();
	
		BigDecimal[] inputLayer = new BigDecimal[3];
		BigDecimal[] desiredOutputsLayer = new BigDecimal[3];
		System.out.print ("Establecemos los valores de las neuronas inputs y las salidas deseadas (no las usaremos en esta prueba): \n");
		inputLayer[0] = new BigDecimal(3);
		inputLayer[1] = new BigDecimal(7);
		inputLayer[2] = new BigDecimal(6);
		
		desiredOutputsLayer[0] = new BigDecimal(300);
		desiredOutputsLayer[1] = new BigDecimal(700);
		desiredOutputsLayer[2] = new BigDecimal(600);
	
		int numNOcultas = 2, learningCNT = 1;
		ne.setUpPatronWithoutBias (numNOcultas, inputLayer, learningCNT, desiredOutputsLayer, W, V);
		
		System.out.print ("Valores de entrada: \n ");
		for (int i = 0; i< ne.getInputLayer().length; i++){
			System.out.print(ne.getInputLayer()[i].getOutValue()+ " ");
		}
		System.out.print ("\n Valores capa oculta (inicializados a 0): ");
		for (int i = 0; i< ne.getHiddenLayer().length; i++){
			System.out.print(ne.getHiddenLayer()[i].getOutValue()+ " ");
		}
		System.out.print (" \n Valores capa salida (inicializados a 0): ");
		for (int i = 0; i< ne.getOutputLayer().length; i++){
			System.out.print(ne.getOutputLayer()[i].getOutValue()+ "\n");
		}	
		
		
	}
	
	
	public void testUpdateConnections (){
		Network ne = new Network();
		
		System.out.print("Creamos matrices W y V \n");
		BigDecimal [][] auxW = new BigDecimal[2][3];
		auxW[0][0] = new BigDecimal(1); 
		auxW[0][1] = new BigDecimal(2);
		auxW[0][2] = new BigDecimal(4);
		
		auxW[1][0] = new BigDecimal(7); 
		auxW[1][1] = new BigDecimal(3);
		auxW[1][2] = new BigDecimal(1);
		
		Matrix W = new Matrix(auxW);
		
		W.printMatrix();

		BigDecimal [][] auxV = new BigDecimal[3][2];
		auxV[0][0] = new BigDecimal(5); 
		auxV[0][1] = new BigDecimal(8);
		
		auxV[1][0] = new BigDecimal(1);
		auxV[1][1] = new BigDecimal(4);
		
		auxV[2][0] = new BigDecimal(2);
		auxV[2][1] = new BigDecimal(3);
		
		Matrix V = new Matrix(auxV);
		
		V.printMatrix();
	
		int numNOcultas = 2;
		BigDecimal[] inputLayer = new BigDecimal[3];
		BigDecimal[] desiredOutputsLayer = new BigDecimal[3];
		System.out.print ("Establecemos los valores de las neuronas inputs y las salidas deseadas (no las usaremos en esta prueba): \n");
		inputLayer[0] = new BigDecimal(3);
		inputLayer[1] = new BigDecimal(7);
		inputLayer[2] = new BigDecimal(6);
		
		desiredOutputsLayer[0] = new BigDecimal(300);
		desiredOutputsLayer[1] = new BigDecimal(700);
		desiredOutputsLayer[2] = new BigDecimal(600);
		
		//ne.setUpPatron(numNOcultas, inputLayer, desiredOutputsLayer, W, V);
		
		System.out.print ("FeedForward: \n");
		ne.feedForward();
		
		System.out.print ("Mostramos las conexiones:  \n");
		for (Neuron n: ne.getHiddenLayer()){
			n.printConnections();
			
		}
		
		
		System.out.print("Creamos las nuevas matrices W y V \n");
		BigDecimal [][] auxW2 = new BigDecimal[2][3];
		auxW2[0][0] = new BigDecimal(8); 
		auxW2[0][1] = new BigDecimal(10);
		auxW2[0][2] = new BigDecimal(15);
		
		auxW2[1][0] = new BigDecimal(31); 
		auxW2[1][1] = new BigDecimal(22);
		auxW2[1][2] = new BigDecimal(12);
		
		Matrix W2 = new Matrix(auxW2);
		
		W2.printMatrix();

		BigDecimal [][] auxV2 = new BigDecimal[3][2];
		auxV2[0][0] = new BigDecimal(3); 
		auxV2[0][1] = new BigDecimal(4);
		
		auxV2[1][0] = new BigDecimal(7);
		auxV2[1][1] = new BigDecimal(8);
		
		auxV2[2][0] = new BigDecimal(9);
		auxV2[2][1] = new BigDecimal(10);
		
		Matrix V2 = new Matrix(auxV2);
		
		V2.printMatrix();
		
		ne.updateConnections(W2, V2);
		
		for (Neuron n: ne.getHiddenLayer()){
			n.printConnections();
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	public void testCalculateError (){
Network ne = new Network();
		
		System.out.print("Creamos matrices W y V \n");
		BigDecimal [][] auxW = new BigDecimal[2][3];
		auxW[0][0] = new BigDecimal(1); 
		auxW[0][1] = new BigDecimal(2);
		auxW[0][2] = new BigDecimal(4);
		
		auxW[1][0] = new BigDecimal(7); 
		auxW[1][1] = new BigDecimal(3);
		auxW[1][2] = new BigDecimal(1);
		
		Matrix W = new Matrix(auxW);
		
		W.printMatrix();

		BigDecimal [][] auxV = new BigDecimal[3][2];
		auxV[0][0] = new BigDecimal(5); 
		auxV[0][1] = new BigDecimal(8);
		
		auxV[1][0] = new BigDecimal(1);
		auxV[1][1] = new BigDecimal(4);
		
		auxV[2][0] = new BigDecimal(2);
		auxV[2][1] = new BigDecimal(3);
		
		Matrix V = new Matrix(auxV);
		
		V.printMatrix();
	
		int numNOcultas = 2;
		BigDecimal[] inputLayer = new BigDecimal[3];
		BigDecimal[] desiredOutputsLayer = new BigDecimal[3];
		System.out.print ("Establecemos los valores de las neuronas inputs y las salidas deseadas (no las usaremos en esta prueba): \n");
		inputLayer[0] = new BigDecimal(3);
		inputLayer[1] = new BigDecimal(7);
		inputLayer[2] = new BigDecimal(6);
		
		desiredOutputsLayer[0] = new BigDecimal(300);
		desiredOutputsLayer[1] = new BigDecimal(700);
		desiredOutputsLayer[2] = new BigDecimal(600);
		
		//ne.setUpPatron(numNOcultas, inputLayer,1, desiredOutputsLayer, W, V);
		
		
		System.out.print ("Suponemos que ha entrenado con todos los patrones, y las matrices obtenidas son: ");
		BigDecimal [][] auxW2 = new BigDecimal[2][3];
		auxW2[0][0] = new BigDecimal(-3); 
		auxW2[0][1] = new BigDecimal(-18);
		auxW2[0][2] = new BigDecimal(3);
		
		auxW2[1][0] = new BigDecimal(2); 
		auxW2[1][1] = new BigDecimal(10);
		auxW2[1][2] = new BigDecimal(30);
		
		Matrix W2 = new Matrix(auxW2);
		
		W2.printMatrix();

		BigDecimal [][] auxV2 = new BigDecimal[3][2];
		auxV2[0][0] = new BigDecimal(10); 
		auxV2[0][1] = new BigDecimal(1);
		
		auxV2[1][0] = new BigDecimal(-2);
		auxV2[1][1] = new BigDecimal(4);
		
		auxV2[2][0] = new BigDecimal(3);
		auxV2[2][1] = new BigDecimal(7);
		
		Matrix V2 = new Matrix(auxV2);
		
		V2.printMatrix();
		
		//ne.setUpPatron(numNOcultas, inputLayer,1, desiredOutputsLayer, W2, V2);
		
		
		System.out.print("Error obtenido: "+ ne.calculateError());
	
		
		
		
		
	}
	
	
	
	
	void testingUpdateWV (){
		
				
				Network ne = new Network();
				
				System.out.print("Creamos matrices W y V \n");
				BigDecimal [][] auxW = new BigDecimal[2][3];
				auxW[0][0] = new BigDecimal(1); 
				auxW[0][1] = new BigDecimal(2);
				auxW[0][2] = new BigDecimal(4);
				
				auxW[1][0] = new BigDecimal(7); 
				auxW[1][1] = new BigDecimal(3);
				auxW[1][2] = new BigDecimal(1);
				
				Matrix W = new Matrix(auxW);
				
				W.printMatrix();

				BigDecimal [][] auxV = new BigDecimal[3][2];
				auxV[0][0] = new BigDecimal(5); 
				auxV[0][1] = new BigDecimal(8);
				
				auxV[1][0] = new BigDecimal(1);
				auxV[1][1] = new BigDecimal(4);
				
				auxV[2][0] = new BigDecimal(2);
				auxV[2][1] = new BigDecimal(3);
				
				Matrix V = new Matrix(auxV);
				
				V.printMatrix();
			
				int numNOcultas = 2;
				BigDecimal[] inputLayer = new BigDecimal[3];
				BigDecimal[] desiredOutputsLayer = new BigDecimal[3];
				System.out.print ("Establecemos los valores de las neuronas inputs y las salidas deseadas (no las usaremos en esta prueba): \n");
				inputLayer[0] = new BigDecimal(3);
				inputLayer[1] = new BigDecimal(7);
				inputLayer[2] = new BigDecimal(6);
				
				desiredOutputsLayer[0] = new BigDecimal(300);
				desiredOutputsLayer[1] = new BigDecimal(700);
				desiredOutputsLayer[2] = new BigDecimal(600);
				
			//	ne.setUpPatron(numNOcultas, inputLayer, 0.001, desiredOutputsLayer, W, V);
				
	
				//ne.train();
																		 //La entrenamos
				W = ne.getW ();																				 //after training, we get the matrix W and V
				V = ne.getV ();
				
			W.printMatrix();
			V.printMatrix();
				
		
		
		
	}
	
	
	
	
	//Prueba correspondiente a los folios escaneados
	public static void main(String[] args){
		
		testingNetworkClass  testN = new testingNetworkClass();
		
		/** Probando  el SetUp sin bias*/
		//testN.testSetUpNetworkWithoutBias();
		
		/** Probando el SetUp con bias*/
		testN.testingCreateNetworkWithBias();
		
//		
//		System.out.print ("Probando el funcionamiento de punteros en java: \n");
//		
//		Neuron a = new Neuron(new BigDecimal(56), false);
//		
//		Neuron[] prueba1 = new Neuron[2];
//		Neuron[] prueba2 = new Neuron[2];
//		
//		prueba1[0] = a;
//		prueba2[0] = a;
//		
//		prueba2[0].setOutValue(new BigDecimal(23));
//		
//		System.out.print(prueba1[0].getOutValue() + "; "+ prueba2[0].getOutValue()); 
//	
		//testN.testUpdateConnections();
		//testN.testCalculateError();
		//testN.testSetUpNetworkANDFeedForward();
		//testN.testingUpdateWV(); //Funciona perfectamente
		//testN.testingCreateNetworkBias();
	
	
	
		
		
		
		
		
		
	}
	
	
}
