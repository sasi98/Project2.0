package dataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JTextPane;

import org.apache.log4j.Logger;

import utilities.Matrix;

public class ReadFile {
	private String name; //Nombre archivo
	private File file; //Archivo
	private FileReader reader;
	private BufferedReader br;
	//private Matrix weightMatrix;
	private static final Logger log = Logger.getLogger(ReadFile.class);
	
	
	
	public ReadFile (File file) throws FileNotFoundException {
		super();
		this.file = file;
		reader = new FileReader (file);
		br = new BufferedReader(reader);
	}
	
	public ReadFile (String fileName) throws FileNotFoundException {
		super();
		this.name = fileName;
		reader = new FileReader(fileName);
		br = new BufferedReader(reader);
	}
	
	

	
	
	

	
	
//	public Matrix readWeightMatrix (){
//		ArrayList<ArrayList<BigDecimal>> arrayMatrix = new ArrayList<>();
//		String row;
//		int numRow = 0, numColum = 0;
//		try {
//			row = br.readLine();
//			while (row != null){
//				StringTokenizer st = new StringTokenizer (row,";" );
//				ArrayList<BigDecimal> arrayRow = new ArrayList<>();
//			//	System.out.println(row+ "\n");
//				while(st.hasMoreTokens()){  
//					String strValue = (String) st.nextElement();
//					strValue = strValue.replace(",", ".");
//					
////					System.out.print("Element "+ StrValue+  "Token: "+ StrValue2);
//					BigDecimal value = new BigDecimal(strValue);
//					arrayRow.add(value);
//				}
//			
//			arrayMatrix.add(arrayRow);
//			numColum = arrayRow.size(); 
//			row = br.readLine();
//			}
//			numRow = arrayMatrix.size();
//			
//			
//			System.out.print("Número de filas: " + numRow+ "Número de columnas: "+ numColum+ "\n");
//			//Pasar los valores del array de arrays a la matriz de pesos.
//			Matrix weightMatrix = new Matrix (new BigDecimal[numRow][numColum]);
//			for (int i = 0; i<weightMatrix.getRow(); i++){
//				System.out.print("mostrando i: " + i);
//				ArrayList<BigDecimal> aux = arrayMatrix.get(i);
//				
//				for (int j = 0; j < weightMatrix.getColumn(); j++){
//					weightMatrix.setValuePos(i, j,aux.get(j));
//				}
//			}
//			
//			weightMatrix.printMatrix();
//			return weightMatrix; 
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
		
			
			
		
		
			
		
		
	}
	
	
	
	
	