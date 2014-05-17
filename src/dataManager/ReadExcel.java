package dataManager;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;







public class ReadExcel { 
	
	private static final int LONG_COMPANY = 7; //Ancho de tabla de los datos de cada empresa
	private static final String DATA_BASE_NAME = "files\\dataExcel.xls";
	private static Workbook dataBase;
	public static ArrayList<Company> Companies = new ArrayList<Company>();
	private static Logger log = Logger.getLogger(ReadExcel.class);
	
	
	 public ReadExcel() throws BiffException, IOException {
			super();
			//PropertyConfigurator.configure("files\\log4j.properties");
			dataBase = Workbook.getWorkbook(new File(DATA_BASE_NAME));
		}
	
	 public Company readCompanyById (String idStr){
			log.info("Leyendo empresa con id: "+ idStr+ "\n");
			Company comp = null;
			Sheet hoja = dataBase.getSheet("VALUES");
			int id = Integer.parseInt(idStr);
			int posX = 1;
			int posY =  (id-1) * LONG_COMPANY + 1;
			String  idStr2, name, quoteStr, dataStr, riStr, rmStr;
			idStr2 = hoja.getCell(posX, posY).getContents();
			
			if (idStr.equals(idStr2)){
				name = hoja.getCell(posX+2, posY).getContents();
				quoteStr = hoja.getCell(posX+11, posY).getContents();
				int quote = Integer.parseInt(quoteStr);
				ArrayList<Date> dates = new ArrayList<Date>();
				ArrayList<BigDecimal> ris = new ArrayList<BigDecimal>();
				ArrayList<BigDecimal> rms = new ArrayList<BigDecimal>();
			
				for (int j = 2; j<=quote; j++) //Get dates
				{			
					dataStr = hoja.getCell(j, posY+1).getContents();
					Date d = stringToDate(dataStr);
					dates.add(d);
					
				}
				
				for (int j = 2; j<=quote; j++) //Get Ris
				{			
					riStr = hoja.getCell(j, posY+4).getContents();
					riStr = riStr.replace(",", ".");
					BigDecimal ri = new BigDecimal(riStr);
					ris.add(ri);
				}
				
				for (int j = 2; j<=quote; j++) //Get Rms
				{			
					rmStr = hoja.getCell(j, posY+5).getContents();
					rmStr = rmStr.replace(",", ".");
					BigDecimal rm = new BigDecimal(rmStr);
					rms.add(rm);
				}
				
				comp = new Company(idStr, name, quote, dates, ris,rms);
			}
			else{
				
				log.error("El identificador introducido "+ idStr + " no coincide con ninguno encontrado en el documento "+ DATA_BASE_NAME+ "\n");
			}
			  return comp;
		 }
	 
	  //Convert a string into a Date object
	 public static Date stringToDate (String dateStr){
	 	SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
	 	Date fecha = new Date();
	 	try {
	 	fecha = formatoDelTexto.parse(dateStr);

	 	} catch (ParseException ex) {

	 	ex.printStackTrace();

	 	}
	 	fecha.getDay();
	 	return fecha;
	 }
	   
	 
	 private void leerArchivoExcel(String archivoDestino) { 
			try {
				Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino)); 
				System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets()); 
				for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++){  //Recorre cada hoja 
					Sheet hoja = archivoExcel.getSheet(sheetNo); 
					int numColumnas = hoja.getColumns(); 
					int numFilas = hoja.getRows(); 
					String data; 
					System.out.println("Nombre de la Hoja\t" + archivoExcel.getSheet(sheetNo).getName()); 
					if (archivoExcel.getSheet(sheetNo).getName().equals("GS")){
						System.out.print("Reconoce nombre");
						for (int fila = 0; fila < numFilas; fila++) { // Recorre cada fila de la hoja
							for (int columna = 0; columna < numColumnas; columna++) { // Recorre cada columna
								data = hoja.getCell(columna, fila).getContents(); 
								System.out.print(data + " "); 
							} 
							System.out.println("\n"); 
						} 
					}
				}
			}
			catch (Exception ioe) { 
				ioe.printStackTrace(); 
			}	 
		} 
	 
	
	 //REHACER!
	 
	//Una vez se tengan todas, se pueden crear los diferentes grupos, usando el identificador
	
