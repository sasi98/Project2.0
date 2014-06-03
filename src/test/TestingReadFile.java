package test;

import java.io.FileNotFoundException;

import utilities.WeightMatrix;
import dataManager.ReadFile;

public class TestingReadFile {

	public void testReadMatrices(){
		ReadFile lector;
		try {
			lector = new ReadFile("C:\\repositoryGit\\Salidas\\MatricesObtenidas.csv");
			WeightMatrix matrices = lector.readWeightMatrix();
			matrices.getW().printMatrixConsole();
			matrices.getV().printMatrixConsole();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	public TestingReadFile() {
		// TODO Auto-generated constructor stub
	}
	

	public static void main(String[] args) {
		TestingReadFile prueba = new TestingReadFile();
		prueba.testReadMatrices();
		
		
		
	}
	
	

}
