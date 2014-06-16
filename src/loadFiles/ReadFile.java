package loadFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

import utilities.Matrix;
import utilities.WeightMatrix;


public class ReadFile {

	protected String name; //Nombre archivo
	protected File file; //Archivo
	protected FileReader reader;
	protected BufferedReader br;
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
		
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
	//pre: Fichero debe de contener el formato adecuado. Las matrices deben de ser escritas en el orden 
	//W, V y van precededidas por una linea con el formato: "Matrix W/V:" fichero con extensión .csv
	//Return WeightMatrix (clase que contiene las matrices de pesos de la red) leido del fichero
	public WeightMatrix readWeightMatrix (){
		ArrayList<ArrayList<BigDecimal>> arrayMatrix = new ArrayList<>();
		Matrix W = null, V = null;
		String row;
		try {
			row = br.readLine();
			while (row != null){
				if (row.equals("Matrix W:") ){
					row = br.readLine();
				}
				if (row.equals("Matrix V:")){     //Guardamos Matrix W
					W = Matrix.createMatrixFromArrays(arrayMatrix);
					row = br.readLine(); //Inicializamos valores para leer V
					arrayMatrix = new ArrayList<>();
					}
						
				StringTokenizer st = new StringTokenizer (row,";" );
				ArrayList<BigDecimal> arrayRow = new ArrayList<>();
				while(st.hasMoreTokens()){  
					String strValue = (String) st.nextElement();
					strValue = strValue.replace(",", ".");
					BigDecimal value = new BigDecimal(strValue);
					arrayRow.add(value);
				}
				arrayMatrix.add(arrayRow);
			row = br.readLine();
			}			
			V = Matrix.createMatrixFromArrays(arrayMatrix);
			return new WeightMatrix(W, V); 
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
		//Lee la matrix de pesos 
	public Matrix readSingleWeightMatrix (){
		ArrayList<ArrayList<BigDecimal>> arrayMatrix = new ArrayList<>();
		String row;
		try {
			row = br.readLine();
			while (row != null){
				if (row.equals("Matrix W:") ){
					row = br.readLine();
				}
				StringTokenizer st = new StringTokenizer (row,";" );
				ArrayList<BigDecimal> arrayRow = new ArrayList<>();
				while(st.hasMoreTokens()){  
					String strValue = (String) st.nextElement();
						strValue = strValue.replace(",", ".");
					BigDecimal value = new BigDecimal(strValue);
					arrayRow.add(value);
				}
			arrayMatrix.add(arrayRow); 
			row = br.readLine();
			}
			Matrix weightMatrix = Matrix.createMatrixFromArrays(arrayMatrix);
			return weightMatrix; 
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
		
				
				
			
			
				
}
