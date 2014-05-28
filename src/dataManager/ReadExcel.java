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


public class ReadExcel { 
	
	private static final int LONG_COMPANY = 7; //Ancho de tabla de los datos de cada empresa
	private static final String DATA_BASE_NAME = "files\\dataExcel.xls";
	private static Workbook dataBase;
	private static Logger log = Logger.getLogger(ReadExcel.class);
	
	
	 public ReadExcel() throws BiffException, IOException {
			super();
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
				
				comp = new Company(idStr, name, quote-1, dates, ris,rms);
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
	 
}
	   
	 
	 
