package test;

import java.io.FileNotFoundException;

import dataManager.ReadFile;

public class TestingReadFile {

	public void testReadMatrices(){
		ReadFile lector;
		try {
			lector = new ReadFile("C:\\repositoryGit\\Salidas\\matrices.csv");
			lector.readWeightMatrix();
			
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
