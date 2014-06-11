package test;

import gui.TrainingWindow;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import utilities.Matrix;





public class TestingMathFuntion {

	
	
	public TestingMathFuntion() {
		// TODO Auto-generated constructor stub
	}
	
	public static double derivativeFunction(final double x) {
		  return (1.0 - x * x);
		}
	public static void main(String[] args){
//		double x = 5;
//		double tang = Math.tanh(x);
//		System.out.print("Tangencial hiperbólica: "+ tang);
//		//System.out.print ("Derivada de la tangencial: "+ Math.);
//		derivativeFunction (x);
//		System.out.print("derivada "+ derivativeFunction (x));
//		String fileName = "C:\\repositoryGit\\Salidas\\"+ TrainingWindow.getCurrentTimeStamp()+ " ";
//		File directory = new File(fileName);
//		try{
//			boolean creado = directory.mkdir();
//			System.out.print("creado: "+ creado+"\n");
//		}
//		catch(SecurityException se){
//			
//		}
		
//		BigDecimal [][] auxW = new BigDecimal[3][3];
//		auxW[0][0] = new BigDecimal(1); 
//		auxW[0][1] = new BigDecimal(2);
//		auxW[0][2] = new BigDecimal(4);
//		
//		auxW[1][0] = new BigDecimal(7); 
//		auxW[1][1] = new BigDecimal(3);
//		auxW[1][2] = new BigDecimal(1);
//		
//		auxW[2][0] = new BigDecimal(9); 
//		auxW[2][1] = new BigDecimal(3);
//		auxW[2][2] = new BigDecimal(1);
//			
//		Matrix W = new Matrix(auxW);
//		W.printMatrixConsole();
//		
//		System.out.print("Obtenemos el máximo de la diagonal "+ W.getMaxDiagonal()+"\n");
//		
		
		
		System.out.print("Probando el crear matriz from array de vectores\n ");
		BigDecimal [] auxW = new BigDecimal[3];
		BigDecimal [] auxW2 = new BigDecimal[3];
		BigDecimal [] auxW3 = new BigDecimal[3];
		
		auxW[0] = new BigDecimal(1); 
		auxW[1] = new BigDecimal(2);
		auxW[2] = new BigDecimal(4);
		
		auxW2[0] = new BigDecimal(7); 
		auxW2[1] = new BigDecimal(3);
		auxW2[2] = new BigDecimal(1);
		
		auxW3[0] = new BigDecimal(9); 
		auxW3[1] = new BigDecimal(3);
		auxW3[2] = new BigDecimal(1);
		
		
		ArrayList<BigDecimal[]> prueba = new ArrayList<>();
		prueba.add(auxW);
		prueba.add(auxW2);
		prueba.add(auxW3);
		
		Matrix matrixInputs = Matrix.createMatrixFromArrayOfVectors(prueba);
	
		matrixInputs.printMatrixConsole();
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	

}
