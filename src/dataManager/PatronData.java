package dataManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.PropertyConfigurator;

import jxl.read.biff.BiffException;
import architecture.Network;
import architecture.NetworkManager;
import architecture.Neuron;


public class PatronData {
	
	private String idCompany; //Se lo pasamos desde la interfaz
	private static Company company;
	
	
	
	
	
	
	public PatronData (String idCompany) {
		super();
		//PropertyConfigurator.configure("files\\log4j.properties");
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
	//entero que indica la posición desde la que se empiezan a seleccionar los patrones
	//cnt: cnst por la que se multiplicarán los valores
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
		//entero que indica la posición desde la que se empiezan a seleccionar los patrones
		//cnt: cnst por la que se multiplicarán los valores
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
			//since:  entero que indica la posición desde la que se empiezan a seleccionar los patrones
				//El primer valor es el 1
			//cnt: cnst por la que se multiplicarán los valores
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
		
		
		
		// Los patrones deben ser seleccionados de forma aletoria de entre toda la tabla de datos, 
		//rm  y ri van siempre a la par, por lo q en el caso de los aletorios hay q usar la misma 
		//lista de aleatorios para seleccionarlos
		//numPatrones= nº de listas de aletorios a generar
		//numdays= nº de valores aleatorios por patrón
		//Return: Lista de listas de enteros correspondientes a los aletorios generados para la selección de los patrones 
		
		public ArrayList<ArrayList<Integer>> generateRandomLists (int numPatrones, int numdays){
			ArrayList<ArrayList<Integer>> randoms = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i< numPatrones; i++){
				ArrayList<Integer> randomList = generateRandomList(company.getQuoteDays(), numdays);
				randoms.add(randomList);
				
				//map.put(createRandomPatron(company.getRms(), randomList, cnt), createRandomPatron(company.getRis(), randomList, cnt));
			}
			return randoms;
		}
		
		
		//Pre:  generateRandomLists debe ser ejecutado
		//cnt: cnt por la que multiplicaremos los valores
		//randomList: lista de listas que contiene los aleatorios (corrrespondientes a los índices) de los valores que debemos seleccionar
		//return: array de patrones Rm aleatorios
		public ArrayList<BigDecimal[]> createRandomArrayRm (int cnt, ArrayList<ArrayList<Integer>> randomLists){
			ArrayList<BigDecimal[]> arrayRm = new ArrayList<BigDecimal[]>();
			for (ArrayList<Integer> randomList: randomLists){
					arrayRm.add(createRandomPatron(company.getRms(), randomList, cnt));
				}
			return arrayRm;
		}
			
		
		//Pre:  generateRandomLists debe ser ejecutado
		//cnt: cnt por la que multiplicaremos los valores
		//randomList: lista de listas que contiene los aleatorios (corrrespondientes a los índices) de los valores que debemos seleccionar
		//return: array de patrones Ri aleatorios		
		public ArrayList<BigDecimal[]> createRandomArrayRi (int cnt, ArrayList<ArrayList<Integer>> randomLists){
			ArrayList<BigDecimal[]> arrayRi = new ArrayList<BigDecimal[]>();
			for (ArrayList<Integer> randomList: randomLists){
				arrayRi.add(createRandomPatron(company.getRis(), randomList, cnt));
				}
			return arrayRi;
		}
		
		
		
		
	
		
	
		//it: iterador posicionado al inicio del array del q seleccionaremos los datos (rm o ri de la empresa)	
		//numdays = número de días de rango a seleccionar 
		//cnt = constante por la q multiplicar los datos
		public BigDecimal[] createOrdenPatron (Iterator<BigDecimal> it, int numDays, int cnt){
			int cont = 0, 
				i = 0;
			BigDecimal [] dataVector = new BigDecimal[numDays];
			while(it.hasNext() && cont < numDays){
				BigDecimal b = it.next();
				BigDecimal aux = b.multiply(new BigDecimal (cnt));
				aux = aux.setScale (NetworkManager.PRECISION, RoundingMode.HALF_UP);
				dataVector[i] = aux;
				i++;
				cont++;
			}
			return dataVector;
		}
		
		
		//max: cota superior de los valores aletorios a generar
		//size: Nº de aleatorios generados que tendrá la lista 
		//Return: ArrayList de tamaño "size" con valores aletorios NO repetidos de entre 0 y max-1
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
		//randomList.size = número de días de rango a seleccionar
		//randomList = lista con las posiciones aleatorias q usaremos para obtener los valores de valuesR
		//valuesR = vector de datos Rm y Ri de la empresa
		//cnt = constante por la q multiplicar los datos
		public BigDecimal[] createRandomPatron (ArrayList<BigDecimal> valuesR, ArrayList<Integer> randomList, int cnt){
			BigDecimal[] dataVector = new BigDecimal[randomList.size()];
			int j = 0;
			for (int i: randomList){
				dataVector[j] = valuesR.get(i).multiply(new BigDecimal(cnt));
				j++;
			}
			return dataVector;
			}


		
		public void printPatrones (ArrayList<BigDecimal[]> arrayPatrones){
			for (BigDecimal[] v: arrayPatrones){
				System.out.print(" \n Patrón Nº: "+ arrayPatrones.indexOf(v)+"\n " );
				for (int i = 0; i<v.length; i++){
					System.out.print(v[i]+ " ");
				}
			}
		}
	
	
	
	

}
