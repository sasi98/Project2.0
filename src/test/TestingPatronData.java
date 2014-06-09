package test;

import java.math.BigDecimal;
import java.util.ArrayList;

import dataManager.PatronData;
import architecture.NetworkManager;

public class TestingPatronData {
	
	
	public TestingPatronData() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void testRandom(){
		PatronData manejador = new PatronData("002");
		int numNeuronES = 6, numPatrones = 8;
		ArrayList<ArrayList<Integer>> randomLists = manejador.generateRandomLists(numPatrones, numNeuronES); 
		ArrayList<BigDecimal[]> inputs = manejador.createRandomArrayRm(NetworkManager.CNT,
				randomLists);
		for (ArrayList<Integer> list: randomLists){
			for (int i: list){
				System.out.print(i+" ");
			}
			System.out.print(" \n");
		}
		ArrayList<BigDecimal[]> outputs = manejador.createRandomArrayRi(
				NetworkManager.CNT, randomLists);
		manejador.printPatrones(inputs);
		manejador.printPatrones(outputs);
	}
	
	public static void main(String[] args) {
		TestingPatronData prueba = new TestingPatronData();
		prueba.testRandom();

		
	}
	


}
