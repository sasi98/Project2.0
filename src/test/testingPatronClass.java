package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;








import jxl.read.biff.BiffException;
import dataManager.*;
import architecture.*;

public class testingPatronClass {
	
	
	public testingPatronClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void testPatronDataConstructor(){
		PatronData p = new PatronData("002");
		p.getCompany().printCompany();
	}
	
	public void testGenerateRandom (){
		PatronData p = new PatronData("002");
		int max =  p.getCompany().getQuoteDays();
		System.out.println ("Probamos el generar lista de randoms:  \n");
		ArrayList<Integer> ale = p.generateRandomList(max, 75);
		for (Integer b: ale){
			System.out.println(b + "");
		}
		System.out.println ("Si todo es correcto, dar valores entre 0 y "+ max + " sin repetir "
				+ "Size: " + ale.size()+  "\n");
	}
	

	public void testPatronesSolapados (){
		PatronData patron = new PatronData("002");
		System.out.print("Creando patrones Rm solapados y de tamaño 8: \n");
		ArrayList<BigDecimal[]> arrayRm = patron.createSolapadoArrayRm(10, 7, 100, 60);
		patron.printPatrones(arrayRm);
		
		System.out.print("Creando patrones Ri solapados y de tamaño 8: \n");
		ArrayList<BigDecimal[]> arrayRi = patron.createSolapadoArrayRi(5, 1, 100, 8);
		patron.printPatrones(arrayRi);
	}
	
	
	public void testPatronesNoSolapados (){
		PatronData patron = new PatronData("002");
		System.out.print("Creando patrones Rm no solapados y de tamaño 8: \n");
		ArrayList<BigDecimal[]> arrayRm = patron.createNoSolapadoArrayRm(10, 6, 100, 7);
		patron.printPatrones(arrayRm);
		
		System.out.print("Creando patrones Ri no solapados y de tamaño 8: \n");
		ArrayList<BigDecimal[]> arrayRi = patron.createNoSolapadoArrayRi(5, 1, 100, 8);
		patron.printPatrones(arrayRi);
	}
	
	
	public void testPatronesAleatorios (){
		PatronData patron = new PatronData("002");
		System.out.print("Creando lista de listas de aleatorios: \n");
		ArrayList<ArrayList<Integer>> ale = patron.generateRandomLists(10, 8);
		
		for (ArrayList<Integer> list: ale){
			for (int numAle: list){
				System.out.print (numAle +" ");
			}
			System.out.print ("\n");
		}
		
		
		System.out.print("Creando 10 patrones Rm aletorios y de tamaño 8: \n");
		ArrayList<BigDecimal[]> arrayRm = patron.createRandomArrayRm(100, ale);
		patron.printPatrones(arrayRm);
		
		System.out.print("Creando 10 patrones Ri aleatorios y de tamaño 8: \n");
		ArrayList<BigDecimal[]> arrayRi = patron.createRandomArrayRi(100, ale);
		patron.printPatrones(arrayRi);
	
		
	}
	
	
	
	
	

	public static void main(String[] args) {
		
		testingPatronClass testP = new testingPatronClass();
	//	testP.testPatronesSolapados(); //Works
	//	testP.testPatronesNoSolapados(); //Works fine
		testP.testPatronesAleatorios();
	
		
		
//		PatronData patron = new PatronData("002");
//		patron.getCompany().printCompany();
//		ArrayList<Integer> randomlist = patron.generateRandomList(patron.getCompany().getQuoteDays(),8 );
//		for (int a: randomlist){
//			System.out.print(a + " ");
//			
//		}
//		BigDecimal[] prueba = patron.createRandomPatron(patron.getCompany().getRms(), randomlist, 100);
//		for (int i = 0; i<prueba.length; i++){
//			System.out.print(prueba[i]+ "\n");
//			
//		}
		
//		try {
//			
//			//Empty commit just for try
//			//second attempt
//			ReadExcel excel = new ReadExcel();
//			
//			System.out.print("Creamos un patrón de 8 neuronas de entrada. "
//					+ "Datos Rm procedentes de la empresa 002");
//			
//			Company comp =  excel.readCompanyById("002");
//			Iterator<BigDecimal> it = comp.getRms().listIterator(0);
//			Layer p = new Layer();
//			p.createLayer(it, 8, new BigDecimal(1000));
//			p.printPatron();
//			
//			System.out.print("Creamos un patrón de 5 neuronas de entrada, y una neurona bias. "
//					+ "Datos Rm procedentes de la empresa 002");
//			Iterator<BigDecimal> it2 = comp.getRms().listIterator(0);
//			Patron p2 = new Patron();
//			p2.createPatronWithBiasNeuron(it2, 5, new BigDecimal(1000));
//			p2.printPatron();
//				
//
//			}
//		catch (BiffException | IOException e) {
//			e.printStackTrace();
//		} 
//		
		
		
		
		
		
		
		
	}
	

}