//  private void readVALUESheet (){
//	  PropertyConfigurator.configure("log4j.properties");
//      int cont = 0, quote = 0;
//	  Sheet hoja = dataBase.getSheet("VALUES");
//	  int numCol = hoja.getColumns(), numFil = hoja.getRows();  
//	  String id = "000", name, quoteStr,dataStr, rmStr, riStr;
//	  BigDecimal ri, rm;
//	  
//	  ArrayList<Date> dates;
//	  ArrayList<BigDecimal> ris, rms;
//	  
//      //Each 7 Rows, there is a new company
//	  for (int fila = 0; fila < numFil; fila++) { // Recorre cada fila de la hoja
//		  for (int columna = 0; columna < numCol; columna++) { // Recorre cada columna
//		    if (fila % 7 == 1) { //New company. Get id, name and quote (same row)
//		    	System.out.print("New company");
//		    	cont++;
//		      if(columna == 1){
//				  id = hoja.getCell(columna, fila).getContents();
//				  //System.out.print(id + " ");
//				  log.trace("ID:"+ id);
//		      }
//		      if(columna == 3){
//				  name = hoja.getCell(columna, fila).getContents();
//				  //System.out.print(name + " ");
//				  log.trace("Name:"+ name);
//		      }
//		      if (columna == 12){
//				  quoteStr = hoja.getCell(columna, fila).getContents();
//				  try {
//					quote = Integer.parseInt(quoteStr.trim());
//					System.out.print(quote + " ");
//					log.trace("Nº quote:"+ quote);
//				   } catch (NumberFormatException e) {
//			       //Will Throw exception!
//			       //do something! anything to handle the exception.
//				   } 
//		      }
//		    }
//									
//		    if ((columna >= 2) && (columna <= quote)){ //Rango para los datos: fecha, Ri, Rm 
//		    	log.trace("Pasa por el rango de los datos");
//		    	
//		    	if (fila%7 == 2){ //Get date
//		    		log.trace("Company:" + id + "Get date");
//		    		dataStr = hoja.getCell(columna, fila).getContents();
//		    		Date d = stringToDate(dataStr);
//		    		log.trace(d);
//		    		//dates.add(d);
//		    		
//		    	}
//		    	if (fila%7 == 5){ // Get Ri
//		    		log.trace("Company:" + id + "Get Ri");
//		    		riStr = hoja.getCell(columna, fila).getContents();
//		    		riStr = riStr.replace(",", ".");
//		    		ri = new BigDecimal(riStr);
//		    		//ris.add(ri);
//		    	log.trace(ri);
//		
//		    	}
//		    	if (fila%7 == 6){ // Get Rm
//		    		log.trace("Company:" + id + "Get Rm");
//		    		rmStr = hoja.getCell(columna, fila).getContents();
//		    		rmStr = rmStr.replace(",", ".");
//		    		rm = new BigDecimal(rmStr);
//		    		//rms.add(rm);
//		    	log.trace(rm);
//		    	}
//		    }
//		    	
//		    }
//		}
//		    
////		    else{
////		    	if ((fila%7 == 6) && (columna == quote)){ //Estamos al final, guardamos la company
////		    		cont++;
////		    		 Company comp = new Company(id, name, quote, dates, ris, rms); 
////		    		 Companies.add(comp);
////		    		 dates = new ArrayList<Date>();
////		    		 ris = new ArrayList<BigDecimal>();
////		    		 rms = new ArrayList<BigDecimal>();
////		    		 
////		    		 
////		    		
////		    	}
////
//					System.out.println("\n"); 		 
//		  System.out.print("VECES QUE GUARDA UNA EMPRESA:" + cont);
//
//	}
//	

			

	public static void main(String arg[]) throws BiffException, IOException { 
		
		/**PRUEBAS*/
	
//		excel.readVALUESheet("archivoPrueba.xls");
//		excel.leerArchivoExcel("archivoPrueba.xls"); //Versión antigua del excel (97-2003)
//		for (Company p: Companies){
//			p.printCompany();
//		}
//		Company p = Companies.get(23);
//		p.printCompany();
//		excel.readVALUESheet();
	
		
//		Iterator<BigDecimal> it = p.getRms().iterator();
//		Neuron n = createNeuronRm(p, it,10);
//		n.printNeuron();
//		
//		p.printCompany();
//		Date ini = new Date(2012, 9, 12);
//		Date fin = new Date(2012, 9, 22);
//		Neuron n = createNeuron("043", ini, fin, "rm");
//		n.printNeuron();
	} 
}







