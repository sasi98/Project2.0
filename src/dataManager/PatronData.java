package dataManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

import jxl.read.biff.BiffException;
import architecture.Network;
import architecture.Neuron;


public class PatronData {
	
	private String idCompany; //Se lo pasamos desde la interfaz
	private static Company company;
	
	
	
	
	
	
	public PatronData (String idCompany) {
		super();
		this.idCompany = idCompany;
		try {
			ReadExcel file = new ReadExcel();
			this.company = file.readCompanyById(idCompany);
			
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	

	public Company getCompany() {
		return company;
	}




	public void setCompany(Company company) {
		this.company = company;
	}

	
	
	
	//numPatrones = num patrones que van a ser creados, 
	//entero que indica la posici�n desde la que se empiezan a seleccionar los patrones
	//cnt: cnst por la que se multiplicar�n los valores
	//Return: array con vectores de datos Rm solapados 
	public ArrayList<BigDecimal[]> createSolapadoArrayRm (int numPatrones, int since, int cnt, int numdays){
		if ((numPatrones+since+numdays-2) <= company.getQuoteDays()){ //Datos fuera de rango
			System.out.println("tope: "+ numPatrones+since+numdays+ "\n");
			ArrayList<BigDecimal[]> arrayRm = new ArrayList<BigDecimal[]>();
			int cont = since-1;
			Iterator<BigDecimal> it = company.getRms().listIterator(cont);
			while (it.hasNext() && cont < (numPatrones+since-1)){
				BigDecimal[] vD = createOrdenPatron (it, numdays, cnt);
				arrayRm.add(vD);
				cont++;
				it = company.getRms().listIterator(cont);
			}
			return arrayRm;
		}
		return null;
	}
	
	//numPatrones = num patrones que van a ser creados, 
		//entero que indica la posici�n desde la que se empiezan a seleccionar los patrones
		//cnt: cnst por la que se multiplicar�n los valores
	//Return: array con vectores de datos Ri solapados
		public ArrayList<BigDecimal[]> createSolapadoArrayRi (int numPatrones, int since, int cnt, int numdays){
			if ((numPatrones+since+numdays-2) <= company.getQuoteDays()){  //Datos fuera de rango
				ArrayList<BigDecimal[]> arrayRi = new ArrayList<BigDecimal[]>();
				int cont = since-1;
				Iterator<BigDecimal> it = company.getRis().listIterator(cont);
				while (it.hasNext() && cont < (numPatrones+since-1)){
					BigDecimal[] vD = createOrdenPatron (it, numdays, cnt);
					arrayRi.add(vD);
					cont++;
					it = company.getRis().listIterator(cont);
				}
				return arrayRi;
			}
			return null;
		}	
	
		
			//numPatrones = num patrones que van a ser creados, 
			//since:  entero que indica la posici�n desde la que se empiezan a seleccionar los patrones
				//El primer valor es el 1
			//cnt: cnst por la que se multiplicar�n los valores
			//Return: array con vectores de datos Rm no solapados 
		public ArrayList<BigDecimal[]> createNoSolapadoArrayRm (int numPatrones, int since, int cnt, int numdays){
			if (((numPatrones*numdays) + since-1) <= company.getQuoteDays()){ //Datos fuera de rango 
				ArrayList<BigDecimal[]> arrayRm = new ArrayList<BigDecimal[]>();
				Iterator<BigDecimal> it = company.getRms().listIterator(since-1);
				int limit = numPatrones*numdays, 
					cont = since-1;
				while (it.hasNext() && (cont < (limit + since-1))){
					BigDecimal[] vD = createOrdenPatron (it, numdays, cnt);
					arrayRm.add(vD);
					cont = cont + numdays;
				}
				return arrayRm;
			}
			return null;
		}
		
		public ArrayList<BigDecimal[]> createNoSolapadoArrayRi (int numPatrones, int since, int cnt, int numdays){
			if (((numPatrones*numdays) + since-1) <= company.getQuoteDays()){ //Datos fuera de rango 
				ArrayList<BigDecimal[]> arrayRi = new ArrayList<BigDecimal[]>();
				Iterator<BigDecimal> it = company.getRis().listIterator(since-1);
				int limit = numPatrones*numdays, 
						cont = since-1;
				while (it.hasNext() && (cont < (limit + since-1))){
					BigDecimal[] vD = createOrdenPatron (it, numdays, cnt);
					arrayRi.add(vD);
					cont = cont + numdays;
				}
				return arrayRi;
			}
			return null;
		}
		
		
		
		//Pre: Los patrones son seleccionados de forma aletoria de entre toda la tabla de datos 
		//Return: array de patrones de datos Rm aleatorios. 
		public ArrayList<BigDecimal[]> createRandomArrayRm (int numPatrones, int numdays, int cnt){
			ArrayList<BigDecimal[]> arrayRm = new ArrayList<BigDecimal[]>();
			for (int i = 0; i< numPatrones; i++){
				ArrayList<Integer> randomList = generateRandomList(company.getQuoteDays(), numdays);  
				arrayRm.add(createRandomPatron(company.getRms(), randomList, cnt));
			}
			return arrayRm;
		}
		
		public ArrayList<BigDecimal[]> createRandomArrayRi (int numPatrones, int numdays, int cnt){
			ArrayList<BigDecimal[]> arrayRm = new ArrayList<BigDecimal[]>();
			for (int i = 0; i< numPatrones; i++){
				ArrayList<Integer> randomList = generateRandomList(company.getQuoteDays(), numdays);  
				arrayRm.add(createRandomPatron(company.getRis(), randomList, cnt));
			}
			return arrayRm;
		}
		
		
	
		//it: iterador posicionado al inicio del array del q seleccionaremos los datos (rm o ri de la empresa)	
		//numdays = n�mero de d�as de rango a seleccionar 
		//cnt = constante por la q multiplicar los datos
		public BigDecimal[] createOrdenPatron (Iterator<BigDecimal> it, int numDays, int cnt){
			int cont = 0, 
				i = 0;
			BigDecimal [] dataVector = new BigDecimal[numDays];
			while(it.hasNext() && cont < numDays){
				BigDecimal b = it.next();
				BigDecimal aux = b.multiply(new BigDecimal (cnt));
				aux = aux.setScale (Network.PRECISION, RoundingMode.HALF_UP);
				dataVector[i] = aux;
				i++;
				cont++;
			}
			return dataVector;
		}
		
		
		//max: cota superior de los valores aletorios a generar
		//size: N� de aleatorios generados que tendr� la lista 
		//Return: ArrayList de tama�o "size" con valores aletorios NO repetidos de entre 0 y max-1
		public ArrayList<Integer> generateRandomList (int max, int size){
			ArrayList<Integer> listRandom = new ArrayList<>();
			while (listRandom.size() < size){
				int random = (int) (Math.random()*max);
				while (listRandom.contains(random))
					random = (int) (Math.random()*max);
				listRandom.add(random);
			}
			return listRandom;
		}
		
		
		//Los datos de las empresas deben de ser seleccionados a la par, por lo que en el caso de 
		//los aletorios les pasaremos el vector de aletorios previamente obtenido en generateRandomList
		//randomList.size = n�mero de d�as de rango a seleccionar
		//randomList = lista con las posiciones aleatorias q usaremos para obtener los valores de valuesR
		//valuesR = vector de datos Rm y Ri de la empresa
		//cnt = constante por la q multiplicar los datos
		public BigDecimal[] createRandomPatron (ArrayList<BigDecimal> valuesR, ArrayList<Integer> randomList, int cnt){
			BigDecimal[] dataVector = new BigDecimal[randomList.size()];
			int j = 0;
			for (int i: randomList){
				dataVector[j] = valuesR.get(i).multiply(new BigDecimal(100));
				j++;
			}
			return dataVector;
			}


		
		public void printPatrones (ArrayList<BigDecimal[]> arrayPatrones){
			for (BigDecimal[] v: arrayPatrones){
				System.out.print(" \n Patr�n N�: "+ arrayPatrones.indexOf(v)+"\n " );
				for (int i = 0; i<v.length; i++){
					System.out.print(v[i]+ " ");
				}
			}
		}
	
	
	
	

}
