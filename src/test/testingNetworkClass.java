package test;

import java.awt.Dimension;
import java.math.BigDecimal;

import architecture.Network;
import utilities.Matrix;

public class testingNetworkClass {

	
	public void testCreateRandomMatrix (){
		int row = 2, column = 3;
		Dimension d = new Dimension(row, column); //ancho = nºfilas;  largo = nºcolumnas
		Matrix randomW = Matrix.createRandomMatrix(-10, 10, d, Network.PRECISION);
		randomW.printMatrix();
	}
	
	
	public void testSetUpNetworkANDFeedForward (){
		
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
		
		ne.setUpPatron(numNOcultas, inputLayer, desiredOutputsLayer, W, V);
		
		System.out.print ("FeedForward: \n");
		ne.feedForward();
		
		
		System.out.print ("Valores capa oculta: ");
		for (int i = 0; i< ne.getHiddenLayer().length; i++){
			System.out.print(ne.getHiddenLayer()[i].getOutValue()+ " ");
		}
		System.out.print (" \n Valores capa salida: ");
		for (int i = 0; i< ne.getOutputLayer().length; i++){
			System.out.print(ne.getOutputLayer()[i].getOutValue()+ "\n");
		}	
	
	}
	
	
	
	
	
	
	
	
	
	//Prueba correspondiente a los folios escaneados
	public static void main(String[] args){
		testingNetworkClass  testN = new testingNetworkClass();
		
		
		testN.testSetUpNetworkANDFeedForward(); //works!!
		
		
	
	
		
	
	
		
		
		
		
		
		
	}
	
	
}
